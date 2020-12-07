package com.qkd.customerservice.bean;

import java.io.Serializable;

/**
 * Created on 12/3/20 13:15
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class Expression implements Serializable {

    private int resId;
    private String unique;

    public Expression(int resId, String unique) {
        this.resId = resId;
        this.unique = unique;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "resId=" + resId +
                ", unique='" + unique + '\'' +
                '}';
    }
}
