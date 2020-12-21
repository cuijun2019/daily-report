package com.etone.project.base.type;

/**
 * 该类描述
 *
 * @author <a href="mailto:417877417@qq.com">menergy</a>
 *         DateTime: 14-10-9  下午5:23
 */
public class VerifyResult {

    public int Code;

    public String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
