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
@RequestMapping(value = "/builder")
public class ModelBuilderController {

    @RequestMapping(value="/computationalModel",method = RequestMethod.GET)
    public ModelAndView getComputational(HttpServletRequest req) {
        System.out.println("computational model builder");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("computational_builder");


        return modelAndView;

    }


    @RequestMapping(value="/logicalModel",method = RequestMethod.GET)
    public ModelAndView getLogical(HttpServletRequest req) {
        System.out.println("logical model builder");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("logical_builder");


        return modelAndView;

    }
}
