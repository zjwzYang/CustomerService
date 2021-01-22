package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 12/16/20 16:42
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CustomerBookOutput extends BaseOutput {


    /**
     * errorMsg : null
     * data : [{"openId":"oIzSpwyRRlqZwk5_TuexLuRvgW_U","userName":"燕春","userAvatarUrl":"http://thirdwx.qlogo.cn/mmopen/7khzQhlN6tZECmAEnGrHpupLPibGdAl1WJsO1d1XZISxAIWBlA3LVbz1unZbiah3TeBV6lmlDF1zpGpKSLZSyicHo5Vg0QnjJlm/132","belongName":"宝趣保","userStatus":1,"remark":null},{"openId":"oMRUWs5s-pGsMTKxJwXsg7aOhZg0","userName":"我","userAvatarUrl":"http://thirdwx.qlogo.cn/mmopen/0tTQ57qOzKJU1f8UajCibu8a358ic27rcyuIze0HJFKLNuRGuV3GecnpKicuKyk8rxcZTNcHUCHL1JdpB0gGuNiahqqoSv2ibjZnk/132","belongName":"宝趣保","userStatus":1,"remark":"bxhdjs"},{"openId":"oMRUWs1jwu7VxnKeGQOD9EWjJzZY","userName":"啊！基米杨","userAvatarUrl":"http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLDyEMaSfgoeFiaOsib1kdFtSJ97icXt63GEibZJ2xDg2FulANNbGVXKyLDO8qic2PQqYjPiaseGgeYDB2kw/132","belongName":"宝趣保","userStatus":1,"remark":"gjff"},{"openId":"o2wjH6TjmkosI65BSdljFsG_fAEs","userName":"you.jiu","userAvatarUrl":"http://thirdwx.qlogo.cn/mmopen/Q3auHgzwzM4tYicicYfpyaXgrWsZEFPP3bHRjLa2Gc8uFCZAjdIrNXBVnxG2V81khzVHxZ7wObquuiaJJgF5jYEBCseOQQ0sBRBZY5yDotebrU/132","belongName":"趣保宝","userStatus":2,"remark":null},{"openId":"o2wjH6RK0LAlTKJVYArlaK8Jk7R8","userName":"GGGXF23","userAvatarUrl":"http://thirdwx.qlogo.cn/mmopen/yWFrcfjcfbHgicfryfBB4hcZvG5giaG38QwiaHoePdfXISp3jUUSLbckZEEUhTV2j8eicbogKQfpemgDCPxvrAfTb2fBZMom6oTe/132","belongName":"趣保宝","userStatus":2,"remark":null},{"openId":"oMRUWsz5RBAxLanlU1ITgWBEGd6I","userName":"GGGXF23","userAvatarUrl":"http://thirdwx.qlogo.cn/mmopen/0tTQ57qOzKJ1UIf7N6bmfopf3RjnfY6ibFUPXsxlODeRwnEobicQPLJbGic0xvEibQsfNiczViaEBElF5MX8VzMeDOvxicNSC0nJxUV/132","belongName":"宝趣保","userStatus":2,"remark":null}]
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
         * belongName : 宝趣保
         * userStatus : 1
         * remark : null
         */

        private String openId;
        private String userName;
        private String userAvatarUrl;
        private String belongName;
        private int userStatus;
        private String remark;
        private int isAddWechat;

        public int getIsAddWechat() {
            return isAddWechat;
        }

        public void setIsAddWechat(int isAddWechat) {
            this.isAddWechat = isAddWechat;
        }

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

        public String getBelongName() {
            return belongName;
        }

        public void setBelongName(String belongName) {
            this.belongName = belongName;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}