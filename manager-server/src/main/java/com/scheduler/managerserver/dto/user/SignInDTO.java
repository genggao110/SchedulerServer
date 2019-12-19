package com.scheduler.managerserver.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 登录DTO
 * @Author: wangming
 * @Date: 2019-12-19 11:33
 */
@Data
public class SignInDTO {
    @NotEmpty(message = "账户不能为空")
    private String name;

    @NotEmpty (message = "密码不能为空")
    private String password;
}
