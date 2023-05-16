package com.cabbage.mms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.cabbage.mms.Entity.Employee;
import com.cabbage.mms.Entity.Result;
import com.cabbage.mms.Exception.LoginException;
import com.cabbage.mms.annotation.ShiroLogin;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@SuppressWarnings("all")
@RestController
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:8082" })
public class LoginController {

    @RequestMapping(value = "/user/getImage", method = RequestMethod.GET)
    public void getImage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        GifCaptcha captcha = new GifCaptcha(150, 50, 5);
        response.setContentType("img/png");
        CaptchaUtil.out(captcha, request, response);
        request.getSession().setAttribute("code", captcha.text());
    }

    @ShiroLogin(isLogin = true)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result loginResult(@RequestBody Employee user, HttpServletRequest request,
            HttpServletResponse response) throws NoSuchAlgorithmException, IOException, LoginException {
        Subject subject = SecurityUtils.getSubject();
        // String sessionCode = request.getSession().getAttribute("code").toString();
        // if (!subject.isAuthenticated() &&
        // user.getCode().equalsIgnoreCase(sessionCode)) {
        UsernamePasswordToken token = new UsernamePasswordToken(String.valueOf(user.getId()),
                user.getPassword(), true);
        // 默认是开启记住我的
        subject.login(token);
        // } else {
        // throw new LoginException(user.getId(), subject, "您的用户名/密码有误，请重新登录！");
        // }
        return Result.success(200, "登录成功", "登录成功");
    }
}
