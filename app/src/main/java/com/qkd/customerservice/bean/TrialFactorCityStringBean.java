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
public class TrialFactorCityStringBean extends TrialFactorFatherBean {

    @SerializedName("value")
    private String value;
    @SerializedName("detail")
    private List<TrialFactorCityBean.DetailDTO> detail;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<TrialFactorCityBean.DetailDTO> getDetail() {
        return detail;
    }

    public void setDetail(List<TrialFactorCityBean.DetailDTO> detail) {
        this.detail = detail;
    }

    public static class ValueDTO {
        @SerializedName("label")
        private String labelX;
        @SerializedName("value")
        private String value;
        @SerializedName("parents")
        private List<TrialFactorCityBean.ChildrenDTO> parents;

        public String getLabelX() {
            return labelX;
        }

        public void setLabelX(String labelX) {
            this.labelX = labelX;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<TrialFactorCityBean.ChildrenDTO> getParents() {
            return parents;
        }

        public void setParents(List<TrialFactorCityBean.ChildrenDTO> parents) {
            this.parents = parents;
        }
    }
}
