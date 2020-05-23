package com.scheduler.managerserver.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Author: wangming
 * @Date: 2019-12-24 17:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Validate {

    @Id
    String id;
    String userId;
    String email;
    String verifyCode;
    // 默认为email，方便以后新增需求（例如手机类型）
    String type;
    Date create;
    Date modified;
}
