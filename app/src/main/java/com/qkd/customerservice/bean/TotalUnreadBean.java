package com.qkd.customerservice.bean;

/**
 * Created on 1/22/21 08:58
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class TotalUnreadBean {
    private int unreadTotalCount;

    public TotalUnreadBean(int unreadTotalCount) {
        this.unreadTotalCount = unreadTotalCount;
    }

    public int getUnreadTotalCount() {
        return unreadTotalCount;
    }

    public void setUnreadTotalCount(int unreadTotalCount) {
        this.unreadTotalCount = unreadTotalCount;
    }
}