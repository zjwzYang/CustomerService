package com.qkd.customerservice.bean;

import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMMessage;

/**
 * Created on 12/9/20 12:39
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ConversationBean {
    private String showName;
    private String userId;
    private String faceUrl;
    private String conversationID;
    private int unreadCount;
    private V2TIMMessage lastMessage;
    private boolean addedWx;
    private boolean topFlag;

    public ConversationBean(V2TIMConversation conversation) {
        this.showName = conversation.getShowName();
        this.userId = conversation.getUserID();
        this.faceUrl = conversation.getFaceUrl();
        this.unreadCount = conversation.getUnreadCount();
        this.lastMessage = conversation.getLastMessage();
        this.conversationID = conversation.getConversationID();
    }

    public boolean isTopFlag() {
        return topFlag;
    }

    public void setTopFlag(boolean topFlag) {
        this.topFlag = topFlag;
    }

    public boolean isAddedWx() {
        return addedWx;
    }

    public void setAddedWx(boolean addedWx) {
        this.addedWx = addedWx;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public V2TIMMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(V2TIMMessage lastMessage) {
        this.lastMessage = lastMessage;
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

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}