package com.scheduler.managerserver.controller;

import com.scheduler.managerserver.service.ValidateService;
import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangming
 * @Date: 2019-12-24 21:11
 */
@RestController
@RequestMapping(value = "/validate")
public class ValidateController {

    @Autowired
    ValidateService validateService;

    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    @ApiOperation(value = "获取邮箱验证码", notes = "获取邮箱验证码")
    public JsonResult sendValidationEmail(@RequestParam("email") String email){
        return ResultUtils.success(validateService.getCode(email));
    }

}
