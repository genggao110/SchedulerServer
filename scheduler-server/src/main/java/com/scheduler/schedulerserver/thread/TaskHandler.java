package com.scheduler.schedulerserver.thread;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.scheduler.schedulerserver.constant.ManagerServerConstants;
import com.scheduler.schedulerserver.domain.Task;
import com.scheduler.schedulerserver.domain.xml.DataTemplate;
import com.scheduler.schedulerserver.domain.xml.Model;
import com.scheduler.schedulerserver.domain.xml.ShareData;
import com.scheduler.schedulerserver.dto.ServicesMapping;
import com.scheduler.utils.MyHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: wangming
 * @Date: 2019-12-03 17:09
 */
public class TaskHandler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(TaskHandler.class);
    ThreadFactory namedFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    ThreadPoolExecutor pool = new ThreadPoolExecutor(5,200,0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024),namedFactory,new ThreadPoolExecutor.AbortPolicy());

    private Task task;

    public TaskHandler(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        List<Future<Model>> futureList = new ArrayList<>();
        //构建一个共享的数据标识，标识数据已经被准备好
        ConcurrentHashMap<String, ShareData> sharedMap = getAllPreparedData(task);
        AdvanceHandler advanceHandler = new AdvanceHandler(task.getModels(),sharedMap, task.getUserName());
        //深拷贝，为了下一步的任务处理
        List<Model> modelList = new ArrayList<>(task.getModels());
        //针对于有多少个任务，就开启多少个线程来处理任务
        for (int i = 0; i < task.getModels().size(); i++){
            //根据模型获取到获取模型的pid
            Model model = task.getModels().get(i);
            String pid = model.getPid();
            //在ServicesMappings中查询到对应的serviceMapping
            List<ServicesMapping> servicesMappings = task.getServicesMappings();
            int k = -1;
            for(int j = 0; j < servicesMappings.size(); j++){
                if(servicesMappings.get(j).getPid().equals(pid)){
                    k = j;
                    break;
                }
            }
            ServicesMapping servicesMapping = null;
            if(k != -1){
                servicesMapping = servicesMappings.get(k);
            }
            AdvanceCallable advanceCallable = new AdvanceCallable(advanceHandler,i, servicesMapping);
            Future<Model> future = pool.submit(advanceCallable);
            futureList.add(future);
        }
        //遍历每个线程的结果，因为考虑到有可能某个服务运行时间过长，为此参考老铁的实现策略
        boolean globalStatus = true;
        for(int i = 0; i < futureList.size(); i++){
            Model model = null;
            try{
                model = futureList.get(i).get(1,TimeUnit.DAYS);
            }catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                modelList.get(i).setStatus(2);
                model = modelList.get(i);
            }finally {
                if(model.getStatus() != 1){
                    globalStatus = false;
                }
                modelList.set(i,model);
            }
        }
        if(globalStatus){
            task.setStatus(1);
        }else{
            task.setStatus(-1);
        }
        pool.shutdown();
        //更新数据库记录,发送Http请求到ManagerServer
        try{
            String url = "http://" + ManagerServerConstants.MANAGERSERVER + "/task/updateRecord";
            ObjectMapper mapper = new ObjectMapper();
            JSONObject params = mapper.readValue(mapper.writeValueAsBytes(task),JSONObject.class);
            String resJson = MyHttpUtils.POSTWithJSON(url,"UTF-8",null,params);
            JSONObject jResponse = JSONObject.parseObject(resJson);
            if(jResponse.getIntValue("code") == -1){
                log.info("Warning: " + Thread.currentThread().getName() + "update record error");
            }else{
                log.info(Thread.currentThread().getName() + " update record finish");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("Warning: " + Thread.currentThread().getName() + "request and update record error, " + e.getMessage());
        }finally {
            //garbage collection
            task = null;
            sharedMap.clear();
            sharedMap = null;
        }
    }

    /**
     * 线程初始化操作，将已经准备好的数据放入到线程间共享的concurrentHashMap之中
     * @param task
     * @return java.util.concurrent.ConcurrentHashMap<java.lang.String,com.example.demo.domain.xml.ShareData>
     * @author wangming
     * @date 2019/11/19 15:57
     */
    private ConcurrentHashMap<String, ShareData> getAllPreparedData(Task task){
        ConcurrentHashMap<String, ShareData> sharedData = new ConcurrentHashMap<>();
        List<Model> models = task.getModels();
        for(Model model : models){
            //只需要考虑输入数据
            List<DataTemplate> inputs = model.getInputData().getInputs();
            for (DataTemplate dataTemplate : inputs){
                if(!dataTemplate.getType().equals("link") && !dataTemplate.getValue().isEmpty()){
                    ShareData shareData = new ShareData(dataTemplate.getValue(),dataTemplate.getType());
                    sharedData.put(dataTemplate.getDataId(),shareData);
                }
            }
        }
        return sharedData;
    }
}
