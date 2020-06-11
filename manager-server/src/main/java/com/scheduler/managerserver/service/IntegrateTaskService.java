package com.scheduler.managerserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scheduler.managerserver.dao.ComputerInfoDao;
import com.scheduler.managerserver.dto.integrate.*;
import com.scheduler.managerserver.po.ComputerInfo;
import com.scheduler.managerserver.pojo.computer.Hardware;
import com.scheduler.utils.MyHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 集成任务service类
 * @Author: wangming
 * @Date: 2020-05-24 15:30
 */
@Service
public class IntegrateTaskService {

    @Value("${prop.managerAddress}")
    private String MANAGER_ADDRESS;

    @Autowired
    ComputerInfoService computerInfoService;

    @Autowired
    ComputerInfoDao computerInfoDao;

    public SelectResult getSuitableTaskServer(IConditionDTO iConditionDTO){
        SelectResult selectResult = new SelectResult();
        String pid = iConditionDTO.getPid();
        //根据pid、通过ManagerServer获取到所有包含该模型服务的可用计算资源节点(mac地址唯一标识)
        String url =  MANAGER_ADDRESS + "/GeoModeling/computableModel/getAllTaskServerNode/" + pid;
        try{
            String result = MyHttpUtils.GET(url,"UTF-8",null);
            JSONObject jResponse = JSON.parseObject(result);
            if(jResponse.getIntValue("code") == 1){
                //TODO 对返回后的可用计算资源进行处理
                JSONArray suitableNodeInfo = jResponse.getJSONArray("data");
                if(suitableNodeInfo.size() == 0){
                    //说明不存在合适的计算模型以供调用，直接返回
                    return null;
                }else {
                    //至少存在一个合适的计算资源
                    List<TaskAndContainerReturnInfo> taskAndContainerReturnInfos = new ArrayList<>();
                    ObjectMapper mapper = new ObjectMapper();
                    for(int i =0;i < suitableNodeInfo.size(); i++){
                        JSONObject jsonObject = suitableNodeInfo.getJSONObject(i);
                        TaskAndContainerReturnInfo taskAndContainerReturnInfo = mapper.convertValue(jsonObject, TaskAndContainerReturnInfo.class);
                        taskAndContainerReturnInfos.add(taskAndContainerReturnInfo);
                    }
                    //TODO 需要根据额外的条件信息对服务结果进行筛选，目前就只考虑运行模型容器正在开展的任务数量
                    selectResult = selectSuitableResourceToInvoke(taskAndContainerReturnInfos, iConditionDTO);
                    return selectResult;
                }
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            selectResult = null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            selectResult = null;
        }
        return selectResult;
    }

    //TODO 根据局部优化的条件来对拥有特定模型的计算资源进行筛选
    private SelectResult selectSuitableResourceToInvoke(List<TaskAndContainerReturnInfo> taskAndContainerReturnInfos, IConditionDTO iConditionDTO){
        if(taskAndContainerReturnInfos.size() != 1){
            //当存在多个服务的时候才需要进行筛选操作
            //无qos权重时，只考虑count因素,从小到大排序
            if(iConditionDTO.getFactor() == null){
                taskAndContainerReturnInfos.sort(Comparator.comparing(TaskAndContainerReturnInfo::getCount));
            }else {
                //有qos权重时，考虑qos，从大到小排序
                taskAndContainerReturnInfos.sort(Comparator.comparing(TaskAndContainerReturnInfo::getQos_value).reversed());
            }
            //取第一个作为推荐的，后续的全部作为备选方案
            TaskAndContainerReturnInfo taskAndContainerReturnInfo = taskAndContainerReturnInfos.get(0);
            SelectResult selectResult = new SelectResult();
            String url = "http://" + taskAndContainerReturnInfo.getHost() + ":" + taskAndContainerReturnInfo.getPort() + "/task/invoke/" + taskAndContainerReturnInfo.getSid();
            selectResult.setRecommend(url);
            List<String> optional = new ArrayList<>();
            for(int i = 1; i< taskAndContainerReturnInfos.size(); i++){
                TaskAndContainerReturnInfo temp = taskAndContainerReturnInfos.get(i);
                String optional_url = "http://" + temp.getHost() + ":" + temp.getPort() + "/task/invoke/" + temp.getSid();
                optional.add(optional_url);
            }
            selectResult.setOptional(optional);
            return selectResult;

        }else {
            //说明只存在一个符合的，不需要在进行优化筛选了，返回给前台的信息是拼装过后的url
            TaskAndContainerReturnInfo taskAndContainerReturnInfo = taskAndContainerReturnInfos.get(0);
            SelectResult selectResult = new SelectResult();
            String url = "http://" + taskAndContainerReturnInfo.getHost() + ":" + taskAndContainerReturnInfo.getPort() + "/task/invoke/" + taskAndContainerReturnInfo.getSid();
            selectResult.setRecommend(url);
            selectResult.setOptional(new ArrayList<>());
            return selectResult;
        }
    }

    private List<TaskAndContainerReturnInfo> QosFactoryEstimate(List<TaskAndContainerReturnInfo> taskAndContainerReturnInfos, IConditionDTO conditionDTO){
        //首先分别根据计算容器的id查询到其对应的相关信息
        List<ComputerInfo> computerInfos = new ArrayList<>();
        List<QosEstimateHelp> qosEstimateHelps = new ArrayList<>();
        for(int i =0 ; i< taskAndContainerReturnInfos.size(); i++){
            ComputerInfo computerInfo = computerInfoDao.findFirstByMacAndStatus(taskAndContainerReturnInfos.get(i).getMac(), true);
            if(computerInfo != null){
                computerInfos.add(computerInfo);
                QosEstimateHelp temp = new QosEstimateHelp();
                temp.setHost(taskAndContainerReturnInfos.get(i).getHost());
                temp.setPort(taskAndContainerReturnInfos.get(i).getPort());
                temp.setSid(taskAndContainerReturnInfos.get(i).getSid());
                temp.setReliability(taskAndContainerReturnInfos.get(i).getReliability());
                qosEstimateHelps.add(temp);
            }
        }
        //后面分别对每个因素进行判断，填充相应的数字信息(后面再做具体的归一化和评分处理)
        for(int j = 0; j < computerInfos.size(); j++){
            ComputerInfo computerInfo = computerInfos.get(j);
            QosEstimateHelp qosEstimateHelp = qosEstimateHelps.get(j);
            if(conditionDTO.getFactor().getPerformance() != 0){
                //1. 性能评判因素,说明该因素有权重,而该因素本质上就ping值的大小，ping值越高，质量越低，(需要做归一化处理)
                //TODO 简单操作直接处理(因为需要考虑局域网和公网环境，为此将其分为三个级别)
                double ping_value = computerInfo.getPing_value();
                if (computerInfo.getType() == 2){
                    qosEstimateHelp.setPerformance(0);
                }else {
                    if(ping_value >= 2000){
                        qosEstimateHelp.setPerformance(0.2);
                    }else if(ping_value > 60 && ping_value < 2000){
                        qosEstimateHelp.setPerformance(0.5);
                    }else {
                        qosEstimateHelp.setPerformance(0.8);
                    }
                }
//            }else if(conditionDTO.getFactor().getReliability() != 0){
//                //2. 可靠性，服务执行成功的概率,已经在前面进行获取值了
            }else if(conditionDTO.getFactor().getAvailability() != 0){
                //3. 可用性，都默认设置为1
                qosEstimateHelp.setAvailability(1);
            }else if(conditionDTO.getFactor().getReputation() != 0){
                //4. 信誉度，因为目前没有评分系统，默认都为1，归一化后的值
                qosEstimateHelp.setReputation(1);
            }else if(conditionDTO.getFactor().getCpu_performance() != 0){
                //获取cpu性能评分，这里直接以CPU_Frequency和CPU_Core作为基础，CPU_Frequency最大值取4.5， CPU_Core取16,进行归一化
                Hardware hardware = computerInfo.getHardware_info().getStaticInfo();
                //获取CPU_Frequency大小值
                double cpu_frequemcy = Double.parseDouble(hardware.getCpu_frequency().substring(0,hardware.getCpu_frequency().length() - 3));
                double score = ((hardware.getCpu_core() / 16) + (cpu_frequemcy / 4.5)) / 2;
                qosEstimateHelp.setCpu_performance(score);
            }else if(conditionDTO.getFactor().getMemory_size() != 0){
                Hardware hardware = computerInfo.getHardware_info().getStaticInfo();
                int index = getIndex(hardware.getMemory_size());
                double size_value = Double.parseDouble(hardware.getMemory_size().substring(0, index+1));
                //获取内存大小，默认最大值为32G
                double score = size_value / 32;
                qosEstimateHelp.setMemory_size(size_value);
            }
        }
        //组织返回值，根据不同的权重计算不同的返回值
        qosEstimateHelps.forEach(qosEstimateHelp -> {
            double qos_value = calculateQosValue(qosEstimateHelp,conditionDTO.getFactor());
            String sid = qosEstimateHelp.getSid();
            for(int k = 0; k < taskAndContainerReturnInfos.size(); k++){
                if(taskAndContainerReturnInfos.get(k).getSid().equals(sid)){
                    taskAndContainerReturnInfos.get(k).setQos_value(qos_value);
                    break;
                }
            }
        });
        //对结果进行排序
        return taskAndContainerReturnInfos;
    }

    /**
     * 根据局部优化给予的权重和对应的QoS属性值计算相应服务的QoS得分,因实验环境不能达到真实需求，简略版本实现
     * 后面可以构建复杂的服务环境，自行实现，原理也很简单
     * @param qosEstimateHelp
     * @param qoSFactor
     * @return
     */
    private double calculateQosValue(QosEstimateHelp qosEstimateHelp, QoSFactor qoSFactor){
        double value = qosEstimateHelp.getPerformance() * qoSFactor.getPerformance();
        value += qosEstimateHelp.getReliability() * qoSFactor.getReliability();
        value += qosEstimateHelp.getAvailability() * qoSFactor.getAvailability();
        value += qosEstimateHelp.getReputation() * qoSFactor.getReputation();
        value += qosEstimateHelp.getCpu_performance() * qoSFactor.getCpu_performance();
        value += qosEstimateHelp.getMemory_size() * qoSFactor.getMemory_size();
        return value;
    }

    /**
     * 工具方法，分离单位和数字
     * @param s
     * @return
     */
    private int getIndex(String s){
        for(int i = s.length() - 1; i>=0;i--){
            if((s.charAt(i) - 48) <= 9){
                return i;
            }
        }
        return -1;
    }
}
