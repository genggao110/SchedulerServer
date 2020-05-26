package com.scheduler.schedulerserver.domain.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * @Author: wangming
 * @Date: 2019-12-03 15:11
 */
@Data
public class Model {

    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "pid", isAttribute = true)
    private String pid;

    @JacksonXmlProperty(localName = "description", isAttribute = true)
    private String description;

    @JacksonXmlProperty(localName = "modelServiceUrl", isAttribute = true)
    private String modelServiceUrl;

    @JacksonXmlProperty(localName = "OutputData")
    private OutputData outputData;

    @JacksonXmlProperty(localName = "InputData")
    private InputData inputData;

    /**
     * 0代表未开始，-1代表运行失败，1代表运行成功, 2代表运行超时
     */
    private int status = 0;

    private String taskIpAndPort;
}
