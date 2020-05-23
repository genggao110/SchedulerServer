package com.scheduler.managerserver.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.scheduler.managerserver.annotation.PassToken;
import com.scheduler.managerserver.annotation.UserLoginToken;
import com.scheduler.managerserver.po.User;
import com.scheduler.managerserver.service.UserService;
import com.scheduler.managerserver.utils.JwtTokenUtil;
import com.scheduler.webCommons.enums.ResultEnum;
import com.scheduler.webCommons.exception.MyException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 验证拦截器，拦截token
 * @Author: wangming
 * @Date: 2019-12-19 11:12
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = JwtTokenUtil.getTokenFromRequest(request);
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if(method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if(userLoginToken.required()){
                //执行认证,错误抛出
                if (token == null || !token.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
                    response.setStatus(401);
                    throw new MyException(ResultEnum.NO_TOKEN);
                }
                Claims claims = JwtTokenUtil.getTokenBody(token);
                //获取token中的user id
                String userId = (String) claims.get("id");
                //如果查询不存在，错误已经被处理
                User user = userService.findUserById(userId);
                if(!user.getPassword().equals((String)claims.get("password"))){
                    response.setStatus(401);
                    throw new MyException(ResultEnum.USER_PASSWORD_NOT_MATCH);
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
