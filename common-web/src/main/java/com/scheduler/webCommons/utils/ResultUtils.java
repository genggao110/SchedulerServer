package com.scheduler.webCommons.utils;

import com.scheduler.webCommons.bean.JsonResult;
import com.scheduler.webCommons.enums.ResultEnum;

/**
 * @Author: wangming
 * @Date: 2019-12-03 20:58
 */
public class ResultUtils {

    public static JsonResult success(){
        return success(null);
    }

    public static JsonResult success(Object obj) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setMsg(ResultEnum.SUCCESS.getMsg());
        jsonResult.setCode(ResultEnum.SUCCESS.getCode());
        jsonResult.setData(obj);
        return jsonResult;
    }

    public static JsonResult error(Integer code, String msg){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(code);
        jsonResult.setMsg(msg);
        jsonResult.setData(null);
        return jsonResult;
    }
}
