package com.scheduler.managerserver.dto.computer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2020-05-21 22:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendComputer {
    String oid;
    double score;
    String mac;
}
