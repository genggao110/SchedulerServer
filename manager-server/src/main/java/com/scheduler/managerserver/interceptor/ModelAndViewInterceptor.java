package com.scheduler.managerserver.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Component
public class ModelAndViewInterceptor implements HandlerInterceptor {

    /**
     * 判断session 是否有值
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if(modelAndView!=null) {
            HttpSession session = request.getSession();
            if (session.getAttribute("uid") == null) {
                modelAndView.addObject("logged", false);
            } else {
                Map<String,String> user = new HashMap<>();
                user.put("oid",session.getAttribute("oid").toString() );
                user.put("uid",session.getAttribute("uid").toString() );
                user.put("name",session.getAttribute("name").toString() );
                modelAndView.addObject("user", user);
                modelAndView.addObject("logged", true);
            }
        }
    }
}
