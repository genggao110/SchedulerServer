package com.scheduler.managerserver.config;

import com.scheduler.managerserver.resolver.RequestHandlerMethodArgumentResolver;
import com.scheduler.webCommons.interceptor.TimeCalculateInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: wangming
 * @Date: 2019-12-19 21:02
 */
@Configuration
public class ApiWebMvcConfigurerAdapter implements WebMvcConfigurer{

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestHandlerMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new TimeCalculateInterceptor();
    }
}
