package com.scheduler.managerserver.utils;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * copy from sunlinzhi
 * @Author: wangming
 * @Date: 2019-12-19 22:14
 */
public class CookieUtils {
    public static void create(HttpServletResponse httpServletResponse, String name, String value) throws UnsupportedEncodingException {
        //Cookie只能存在ASCII字母数字字子服，不能包含逗号、分号或者空格，也不能以$字符开头
        //因此往往需要进行URL转义
        value = URLEncoder.encode(value, "UTF-8");
        Cookie cookie = new Cookie(name, value);
        // secure=true => 仅仅能在 HTTPS 连接中被浏览器传递到服务器端进行会话验证.
        cookie.setSecure(false);
        //使得 Javascript 脚本不能读取 cookies.
        cookie.setHttpOnly(true);
        //设置 Cookies 的过期值. maxAge=0 => 立即过期, maxAge=-1 => 永不过期
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }

    public static void clear(HttpServletResponse httpServletResponse, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    public static String getValue(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
        return cookie != null ? cookie.getValue() : null;
    }
}
