package com.qkd.customerservice.bean;

import com.google.gson.annotations.SerializedName;
import com.qkd.customerservice.net.BaseOutput;

/**
 * Created on 12/17/20 09:31
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class LoginOutput extends BaseOutput {


    @SerializedName("data")
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("identifier")
        private String identifier;
        @SerializedName("serviceId")
        private Integer serviceId;
        @SerializedName("userSig")
        private String userSig;
        @SerializedName("tokenMap")
        private TokenMapDTO tokenMap;
        @SerializedName("status")
        private Integer status;
        @SerializedName("coreToken")
        private String coreToken;

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public Integer getServiceId() {
            return serviceId;
        }

        public void setServiceId(Integer serviceId) {
            this.serviceId = serviceId;
        }

        public String getUserSig() {
            return userSig;
        }

        public void setUserSig(String userSig) {
            this.userSig = userSig;
        }

        public TokenMapDTO getTokenMap() {
            return tokenMap;
        }

        public void setTokenMap(TokenMapDTO tokenMap) {
            this.tokenMap = tokenMap;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCoreToken() {
            return coreToken;
        }

        public void setCoreToken(String coreToken) {
            this.coreToken = coreToken;
        }

        public static class TokenMapDTO {
            @SerializedName("expireTime")
            private String expireTime;
            @SerializedName("token")
            private String token;

            public String getExpireTime() {
                return expireTime;
            }

            public void setExpireTime(String expireTime) {
                this.expireTime = expireTime;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}