package com.scheduler.schedulerserver.controller;

import com.scheduler.schedulerserver.domain.Task;
import com.scheduler.schedulerserver.service.TaskService;
import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.scene.chart.ValueAxis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: wangming
 * @Date: 2019-12-03 19:54
 */
@Api(value = "计算任务提交模块")
@RestController
@RequestMapping(value = "/task")
@CrossOrigin
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Value("${prop.task-upload}")
    private String TASK_UPLOAD_FOLDER;

    @Autowired
    TaskService taskService;

    @GetMapping("/hello")
    public String index(){
        return "Hello World";
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "提交模型运行任务")
    JsonResult submitTask(@RequestParam("file")MultipartFile file, @RequestParam("userName") String userName){
        if (file.isEmpty()) {
            return ResultUtils.error(-1, "上传文件为空");
        }else{
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            //TODO 验证xml文件是否符合标准格式(校验函数待实现)
            if (!suffix.equals("xml") || false){
                return ResultUtils.error(-1, "上传的文件不是xml或者文件不符合规定的xml格式");
            }else{
                String uid = taskService.handlerTaskConfiguration(file, userName);
                if (uid == null){
                    return ResultUtils.error(-1,"解析xml文件或者任务放置出现问题！");
                }else{
                    return ResultUtils.success(uid);
                }
            }
        }
    }

    @RequestMapping(value = "/updateRecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "更新Task运行记录")
    JsonResult updateRecord(@RequestBody Task task){
        if(!taskService.updateRecord(task)){
            return ResultUtils.error(-1,"update record error!");
        }else{
            return ResultUtils.success(true);
        }
    }



}
