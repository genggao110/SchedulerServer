package com.scheduler.managerserver.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: wangming
 * @Date: 2019-12-25 10:28
 */
@Data
public class ResetPasswordDTO {
    @NotEmpty(message = "邮箱不能为空")
    private String email;

    @NotEmpty (message = "验证码不能为空")
    private String verifyCode;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "确认密码不能为空")
    private String confirmPassword;
}
