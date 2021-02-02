package com.qkd.customerservice.bean;

/**
 * Created on 2/2/21 13:07
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class DeleteConversationBean {

    private String userID;
    private String conversationID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }
}