package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

/**
 * Created on 1/22/21 09:38
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class UpdateRemarkOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : 更新备注成功
     */

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}