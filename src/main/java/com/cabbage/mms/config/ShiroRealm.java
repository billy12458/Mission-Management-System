package com.cabbage.mms.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import com.cabbage.mms.Entity.Employee;
import com.cabbage.mms.utils.ApplicationContextUtils;
import com.cabbage.mms.service.EmployeeService;

/**
 * The custom realm for handling login requests
 * 
 * @author 86136
 * @version 0.1
 */
@SuppressWarnings("all")
public class ShiroRealm extends AuthorizingRealm {

    /**
     * The main method for handling authorization requests,which is currently stuck
     * in the code for convenience
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 登录成功后进行授权，粗粒度管理
        EmployeeService impl = (EmployeeService) ApplicationContextUtils.getBean("employeeService");
        Integer userName = Integer.parseInt(principalCollection.getPrimaryPrincipal().toString());
        Employee employee = impl.findById(userName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (!ObjectUtils.isEmpty(employee)) {
            if (employee.getType() == 0) {
                info.addRole("user");
                info.addRole("employee");
            }
            if (employee.getType() == 1) {
                info.addRole("user");
                info.addRole("director");
            }
            if (employee.getType() == 2) {
                info.addRole("user");
                info.addRole("administrator");
            }
        }
        return info;
    }

    /**
     * The main method for interacting with mysql database through mybatis framework
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        EmployeeService impl = (EmployeeService) ApplicationContextUtils.getBean("employeeService");
        UsernamePasswordToken token = (UsernamePasswordToken) arg0;
        Integer userName = Integer.parseInt(token.getPrincipal().toString());
        Employee employee = impl.findById(userName);
        if (!ObjectUtils.isEmpty(employee) && employee.getStatus() == 0) {
            return new SimpleAuthenticationInfo(employee.getId(), employee.getPassword(),
                    ByteSource.Util.bytes(employee.getSalt()), this.getName());
        } else if (employee.getStatus() == 1) {
            throw new LockedAccountException("此账户已锁定！请联系工作人员");
        }
        return null;
    }

}
