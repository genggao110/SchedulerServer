package com.scheduler.managerserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.scheduler.managerserver.dto.UserAddDTO;
import com.scheduler.managerserver.service.PortalUserService;
import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *  门户用户验证
 * @Author: wangming
 * @Date: 2020-05-16 10:22
 */
@RestController
@RequestMapping(value= "/portal")
public class PortalUserController {

    @Autowired
    PortalUserService portalUserService;

    @RequestMapping(value="/user/in", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录接口")
    public String login(@RequestParam(value = "account") String account,
                            @RequestParam(value = "password_md5") String password,
                            HttpServletRequest request) {
        JSONObject result = portalUserService.getUserInfoFromPortal(account, password);
        if(result == null){
           return "0";
        }else{
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30*60);//设置session过期时间 为30分钟
            session.setAttribute("oid", result.get("oid"));
            session.setAttribute("uid", result.get("uid"));
            session.setAttribute("name", result.get("name"));

            return "1";
        }

    }

    @RequestMapping(value="/user/add", method = RequestMethod.POST)
    @ApiOperation(value = "用户添加接口")
    public JsonResult login(UserAddDTO user) {
        int result = portalUserService.addUserToPortal(user);
        return ResultUtils.success(result);
    }

    @RequestMapping(value="/user/getUsers", method = RequestMethod.GET)
    @ApiOperation(value = "用户获取接口")
    public JsonResult getUsers(HttpServletRequest request ) {

        HttpSession session = request.getSession();
        if (session.getAttribute("uid") == null) {
            return ResultUtils.error(-1, "no login");

        } else {

            return ResultUtils.success(portalUserService.getUsersFromPortal()) ;
        }
    }

    @RequestMapping(value="/user/getUser", method = RequestMethod.GET)
    @ApiOperation(value = "用户获取接口")
    public JsonResult getUsers(@RequestParam("userName") String userName , HttpServletRequest request ) {

        HttpSession session = request.getSession();
        if (session.getAttribute("uid") == null) {
            return ResultUtils.error(-1, "no login");

        } else {

            return ResultUtils.success(portalUserService.getUsersFromPortal()) ;
        }
    }

    @RequestMapping(value="/user/searchUserByType", method = RequestMethod.GET)
    @ApiOperation(value = "用户搜索接口关键词类型自定")
    public JsonResult searchUserByType(@RequestParam("searchText") String searchText ,
                                       @RequestParam("type") String type, HttpServletRequest request ) {

        HttpSession session = request.getSession();
        if (session.getAttribute("uid") == null) {
            return ResultUtils.error(-1, "no login");

        } else {

            return ResultUtils.success(portalUserService.getUsersFromPortalByType(searchText,type)) ;
        }
    }
}
