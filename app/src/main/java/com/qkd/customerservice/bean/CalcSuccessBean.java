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
    private List<TrialFactorFatherBean> factorBeans;
    private String price;
    private String templateContent;

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public List<TrialFactorFatherBean> getFactorBeans() {
        return factorBeans;
    }

    public void setFactorBeans(List<TrialFactorFatherBean> factorBeans) {
        this.factorBeans = factorBeans;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}