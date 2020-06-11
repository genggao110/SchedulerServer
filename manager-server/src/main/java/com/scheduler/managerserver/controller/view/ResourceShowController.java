package com.scheduler.managerserver.controller.view;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wangming
 * @Date: 2020-05-20 10:51
 */
@RestController
@RequestMapping(value = "/list")
public class ResourceShowController {

    @RequestMapping(value="/model",method = RequestMethod.GET)
    public ModelAndView getModelItems(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("model_list");
        return modelAndView;

    }

    @RequestMapping(value="/modelInfo/{id}",method = RequestMethod.GET)
    public ModelAndView getModelInfo(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("oid",id);
        modelAndView.setViewName("model_info");
        return modelAndView;

    }

    @RequestMapping(value="/data",method = RequestMethod.GET)
    public ModelAndView getdatas(HttpServletRequest req) {
        System.out.println("data list");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("data_list");


        return modelAndView;

    }

    @RequestMapping(value="/dataInfo",method = RequestMethod.GET)
    public ModelAndView getdatainfo(HttpServletRequest req) {
        System.out.println("data info");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("data_info");


        return modelAndView;

    }

    @RequestMapping(value="/compute",method = RequestMethod.GET)
    public ModelAndView getcomputes(HttpServletRequest req) {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("compute_list");

        return modelAndView;

    }
}
