package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

/**
 * Created on 12/15/20 09:38
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class TokenBean extends BaseOutput {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}