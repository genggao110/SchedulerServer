package com.scheduler.schedulerserver.domain;

import com.scheduler.schedulerserver.domain.xml.Model;
import com.scheduler.schedulerserver.dto.ServicesMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-12-03 14:51
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    private String id;
    private String uid;
    private String taskId;
    private String name;
    private String version;
    private String description;
    private List<Model> models;
    /**
     * 0-started, 1 - finished, -1 - failed
     */
    private int status;
    private Date date;
    private String userName;
    private Date finish;
    private List<ServicesMapping> servicesMappings;
}
