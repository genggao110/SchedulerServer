package com.scheduler.managerserver.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-12-19 11:31
 */
@Data
public abstract class BaseSplitPageDTO {
     Integer page = 1;
     Integer pageSize = 10;
     Boolean asc = false;
     List<String> properties;
}
