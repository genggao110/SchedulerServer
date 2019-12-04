package com.scheduler.schedulerserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2019-12-03 16:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExDataDTO {
    String statename;
    String event;
    String url;
    String tag;
    String suffix;
}
