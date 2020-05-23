package com.scheduler.managerserver.controller.view;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wangming
 * @Date: 2020-05-20 10:56
 */
@RestController
public class AdaptorRestController {
    @RequestMapping(value="/adaptor",method = RequestMethod.GET)
    public ModelAndView getModelItems(HttpServletRequest req) {
        System.out.println("adaptor");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adaptor");

        return modelAndView;

    }
}
