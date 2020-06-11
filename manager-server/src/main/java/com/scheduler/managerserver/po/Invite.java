package com.scheduler.managerserver.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class Invite {
    @Id
    String id;

    String oid;
    String project;
    String model;
    String modelName;
    String invite;
    String invited;
    int status;  //0 1

    Date createDate;
}
