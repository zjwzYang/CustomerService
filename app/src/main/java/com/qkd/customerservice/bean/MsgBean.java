package com.qkd.customerservice.bean;

/**
 * Created on 12/4/20 12:46
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class MsgBean {

    private MsgType msgType;
    private int type;
    private String nickName;
    private String senderId;
    private String sendTime;
    private String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public enum MsgType {
        TEXT,
        IMAGE,
        VOICE,
        ARTICLE,
        PRODUCT
    }
}