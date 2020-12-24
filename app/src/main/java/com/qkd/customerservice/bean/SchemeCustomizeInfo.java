package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 12/18/20 08:38
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class SchemeCustomizeInfo extends BaseOutput {

    /**
     * errorMsg : null
     * data : {"id":2,"orderNumber":10087,"schemeType":2,"amount":"99.0","payFlag":1,"chargeFlag":1,"userId":"76","platformId":"1","nickName":"222","gender":"","city":null,"birthday":"1999-12-12","age":"30","phoneNumber":"19954875488","family":"本人,配偶,母亲,父亲,配偶父亲,配偶母亲","familyIncome":"30~50万","familyLoan":"50万以下","sonAmount":"0","dauAmount":"1","orderStatus":1,"createTime":"2020-12-17 16:45:55","updateTime":"2020-12-17 16:45:57","endTime":null,"applyPersonList":[{"id":2,"orderNumber":10087,"userId":"76","insuredPerson":"大儿子","gender":"男","birthday":"1999-12-12","age":"20","medicalHistory":"无","medicalHistoryImg":null,"profession":"律师","socialSecurity":"有","createTime":"2020-12-07 14:49:32","updateTime":"2020-12-07 14:49:35"},{"id":3,"orderNumber":10087,"userId":"76","insuredPerson":"大女儿","gender":"女","birthday":"1999-12-12","age":"20","medicalHistory":"无","medicalHistoryImg":null,"profession":"警察","socialSecurity":"有","createTime":"2020-12-07 14:50:11","updateTime":"2020-12-07 14:50:14"},{"id":9,"orderNumber":10087,"userId":"76","insuredPerson":"小儿子","gender":"男","birthday":"1955-12-11","age":"25","medicalHistory":null,"medicalHistoryImg":null,"profession":null,"socialSecurity":null,"createTime":null,"updateTime":null}]}
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
         * id : 2
         * orderNumber : 10087
         * schemeType : 2
         * amount : 99.0
         * payFlag : 1
         * chargeFlag : 1
         * userId : 76
         * platformId : 1
         * nickName : 222
         * gender :
         * city : null
         * birthday : 1999-12-12
         * age : 30
         * phoneNumber : 19954875488
         * family : 本人,配偶,母亲,父亲,配偶父亲,配偶母亲
         * familyIncome : 30~50万
         * familyLoan : 50万以下
         * sonAmount : 0
         * dauAmount : 1
         * orderStatus : 1
         * createTime : 2020-12-17 16:45:55
         * updateTime : 2020-12-17 16:45:57
         * endTime : null
         * applyPersonList : [{"id":2,"orderNumber":10087,"userId":"76","insuredPerson":"大儿子","gender":"男","birthday":"1999-12-12","age":"20","medicalHistory":"无","medicalHistoryImg":null,"profession":"律师","socialSecurity":"有","createTime":"2020-12-07 14:49:32","updateTime":"2020-12-07 14:49:35"},{"id":3,"orderNumber":10087,"userId":"76","insuredPerson":"大女儿","gender":"女","birthday":"1999-12-12","age":"20","medicalHistory":"无","medicalHistoryImg":null,"profession":"警察","socialSecurity":"有","createTime":"2020-12-07 14:50:11","updateTime":"2020-12-07 14:50:14"},{"id":9,"orderNumber":10087,"userId":"76","insuredPerson":"小儿子","gender":"男","birthday":"1955-12-11","age":"25","medicalHistory":null,"medicalHistoryImg":null,"profession":null,"socialSecurity":null,"createTime":null,"updateTime":null}]
         */

        private int id;
        private long orderNumber;
        private int schemeType;
        private String amount;
        private int payFlag;
        private int chargeFlag;
        private String userId;
        private String platformId;
        private String nickName;
        private String gender;
        private String city;
        private String birthday;
        private String age;
        private String phoneNumber;
        private String family;
        private String familyIncome;
        private String familyLoan;
        private String sonAmount;
        private String dauAmount;
        private int orderStatus;
        private String createTime;
        private String updateTime;
        private Object endTime;
        private List<ApplyPersonListBean> applyPersonList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(long orderNumber) {
            this.orderNumber = orderNumber;
        }

        public int getSchemeType() {
            return schemeType;
        }

        public void setSchemeType(int schemeType) {
            this.schemeType = schemeType;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getPayFlag() {
            return payFlag;
        }

        public void setPayFlag(int payFlag) {
            this.payFlag = payFlag;
        }

        public int getChargeFlag() {
            return chargeFlag;
        }

        public void setChargeFlag(int chargeFlag) {
            this.chargeFlag = chargeFlag;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPlatformId() {
            return platformId;
        }

        public void setPlatformId(String platformId) {
            this.platformId = platformId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public String getFamilyIncome() {
            return familyIncome;
        }

        public void setFamilyIncome(String familyIncome) {
            this.familyIncome = familyIncome;
        }

        public String getFamilyLoan() {
            return familyLoan;
        }

        public void setFamilyLoan(String familyLoan) {
            this.familyLoan = familyLoan;
        }

        public String getSonAmount() {
            return sonAmount;
        }

        public void setSonAmount(String sonAmount) {
            this.sonAmount = sonAmount;
        }

        public String getDauAmount() {
            return dauAmount;
        }

        public void setDauAmount(String dauAmount) {
            this.dauAmount = dauAmount;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public List<ApplyPersonListBean> getApplyPersonList() {
            return applyPersonList;
        }

        public void setApplyPersonList(List<ApplyPersonListBean> applyPersonList) {
            this.applyPersonList = applyPersonList;
        }

        public static class ApplyPersonListBean {
            /**
             * id : 2
             * orderNumber : 10087
             * userId : 76
             * insuredPerson : 大儿子
             * gender : 男
             * birthday : 1999-12-12
             * age : 20
             * medicalHistory : 无
             * medicalHistoryImg : null
             * profession : 律师
             * socialSecurity : 有
             * createTime : 2020-12-07 14:49:32
             * updateTime : 2020-12-07 14:49:35
             */

            private int id;
            private long orderNumber;
            private String userId;
            private String insuredPerson;
            private String gender;
            private String birthday;
            private String age;
            private String medicalHistory;
            private Object medicalHistoryImg;
            private String profession;
            private String socialSecurity;
            private String createTime;
            private String updateTime;

            private List<ProductListOutput.DataBean> productList;

            public List<ProductListOutput.DataBean> getProductList() {
                return productList;
            }

            public void setProductList(List<ProductListOutput.DataBean> productList) {
                this.productList = productList;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(long orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getInsuredPerson() {
                return insuredPerson;
            }

            public void setInsuredPerson(String insuredPerson) {
                this.insuredPerson = insuredPerson;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getMedicalHistory() {
                return medicalHistory;
            }

            public void setMedicalHistory(String medicalHistory) {
                this.medicalHistory = medicalHistory;
            }

            public Object getMedicalHistoryImg() {
                return medicalHistoryImg;
            }

            public void setMedicalHistoryImg(Object medicalHistoryImg) {
                this.medicalHistoryImg = medicalHistoryImg;
            }

            public String getProfession() {
                return profession;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public String getSocialSecurity() {
                return socialSecurity;
            }

            public void setSocialSecurity(String socialSecurity) {
                this.socialSecurity = socialSecurity;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}