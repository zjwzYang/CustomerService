package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 12/17/20 16:51
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class CustomizedListBean extends BaseOutput {

    /**
     * recordsTotal : 1
     * recordsFiltered : 1
     * data : [{"id":2,"createTime":"2020-12-17 16:45:55","updateTime":"2020-12-17 16:45:57","age":"30","amount":99,"birthday":"1999-12-12","chargeFlag":1,"city":null,"dauAmount":"1","endTime":null,"family":"本人,配偶,母亲,父亲,配偶父亲,配偶母亲","familyIncome":"30~50万","familyLoan":"50万以下","gender":"","nickName":"222","orderNumber":"10087","orderStatus":1,"payFlag":1,"phoneNumber":"19954875488","platformId":1,"schemeType":2,"sonAmount":"0","userId":76,"wxName":"you.jiu","source":null}]
     */

    private int recordsTotal;
    private int recordsFiltered;
    private List<DataBean> data;

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * createTime : 2020-12-17 16:45:55
         * updateTime : 2020-12-17 16:45:57
         * age : 30
         * amount : 99.0
         * birthday : 1999-12-12
         * chargeFlag : 1
         * city : null
         * dauAmount : 1
         * endTime : null
         * family : 本人,配偶,母亲,父亲,配偶父亲,配偶母亲
         * familyIncome : 30~50万
         * familyLoan : 50万以下
         * gender :
         * nickName : 222
         * orderNumber : 10087
         * orderStatus : 1
         * payFlag : 1
         * phoneNumber : 19954875488
         * platformId : 1
         * schemeType : 2
         * sonAmount : 0
         * userId : 76
         * wxName : you.jiu
         * source : null
         */

        private int id;
        private String createTime;
        private String updateTime;
        private String age;
        private double amount;
        private String birthday;
        private int chargeFlag;
        private Object city;
        private String dauAmount;
        private Object endTime;
        private String family;
        private String familyIncome;
        private String familyLoan;
        private String gender;
        private String nickName;
        private String orderNumber;
        private int orderStatus;
        private int payFlag;
        private String phoneNumber;
        private int platformId;
        private int schemeType;
        private String sonAmount;
        private int userId;
        private String wxName;
        private Object source;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getChargeFlag() {
            return chargeFlag;
        }

        public void setChargeFlag(int chargeFlag) {
            this.chargeFlag = chargeFlag;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public String getDauAmount() {
            return dauAmount;
        }

        public void setDauAmount(String dauAmount) {
            this.dauAmount = dauAmount;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getPayFlag() {
            return payFlag;
        }

        public void setPayFlag(int payFlag) {
            this.payFlag = payFlag;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public int getPlatformId() {
            return platformId;
        }

        public void setPlatformId(int platformId) {
            this.platformId = platformId;
        }

        public int getSchemeType() {
            return schemeType;
        }

        public void setSchemeType(int schemeType) {
            this.schemeType = schemeType;
        }

        public String getSonAmount() {
            return sonAmount;
        }

        public void setSonAmount(String sonAmount) {
            this.sonAmount = sonAmount;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getWxName() {
            return wxName;
        }

        public void setWxName(String wxName) {
            this.wxName = wxName;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
        }
    }
}