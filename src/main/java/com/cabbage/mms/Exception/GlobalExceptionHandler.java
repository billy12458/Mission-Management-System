package com.cabbage.mms.Exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * The basic ExceptionHandler for all kinds of exceptions,currently all kinds of
 * exception
 * 
 * @author 86136
 * @version 0.1
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    public Environment env;

    // 先写一个Exception，后面会添加自定义的异常类
    @ExceptionHandler(value = Exception.class)
    public ModelAndView errorPage(Exception allException, HttpServletRequest request, HttpServletResponse response) {
        ModelMap map = new ModelMap();
        map.addAttribute("reason", allException.getMessage());
        map.addAttribute("telephone", env.getProperty("author.telephone"));
        map.addAttribute("email", env.getProperty("author.email"));
        map.addAttribute("url", request.getRequestURI());
        // 可以根据前端页面是如何展示的来多添加一些参数
        return new ModelAndView("404", map);
    }

}
