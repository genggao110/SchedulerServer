package com.scheduler.webCommons.enums;

public enum ResultEnum {

    SUCCESS(0, "成功"),
    DEFAULT_EXCEPTION(-1, "默认的服务器内部异常，我并不想进行处理！！"),
    NO_OBJECT(-2, "没有对应的对象"),
    EXIST_OBJECT(-3, "同名对象已存在，请更换名称"),
    NO_TOKEN(-4, "Missing Token"),
    TOKEN_WRONG(-5, "Token Wrong,请重新登录"),
    USER_PASSWORD_NOT_MATCH(-6, "账户名和密码不匹配"),
    UPLOAD_TYPE_ERROR(-7,"上传文件的类型不支持"),
    QUERY_TYPE_ERROR(-8,"查询类型不支持"),
    REMOTE_SERVICE_ERROR(-9,"远程服务调用出错"),
    CERTIFICATE_ERROR(-10,"证书生成出错")
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg){
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
