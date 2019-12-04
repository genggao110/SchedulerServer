package com.scheduler.schedulerserver.dao;

import com.scheduler.schedulerserver.domain.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskDao extends MongoRepository<Task, String> {

    Task findTaskByTaskId(String taskId);
}
