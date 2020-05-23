package com.scheduler.managerserver.po;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * 部署任务数据库存储实体
 * @Author: wangming
 * @Date: 2020-05-22 10:14
 */
@Data
public class DeployTask {
    @Id
    String id;
    /**
     * task server ip
     */
    String ip;
    int port;
    /**
     * 部署任务具体部署的计算资源
     */
    String mac;
    /**
     * 模型部署任务的执行状态， -1表示失败，0表示还在部署中，1代表模型部署成功
     */
    int status;
    /**
     * task server返回的部署任务id
     */
    String taskId;
    /**
     * 与门户绑定的计算模型条目
     */
    String oid;
    /**
     * 待部署模型的名字
     */
    String modelName;

    /**
     * 模型服务容器的类型，1代表公网类型，2代表是局域网类型
     */
    int type;
}
