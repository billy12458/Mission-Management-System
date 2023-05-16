package com.cabbage.mms.aop;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.cabbage.mms.Entity.Status;
import io.swagger.annotations.Api;

@Aspect
@Component
@SuppressWarnings("all")
@SuppressAjWarnings
@Api(value = "the aspect class for employee status")
public class StatusAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("execution(public * com.cabbage.mms.controller.*.*(..))")
    public void writeRedis() {

    }

    @Pointcut("execution(public * com.cabbage.mms.controller.*.*(..)) && @annotation(com.cabbage.mms.annotation.ShiroLogin)")
    public void loginRedis() {

    }

    @Pointcut("execution(public * com.cabbage.mms.controller.*.*(..)) && @annotation(com.cabbage.mms.annotation.ShiroLogout)")
    public void logoutRedis() {

    }

    // 先这么写着。后面改一改
    @After("loginRedis()")
    public void doAfterLogin() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Integer id = getIdAsInteger(subject.getPrincipal().toString());
            Status status = (Status) redisTemplate.opsForValue().get(id);
            status.setLastLoginTime(getTime());
            status.setOnline(true);
            redisTemplate.opsForValue().set(id, status);
        }
    }

    // @After("writeRedis()")
    // public void doAfterVisit(JoinPoint joinPoint) {
    // Subject subject = SecurityUtils.getSubject();
    // Integer id = getIdAsInteger(subject.getPrincipal().toString());
    // Status status = (Status) redisTemplate.opsForValue().get(id);
    // status.setLastVistedTime(getTime());
    // redisTemplate.opsForValue().set(id, status);
    // }

    @Before("logoutRedis()")
    public void doLogoutRedis() {
        Subject subject = SecurityUtils.getSubject();
        Integer id = getIdAsInteger(subject.getPrincipal().toString());
        Status status = (Status) redisTemplate.opsForValue().get(id);
        status.setLastLogoutTime(getTime());
        status.setOnline(false);
        redisTemplate.opsForValue().set(id, status);
    }

    private Integer getIdAsInteger(String id) {
        return Integer.parseInt(id);
    }

    private String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
