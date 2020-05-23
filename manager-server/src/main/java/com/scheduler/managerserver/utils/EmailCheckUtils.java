package com.scheduler.managerserver.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: wangming
 * @Date: 2019-12-25 16:51
 */
public class EmailCheckUtils {

    public static boolean checkEmailLegality(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
