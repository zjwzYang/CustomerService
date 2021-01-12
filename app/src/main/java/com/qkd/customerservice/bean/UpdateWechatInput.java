package com.qkd.customerservice.bean;

/**
 * Created on 1/4/21 15:29
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class UpdateWechatInput {
    private String openId;
    private int isAddWechat;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getIsAddWechat() {
        return isAddWechat;
    }

    public void setIsAddWechat(int isAddWechat) {
        this.isAddWechat = isAddWechat;
    }
}