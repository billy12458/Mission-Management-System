package com.cabbage.mms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The basic controller for going to different pages
 * 
 * @author 陈铕为
 * @version 0.1
 */
@SuppressWarnings("all")
@Controller
@CrossOrigin(origins = "http://localhost:8081")
public class PageController {

    /*
     * @RequestMapping("/{page}")
     * public ModelAndView returnPageModelAndView(@PathVariable("page") String page)
     * {
     * return new ModelAndView(page);
     * }
     */
}
