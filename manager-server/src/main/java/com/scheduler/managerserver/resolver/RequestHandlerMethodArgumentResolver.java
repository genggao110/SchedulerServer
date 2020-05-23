package com.scheduler.managerserver.resolver;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scheduler.managerserver.annotation.RequestModel;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的请求方法参数处理器
 * @Author: wangming
 * @Date: 2019-12-19 20:02
 */
public class RequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestModel.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if(StringUtils.equals(request.getMethod(), "POST")){
            //处理Post类型的请求
            if (request.getContentType().contains("application/json")){
                return this.doPostJson(methodParameter, request);
            }else{
                //处理form 表单类型的
                return this.doGetAndPostForm(methodParameter, nativeWebRequest);
            }
        }else if(StringUtils.equals(request.getMethod(), "GET")){
            //处理Get类型的请求
            return this.doGetAndPostForm(methodParameter, nativeWebRequest);
        }

        return methodParameter.getParameterType().newInstance();
    }


    private Object doPostJson(MethodParameter parameter, HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        int len;
        while ((len = reader.read(buf)) != -1) {
            sb.append(buf, 0, len);
        }
        Class<?> parameterType = parameter.getParameterType();
        return JSON.parseObject(sb.toString(), parameterType);
    }

    /**
     * 处理 Get请求、Form表单形式的Post请求
     * @param parameter
     * @param webRequest
     * @return java.lang.Object
     * @author wangming
     * @date 2019/12/19 20:53
     */
    private Object doGetAndPostForm(MethodParameter parameter, NativeWebRequest webRequest) throws IllegalAccessException, InstantiationException, IOException {

        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        Class<?> parameterType = parameter.getParameterType();
        if (parameterMap == null || parameterMap.size() <= 0) {
            return parameterType.newInstance();
        }
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String[] arr = entry.getValue();
            if (ArrayUtils.isNotEmpty(arr)) {
                map.put(entry.getKey(), arr[0]);
            }
        }
        ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String json = mapper.writeValueAsString(map);
        return mapper.readValue(json,parameterType);
    }

}
