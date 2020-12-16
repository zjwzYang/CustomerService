package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 12/16/20 16:42
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class CustomerBookOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : [{"openId":"oIzSpwyRRlqZwk5_TuexLuRvgW_U","userName":"燕春","userAvatarUrl":"http://thirdwx.qlogo.cn/mmopen/7khzQhlN6tZECmAEnGrHpupLPibGdAl1WJsO1d1XZISxAIWBlA3LVbz1unZbiah3TeBV6lmlDF1zpGpKSLZSyicHo5Vg0QnjJlm/132"},{"openId":"oMRUWs1jwu7VxnKeGQOD9EWjJzZY","userName":"啊！基米杨","userAvatarUrl":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLDyEMaSfgoeFiaOsib1kdFtSJ97icXt63GEibZJ2xDg2FulANNbGVXKyLDO8qic2PQqYjPiaseGgeYDB2kw/132"}]
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
         * userName : 燕春
         * userAvatarUrl : http://thirdwx.qlogo.cn/mmopen/7khzQhlN6tZECmAEnGrHpupLPibGdAl1WJsO1d1XZISxAIWBlA3LVbz1unZbiah3TeBV6lmlDF1zpGpKSLZSyicHo5Vg0QnjJlm/132
         */

        private String openId;
        private String userName;
        private String userAvatarUrl;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAvatarUrl() {
            return userAvatarUrl;
        }

        public void setUserAvatarUrl(String userAvatarUrl) {
            this.userAvatarUrl = userAvatarUrl;
        }
    }
}