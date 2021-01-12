package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 1/4/21 15:47
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class WxchatListOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : [{"openId":"oIzSpwyRRlqZwk5_TuexLuRvgW_U","isAddWechat":0},{"openId":"oMRUWs1jwu7VxnKeGQOD9EWjJzZY","isAddWechat":0},{"openId":"oMRUWs1AvT7V0A7D564f5_aaWLDE","isAddWechat":0},{"openId":"o2wjH6RK0LAlTKJVYArlaK8Jk7R8","isAddWechat":0}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * openId : oIzSpwyRRlqZwk5_TuexLuRvgW_U
         * isAddWechat : 0
         */

        private String openId;
        private int isAddWechat;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public int getIsAddWechat() {
            return isAddWechat;
        }

        public void setIsAddWechat(int isAddWechat) {
            this.isAddWechat = isAddWechat;
        }
    }
}