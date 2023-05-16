package com.cabbage.mms.Exception;

import org.apache.shiro.subject.Subject;
import lombok.AllArgsConstructor;

/**
 * The exception class for exceptions in login requests
 * 
 * @author 陈铕为
 * @version 0.1
 */
@AllArgsConstructor
public class LoginException extends Exception {

    public String userName;

    public Subject subject;

    public String message;

    public LoginException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return userName + message + "\n是否登录：" + subject.isAuthenticated();
    }
}