package com.cabbage.mms.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import com.cabbage.mms.Entity.Employee;
import com.cabbage.mms.Entity.Result;
import com.cabbage.mms.service.EmployeeService;
import com.cabbage.mms.utils.ApplicationContextUtils;

@SuppressWarnings("all")
@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class ProfileController {

    public EmployeeService employeeService() {
        return (EmployeeService) ApplicationContextUtils.getBean("employeeService");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateProfile(@RequestBody Employee employee, HttpServletResponse response)
            throws IOException {
        Integer id = Integer.parseInt(SecurityUtils.getSubject().getPrincipal().toString());
        employeeService().updateEmployee(employee, id);
        // response.setContentType("text/html;charset=utf-8");
        // PrintWriter writer = response.getWriter();
        // writer.print("<script type='text/javascript'>alert('个人信息更新成功!');</script>");
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public Result returnProfile() {
        Integer id = Integer.parseInt(SecurityUtils.getSubject().getPrincipal().toString());
        Employee employee = employeeService().findProfileById(id);
        return Result.success(employee);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public Result hasAnyRoles() {
        Subject subject = SecurityUtils.getSubject();
        StringBuffer buffer = new StringBuffer();
        buffer.append(subject.hasRole("director") + ",");
        buffer.append(subject.hasRole("admin"));
        return Result.success(buffer.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/my/json", method = RequestMethod.GET)
    public String returnJSONProfile() {
        Integer id = Integer.parseInt(SecurityUtils.getSubject().getPrincipal().toString());
        Employee employee = employeeService().findProfileById(id);
        return employee.toString();
    }

}
