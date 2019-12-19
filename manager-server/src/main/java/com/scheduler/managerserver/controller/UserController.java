package com.scheduler.managerserver.controller;

import com.scheduler.managerserver.annotation.UserLoginToken;
import com.scheduler.managerserver.dto.user.AddUserDTO;
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
    public JsonResult doRegister(@RequestBody AddUserDTO addUserDTO){
        userService.add(addUserDTO);
        return ResultUtils.success("新建用户成功");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    public JsonResult doLogin(@RequestBody SignInDTO signInDTO){
        return ResultUtils.success(userService.login(signInDTO));
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息")
    @UserLoginToken
    public JsonResult getMessage(@RequestParam("name") String name){
        return ResultUtils.success(userService.findUserByName(name));
    }
}
