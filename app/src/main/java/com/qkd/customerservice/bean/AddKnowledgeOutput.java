package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

/**
 * Created on 1/4/21 13:19
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class AddKnowledgeOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : 新增素材成功
     */

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}