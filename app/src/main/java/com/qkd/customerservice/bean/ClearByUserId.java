package com.qkd.customerservice.bean;

/**
 * Created on 12/9/20 12:55
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ClearByUserId {
    private String userId;

    public ClearByUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}