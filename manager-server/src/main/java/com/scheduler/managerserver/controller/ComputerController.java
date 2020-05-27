package com.scheduler.managerserver.controller;

import com.scheduler.managerserver.dao.ComputerInfoDao;
import com.scheduler.managerserver.dto.computer.ComputerInfoDTO;
import com.scheduler.managerserver.dto.computer.DynamicInfoDTO;
import com.scheduler.managerserver.po.ComputerInfo;
import com.scheduler.managerserver.pojo.environment.HardwareEnvironment;
import com.scheduler.managerserver.service.ComputerInfoService;
import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: wangming
 * @Date: 2020-04-22 19:49
 */
@RestController
@RequestMapping(value = "/computer")
public class ComputerController {

    @Autowired
    ComputerInfoDao computerInfoDao;

    @Autowired
    ComputerInfoService computerInfoService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增计算资源或者更新计算资源信息")
    JsonResult addComputerResource(@RequestBody ComputerInfoDTO computerInfoDTO) {
        ComputerInfo computerInfo = computerInfoDao.findFirstByUserIdAndMac(computerInfoDTO.getUserId(), computerInfoDTO.getMac());
        if(computerInfo == null) {
            //新增计算资源
            computerInfoService.addComputer(computerInfoDTO);
            return ResultUtils.success("Insert Success");
        }else {
            //内容的更新
            try {
                computerInfoService.updateComputer(computerInfo, computerInfoDTO);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            return ResultUtils.success("Update Success");
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ApiOperation("移除特定的计算资源")
    JsonResult removeComputerResource(@RequestParam("userName") String userName,
                                      @RequestParam("mac") String mac) {
        ComputerInfo computerInfo = computerInfoDao.findFirstByUserIdAndMac(userName, mac);
        if(computerInfo == null) {
            return ResultUtils.error(-1, "No computer resource matches this userName and mac");
        }else {
            computerInfoDao.delete(computerInfo);
            return ResultUtils.success("Delete success!");
        }
    }

    @RequestMapping(value = "/getByUsers", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户的计算资源信息")
    JsonResult getAllComputerResource(@RequestParam("userNames[]") String[] userNames) {
        List<ComputerInfo> array = new ArrayList<>();

        for(String id : userNames){
            array.addAll(computerInfoDao.findAllByUserId(id));
        }

        return ResultUtils.success(array);
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有的计算资源信息")
    JsonResult getAllComputerResource() {
        return ResultUtils.success(computerInfoDao.findAll());
    }

    @RequestMapping(value = "/getByUserName", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户名查询其拥有的计算资源")
    JsonResult getComputerResourceByUserName(@RequestParam("userName") String userName){
        return ResultUtils.success(computerInfoDao.findAllByUserId(userName));
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation(value = "由task server监视模型容器的状态信息，从而更新相关的信息")
    JsonResult updateComputerResourceStatus(@RequestParam("user") String userName,
                                            @RequestParam("mac") String mac,
                                            @RequestParam("status") boolean status,
                                            @RequestParam("ping")double ping){
        ComputerInfo computerInfo = computerInfoDao.findFirstByUserIdAndMac(userName,mac);
        if(computerInfo == null) {
            return ResultUtils.error(-1, "No computer resource matches this userName and mac");
        }else {
            //更新状态
            computerInfo.setStatus(status);
            Date now = new Date();
            computerInfo.setUpdateDate(now);
            //判断计算资源类型，从而决定ping值是否需要更新
            if(computerInfo.getType() == 1){
                //需要更新ping值
                computerInfo.setPing_value(ping);
            }
            computerInfoDao.save(computerInfo);
            return ResultUtils.success("Update suc!");
        }
    }

    @RequestMapping(value = "/updateDynamicInfo", method = RequestMethod.POST)
    @ApiOperation(value = "更新计算资源的动态环境信息，包含CPU利用率和内存占用率")
    JsonResult updateDynamicInfo(@RequestBody DynamicInfoDTO dynamicInfoDTO){
        ComputerInfo computerInfo = computerInfoDao.findFirstByUserIdAndMac(dynamicInfoDTO.getUserId(),dynamicInfoDTO.getMac());
        if(computerInfo == null){
            return ResultUtils.error(-1, "No computer resource matches this userName and mac");
        }else{
            //更新动态环境信息
            computerInfo.setStatus(true);
            Date now = new Date();
            computerInfo.setUpdateDate(now);
            HardwareEnvironment hardwareEnvironment = computerInfo.getHardware_info();
            hardwareEnvironment.setDynamicInfo(dynamicInfoDTO.getDynamicInfo());
            computerInfo.setHardware_info(hardwareEnvironment);
            computerInfoDao.save(computerInfo);
            return ResultUtils.success("Update dynamic info success!");
        }
    }
}
