package com.qkd.customerservice.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created on 2021/6/22 15:49
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class CovereAreaBean {

    @SerializedName("code")
    private String code;
    @SerializedName("level")
    private Integer level;
    @SerializedName("name")
    private String name;
    @SerializedName("children")
    private List<ChildrenDTOX> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildrenDTOX> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenDTOX> children) {
        this.children = children;
    }

    public static class ChildrenDTOX {
        @SerializedName("code")
        private String code;
        @SerializedName("level")
        private Integer level;
        @SerializedName("name")
        private String name;
        @SerializedName("children")
        private List<ChildrenDTO> children;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ChildrenDTO> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenDTO> children) {
            this.children = children;
        }

        public static class ChildrenDTO {
            @SerializedName("code")
            private String code;
            @SerializedName("level")
            private Integer level;
            @SerializedName("name")
            private String name;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}