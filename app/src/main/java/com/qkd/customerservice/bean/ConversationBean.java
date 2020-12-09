package com.qkd.customerservice.bean;

import com.tencent.imsdk.v2.V2TIMConversation;

/**
 * Created on 12/9/20 12:39
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class ConversationBean {
    private String showName;
    private String userId;
    private String faceUrl;
    private boolean hasUnread;

    public ConversationBean(V2TIMConversation conversation) {
        this.showName = conversation.getShowName();
        this.userId = conversation.getUserID();
        this.faceUrl = conversation.getFaceUrl();
    }

    public String getShowName() {
        return showName;
    }

    public String getUserId() {
        return userId;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public boolean isHasUnread() {
        return hasUnread;
    }

    public void setHasUnread(boolean hasUnread) {
        this.hasUnread = hasUnread;
    }
}