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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: wangming
 * @Date: 2019-12-19 15:15
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView getRegister() {
        System.out.println("register");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("name", "OpenGMS");

        return modelAndView;
    }

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    @ApiOperation(value = "用户注册")
//    public JsonResult doRegister(@RequestModel AddUserDTO addUserDTO){
//        return ResultUtils.success(userService.add(addUserDTO));
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @ApiOperation(value = "用户登录")
//    public JsonResult doLogin(@RequestModel SignInDTO signInDTO){
//        return ResultUtils.success(userService.login(signInDTO));
//    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogin(HttpServletRequest request) {
        System.out.println("login");
        HttpSession session = request.getSession();
        ModelAndView modelAndView = new ModelAndView();
        if (session.getAttribute("uid") == null) {
            modelAndView.setViewName("login");
            Object preUrl_obj = session.getAttribute("preUrl");
            String preUrl = null;
            if (preUrl_obj != null) {
                preUrl = preUrl_obj.toString();
            }else {
                preUrl = request.getHeader("REFERER");
            }
            modelAndView.addObject("preUrl", preUrl);
            session.removeAttribute("preUrl");
        } else {
            modelAndView.setViewName("redirect:/list/model");

        }

        return modelAndView;
    }

    @RequestMapping(value = "/out", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        System.out.println("out");
        HttpSession session = request.getSession();
        session.removeAttribute("oid");
        session.removeAttribute("uid");
        session.removeAttribute("name");
        session.removeAttribute("logged");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/list/model");

        return modelAndView;
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public String loadUser(HttpServletRequest request) {
        System.out.println("loadUser");

        HttpSession session = request.getSession();
        Object oid = session.getAttribute("oid");

        JSONObject user = new JSONObject();

        if (oid == null) {
            user.put("oid", "");
            return user.toString();
        } else {

            user.put("oid", session.getAttribute("oid").toString());
            user.put("userName", session.getAttribute("uid").toString());
            user.put("name", session.getAttribute("name").toString());
            return user.toString();
        }
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

    @RequestMapping(value = "/userSpace", method = RequestMethod.GET)
    public ModelAndView getUserSpace1(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        System.out.println("userSpace1");

        modelAndView.setViewName("userSpace1");

        return modelAndView;
    }


    @RequestMapping(value = "/getInvite", method = RequestMethod.GET)
    public JsonResult getInvite(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = session.getAttribute("uid").toString();
        if (userId == null){
            return ResultUtils.error(-1, "no login");
        }else {
            return ResultUtils.success(userService.getInvite(userId));
        }

    }

    @RequestMapping(value = "/acceptInvite", method = RequestMethod.POST)
    public JsonResult acceptInvite(@RequestParam("invite") String inviteOid, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userId = session.getAttribute("uid").toString();
        if (userId == null){
            return ResultUtils.error(-1, "no login");
        }else {
            return ResultUtils.success(userService.acceptInvite(inviteOid));
        }

    }


//    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
//    @ApiOperation(value = "重置密码")
//    public JsonResult resetPassword(@RequestModel ResetPasswordDTO resetPasswordDTO){
//        JSONObject result = userService.resetPassword(resetPasswordDTO);
//        if (result.getIntValue("code") != 0) {
//            return ResultUtils.error(result.getIntValue("code"), result.getString("message"));
//        }
//        return ResultUtils.success(result.getString("message"));
//    }

}
