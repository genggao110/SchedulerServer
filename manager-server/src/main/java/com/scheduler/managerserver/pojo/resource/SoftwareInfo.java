package com.scheduler.managerserver.pojo.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2020-05-21 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftwareInfo {
    String name;
    String value;
    String platform;
    String minVersion;
    String maxVersion;
}
