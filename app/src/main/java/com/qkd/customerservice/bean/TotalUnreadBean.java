package com.qkd.customerservice.bean;

/**
 * Created on 1/22/21 08:58
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
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