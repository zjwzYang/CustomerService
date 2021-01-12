package com.qkd.customerservice.bean;

import java.io.Serializable;

/**
 * Created on 12/4/20 13:38
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class MoreAction implements Serializable {
    private String actionName;
    private int actionReId;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getActionReId() {
        return actionReId;
    }

    public void setActionReId(int actionReId) {
        this.actionReId = actionReId;
    }

    @Override
    public String toString() {
        return "MoreAction{" +
                "actionName='" + actionName + '\'' +
                ", actionReId=" + actionReId +
                '}';
    }
}