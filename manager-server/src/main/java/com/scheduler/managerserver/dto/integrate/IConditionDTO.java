package com.scheduler.managerserver.dto.integrate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 局部优化条件，目前只包含pid进行测试，后期包含用户分配的权重矩阵
 * @Author: wangming
 * @Date: 2020-05-24 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IConditionDTO {
    String pid;
}
