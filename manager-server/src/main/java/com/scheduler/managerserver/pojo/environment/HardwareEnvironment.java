package com.scheduler.managerserver.pojo.environment;

import com.scheduler.managerserver.pojo.computer.Hardware;
import com.scheduler.managerserver.pojo.computer.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2020-04-22 19:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HardwareEnvironment {
    Hardware staticInfo;
    Optional dynamicInfo;
}
