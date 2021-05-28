package com.qkd.customerservice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created on 2021/5/27 16:41
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class TrialOccupationBean extends TrialFactorFatherBean {

    @SerializedName("value")
    private ValueBean value;
    @SerializedName("detail")
    private List<DetailDTO> detail;

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public List<DetailDTO> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailDTO> detail) {
        this.detail = detail;
    }

    public static class ValueBean {

        @SerializedName("level")
        private String level;
        @SerializedName("label")
        private String label;
        @SerializedName("value")
        private String value;
        @SerializedName("parents")
        private List<ParentsDTO> parents;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<ParentsDTO> getParents() {
            return parents;
        }

        public void setParents(List<ParentsDTO> parents) {
            this.parents = parents;
        }

        public static class ParentsDTO {
            @SerializedName("label")
            private String label;
            @SerializedName("value")
            private String value;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

    public static class DetailDTO implements Parcelable {
        @SerializedName("label")
        private String labelX;
        @SerializedName("value")
        private String value;
        @SerializedName("children")
        private List<ChildrenDTOXX> children;

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

        public List<ChildrenDTOXX> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenDTOXX> children) {
            this.children = children;
        }

        public static class ChildrenDTOXX implements Parcelable {
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

            public static class ChildrenDTOX implements Parcelable {
                @SerializedName("code")
                private String code;
                @SerializedName("label")
                private String labelX;
                @SerializedName("value")
                private String value;
                @SerializedName("children")
                private List<ChildrenDTO> children;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

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

                public static class ChildrenDTO implements Parcelable {
                    @SerializedName("code")
                    private String code;
                    @SerializedName("level")
                    private String level;
                    @SerializedName("label")
                    private String labelX;
                    @SerializedName("value")
                    private String value;

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public String getLevel() {
                        return level;
                    }

                    public void setLevel(String level) {
                        this.level = level;
                    }

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

                    protected ChildrenDTO(Parcel in) {
                        code = in.readString();
                        level = in.readString();
                        labelX = in.readString();
                        value = in.readString();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(code);
                        dest.writeString(level);
                        dest.writeString(labelX);
                        dest.writeString(value);
                    }

                    @SuppressWarnings("unused")
                    public static final Parcelable.Creator<ChildrenDTO> CREATOR = new Parcelable.Creator<ChildrenDTO>() {
                        @Override
                        public ChildrenDTO createFromParcel(Parcel in) {
                            return new ChildrenDTO(in);
                        }

                        @Override
                        public ChildrenDTO[] newArray(int size) {
                            return new ChildrenDTO[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.code);
                    dest.writeString(this.labelX);
                    dest.writeString(this.value);
                    dest.writeTypedList(this.children);
                }

                public void readFromParcel(Parcel source) {
                    this.code = source.readString();
                    this.labelX = source.readString();
                    this.value = source.readString();
                    this.children = source.createTypedArrayList(ChildrenDTO.CREATOR);
                }

                public ChildrenDTOX() {
                }

                protected ChildrenDTOX(Parcel in) {
                    this.code = in.readString();
                    this.labelX = in.readString();
                    this.value = in.readString();
                    this.children = in.createTypedArrayList(ChildrenDTO.CREATOR);
                }

                public static final Creator<ChildrenDTOX> CREATOR = new Creator<ChildrenDTOX>() {
                    @Override
                    public ChildrenDTOX createFromParcel(Parcel source) {
                        return new ChildrenDTOX(source);
                    }

                    @Override
                    public ChildrenDTOX[] newArray(int size) {
                        return new ChildrenDTOX[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.labelX);
                dest.writeString(this.value);
                dest.writeTypedList(this.children);
            }

            public void readFromParcel(Parcel source) {
                this.labelX = source.readString();
                this.value = source.readString();
                this.children = source.createTypedArrayList(ChildrenDTOX.CREATOR);
            }

            public ChildrenDTOXX() {
            }

            protected ChildrenDTOXX(Parcel in) {
                this.labelX = in.readString();
                this.value = in.readString();
                this.children = in.createTypedArrayList(ChildrenDTOX.CREATOR);
            }

            public static final Creator<ChildrenDTOXX> CREATOR = new Creator<ChildrenDTOXX>() {
                @Override
                public ChildrenDTOXX createFromParcel(Parcel source) {
                    return new ChildrenDTOXX(source);
                }

                @Override
                public ChildrenDTOXX[] newArray(int size) {
                    return new ChildrenDTOXX[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.labelX);
            dest.writeString(this.value);
            dest.writeTypedList(this.children);
        }

        public void readFromParcel(Parcel source) {
            this.labelX = source.readString();
            this.value = source.readString();
            this.children = source.createTypedArrayList(ChildrenDTOXX.CREATOR);
        }

        public DetailDTO() {
        }

        protected DetailDTO(Parcel in) {
            this.labelX = in.readString();
            this.value = in.readString();
            this.children = in.createTypedArrayList(ChildrenDTOXX.CREATOR);
        }

        public static final Creator<DetailDTO> CREATOR = new Creator<DetailDTO>() {
            @Override
            public DetailDTO createFromParcel(Parcel source) {
                return new DetailDTO(source);
            }

            @Override
            public DetailDTO[] newArray(int size) {
                return new DetailDTO[size];
            }
        };
    }
}