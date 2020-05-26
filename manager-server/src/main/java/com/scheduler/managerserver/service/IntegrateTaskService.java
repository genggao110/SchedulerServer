package com.scheduler.managerserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scheduler.managerserver.dto.integrate.IConditionDTO;
import com.scheduler.managerserver.dto.integrate.SelectResult;
import com.scheduler.managerserver.dto.integrate.TaskAndContainerReturnInfo;
import com.scheduler.managerserver.po.ComputerInfo;
import com.scheduler.utils.MyHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public SelectResult getSuitableTaskServer(IConditionDTO iConditionDTO){
        SelectResult selectResult = new SelectResult();
        //TODO 目前只根据pid来获取到可用的模型服务(利用ManagerServer之前的设计理念)
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
            //TODO 目前只考虑count因素,从小到大排序
            taskAndContainerReturnInfos.sort(Comparator.comparing(TaskAndContainerReturnInfo::getCount));
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
}
