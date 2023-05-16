package com.cabbage.mms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cabbage.mms.Entity.Employee;
import com.cabbage.mms.service.EmployeeService;
import com.cabbage.mms.utils.AlertUtils;
import com.cabbage.mms.utils.ApplicationContextUtils;
import com.github.pagehelper.PageInfo;

@SuppressWarnings("all")
@Controller
@RequestMapping("/emp")
// 目前只用http
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:8082" })
public class EmployeeController {

    public EmployeeService employeeService() {
        return (EmployeeService) ApplicationContextUtils.getBean("employeeService");
    }

    // 加几个配置bean
    @RequiresRoles(value = { "administrator", "user" })
    @GetMapping("/findById/{id}")
    @ResponseBody
    public String returnEmpProfile(@PathVariable("id") Integer id, HttpServletResponse response)
            throws IOException {
        Employee employee = employeeService().findProfileById(id);
        return employee.toString();
    }

    @PostMapping(value = "/update")
    public void updateEmployee(@RequestBody Employee employee, HttpServletResponse response)
            throws IOException {
        Integer id = Integer.parseInt(SecurityUtils.getSubject().getPrincipal().toString());
        employeeService().updateEmployee(employee, id);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print("<script type='text/javascript'>alert('更新成功！');</script>");
    }

    @RequiresRoles(value = { "administrator", "user" })
    @GetMapping("/findAll")
    @ResponseBody
    public PageInfo<Employee> getPagedAllEmps(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "count", defaultValue = "false") boolean count, HttpServletResponse response)
            throws AuthenticationException {
        Integer departId = employeeService()
                .findById(Integer.parseInt(SecurityUtils.getSubject().getPrincipal().toString()))
                .getDepartId();
        System.out.println(employeeService().findAll(departId, pageNum, count).toString());
        return employeeService().findAll(departId, pageNum, count);
    }

    @ResponseBody
    @RequiresRoles(value = { "director", "user" })
    @GetMapping("/findNotBinded")
    public PageInfo<Employee> getPagedAllEmpsNotBinded(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "count", defaultValue = "false") boolean count, HttpServletResponse response) {
        return employeeService().findAllNotBinded(pageNum, count);
    }

}
