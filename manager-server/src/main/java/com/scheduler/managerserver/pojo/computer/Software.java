package com.scheduler.managerserver.pojo.computer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2020-04-22 19:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Software {
    String name;
    String version;
    String publisher;
    String type;
    String platform;
}
