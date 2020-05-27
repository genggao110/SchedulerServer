package com.scheduler.managerserver.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模型集成
 * @Author: wangming
 * @Date: 2020-05-26 10:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComputerServerInfo {
    String oid;
    String name;
    String description;
    String author_name;
    String author_oid;
    String contentType;
    String createTime;
    String pid;
    String mdlJson;
    String mdl;
}
