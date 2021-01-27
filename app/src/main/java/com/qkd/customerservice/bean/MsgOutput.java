package com.qkd.customerservice.bean;

/**
 * Created on 1/25/21 16:04
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class MsgOutput {

    /**
     * code : 200
     * message : 消息发送成功
     * flag : true
     * data : null
     */

    private int code;
    private String message;
    private boolean flag;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}