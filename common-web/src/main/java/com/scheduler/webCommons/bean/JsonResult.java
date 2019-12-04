package com.scheduler.webCommons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangming
 * @Date: 2019-12-03 20:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> {

    private Integer code;

    private String msg;

    private T data;
}
