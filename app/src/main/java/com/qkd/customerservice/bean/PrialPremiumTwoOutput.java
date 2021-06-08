package com.qkd.customerservice.bean;

import com.google.gson.annotations.SerializedName;
import com.qkd.customerservice.net.BaseOutput;

/**
 * Created on 2021/6/8 15:59
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class PrialPremiumTwoOutput extends BaseOutput {

    @SerializedName("data")
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}