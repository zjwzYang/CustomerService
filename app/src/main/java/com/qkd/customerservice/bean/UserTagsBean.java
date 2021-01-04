package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 1/4/21 16:42
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class UserTagsBean extends BaseOutput {

    /**
     * errorMsg : null
     * data : ["可发展","困难"]
     */

    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}