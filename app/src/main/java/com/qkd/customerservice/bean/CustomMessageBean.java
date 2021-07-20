package com.qkd.customerservice.bean;

/**
 * Created on 1/25/21 11:19
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CustomMessageBean {

    /**
     * nickname : cmm1
     * url : http://qkbdev.qukandian573.com/programme?orderNumber=551040159992958976&userId=183
     */

    private String nickname;
    private String url;
    private String orderNumber;
    private SchemeInfoBean schemeInfo;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public SchemeInfoBean getSchemeInfo() {
        return schemeInfo;
    }

    public void setSchemeInfo(SchemeInfoBean schemeInfo) {
        this.schemeInfo = schemeInfo;
    }

    static public class SchemeInfoBean {
        private String nickName;
        private int userId;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}