//package com.scheduler.managerserver.config;
//
//import com.scheduler.managerserver.interceptor.AuthenticationInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * token认证配置类
// * @Author: wangming
// * @Date: 2019-12-19 15:12
// */
//@Configuration
//public class AuthenticationConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authenticationInterceptor())
//                .addPathPatterns("/**");
//    }
//
//    @Bean
//    public AuthenticationInterceptor authenticationInterceptor(){
//        return new AuthenticationInterceptor();
//    }
//}