package com.scheduler.managerserver.service.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.scheduler.managerserver.po.User;
import org.springframework.stereotype.Service;

/**
 * @Author: wangming
 * @Date: 2019-12-19 11:00
 */
@Service
public class TokenService {
    public String getToken(User user){
        //TODO 添加自定义信息
        String token = "";
        token = JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
