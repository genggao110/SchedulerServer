package com.scheduler.managerserver.vo;

import com.scheduler.mdl.MdlDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2019-12-28 22:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelInfoVO {

    String id;
    String name;
    String packagePath;
    String description;
    /**
    * 模型部署包的md5值，当用作地理模型服务的时候是唯一标识符
    */
    String pid;
    /**
     * 类型标识符，标识是部署包还是服务, 1 代表服务， 0 代表部署包
     */
    int type;
    MdlDocument mdlDocument;

}
