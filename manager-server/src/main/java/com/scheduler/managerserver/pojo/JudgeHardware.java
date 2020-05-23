package com.scheduler.managerserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 五个字段参与择优推荐
 * @Author: wangming
 * @Date: 2020-05-21 23:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeHardware {
    int cpu_core;
    double cpu_frequency;
    int memory_size;
    int band_width;
    int disk_size;
}
