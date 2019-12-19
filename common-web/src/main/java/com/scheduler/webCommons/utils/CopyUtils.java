package com.scheduler.webCommons.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * java bean的拷贝
 * @Author: wangming
 * @Date: 2019-12-19 11:38
 */
public class CopyUtils {

    public static String[] getNullPropertyName (Object resource){
        final BeanWrapper src = new BeanWrapperImpl(resource);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {emptyNames.add(pd.getName());}
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 只拷贝不为null属性
     * @param src
     * @param target
     * @return void
     * @author wangming
     * @date 2019/12/19 14:10
     */
    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyName(src));
    }
}
