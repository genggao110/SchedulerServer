package com.scheduler.managerserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.scheduler.managerserver.annotation.RequestModel;
import com.scheduler.managerserver.annotation.UserLoginToken;
import com.scheduler.managerserver.dto.user.AddUserDTO;
import com.scheduler.managerserver.dto.user.ResetPasswordDTO;
import com.scheduler.managerserver.dto.user.SignInDTO;
import com.scheduler.managerserver.service.UserService;
import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wangming
 * @Date: 2019-12-19 15:15
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册")
    public JsonResult doRegister(@RequestModel AddUserDTO addUserDTO){
        return ResultUtils.success(userService.add(addUserDTO));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    public JsonResult doLogin(@RequestModel SignInDTO signInDTO){
        return ResultUtils.success(userService.login(signInDTO));
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息")
    @UserLoginToken
    public JsonResult getMessage(@RequestParam("name") String name){
        return ResultUtils.success(userService.findUserByName(name));
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    @ApiOperation(value = "验证用户名是否已经被注册")
    public JsonResult validate(@RequestParam("name") String name){
        return ResultUtils.success(userService.validateUserName(name));
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ApiOperation(value = "重置密码")
    public JsonResult resetPassword(@RequestModel ResetPasswordDTO resetPasswordDTO){
        JSONObject result = userService.resetPassword(resetPasswordDTO);
        if (result.getIntValue("code") != 0) {
            return ResultUtils.error(result.getIntValue("code"), result.getString("message"));
        }
        return ResultUtils.success(result.getString("message"));
    }

}
