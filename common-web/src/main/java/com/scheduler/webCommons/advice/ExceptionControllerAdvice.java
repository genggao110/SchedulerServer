package com.scheduler.webCommons.advice;

import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.exception.MyException;
import com.scheduler.webCommons.utils.ResultUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: wangming
 * @Date: 2019-12-03 20:52
 */
@ControllerAdvice
@ResponseBody
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<JsonResult> defaultErrorHandler(Exception e){
        //自定义异常
        if(e instanceof MyException){
            MyException myException = (MyException)e;
            return ResponseEntity.status(HttpStatus.OK).body(ResultUtils.error(myException.getCode(), myException.getMessage()));
        }else{
            //未定义的其他异常，表现为服务器内部的异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultUtils.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage()));
        }
    }
}
