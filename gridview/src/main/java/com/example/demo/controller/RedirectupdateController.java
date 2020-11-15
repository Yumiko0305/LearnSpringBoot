package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RedirectupdateController {
    @RequestMapping(value = "/templates/updateUserInfo.html",method = RequestMethod.GET)
    public ModelAndView updateUserInfoPage(String id) {
        ModelAndView modelAndView = new ModelAndView();
        //设置模型数据
        modelAndView.addObject("id",id);
        //设置视图名称
        modelAndView.setViewName("updateUserInfo");
        System.out.print(id);
        return modelAndView;
    }
}
