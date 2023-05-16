package com.cabbage.mms.Entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一返回类
 * 
 * @author 陈铕为
 */
@Data
public class Result implements Serializable {
    
    // 返回的响应号 200-ok
    private int code;
    // 返回的响应消息
    private String msg;
    // 返回的响应数据
    private Object Data;

    public static Result success(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result success(Object data) {
        return success(200, "操作成功", data);
    }

    public static Result fail(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg, Object data) {
        return fail(400, msg, data);
    }

    public static Result fail(String msg) {
        return fail(400, msg, null);
    }

}
