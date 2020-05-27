package com.scheduler.managerserver.controller;

import com.scheduler.managerserver.dto.integrate.IConditionDTO;
import com.scheduler.managerserver.dto.integrate.SelectResult;
import com.scheduler.managerserver.service.IntegrateTaskService;
import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模型集成控制类，负责集成任务中各个服务的优选，从而为后面的任务调度执行构建前期准备工作
 * @Author: wangming
 * @Date: 2020-05-24 15:28
 */
@RestController
@RequestMapping(value = "/integrate")
public class IntegrateTaskController {

    @Autowired
    IntegrateTaskService integrateTaskService;

    @RequestMapping(value = "/getSuitableTaskNode",method = RequestMethod.POST)
    @ApiOperation(value = "根据pid和用户提供的局部优化信息，去挑选出符合条件的模型服务")
    public JsonResult getSuitableTaskServer(@RequestBody IConditionDTO iConditionDTO){
        //TODO 进行处理
        SelectResult suitableTaskServer = integrateTaskService.getSuitableTaskServer(iConditionDTO);
        if(suitableTaskServer == null){
            return ResultUtils.error(-1, "there is no suitable computer resource");
        }
        return ResultUtils.success(suitableTaskServer);
    }
}
