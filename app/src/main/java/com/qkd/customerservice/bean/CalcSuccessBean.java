package com.qkd.customerservice.bean;

import java.util.List;

/**
 * Created on 2021/5/11 13:23
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class CalcSuccessBean {
    private List<TrialFactorBean> factorBeans;
    private String price;

    public List<TrialFactorBean> getFactorBeans() {
        return factorBeans;
    }

    public void setFactorBeans(List<TrialFactorBean> factorBeans) {
        this.factorBeans = factorBeans;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}