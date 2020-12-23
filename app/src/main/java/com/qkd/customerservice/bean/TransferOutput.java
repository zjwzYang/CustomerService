package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

/**
 * Created on 12/23/20 15:22
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class TransferOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : 转移客户成功
     */

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}