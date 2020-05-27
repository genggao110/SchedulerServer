package com.scheduler.managerserver.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
@Data
public class Project {
    @Id
    String id;

    String oid;
    String title;
    String description;
    String owner;//存储用户userName
    List<User> invitedUsers = new ArrayList<>();
    List<User> invitingUsers = new ArrayList<>();
    String model;
    String modelName;
    int status;//0 1ready
    Date createDate;
    String resource;//最终选择的计算资源

}
