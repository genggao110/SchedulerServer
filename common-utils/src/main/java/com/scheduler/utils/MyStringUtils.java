package com.scheduler.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: wangming
 * @Date: 2019-11-28 09:54
 */
public class MyStringUtils {

    public static String replaceBlank(String str){
        String dest = "";
        if(str != null){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * @Description: 通用方法，比较版本号的大小，前者大返回一个正数，后者大返回一个负数，相等则返回0
     * @Param: [version1, version2]
     * @return: int
     * @Author: WangMing
     * @Date: 2019/4/22
     */
    public static int compareAppVersion(String version1, String version2){
        if(version1 == null || version2 == null){
            throw new RuntimeException("版本号不能为空");
        }
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");

        int index = 0;
        //取数组最小长度值
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        //先比较长度，再比较字符
        while (index < minLength
                && (diff = versionArray1[index].length() - versionArray2[index].length()) == 0
                && (diff = versionArray1[index].compareTo(versionArray2[index])) == 0){
            index++;
        }

        //如果已经分出大小，则直接返回，如果未区分出大小，则再进行比较位数，有子版本的为大
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
}
