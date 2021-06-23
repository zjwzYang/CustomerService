package com.qkd.customerservice.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created on 2021/6/22 15:03
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class PlatformThreeOutput {

    @SerializedName("curStep")
    private String curStep;
    @SerializedName("productCode")
    private String productCode;
    @SerializedName("data")
    private DataDTO data;
    @SerializedName("nextStep")
    private String nextStep;
    @SerializedName("insureUniqueId")
    private String insureUniqueId;
    @SerializedName("productName")
    private String productName;
    @SerializedName("status")
    private Integer status;

    public String getCurStep() {
        return curStep;
    }

    public void setCurStep(String curStep) {
        this.curStep = curStep;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public String getInsureUniqueId() {
        return insureUniqueId;
    }

    public void setInsureUniqueId(String insureUniqueId) {
        this.insureUniqueId = insureUniqueId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static class DataDTO {
        @SerializedName("basePremium")
        private String basePremium;
        @SerializedName("showPrice")
        private String showPrice;
        @SerializedName("exemptPremium")
        private String exemptPremium;
        @SerializedName("fee")
        private String fee;
        @SerializedName("additional")
        private String additional;
        @SerializedName("exemptAmount")
        private String exemptAmount;

        public String getBasePremium() {
            return basePremium;
        }

        public void setBasePremium(String basePremium) {
            this.basePremium = basePremium;
        }

        public String getShowPrice() {
            return showPrice;
        }

        public void setShowPrice(String showPrice) {
            this.showPrice = showPrice;
        }

        public String getExemptPremium() {
            return exemptPremium;
        }

        public void setExemptPremium(String exemptPremium) {
            this.exemptPremium = exemptPremium;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getAdditional() {
            return additional;
        }

        public void setAdditional(String additional) {
            this.additional = additional;
        }

        public String getExemptAmount() {
            return exemptAmount;
        }

        public void setExemptAmount(String exemptAmount) {
            this.exemptAmount = exemptAmount;
        }
    }
}