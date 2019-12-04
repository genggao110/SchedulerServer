package com.scheduler.webCommons.enums;

public enum ResultEnum {

    SUCCESS(1, "suc"),
    NO_OBJECT(-1, "err");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
