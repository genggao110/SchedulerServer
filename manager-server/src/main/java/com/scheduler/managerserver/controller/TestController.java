package com.scheduler.managerserver.controller;

import com.scheduler.managerserver.pojo.computer.Hardware;
import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wangming
 * @Date: 2020-05-18 17:31
 */
@RestController
@RequestMapping(value = "/all")
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiOperation(value = "获取邮箱验证码", notes = "获取邮箱验证码")
    public JsonResult sendValidationEmail(@RequestBody Hardware hardware){
        return ResultUtils.success(hardware);
    }
}
