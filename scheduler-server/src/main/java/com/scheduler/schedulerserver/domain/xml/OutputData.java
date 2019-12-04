package com.scheduler.schedulerserver.domain.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.ArrayList;

/**
 * @Author: wangming
 * @Date: 2019-12-03 15:17
 */
@Data
public class OutputData {

    @JacksonXmlProperty(localName = "DataTemplate")
    @JacksonXmlElementWrapper(useWrapping = false)
    private ArrayList<DataTemplate> outputs;
}
