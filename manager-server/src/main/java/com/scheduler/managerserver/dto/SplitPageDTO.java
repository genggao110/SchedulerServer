package com.scheduler.managerserver.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-12-19 11:31
 */
@Data
public abstract class SplitPageDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private Boolean asc = false;
    private List<String> properties;
}
