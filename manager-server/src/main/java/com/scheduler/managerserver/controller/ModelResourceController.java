package com.scheduler.managerserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.scheduler.managerserver.dao.DeployTaskDao;
import com.scheduler.managerserver.dto.ModelResourceFindDTO;
import com.scheduler.managerserver.dto.computer.RecommendComputer;
import com.scheduler.managerserver.dto.modelresource.ModelDeployDTO;
import com.scheduler.managerserver.dto.modelresource.ModelEnvironment;
import com.scheduler.managerserver.po.DeployTask;
import com.scheduler.managerserver.service.ModelResourceService;
import com.scheduler.managerserver.vo.ModelInfoVO;
import com.scheduler.managerserver.vo.ModelResourceVO;
import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门户模型服务获取相关api
 * @Author: wangming
 * @Date: 2019-12-28 16:47
 */
@RestController
@RequestMapping(value = "/modelResource")
public class ModelResourceController {

    @Autowired
    ModelResourceService modelResourceService;

    @Autowired
    DeployTaskDao deployTaskDao;

    @RequestMapping(value = "/getPackage", method = RequestMethod.GET)
    @ApiOperation(value = "获取门户计算资源池的模型部署包列表信息，默认分页10条")
    public JsonResult getPackageFromPortal(int page, String sortType, int sortAsc){
        List<ModelInfoVO> result = modelResourceService.getModelResourcePackage(page, sortType, sortAsc);
        if(result == null){
            return ResultUtils.error(-1,"获取可用计算资源失败，请检查门户服务器状态");
        }
        return ResultUtils.success(result);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取门户计算资源部署包信息")
    public JsonResult listPackageResourceFromPortal(ModelResourceFindDTO modelResourceFindDTO){
        JSONObject resources = modelResourceService.listModelResourceFromPortal(modelResourceFindDTO);
        if(resources == null){
            return ResultUtils.error(-1, "get model package resource error!");
        }
        return ResultUtils.success(resources);
    }

    @RequestMapping(value = "/getInfo/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据oid获取门户相关的计算资源详细信息")
    public JsonResult getInfo(@PathVariable("id") String id){
        JSONObject result = modelResourceService.getModelResourceByOid(id);
        if(result == null){
            return ResultUtils.error(-1, "get model package info error!");
        }else {
            return ResultUtils.success(result);
        }
    }

    @RequestMapping(value = "/getAllInfo", method = RequestMethod.GET)
    @ApiOperation(value = "获取门户所有相关的计算资源详细信息")
    public JsonResult getInfo(){
        JSONObject result = modelResourceService.getModelResource();
        if(result == null){
            return ResultUtils.error(-1, "get model package info error!");
        }else {
            return ResultUtils.success(result);
        }
    }

    @RequestMapping(value = "/getComputerForDeploy", method = RequestMethod.POST)
    @ApiOperation(value = "提交模型运行环境信息，推荐合适的计算资源")
    public JsonResult getSuitableComputerForDeploy(@RequestBody ModelEnvironment modelEnvironment){
        //TODO 推荐合适的计算资源
        List<RecommendComputer> suitableComputerForDeploy = modelResourceService.getSuitableComputerForDeploy(modelEnvironment);
        if (suitableComputerForDeploy.size() == 0){
            return ResultUtils.error(-1, "no suitable computer resource for deployment");
        }
        return ResultUtils.success(suitableComputerForDeploy);
    }

    @RequestMapping(value = "/deployModel", method = RequestMethod.POST)
    @ApiOperation(value = "提交模型部署的相关信息json，发起模型部署任务")
    public JsonResult deployModel(@RequestBody ModelDeployDTO modelDeployDTO){
        DeployTask deployTask = modelResourceService.deployModel(modelDeployDTO);
        return ResultUtils.success(deployTask);
    }

    @RequestMapping(value = "/updateDeployRecord", method = RequestMethod.POST)
    @ApiOperation(value = "更新模型部署的数据库记录")
    public JsonResult updateDeployRecord(@RequestParam("taskId") String taskId,
                                         @RequestParam("status") int status){
        DeployTask deployTask = deployTaskDao.findFirstByTaskId(taskId);
        if(deployTask == null){
            return  ResultUtils.error(-1,"No this record");
        }
        modelResourceService.updateDeployRecord(deployTask,status);
        return ResultUtils.success("suc");
    }

    @RequestMapping(value = "/refreshDeployRecord/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据taskId查询最新的部署记录，这里包含向taskServer发起请求")
    public JsonResult refreshDeployRecord(@PathVariable("id") String id){
        DeployTask deployTask = deployTaskDao.findFirstById(id);
        if(deployTask == null){
            return  ResultUtils.error(-1,"No this record");
        }
        DeployTask result = modelResourceService.refreshDeployRecord(deployTask);
        return ResultUtils.success(result);
    }
}
