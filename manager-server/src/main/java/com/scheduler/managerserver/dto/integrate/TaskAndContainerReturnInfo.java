package com.scheduler.managerserver.dto.integrate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Manager Server返回的满足pid的模型容器相关信息
 * @Author: wangming
 * @Date: 2020-05-25 10:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskAndContainerReturnInfo {
    /**
     * task server id
     */
    String id;
    /**
     * task server host
     */
    String host;
    /**
     * task server port
     */
    String port;
    /**
     * model container mac
     */
    String mac;
    /**
     * 模型容器在task server数据库中存储的id,根据这个来拼凑执行url
     */
    String sid;
    int count;
}
