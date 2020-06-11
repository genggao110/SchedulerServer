package com.scheduler.managerserver.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-12-19 10:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {
    String name;
    String userName;
    String email;
    String oid;

}

