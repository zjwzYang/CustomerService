package com.qkd.customerservice.bean;

/**
 * Created on 2021/6/9 13:54
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class CalcTwoSuccessBean {
    private String price;
    private String valueBao; // 保额
    private String valueQi; // 保险期
    private String valueNian; // 缴费期

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValueBao() {
        return valueBao;
    }

    public void setValueBao(String valueBao) {
        this.valueBao = valueBao;
    }

    public String getValueQi() {
        return valueQi;
    }

    public void setValueQi(String valueQi) {
        this.valueQi = valueQi;
    }

    public String getValueNian() {
        return valueNian;
    }

    public void setValueNian(String valueNian) {
        this.valueNian = valueNian;
    }
}