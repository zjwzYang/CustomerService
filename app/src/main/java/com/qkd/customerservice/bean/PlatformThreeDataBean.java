package com.qkd.customerservice.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created on 2021/6/22 09:05
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class PlatformThreeDataBean {

    @SerializedName("moduleCode")
    private String moduleCode;
    @SerializedName("moduleName")
    private String moduleName;
    @SerializedName("description")
    private String description;
    @SerializedName("children")
    private List<ChildrenDTO> children;
    @SerializedName("data")
    private List<ChildrenDTO.DataDTO> data;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ChildrenDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenDTO> children) {
        this.children = children;
    }

    public List<ChildrenDTO.DataDTO> getData() {
        return data;
    }

    public void setData(List<ChildrenDTO.DataDTO> data) {
        this.data = data;
    }

    public static class ChildrenDTO {
        @SerializedName("moduleCode")
        private String moduleCode;
        @SerializedName("moduleName")
        private String moduleName;
        @SerializedName("description")
        private String description;
        @SerializedName("data")
        private List<DataDTO> data;

        public String getModuleCode() {
            return moduleCode;
        }

        public void setModuleCode(String moduleCode) {
            this.moduleCode = moduleCode;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<DataDTO> getData() {
            return data;
        }

        public void setData(List<DataDTO> data) {
            this.data = data;
        }

        public static class DataDTO {
            @SerializedName("elementCode")
            private String elementCode;
            @SerializedName("elementDescribe")
            private String elementDescribe;
            @SerializedName("elementValue")
            private Object elementValue;
            @SerializedName("elementType")
            private String elementType;
            @SerializedName("notAllowRevise")
            private String notAllowRevise;
            @SerializedName("elementName")
            private String elementName;
            @SerializedName("beginDate")
            private String beginDate;
            @SerializedName("endDate")
            private String endDate;
            @SerializedName("min")
            private String min;
            @SerializedName("max")
            private String max;
            @SerializedName("step")
            private String step;
            @SerializedName("suffix")
            private String suffix;
            @SerializedName("elementItems")
            private List<ElementItemsDTO> elementItems;

            private String moduleCode;
            private String moduleName;
            private int index;
            private String showValue;

            public String getShowValue() {
                return showValue;
            }

            public void setShowValue(String showValue) {
                this.showValue = showValue;
            }

            public String getModuleCode() {
                return moduleCode;
            }

            public void setModuleCode(String moduleCode) {
                this.moduleCode = moduleCode;
            }

            public String getModuleName() {
                return moduleName;
            }

            public void setModuleName(String moduleName) {
                this.moduleName = moduleName;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getElementCode() {
                return elementCode;
            }

            public void setElementCode(String elementCode) {
                this.elementCode = elementCode;
            }

            public String getElementDescribe() {
                return elementDescribe;
            }

            public void setElementDescribe(String elementDescribe) {
                this.elementDescribe = elementDescribe;
            }

            public Object getElementValue() {
                return elementValue;
            }

            public void setElementValue(Object elementValue) {
                this.elementValue = elementValue;
            }

            public String getElementType() {
                return elementType;
            }

            public void setElementType(String elementType) {
                this.elementType = elementType;
            }

            public String getNotAllowRevise() {
                return notAllowRevise;
            }

            public void setNotAllowRevise(String notAllowRevise) {
                this.notAllowRevise = notAllowRevise;
            }

            public String getElementName() {
                return elementName;
            }

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }

            public String getStep() {
                return step;
            }

            public void setStep(String step) {
                this.step = step;
            }

            public String getSuffix() {
                return suffix;
            }

            public void setSuffix(String suffix) {
                this.suffix = suffix;
            }

            public void setElementName(String elementName) {
                this.elementName = elementName;
            }

            public String getBeginDate() {
                return beginDate;
            }

            public void setBeginDate(String beginDate) {
                this.beginDate = beginDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public List<ElementItemsDTO> getElementItems() {
                return elementItems;
            }

            public void setElementItems(List<ElementItemsDTO> elementItems) {
                this.elementItems = elementItems;
            }

            public static class ElementItemsDTO {
                @SerializedName("name")
                private String name;
                @SerializedName("value")
                private String value;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class ElementValueItem {

                @SerializedName("name")
                private String name;
                @SerializedName("value")
                private String value;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}