package com.scheduler.managerserver.dto.modelresource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模型部署包进行部署所需相关信息
 * @Author: wangming
 * @Date: 2020-05-22 09:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelDeployDTO {
    /**
     * 模型部署包的获取url
     */
    String packagePath;
    /**
     * task server在manager server中的id,该字段用于获取Task Server的ip和port
     */
    String agentId;
    /**
     * 模型服务容器的唯一标识符
     */
    String mac;
    /**
     * 模型服务容器的类型，1代表公网类型，2代表是局域网类型
     */
    int type;
    /**
     * 部署任务的发起者
     */
    String userName;
    /**
     * 待部署模型的名字
     */
    String modelName;
    /**
     * 部署包的md5值
     */
    String md5;
    /**
     * 与门户绑定的计算模型条目
     */
    String oid;
}
