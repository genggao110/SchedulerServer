package com.scheduler.schedulerserver.service;

import com.scheduler.schedulerserver.SchedulerServerApplication;
import com.scheduler.schedulerserver.dao.TaskDao;
import com.scheduler.schedulerserver.domain.Task;
import com.scheduler.schedulerserver.domain.xml.TaskConfiguration;
import com.scheduler.schedulerserver.utils.XmlParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: wangming
 * @Date: 2019-12-03 20:09
 */
@Service
public class TaskService {

    @Autowired
    TaskDao taskDao;

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    public String handlerTaskConfiguration(MultipartFile file, String userName){
        UUID taskId = UUID.randomUUID();
        //解析xml文件
        try{
            TaskConfiguration taskConfiguration = XmlParseUtils.parseXmlBaseOnStream(file.getInputStream(), "UTF-8");
            Task task = new Task();
            task.setUid(taskConfiguration.getUid());
            task.setDate(new Date());
            task.setName(taskConfiguration.getName());
            task.setVersion(taskConfiguration.getVersion());
            task.setModels(taskConfiguration.getModels());
            task.setTaskId(taskId.toString());
            task.setUserName(userName);
            //数据库插入记录
            String id = taskDao.insert(task).getId();
            task.setId(id);
            LinkedBlockingDeque<Task> linkedBlockingDeque = SchedulerServerApplication.linkedBlockingDeque;
            linkedBlockingDeque.put(task);
        }catch (IOException e){
            e.printStackTrace();
            log.info("parse xml error: " + e.getMessage());
            return null;
        }catch (InterruptedException e){
            e.printStackTrace();
            log.info("push task into the Deque error: " + e.getMessage());
            return null;
        }
        return taskId.toString();
    }

    public boolean updateRecord(Task task){
        Task tempRecord = taskDao.findTaskByTaskId(task.getTaskId());
        if (tempRecord == null){
            return false;
        }else {
            task.setFinish(new Date());
            if(taskDao.save(task) != null){
                return true;
            }
        }
        return false;
    }
}
