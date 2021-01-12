package com.qkd.customerservice.bean;

/**
 * Created on 12/8/20 08:54
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ImageMsg extends MsgBean {
    private String mImgPath;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgPath() {
        return mImgPath;
    }

    public void setImgPath(String imgPath) {
        mImgPath = imgPath;
    }
}