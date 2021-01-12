package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

/**
 * Created on 1/4/21 11:21
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class DeleteKnowledgeOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : 删除素材成功
     */

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}