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
public class TrialFactorCityBean extends TrialFactorFatherBean {

    @SerializedName("value")
    private ValueDTO value;
    @SerializedName("detail")
    private List<DetailDTO> detail;

    public ValueDTO getValue() {
        return value;
    }

    public void setValue(ValueDTO value) {
        this.value = value;
    }

    public List<DetailDTO> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailDTO> detail) {
        this.detail = detail;
    }

    public static class ValueDTO {
        @SerializedName("label")
        private String labelX;
        @SerializedName("value")
        private String value;
        @SerializedName("parents")
        private List<ChildrenDTO> parents;

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

        public List<ChildrenDTO> getParents() {
            return parents;
        }

        public void setParents(List<ChildrenDTO> parents) {
            this.parents = parents;
        }
    }

    public static class DetailDTO {
        @SerializedName("label")
        private String labelX;
        @SerializedName("value")
        private String value;
        @SerializedName("children")
        private List<ChildrenDTOX> children;

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

        public List<ChildrenDTOX> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenDTOX> children) {
            this.children = children;
        }

        public static class ChildrenDTOX {
            @SerializedName("label")
            private String labelX;
            @SerializedName("value")
            private String value;
            @SerializedName("children")
            private List<ChildrenDTO> children;

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

            public List<ChildrenDTO> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenDTO> children) {
                this.children = children;
            }


        }
    }

    public static class ChildrenDTO {
        @SerializedName("label")
        private String labelX;
        @SerializedName("value")
        private String value;

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
    }
}
