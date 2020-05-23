package com.scheduler.managerserver.dao;

import com.scheduler.managerserver.po.DeployTask;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author: wangming
 * @Date: 2020-05-22 22:24
 */
public interface DeployTaskDao extends MongoRepository<DeployTask, String> {
    DeployTask findFirstByTaskId(String taskId);
    DeployTask findFirstById(String id);
}
