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

    public static final int ACTION_TYPE_PHOTO=1;
    public static final int ACTION_TYPE_VIDEO=2;
    public static final int ACTION_TYPE_DINGZHI=3;
    public static final int ACTION_TYPE_CODE=4;

    private String actionName;
    private int actionReId;
    private int actionType;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

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