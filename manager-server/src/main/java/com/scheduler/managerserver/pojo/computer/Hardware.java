package com.scheduler.managerserver.pojo.computer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 硬件环境信息,暂时不支持GPU
 * @Author: wangming
 * @Date: 2020-04-22 19:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hardware {
    String cpu_type;
    int cpu_core;
    String cpu_frequency;
    String memory_size;
    String band_width;
    String disk_size;
    String disk_all;
}
