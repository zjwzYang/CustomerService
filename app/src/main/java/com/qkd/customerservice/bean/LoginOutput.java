package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

/**
 * Created on 12/17/20 09:31
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class LoginOutput extends BaseOutput {


    /**
     * errorMsg : null
     * data : {"identifier":"test_yang","serviceId":1,"userSig":"eJyrVgrxCdZLrSjILEpVsjI1tDAxMDDQAQuWpRYpWSkZ6RkoQfjFKdmJBQWZKUpWhkBFJqZmRibGEJnMlNS8ksy0TLCGktTikvjKxLx0mLbMdKBocahpgHlecKq5j2NUirlRQVmkh1NlboRTtkGiSUqysX5Wprmvo3ZOSGCoqy1UY0lmLtBJhmYGFobmJkCX1QIA8A4yQg__","tokenMap":{"expireTime":"2021-02-15 16:08:59","token":"57cbad87-92e5-4ee9-832e-1ac050357b03"},"status":1,"coreToken":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0X3lhbmciLCJpc3MiOiIiLCJpYXQiOjE2MDgxOTI1Mzl9.dawNh5TAbAH7Pfj__VMJIX0xKzumLroWI9pPlzn5kHGTNxYWr1Rc3BunZnR5QUSneD8NN_Qn6NKBPy-dOWlebw"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * identifier : test_yang
         * serviceId : 1
         * userSig : eJyrVgrxCdZLrSjILEpVsjI1tDAxMDDQAQuWpRYpWSkZ6RkoQfjFKdmJBQWZKUpWhkBFJqZmRibGEJnMlNS8ksy0TLCGktTikvjKxLx0mLbMdKBocahpgHlecKq5j2NUirlRQVmkh1NlboRTtkGiSUqysX5Wprmvo3ZOSGCoqy1UY0lmLtBJhmYGFobmJkCX1QIA8A4yQg__
         * tokenMap : {"expireTime":"2021-02-15 16:08:59","token":"57cbad87-92e5-4ee9-832e-1ac050357b03"}
         * status : 1
         * coreToken : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0X3lhbmciLCJpc3MiOiIiLCJpYXQiOjE2MDgxOTI1Mzl9.dawNh5TAbAH7Pfj__VMJIX0xKzumLroWI9pPlzn5kHGTNxYWr1Rc3BunZnR5QUSneD8NN_Qn6NKBPy-dOWlebw
         */

        private String identifier;
        private int serviceId;
        private String userSig;
        private TokenMapBean tokenMap;
        private int status;
        private String coreToken;

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public String getUserSig() {
            return userSig;
        }

        public void setUserSig(String userSig) {
            this.userSig = userSig;
        }

        public TokenMapBean getTokenMap() {
            return tokenMap;
        }

        public void setTokenMap(TokenMapBean tokenMap) {
            this.tokenMap = tokenMap;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCoreToken() {
            return coreToken;
        }

        public void setCoreToken(String coreToken) {
            this.coreToken = coreToken;
        }

        public static class TokenMapBean {
            /**
             * expireTime : 2021-02-15 16:08:59
             * token : 57cbad87-92e5-4ee9-832e-1ac050357b03
             */

            private String expireTime;
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