package com.qkd.customerservice.bean;

/**
 * Created on 1/4/21 15:29
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
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