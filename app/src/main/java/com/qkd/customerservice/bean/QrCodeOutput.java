package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

/**
 * Created on 1/15/21 10:03
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class QrCodeOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : http://47.114.100.72/files/pic/qrCode67.jpg
     */

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}