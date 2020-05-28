package com.scheduler.managerserver.dto.integrate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 经过QoS局部优化后推荐的计算资源服务url，已经备选的url
 * url的组织结构是： http://(task server ip):(task server port)/task/invoke/sid
 * @Author: wangming
 * @Date: 2020-05-25 11:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectResult {
    String recommend;
    List<String> optional;
}
