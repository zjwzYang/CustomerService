package com.qkd.customerservice.bean;

import java.util.List;

/**
 * Created on 12/3/20 13:15
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExpressionType {
    private int resId;
    private List<Expression> expressionList;

    public ExpressionType(int resId, List<Expression> expressionList) {
        this.resId = resId;
        this.expressionList = expressionList;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }

    @Override
    public String toString() {
        return "ExpressionType{" +
                "resId=" + resId +
                ", expressionList=" + expressionList +
                '}';
    }
}
