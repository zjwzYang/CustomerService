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
public class TrialFactorBean {
    @SerializedName("widget")
    private String widget;
    @SerializedName("name")
    private String name;
    @SerializedName("label")
    private String label;
    @SerializedName("type")
    private String type;
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

    public String getWidget() {
        return widget;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
