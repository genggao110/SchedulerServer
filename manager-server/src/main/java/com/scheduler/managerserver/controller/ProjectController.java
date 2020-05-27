package com.scheduler.managerserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.scheduler.managerserver.dto.ProjectAddDTO;
import com.scheduler.managerserver.po.Project;
import com.scheduler.managerserver.service.ProjectService;
import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 服务请求获取相关api
 * @Author: hhaijoy
 * @Date: 2020-05-23 20:45
 */
@RestController
@RequestMapping(value = "/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createProject(@RequestBody ProjectAddDTO project, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("uid") == null) {
            return "-1";

        } else {
            Project project1 =  projectService.createProject(project) ;
            if(project1==null) return "0";
            else return project1.getOid();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getProjectPage(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();

        HttpSession session = request.getSession();
        if (session.getAttribute("uid") == null) {
            modelAndView.setViewName("redirect:/user/login");

        } else {
            modelAndView.setViewName("projectInfo");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public JsonResult getById(@RequestParam(value = "id")String id, HttpServletRequest request){
        JSONObject project =  projectService.getById(id);
        if(project == null)
            return ResultUtils.error(-1,"no project");
        else
            return ResultUtils.success(project);
    }

    @RequestMapping(value="/getProjectsByUserIdByStatus",method = RequestMethod.GET )
    @ApiOperation("根据status拿取用户的project")
    JsonResult getTasksByUserByStatus(HttpServletRequest request, @RequestParam(value="status") String status,@RequestParam(value="page") int page,
                                      @RequestParam(value="sortType") String sortType,
                                      @RequestParam(value="asc") int sortAsc) {
        HttpSession session = request.getSession();
        String userId = session.getAttribute("uid").toString();
        if (userId == null){
            return ResultUtils.error(-1, "no login");
        }else{
            String username = session.getAttribute("uid").toString();
            return ResultUtils.success(projectService.getProjectsByUserIdByStatus(username,status,page,sortType,sortAsc));
        }

    }

    @RequestMapping(value="/searchProjects",method = RequestMethod.GET )
    @ApiOperation("根据Name搜索用户的project")
    JsonResult searchProjects(HttpServletRequest request,
                              @RequestParam(value="searchText") String searchText,
                              @RequestParam(value="page") int page,
                              @RequestParam(value="sortType") String sortType,
                              @RequestParam(value="asc") int sortAsc) {
        HttpSession session = request.getSession();
        String userId = session.getAttribute("uid").toString();
        if (userId == null){
            return ResultUtils.error(-1, "no login");
        }else{
            String username = session.getAttribute("uid").toString();
            return ResultUtils.success(projectService.searchProjects(searchText.trim(),username,page,sortType,sortAsc));
        }

    }
}
