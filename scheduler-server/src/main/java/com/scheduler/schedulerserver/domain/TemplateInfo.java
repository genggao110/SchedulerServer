package com.scheduler.schedulerserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2020-05-25 22:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateInfo {
    String type;
    String value;
}
