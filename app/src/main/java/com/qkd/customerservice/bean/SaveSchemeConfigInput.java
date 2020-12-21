package com.qkd.customerservice.bean;

import java.util.List;

/**
 * Created on 12/19/20 14:16
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class SaveSchemeConfigInput {

    /**
     * orderNumber : 10087
     * userId : 85
     * nickName : 菜单22
     * dataList : [{"insuranceInfoId":"2","productId":"3","productName":"百汇健康医疗保险","productType":1,"productByUrl":"","insuredAmount":"500万","firstYearPremium":"2530","paymentPeriod":"30年","guaranteePeriod":"保至终身"},{"insuranceInfoId":"2","productId":"6","productName":"炭烤保险重疾险","productType":2,"productByUrl":"","insuredAmount":"200万","firstYearPremium":"6522","paymentPeriod":"10年","guaranteePeriod":"保至70周岁"}]
     */

    private String orderNumber;
    private String userId;
    private String nickName;
    private List<DataListBean> dataList;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * insuranceInfoId : 2
         * productId : 3
         * productName : 百汇健康医疗保险
         * productType : 1
         * productByUrl :
         * insuredAmount : 500万
         * firstYearPremium : 2530
         * paymentPeriod : 30年
         * guaranteePeriod : 保至终身
         */

        private String insuranceInfoId;
        private String productId;
        private String productName;
        private int productType;
        private String productByUrl;
        private String insuredAmount;
        private String firstYearPremium;
        private String paymentPeriod;
        private String guaranteePeriod;

        public String getInsuranceInfoId() {
            return insuranceInfoId;
        }

        public void setInsuranceInfoId(String insuranceInfoId) {
            this.insuranceInfoId = insuranceInfoId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public String getProductByUrl() {
            return productByUrl;
        }

        public void setProductByUrl(String productByUrl) {
            this.productByUrl = productByUrl;
        }

        public String getInsuredAmount() {
            return insuredAmount;
        }

        public void setInsuredAmount(String insuredAmount) {
            this.insuredAmount = insuredAmount;
        }

        public String getFirstYearPremium() {
            return firstYearPremium;
        }

        public void setFirstYearPremium(String firstYearPremium) {
            this.firstYearPremium = firstYearPremium;
        }

        public String getPaymentPeriod() {
            return paymentPeriod;
        }

        public void setPaymentPeriod(String paymentPeriod) {
            this.paymentPeriod = paymentPeriod;
        }

        public String getGuaranteePeriod() {
            return guaranteePeriod;
        }

        public void setGuaranteePeriod(String guaranteePeriod) {
            this.guaranteePeriod = guaranteePeriod;
        }
    }
}