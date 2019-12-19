package com.scheduler.managerserver.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: wangming
 * @Date: 2019-12-19 11:34
 */
@Data
public class AddUserDTO {

    @NotBlank (message = "用户名不能为空")
    String name;
    @NotBlank(message = "密码不能为空")
    String password;
    @NotBlank(message = "用户邮箱不能为空")
    String email;
}
