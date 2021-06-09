package com.qkd.customerservice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created on 2021/6/8 09:38
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class PlatformTwoDataBean {

    @SerializedName("caseCode")
    private String caseCode;
    @SerializedName("originalPrice")
    private Integer originalPrice;
    @SerializedName("priceArgs")
    private PriceArgsDTO priceArgs;
    @SerializedName("price")
    private Integer price;
    @SerializedName("partnerId")
    private Integer partnerId;
    @SerializedName("transNo")
    private String transNo;
    @SerializedName("protectItems")
    private List<ProtectItemsDTO> protectItems;
    @SerializedName("restrictGenes")
    private List<RestrictGenesDTO> restrictGenes;

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public PriceArgsDTO getPriceArgs() {
        return priceArgs;
    }

    public void setPriceArgs(PriceArgsDTO priceArgs) {
        this.priceArgs = priceArgs;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public List<ProtectItemsDTO> getProtectItems() {
        return protectItems;
    }

    public void setProtectItems(List<ProtectItemsDTO> protectItems) {
        this.protectItems = protectItems;
    }

    public List<RestrictGenesDTO> getRestrictGenes() {
        return restrictGenes;
    }

    public void setRestrictGenes(List<RestrictGenesDTO> restrictGenes) {
        this.restrictGenes = restrictGenes;
    }

    public static class PriceArgsDTO {
        @SerializedName("productId")
        private Integer productId;
        @SerializedName("productPlanId")
        private Integer productPlanId;
        @SerializedName("genes")
        private List<GenesDTO> genes;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getProductPlanId() {
            return productPlanId;
        }

        public void setProductPlanId(Integer productPlanId) {
            this.productPlanId = productPlanId;
        }

        public List<GenesDTO> getGenes() {
            return genes;
        }

        public void setGenes(List<GenesDTO> genes) {
            this.genes = genes;
        }

        public static class GenesDTO {
            @SerializedName("sort")
            private Integer sort;
            @SerializedName("value")
            private String value;
            @SerializedName("key")
            private String key;
            @SerializedName("protectItemId")
            private String protectItemId;

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getProtectItemId() {
                return protectItemId;
            }

            public void setProtectItemId(String protectItemId) {
                this.protectItemId = protectItemId;
            }
        }
    }

    public static class ProtectItemsDTO {
        @SerializedName("defaultValue")
        private String defaultValue;
        @SerializedName("protectItemId")
        private Integer protectItemId;
        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("sort")
        private Integer sort;

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public Integer getProtectItemId() {
            return protectItemId;
        }

        public void setProtectItemId(Integer protectItemId) {
            this.protectItemId = protectItemId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }
    }

    public static class RestrictGenesDTO implements Parcelable {
        @SerializedName("defaultValue")
        private String defaultValue;
        @SerializedName("protectItemId")
        private String protectItemId;
        @SerializedName("display")
        private Boolean display;
        @SerializedName("name")
        private String name;
        @SerializedName("sort")
        private Integer sort;
        @SerializedName("type")
        private Integer type;
        @SerializedName("key")
        private String key;
        @SerializedName("values")
        private List<ValuesDTO> values;
        @SerializedName("subRestrictGenes")
        private List<SubRestrictGeneBean> subRestrictGenes;

        private int position;
        private String cityChangeKey;

        public String getCityChangeKey() {
            return cityChangeKey;
        }

        public void setCityChangeKey(String cityChangeKey) {
            this.cityChangeKey = cityChangeKey;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public List<SubRestrictGeneBean> getSubRestrictGenes() {
            return subRestrictGenes;
        }

        public void setSubRestrictGenes(List<SubRestrictGeneBean> subRestrictGenes) {
            this.subRestrictGenes = subRestrictGenes;
        }

        public String getProtectItemId() {
            return protectItemId;
        }

        public void setProtectItemId(String protectItemId) {
            this.protectItemId = protectItemId;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public Boolean isDisplay() {
            return display;
        }

        public void setDisplay(Boolean display) {
            this.display = display;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<ValuesDTO> getValues() {
            return values;
        }

        public void setValues(List<ValuesDTO> values) {
            this.values = values;
        }

        public static class ValuesDTO implements Parcelable {
            @SerializedName("unit")
            private String unit;
            @SerializedName("min")
            private Integer min;
            @SerializedName("max")
            private Integer max;
            @SerializedName("name")
            private String name;
            @SerializedName("subDictionary")
            private SubDictionaryDTO subDictionary;
            @SerializedName("step")
            private Integer step;
            @SerializedName("type")
            private String type;
            @SerializedName("value")
            private String value;

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public Integer getMin() {
                return min;
            }

            public void setMin(Integer min) {
                this.min = min;
            }

            public Integer getMax() {
                return max;
            }

            public void setMax(Integer max) {
                this.max = max;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public SubDictionaryDTO getSubDictionary() {
                return subDictionary;
            }

            public void setSubDictionary(SubDictionaryDTO subDictionary) {
                this.subDictionary = subDictionary;
            }

            public Integer getStep() {
                return step;
            }

            public void setStep(Integer step) {
                this.step = step;
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

            public static class SubDictionaryDTO implements Parcelable {
                @SerializedName("condition")
                private Integer condition;
                @SerializedName("unit")
                private String unit;
                @SerializedName("min")
                private Integer min;

                public Integer getCondition() {
                    return condition;
                }

                public void setCondition(Integer condition) {
                    this.condition = condition;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public Integer getMin() {
                    return min;
                }

                public void setMin(Integer min) {
                    this.min = min;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeValue(this.condition);
                    dest.writeString(this.unit);
                    dest.writeValue(this.min);
                }

                public void readFromParcel(Parcel source) {
                    this.condition = (Integer) source.readValue(Integer.class.getClassLoader());
                    this.unit = source.readString();
                    this.min = (Integer) source.readValue(Integer.class.getClassLoader());
                }

                public SubDictionaryDTO() {
                }

                protected SubDictionaryDTO(Parcel in) {
                    this.condition = (Integer) in.readValue(Integer.class.getClassLoader());
                    this.unit = in.readString();
                    this.min = (Integer) in.readValue(Integer.class.getClassLoader());
                }

                public static final Creator<SubDictionaryDTO> CREATOR = new Creator<SubDictionaryDTO>() {
                    @Override
                    public SubDictionaryDTO createFromParcel(Parcel source) {
                        return new SubDictionaryDTO(source);
                    }

                    @Override
                    public SubDictionaryDTO[] newArray(int size) {
                        return new SubDictionaryDTO[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.unit);
                dest.writeValue(this.min);
                dest.writeValue(this.max);
                dest.writeString(this.name);
                dest.writeParcelable(this.subDictionary, flags);
                dest.writeValue(this.step);
                dest.writeString(this.type);
                dest.writeString(this.value);
            }

            public void readFromParcel(Parcel source) {
                this.unit = source.readString();
                this.min = (Integer) source.readValue(Integer.class.getClassLoader());
                this.max = (Integer) source.readValue(Integer.class.getClassLoader());
                this.name = source.readString();
                this.subDictionary = source.readParcelable(SubDictionaryDTO.class.getClassLoader());
                this.step = (Integer) source.readValue(Integer.class.getClassLoader());
                this.type = source.readString();
                this.value = source.readString();
            }

            public ValuesDTO() {
            }

            protected ValuesDTO(Parcel in) {
                this.unit = in.readString();
                this.min = (Integer) in.readValue(Integer.class.getClassLoader());
                this.max = (Integer) in.readValue(Integer.class.getClassLoader());
                this.name = in.readString();
                this.subDictionary = in.readParcelable(SubDictionaryDTO.class.getClassLoader());
                this.step = (Integer) in.readValue(Integer.class.getClassLoader());
                this.type = in.readString();
                this.value = in.readString();
            }

            public static final Creator<ValuesDTO> CREATOR = new Creator<ValuesDTO>() {
                @Override
                public ValuesDTO createFromParcel(Parcel source) {
                    return new ValuesDTO(source);
                }

                @Override
                public ValuesDTO[] newArray(int size) {
                    return new ValuesDTO[size];
                }
            };
        }

        public static class SubRestrictGeneBean implements Parcelable {

            @SerializedName("defaultValue")
            private String defaultValue;
            @SerializedName("display")
            private Boolean display;
            @SerializedName("type")
            private Integer type;
            @SerializedName("key")
            private String key;
            @SerializedName("values")
            private List<ValuesDTO> values;

            public String getDefaultValue() {
                return defaultValue;
            }

            public void setDefaultValue(String defaultValue) {
                this.defaultValue = defaultValue;
            }

            public Boolean isDisplay() {
                return display;
            }

            public void setDisplay(Boolean display) {
                this.display = display;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public List<ValuesDTO> getValues() {
                return values;
            }

            public void setValues(List<ValuesDTO> values) {
                this.values = values;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.defaultValue);
                dest.writeValue(this.display);
                dest.writeValue(this.type);
                dest.writeString(this.key);
                dest.writeTypedList(this.values);
            }

            public void readFromParcel(Parcel source) {
                this.defaultValue = source.readString();
                this.display = (Boolean) source.readValue(Boolean.class.getClassLoader());
                this.type = (Integer) source.readValue(Integer.class.getClassLoader());
                this.key = source.readString();
                this.values = source.createTypedArrayList(ValuesDTO.CREATOR);
            }

            public SubRestrictGeneBean() {
            }

            protected SubRestrictGeneBean(Parcel in) {
                this.defaultValue = in.readString();
                this.display = (Boolean) in.readValue(Boolean.class.getClassLoader());
                this.type = (Integer) in.readValue(Integer.class.getClassLoader());
                this.key = in.readString();
                this.values = in.createTypedArrayList(ValuesDTO.CREATOR);
            }

            public static final Creator<SubRestrictGeneBean> CREATOR = new Creator<SubRestrictGeneBean>() {
                @Override
                public SubRestrictGeneBean createFromParcel(Parcel source) {
                    return new SubRestrictGeneBean(source);
                }

                @Override
                public SubRestrictGeneBean[] newArray(int size) {
                    return new SubRestrictGeneBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.defaultValue);
            dest.writeString(this.protectItemId);
            dest.writeValue(this.display);
            dest.writeString(this.name);
            dest.writeValue(this.sort);
            dest.writeValue(this.type);
            dest.writeString(this.key);
            dest.writeTypedList(this.values);
            dest.writeTypedList(this.subRestrictGenes);
        }

        public void readFromParcel(Parcel source) {
            this.defaultValue = source.readString();
            this.protectItemId = source.readString();
            this.display = (Boolean) source.readValue(Boolean.class.getClassLoader());
            this.name = source.readString();
            this.sort = (Integer) source.readValue(Integer.class.getClassLoader());
            this.type = (Integer) source.readValue(Integer.class.getClassLoader());
            this.key = source.readString();
            this.values = source.createTypedArrayList(ValuesDTO.CREATOR);
            this.subRestrictGenes = source.createTypedArrayList(SubRestrictGeneBean.CREATOR);
        }

        public RestrictGenesDTO() {
        }

        protected RestrictGenesDTO(Parcel in) {
            this.defaultValue = in.readString();
            this.protectItemId = in.readString();
            this.display = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.name = in.readString();
            this.sort = (Integer) in.readValue(Integer.class.getClassLoader());
            this.type = (Integer) in.readValue(Integer.class.getClassLoader());
            this.key = in.readString();
            this.values = in.createTypedArrayList(ValuesDTO.CREATOR);
            this.subRestrictGenes = in.createTypedArrayList(SubRestrictGeneBean.CREATOR);
        }

        public static final Creator<RestrictGenesDTO> CREATOR = new Creator<RestrictGenesDTO>() {
            @Override
            public RestrictGenesDTO createFromParcel(Parcel source) {
                return new RestrictGenesDTO(source);
            }

            @Override
            public RestrictGenesDTO[] newArray(int size) {
                return new RestrictGenesDTO[size];
            }
        };
    }
}