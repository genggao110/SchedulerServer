package com.scheduler.managerserver.dto.integrate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2020-05-28 03:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QosEstimateHelp {
    double performance;
    double reliability;
    double availability;
    double reputation;
    double cpu_performance;
    double memory_size;
    String sid;
    String host;
    String port;
}
