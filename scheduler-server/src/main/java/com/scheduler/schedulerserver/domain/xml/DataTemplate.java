package com.scheduler.schedulerserver.domain.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * @Author: wangming
 * @Date: 2019-12-03 15:16
 */
@Data
public class DataTemplate {

    @JacksonXmlProperty(localName = "state", isAttribute = true)
    private String state;

    @JacksonXmlProperty(localName = "event", isAttribute = true)
    private String event;

    @JacksonXmlProperty(localName = "value", isAttribute = true)
    private String value;

    @JacksonXmlProperty(localName = "dataId", isAttribute = true)
    private String dataId;

    @JacksonXmlProperty(localName = "type", isAttribute = true)
    private String type;

    //标记这个数据是否已经准备好,设置默认值
    private boolean isPrepared = false;
}
