package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 12/18/20 10:45
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ProductListOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : [{"id":57,"productName":"好医保长期医疗2020好医保长期医疗2020","productType":"1","companyId":"1","companyName":"中国人寿桐乡分公司"},{"id":59,"productName":"泰康微医保长期医疗险","productType":"1","companyId":"2","companyName":"平安保险桐乡分公司"},{"id":60,"productName":"国富医疗定海柱1号","productType":"2","companyId":"3","companyName":"安全保险公司"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 57
         * productName : 好医保长期医疗2020好医保长期医疗2020
         * productType : 1
         * companyId : 1
         * companyName : 中国人寿桐乡分公司
         */

        private int id;
        private String productName;
        private String productType;
        private String companyId;
        private String companyName;
        private String platformId;
        private String platformProductId;
        private int isContainAttachInsurance;
        private String premiumNum;
        private String insuredAmount;
        private String paymentPeriod;
        private String guaranteePeriod;
        private String[][] arrayData;
        private String productIntroduction;
        private String templateContent;

        public String getTemplateContent() {
            return templateContent;
        }

        public void setTemplateContent(String templateContent) {
            this.templateContent = templateContent;
        }

        public String getProductIntroduction() {
            return productIntroduction;
        }

        public void setProductIntroduction(String productIntroduction) {
            this.productIntroduction = productIntroduction;
        }

        public String getGuaranteePeriod() {
            return guaranteePeriod;
        }

        public void setGuaranteePeriod(String guaranteePeriod) {
            this.guaranteePeriod = guaranteePeriod;
        }

        public String getPaymentPeriod() {
            return paymentPeriod;
        }

        public void setPaymentPeriod(String paymentPeriod) {
            this.paymentPeriod = paymentPeriod;
        }

        public String[][] getArrayData() {
            return arrayData;
        }

        public void setArrayData(String[][] arrayData) {
            this.arrayData = arrayData;
        }

        public String getPremiumNum() {
            return premiumNum;
        }

        public void setPremiumNum(String premiumNum) {
            this.premiumNum = premiumNum;
        }

        public String getInsuredAmount() {
            return insuredAmount;
        }

        public void setInsuredAmount(String insuredAmount) {
            this.insuredAmount = insuredAmount;
        }

        public int getIsContainAttachInsurance() {
            return isContainAttachInsurance;
        }

        public void setIsContainAttachInsurance(int isContainAttachInsurance) {
            this.isContainAttachInsurance = isContainAttachInsurance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getPlatformId() {
            return platformId;
        }

        public void setPlatformId(String platformId) {
            this.platformId = platformId;
        }

        public String getPlatformProductId() {
            return platformProductId;
        }

        public void setPlatformProductId(String platformProductId) {
            this.platformProductId = platformProductId;
        }
    }
}