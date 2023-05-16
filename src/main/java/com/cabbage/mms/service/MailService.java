package com.cabbage.mms.service;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.cabbage.mms.Entity.Employee;

@Service("mailService")
public class MailService {

    @Autowired
    public JavaMailSender mailSender;

    public void sendMail(String email, String authenticationCode) {
        // String authenticationCode = RandomStringUtils.randomAlphanumeric(8);
        SimpleMailMessage simple = new SimpleMailMessage();
        simple.setSubject("别忘了修改密码");
        simple.setFrom("2740665479@qq.com");
        // message.setTo("10*****16@qq.com","12****32*qq.com");
        simple.setTo(email);
        simple.setSentDate(new Date());
        simple.setText("你的邮箱验证码为：" + authenticationCode + "，过期时间15分钟");
        mailSender.send(simple);
    }

    public int authenticateCode(Employee employee, HttpServletRequest request) {
        // 这里可以采用三目运算式！
        // return employee.getCode().equalsIgnoreCase(code) ? 0 : 1
        String code = request.getSession().getAttribute("emailCode").toString();
        if (employee.getCode().equalsIgnoreCase(code)) {
            return 0;
        } else {
            return 1;
        }
    }

}
