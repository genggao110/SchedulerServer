package com.scheduler.mdl.behavior;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据相关联的udx schema
 * @Author: wangming
 * @Date: 2019-12-30 10:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {
    String datasetReferenceId;
    String desceiption;
}
