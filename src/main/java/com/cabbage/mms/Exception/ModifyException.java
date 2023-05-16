package com.cabbage.mms.Exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * The exception class for exceptions in login requests
 * 
 * @author 陈铕为
 * @version 0.1
 */
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ModifyException extends Exception {

    public Integer userName;

    public String message;

    public ModifyException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return userName + ":" + message;
    }
}
