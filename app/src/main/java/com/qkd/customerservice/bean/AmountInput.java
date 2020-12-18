package com.qkd.customerservice.bean;

/**
 * Created on 12/18/20 15:06
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class AmountInput {

    private String productType;
    private String fromCustAge;
    private String fromCustHasTss;
    private String fromCustSex;
    private String toCustAge;
    private String toCustHasTss;
    private String toCustSex;
    private String productId;
    private String isMain;

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getToCustAge() {
        return toCustAge;
    }

    public void setToCustAge(String toCustAge) {
        this.toCustAge = toCustAge;
    }

    public String getToCustHasTss() {
        return toCustHasTss;
    }

    public void setToCustHasTss(String toCustHasTss) {
        this.toCustHasTss = toCustHasTss;
    }

    public String getToCustSex() {
        return toCustSex;
    }

    public void setToCustSex(String toCustSex) {
        this.toCustSex = toCustSex;
    }

    public String getFromCustSex() {
        return fromCustSex;
    }

    public void setFromCustSex(String fromCustSex) {
        this.fromCustSex = fromCustSex;
    }

    public String getFromCustHasTss() {
        return fromCustHasTss;
    }

    public void setFromCustHasTss(String fromCustHasTss) {
        this.fromCustHasTss = fromCustHasTss;
    }

    public String getFromCustAge() {
        return fromCustAge;
    }

    public void setFromCustAge(String fromCustAge) {
        this.fromCustAge = fromCustAge;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}