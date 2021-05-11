package com.qkd.customerservice.bean;

import java.util.Map;

/**
 * Created on 2021/5/11 09:05
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class PostTrialPremiumInput {
    private String platformId;
    private String productId;
    private Map<String, Object> factorParams;

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

    public Map<String, Object> getFactorParams() {
        return factorParams;
    }

    public void setFactorParams(Map<String, Object> factorParams) {
        this.factorParams = factorParams;
    }
}