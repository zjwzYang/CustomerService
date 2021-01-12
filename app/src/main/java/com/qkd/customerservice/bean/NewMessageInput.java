package com.qkd.customerservice.bean;

/**
 * Created on 12/15/20 12:46
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class NewMessageInput {

    private String openId;
    private String title;
    private String description;
    private String url;
    private String picUrl;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}