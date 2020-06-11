package com.scheduler.schedulerserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2020-05-28 15:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicesMapping {
    String pid;
    List<String> serviceUrls;
}
