package com.cabbage.mms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cabbage.mms.Entity.Result;
import com.cabbage.mms.Entity.Status;
import com.cabbage.mms.annotation.ShiroLogout;

/**
 * The controller for handling logout requests
 * 
 * @author 86136
 * @version 0.1
 */
@SuppressWarnings("all")
@SuppressAjWarnings
@RestController
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:8082" })
public class LogoutController {

    @Autowired
    public RedisTemplate redisTemplate;

    @ShiroLogout(isLogout = true)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result logoutProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            subject.logout();
            return Result.fail("您未登录，请先登录！");
        } else if (subject.isAuthenticated()) {
            subject.logout();
            return Result.success("您已成功退出！");
        }
        return null;

    }

}