package com.scheduler.mdl.environment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2019-12-30 10:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftwareConfigure {

    String name;
    String platform;
    String version;
}
