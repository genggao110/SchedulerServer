package com.scheduler.schedulerserver.domain.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-12-03 15:12
 */
@Data
@JacksonXmlRootElement(localName = "TaskConfiguration")
public class TaskConfiguration {

    @JacksonXmlProperty(localName = "uid", isAttribute = true)
    private String uid;

    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "version" ,isAttribute = true)
    private String version;

    @JacksonXmlProperty(localName = "Model")
    @JacksonXmlElementWrapper(localName = "Models")
    private LinkedList<Model> models;

    @Id
    private String id;
}
