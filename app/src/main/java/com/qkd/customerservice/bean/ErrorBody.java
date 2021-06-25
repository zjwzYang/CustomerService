package com.qkd.customerservice.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created on 2021/6/25 09:56
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class ErrorBody {

    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}