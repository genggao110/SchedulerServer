package com.scheduler.schedulerserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 备选服务列表
 * @Author: wangming
 * @Date: 2020-05-28 15:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionalDTO {
    List<ServicesMapping> servies;
}
