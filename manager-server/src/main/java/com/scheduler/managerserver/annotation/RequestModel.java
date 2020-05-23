package com.scheduler.managerserver.annotation;

import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，同时支持将POST-Json/POST-Form/GET请求的参数封装为对象
 * @return
 * @author wangming
 * @date 2019/12/19 20:00
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Document
public @interface RequestModel {
}
