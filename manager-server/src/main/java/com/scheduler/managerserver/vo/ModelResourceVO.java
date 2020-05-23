package com.scheduler.managerserver.vo;

import com.scheduler.managerserver.po.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2020-05-20 17:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelResourceVO {
    String oid;
    String name;
    String description;
    String detail;
    String author_name;
    String author_oid;
    String createTime;
    int viewCount;
    String contentType;
    String md5;
    String image;
    /**
     * 模型部署包的路径
     */
    String packagePath;
    String runtime;
    String mdlJson;
    /**
     * 类型标识符，标识该模型部署包是否已经存在相应的模型服务，如果存在就可以进行调用(跳转到门户调用界面)
     */
    int type;
    Resource resources;
}
