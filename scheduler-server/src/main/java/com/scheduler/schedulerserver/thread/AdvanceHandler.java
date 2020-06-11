package com.scheduler.schedulerserver.thread;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scheduler.schedulerserver.constant.ManagerServerConstants;
import com.scheduler.schedulerserver.domain.xml.DataTemplate;
import com.scheduler.schedulerserver.domain.xml.Model;
import com.scheduler.schedulerserver.domain.xml.ShareData;
import com.scheduler.schedulerserver.dto.ExDataDTO;
import com.scheduler.schedulerserver.dto.OutputDataDTO;
import com.scheduler.schedulerserver.dto.ServicesMapping;
import com.scheduler.utils.IPUtils;
import com.scheduler.utils.MyHttpUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 针对于对应的Model节点的操作
 * @Author: wangming
 * @Date: 2019-12-03 16:14
 */
public class AdvanceHandler {

    private List<Model> modelList;
    private String userName;

    /*
     * sharedMap主要用来存储该任务所用到的数据
     */
    private volatile ConcurrentHashMap<String, ShareData> sharedMap;

    public AdvanceHandler(List<Model> modelList, ConcurrentHashMap<String, ShareData> sharedMap, String userName){
        this.modelList = modelList;
        this.sharedMap = sharedMap;
        this.userName = userName;
    }

