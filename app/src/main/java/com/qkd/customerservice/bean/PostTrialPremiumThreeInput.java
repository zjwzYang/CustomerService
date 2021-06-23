package com.qkd.customerservice.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created on 2021/6/22 13:48
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class PostTrialPremiumThreeInput {

    @SerializedName("factorParams")
    private Map<String, Object> factorParams;
    @SerializedName("platformId")
    private String platformId;
    @SerializedName("productId")
    private String productId;

    public Map<String, Object> getFactorParams() {
        return factorParams;
    }

    public void setFactorParams(Map<String, Object> factorParams) {
        this.factorParams = factorParams;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}