package com.cabbage.mms.controller;

import java.io.IOException;
// import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cabbage.mms.Entity.Employee;
// import com.cabbage.mms.Exception.RegisterException;
import com.cabbage.mms.service.EmployeeService;
import com.cabbage.mms.utils.ApplicationContextUtils;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;

@CrossOrigin
@RestController
public class RegisterController {

    // 如果不行就用之前的老办法
    // @Qualifier("employeeService")
    // @Autowired
    // public EmployeeService employeeService;

    public EmployeeService employeeService() {
        return (EmployeeService) ApplicationContextUtils.getBean("employeeService");
    }

    @RequestMapping(value = "/register/getImage", method = RequestMethod.GET)
    public void getImage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        GifCaptcha captcha = new GifCaptcha(150, 50, 5);
        response.setContentType("img/png");
        CaptchaUtil.out(captcha, request, response);
        request.getSession().setAttribute("registerCode", captcha.text());
    }

    // 想一想要跳转到哪里
    @RequestMapping(value = "/register/sure", method = RequestMethod.POST)
    public String registerSubmit(@RequestBody Employee employee, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            // ModelMap map = new ModelMap();
            // String registerCode =
            // request.getSession().getAttribute("registerCode").toString();
            // if (employee.getCode().equalsIgnoreCase(registerCode)) {
            employeeService().saveEmployee(employee);
            // response.setContentType("text/html;charset=utf-8");
            // PrintWriter writer = response.getWriter();
            // writer.print("<script type='text/javascript'>alert('注册成功!');</script>");
            // 可能会有的跳转页面
            return "注册成功";
        } catch (Exception exception) {
            return "注册失败!";
        }
    }

}
