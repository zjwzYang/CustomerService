package com.qkd.customerservice.bean;

/**
 * Created on 12/3/20 13:15
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExpressionType {
    private int resId;

    public ExpressionType(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return "ExpressionType{" +
                "resId=" + resId;
    }
}
