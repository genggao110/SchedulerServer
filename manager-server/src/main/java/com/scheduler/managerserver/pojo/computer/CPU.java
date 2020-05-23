package com.scheduler.managerserver.pojo.computer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2020-04-22 19:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CPU {
    String cpu_type;
    int cpu_core;
    String cpu_frequency;
}
