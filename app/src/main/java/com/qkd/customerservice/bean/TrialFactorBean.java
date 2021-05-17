package com.qkd.customerservice.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created on 2021/5/10 10:55
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class TrialFactorBean extends TrialFactorFatherBean {
    @SerializedName("value")
    private String value;
    @SerializedName("detail")
    private List<List<String>> detail;
    private List<String> selectList;

    public List<String> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<String> selectList) {
        this.selectList = selectList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<List<String>> getDetail() {
        return detail;
    }

    public void setDetail(List<List<String>> detail) {
        this.detail = detail;
    }
}
