package com.cabbage.mms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.cabbage.mms.Entity.Employee;
import com.cabbage.mms.Exception.ModifyException;
import com.cabbage.mms.service.EmployeeService;
import com.cabbage.mms.service.MailService;
import com.cabbage.mms.utils.AlertUtils;
import com.cabbage.mms.utils.ApplicationContextUtils;

@SuppressWarnings("all")
@Controller
public class ForgetPasswordController {

    public MailService mailService() {
        return (MailService) ApplicationContextUtils.getBean("mailService");
    }

    public EmployeeService employeeService() {
        return (EmployeeService) ApplicationContextUtils.getBean("employeeService");
    }

    @RequestMapping(value = "/forgetSendEmail", method = RequestMethod.POST)
    public void sendRestEmail(@RequestBody Employee employee, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String authenticationCode = RandomStringUtils.randomAlphanumeric(8);
        request.getSession().setMaxInactiveInterval(60 * 15);
        request.getSession().setAttribute("emailCode", authenticationCode);
        mailService().sendMail(employee.getEmail(), authenticationCode);
        // AlertUtils.alertJS(response, "验证码发送成功");
    }

    @RequestMapping(value = "/forget", method = RequestMethod.POST)
    public ModelAndView returnModifyModelAndView(@RequestBody Employee employee, HttpServletRequest request) {
        // 也可以用三目运算符！
        int result = mailService().authenticateCode(employee, request);
        if (result == 0) {
            return new ModelAndView("redirect:/modify");
        } else {
            throw new RuntimeException("您的邮箱验证码有误，请重新获取！");
        }
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ModelAndView modifyPassword(@RequestBody Employee employee, HttpServletResponse response)
            throws ModifyException {
        String saltUpdate = RandomStringUtils.randomAlphanumeric(8);
        int result = employeeService().updatePasswordByEmail(employee.getPassword(), saltUpdate, employee.getEmail());
        if (result == 1) {
            return new ModelAndView("redirect:/login");
        } else {
            throw new ModifyException(employee.getId(), "密码更新失败，请重试！");
        }
    }
}