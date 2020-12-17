package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

/**
 * Created on 12/17/20 09:31
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class LoginOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : {"serviceId":1,"identifier":"test_yang","status":3}
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
         * serviceId : 1
         * identifier : test_yang
         * status : 3
         */

        private int serviceId;
        private String identifier;
        private int status;

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}