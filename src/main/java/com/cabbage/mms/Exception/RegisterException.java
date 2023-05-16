package com.cabbage.mms.Exception;

import lombok.AllArgsConstructor;

/**
 * The exception class for exceptions in login requests
 * 
 * @author 陈铕为
 * @version 0.1
 */
@AllArgsConstructor
public class RegisterException extends Exception {

    public String userName;

    public String message;

    public RegisterException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return userName + ":" + message;
    }
}