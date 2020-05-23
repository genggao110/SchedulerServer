package com.scheduler.webCommons.exception;

import com.scheduler.webCommons.enums.ResultEnum;

/**
 * @Author: wangming
 * @Date: 2019-12-03 20:49
 */
public class MyException extends RuntimeException {

    private Integer code;

    public static MyException noObject(){
        return new MyException(ResultEnum.NO_OBJECT);
    }

    public MyException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode(){
        return code;
    }
}