    public Model runModel(int index,ServicesMapping servicesMapping)throws InterruptedException{
        while (true){
            /*
             * TODO check error,
             * 目前是当发现有模型运行出错了的话，就直接返回该节点
             * 而实际过程中因为提交的任务有可能之间并无依赖关系，所以并不能因为一个模型的运行出错导致整个任务的终结
             */
            if(checkError()){
                return this.modelList.get(index);
            }
            //构建用来判断数据是否准备完好的变量
            boolean status = false;
            //分段加锁，在判断完数据准备完毕之后，开启模型调用流程
            synchronized (this.sharedMap){
                List<DataTemplate> inputData = this.modelList.get(index).getInputData().getInputs();
                //检查该模型需要运行的数据是否已经准备好
                if(checkData(inputData)){
                    status = true;
                }else{
                    this.sharedMap.wait();
                }
            }
            //只有数据准备完毕之后才会进入到这里
            if (status){
                //根据modelSerUrl直接和特定的TaskServer通信，避免再从Manager Server转发一次
                String pid = this.modelList.get(index).getPid();
                String modelSerUrl = this.modelList.get(index).getModelServiceUrl();
                //TODO 从url中获取task Server的ip和port(例如http://172.21.212.119:8061)
                String taskIpAndPort = IPUtils.getIPAndPort(URI.create(modelSerUrl)).toString();
                List<ExDataDTO> inputs = TemplateToExData(this.modelList.get(index).getInputData().getInputs());
                JSONObject params = new JSONObject();
                String inputsArray = convertItems2JSON(inputs);
                params.put("pid",pid);
                params.put("username",userName);
                params.put("inputs",inputsArray);
                //这里用了老版本的数据容器，但是task server需要该字段，因此填充为空数组
                params.put("outputs",new JSONArray().toJSONString());

                try{
                    String resJson = MyHttpUtils.POSTWithJSON(modelSerUrl, "UTF-8",null,params);
                    System.out.println(resJson);
                    JSONObject jResponse = JSONObject.parseObject(resJson);
                    if(jResponse.getString("result").equals("err")){
                        //说明找不到可用的地理模型服务
                        synchronized (this.sharedMap){
                            this.modelList.get(index).setStatus(-1);
                            //唤醒其他线程
                            this.sharedMap.notifyAll();
                        }
                        //退出该线程循环
                        break;
                    }else{
                        //得到任务的Id后，进行轮询遍历，以得到模型的运行结果
                        String taskId = jResponse.getString("data");
                        //构建模型运行状态变量
                        int taskStatus;
                        //TODO 构建备选服务计数(当计数超过备选服务个数的时候，说明确实是运行出错了)
                        while(true){
                            //直接向Task Server 发起http请求，获取模型运行结果
                            String taskQueryUrl = taskIpAndPort + "/task/" + taskId;
                            String taskResult = MyHttpUtils.GET(taskQueryUrl, "UTF-8",null);
                            JSONObject taskResultResponse = JSONObject.parseObject(taskResult);
                            if(taskResultResponse.getString("result").equals("suc")){
                                JSONObject jData = taskResultResponse.getJSONObject("data");
                                if(jData == null){
                                    throw new IOException("task Server Error");
                                }else{
                                    String t_status = jData.getString("t_status");
                                    taskStatus = convertStatus(t_status);
                                    //对状态进行判断，运行成功和失败
                                    if (taskStatus == 0){
                                        //表明模型还处于等待运行或者已经运行状态过程中，那么让该线程睡眠一会儿
                                        Thread.sleep(2000);
                                        continue;
                                    }else if(taskStatus == -1){
                                        //模型运行出错的情况，论文优化的部分，如果模型运行失败，则还可以从备选模型服务中选出可用的服务进行再次调度运行更新状态
                                        if(servicesMapping == null){
                                            //说明没有备选服务，直接跳出循环
                                            JSONArray jOutputs = jData.getJSONArray("t_outputs");
                                            updateModelOutputByTask(jOutputs,index);
                                            break;
                                        }else {
                                            //默认只执行一个候选服务，防止运行时间过程
                                            String optionalurl = servicesMapping.getServiceUrls().get(0);
                                            JSONArray optionalOutput = getOptionalModelResult(pid,optionalurl,params);
                                            if(optionalOutput == null){
                                                //说明出错了的
                                                JSONArray jOutputs = jData.getJSONArray("t_outputs");
                                                updateModelOutputByTask(jOutputs,index);
                                                taskStatus = -1;
                                                break;
                                            }else{
                                                //更新输出和url
                                                updateModelOutputAndUrlByTask(optionalOutput,index,optionalurl);
                                                taskStatus = 1;
                                                break;
                                            }
                                        }
                                    }
                                    else{
                                        //失败或成功的状态跳出while循环，在break之前更新一下model里面output信息
                                        JSONArray jOutputs = jData.getJSONArray("t_outputs");
                                        updateModelOutputByTask(jOutputs,index);
                                        break;
                                    }
                                }
                            }else{
                                // 返回result为err，说明taskServer可能出问题了，因为查询不到记录,往外面抛出错误
                                throw new IOException("task Server Error");
                            }
                        }
                        //模型运行成功或者失败的情况
                        synchronized (this.sharedMap){
                            this.modelList.get(index).setStatus(taskStatus);
                            //更新数据的concurrentHashMap
                            updateOutputToGlobalMap(index);
                            //唤醒其他等待的线程
                            this.sharedMap.notifyAll();
                            break;
                        }
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    synchronized (this.sharedMap){
                        this.modelList.get(index).setStatus(-1);
                        this.sharedMap.notifyAll();
                    }
                    break;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    synchronized (this.sharedMap){
                        this.modelList.get(index).setStatus(-1);
                        this.sharedMap.notifyAll();
                    }
                    break;
                }
            }
        }
        return this.modelList.get(index);
    }

    private JSONArray getOptionalModelResult(String pid,String modelSerUrl, JSONObject params){
        String taskIpAndPort = IPUtils.getIPAndPort(URI.create(modelSerUrl)).toString();
        //直接发起执行任务
        try{
            String resJson =   MyHttpUtils.POSTWithJSON(modelSerUrl, "UTF-8",null,params);
            JSONObject jResponse = JSONObject.parseObject(resJson);
            if(jResponse.getString("result").equals("err")){
                //说明备选服务也为空
                return null;
            }else {
                //模型开始运行
                String taskId = jResponse.getString("data");
                int taskStatus;
                while (true){
                    //直接向Task Server 发起http请求，获取模型运行结果
                    String taskQueryUrl = taskIpAndPort + "/task/" + taskId;
                    String taskResult = MyHttpUtils.GET(taskQueryUrl, "UTF-8",null);
                    JSONObject taskResultResponse = JSONObject.parseObject(taskResult);
                    if(taskResultResponse.getString("result").equals("suc")){
                        JSONObject jData = taskResultResponse.getJSONObject("data");
                        if(jData == null){
                            return null;
                        }else {
                            String t_status = jData.getString("t_status");
                            taskStatus = convertStatus(t_status);
                            if(taskStatus == 0){
                                Thread.sleep(2000);
                                continue;
                            }else {
                                //失败或成功的状态跳出while循环，在break之前更新一下model里面output信息
                                JSONArray jOutputs = jData.getJSONArray("t_outputs");
                                return jOutputs;
                            }
                        }
                    }else {
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初步实现策略：
     * 只要集成流程有一个服务运行出错了，那么就会导致整个集成流程不再运行，保留现场痕迹
     * 后面再进行优化，因为可能任务之间并不存在依赖性
     * @param
     * @return boolean
     * @author wangming
     * @date 2019/11/19 21:29
     */
    public boolean checkError(){
        for (Model model : this.modelList){
            if(model.getStatus() == -1){
                return true;
            }
        }
        return false;
    }

    //更新数据到concurrentHashMap中
    private void updateOutputToGlobalMap(int index){
        List<DataTemplate> outputs = this.modelList.get(index).getOutputData().getOutputs();
        for (DataTemplate output : outputs) {
            if (!output.getValue().equals("")){
                ShareData shareData = new ShareData();
                shareData.setValue(output.getValue());
                shareData.setType(output.getType());
                this.sharedMap.put(output.getDataId(),shareData);
            }
        }
    }

    // 将从TaskServer得到的模型运行记录(输出结果信息)更新到原文档中
    private void updateModelOutputByTask(JSONArray jOutputs, int index){
        List<DataTemplate> outputs = this.modelList.get(index).getOutputData().getOutputs();
        for (int i = 0; i < jOutputs.size(); i++){
            JSONObject temp = jOutputs.getJSONObject(i);
            String state = temp.getString("StateName");
            String event = temp.getString("Event");
            //根据state和event去outputs里面查找对应的并且更新相对应的值
            for (DataTemplate dataTemplate : outputs) {
                if(dataTemplate.getState().equals(state) && dataTemplate.getEvent().equals(event)){
                    dataTemplate.setValue(temp.getString("Url"));
                    dataTemplate.setPrepared(true);
                }
            }
        }
    }

    //更新模型运行的输出结果和服务url
    private void updateModelOutputAndUrlByTask(JSONArray jOutputs, int index, String modelSerUrl){
        Model model = this.modelList.get(index);
        model.setModelServiceUrl(modelSerUrl);
        List<DataTemplate> outputs = model.getOutputData().getOutputs();
        for (int i = 0; i < jOutputs.size(); i++){
            JSONObject temp = jOutputs.getJSONObject(i);
            String state = temp.getString("StateName");
            String event = temp.getString("Event");
            //根据state和event去outputs里面查找对应的并且更新相对应的值
            for (DataTemplate dataTemplate : outputs) {
                if(dataTemplate.getState().equals(state) && dataTemplate.getEvent().equals(event)){
                    dataTemplate.setValue(temp.getString("Url"));
                    dataTemplate.setPrepared(true);
                }
            }
        }
    }

    private int convertStatus(String taskStatus){
        int status;
        if(taskStatus.equals("Inited") || taskStatus.equals("Started")){
            //任务处于开始状态
            status = 0;
        }else if(taskStatus.equals("Finished")){
            status = 1;
        }else {
            status = -1;
        }
        return status;
    }

    private List<ExDataDTO> TemplateToExData(List<DataTemplate> dataTemplates){
        List<ExDataDTO> exDataDTOList = new ArrayList<>();
        for (int i = 0; i < dataTemplates.size(); i++) {
            ExDataDTO exDataDTO = new ExDataDTO();
            DataTemplate dataTemplate = dataTemplates.get(i);
            exDataDTO.setStatename(dataTemplate.getState());
            exDataDTO.setEvent(dataTemplate.getEvent());
            exDataDTO.setUrl(dataTemplate.getValue());
            //默认以event name作为tag的标签
            exDataDTO.setTag(dataTemplate.getEvent());
            exDataDTOList.add(exDataDTO);
        }
        return exDataDTOList;
    }

    private String convertItems2JSON(List<ExDataDTO> inputs){
        JSONArray resultJson = new JSONArray();
        for(ExDataDTO input: inputs){
            JSONObject temp = new JSONObject();
            temp.put("StateName", input.getStatename());
            temp.put("Event", input.getEvent());
            temp.put("Url", input.getUrl());
            temp.put("Tag", input.getTag());
            temp.put("Suffix",input.getSuffix());
            resultJson.add(temp);
        }
        return resultJson.toJSONString();
    }

    private List<ExDataDTO> convertJSON2Items(JSONArray jOutputs){
        List<ExDataDTO> outputItems = new ArrayList<>();
        for(int i = 0; i < jOutputs.size(); i++){
            JSONObject temp = jOutputs.getJSONObject(i);
            ExDataDTO exDataDTO = new ExDataDTO();
            exDataDTO.setStatename(temp.getString("StateName"));
            exDataDTO.setEvent(temp.getString("Event"));
            exDataDTO.setTag(temp.getString("Tag"));
            exDataDTO.setUrl(temp.getString("Url"));
            exDataDTO.setSuffix(temp.getString("Suffix"));
            outputItems.add(exDataDTO);
        }
        return outputItems;
    }

    private String convertOutputItems2JSON(List<OutputDataDTO> outputs){
        JSONArray resultJson = new JSONArray();
        for(OutputDataDTO output:outputs){
            JSONObject temp = new JSONObject();
            temp.put("StateName",output.getStatename());
            temp.put("Event", output.getEvent());
            temp.put("Type",output.getTemplate().getType());
            temp.put("Value",output.getTemplate().getValue());
            resultJson.add(temp);
        }
        return resultJson.toJSONString();
    }

    /**
     * 检查数据有没有准备好，只要有一个未准备好就直接返回false
     * @param inputsList
     * @return boolean
     * @author wangming
     * @date 2019/11/19 16:26
     */
    private boolean checkData(List<DataTemplate> inputsList){
        for (DataTemplate template : inputsList) {
            if(template.getType().equals("link")){
                String value = template.getValue();
                if(!sharedMap.containsKey(value)){
                    template.setPrepared(false);
                    return false;
                }else{
                    template.setValue(sharedMap.get(value).getValue());
                    template.setType(sharedMap.get(value).getType());
                    template.setPrepared(true);
                }
            }else{
                if (!sharedMap.containsKey(template.getDataId())){
                    template.setPrepared(false);
                    return false;
                }else{
                    template.setPrepared(true);
                }
            }
        }
        return true;
    }

}
