package com.qkd.customerservice.bean;

import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMMessage;

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
    private String conversationID;
    private int unreadCount;
    private V2TIMMessage lastMessage;

    public ConversationBean(V2TIMConversation conversation) {
        this.showName = conversation.getShowName();
        this.userId = conversation.getUserID();
        this.faceUrl = conversation.getFaceUrl();
        this.unreadCount = conversation.getUnreadCount();
        this.lastMessage = conversation.getLastMessage();
        this.conversationID = conversation.getConversationID();
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