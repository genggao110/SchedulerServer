package com.scheduler.managerserver.pojo.computer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 计算资源动态环境信息，用于择优选择
 * @Author: wangming
 * @Date: 2020-04-22 19:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Optional {
    String cpu_rate;
    String memory_rate;
}
