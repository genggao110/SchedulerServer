package com.scheduler.managerserver.dto.integrate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QoS评价因素所对应的权重值
 * @Author: wangming
 * @Date: 2020-05-28 03:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QoSFactor {
    double performance;
    double reliability;
    double availability;
    double reputation;
    double cpu_performance;
    double memory_size;
}
