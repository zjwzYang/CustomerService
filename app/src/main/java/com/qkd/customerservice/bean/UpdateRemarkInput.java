package com.qkd.customerservice.bean;

/**
 * Created on 1/22/21 09:31
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class UpdateRemarkInput {

    private String openId;
    private String remark;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}