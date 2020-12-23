package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 12/23/20 17:01
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class SchemeConfigOutput extends BaseOutput {

    /**
     * dataList : [{"firstYearPremium":"22190.00","guaranteePeriod":"保至终身","insuranceInfoId":"2","insuredAmount":"35万","paymentPeriod":"5年","productId":"91","productName":"百惠保","productType":"3"},{"firstYearPremium":"5932.50","guaranteePeriod":"保至70周岁","insuranceInfoId":"9","insuredAmount":"25万","paymentPeriod":"10年","productId":"91","productName":"百惠保","productType":"3"}]
     * nickName : 222
     * orderNumber : 10087
     * userId : 76
     */

    private String nickName;
    private String orderNumber;
    private String userId;
    private List<DataListBean> dataList;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * firstYearPremium : 22190.00
         * guaranteePeriod : 保至终身
         * insuranceInfoId : 2
         * insuredAmount : 35万
         * paymentPeriod : 5年
         * productId : 91
         * productName : 百惠保
         * productType : 3
         */

        private String firstYearPremium;
        private String guaranteePeriod;
        private String insuranceInfoId;
        private String insuredAmount;
        private String paymentPeriod;
        private String productId;
        private String productName;
        private String productType;

        public String getFirstYearPremium() {
            return firstYearPremium;
        }

        public void setFirstYearPremium(String firstYearPremium) {
            this.firstYearPremium = firstYearPremium;
        }

        public String getGuaranteePeriod() {
            return guaranteePeriod;
        }

        public void setGuaranteePeriod(String guaranteePeriod) {
            this.guaranteePeriod = guaranteePeriod;
        }

        public String getInsuranceInfoId() {
            return insuranceInfoId;
        }

        public void setInsuranceInfoId(String insuranceInfoId) {
            this.insuranceInfoId = insuranceInfoId;
        }

        public String getInsuredAmount() {
            return insuredAmount;
        }

        public void setInsuredAmount(String insuredAmount) {
            this.insuredAmount = insuredAmount;
        }

        public String getPaymentPeriod() {
            return paymentPeriod;
        }

        public void setPaymentPeriod(String paymentPeriod) {
            this.paymentPeriod = paymentPeriod;
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

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }
    }
}