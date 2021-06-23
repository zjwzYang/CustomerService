package com.qkd.customerservice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created on 2021/6/23 15:20
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class OccupationBean implements Parcelable {

    @SerializedName("code")
    private String code;
    @SerializedName("level")
    private Integer level;
    @SerializedName("name")
    private String name;
    @SerializedName("parentId")
    private String parentId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
        @SerializedName("level")
        private Integer level;
        @SerializedName("name")
        private String name;
        @SerializedName("parentId")
        private String parentId;
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

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
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
            @SerializedName("riskLevel")
            private Integer riskLevel;
            @SerializedName("level")
            private Integer level;
            @SerializedName("name")
            private String name;
            @SerializedName("parentId")
            private String parentId;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public Integer getRiskLevel() {
                return riskLevel;
            }

            public void setRiskLevel(Integer riskLevel) {
                this.riskLevel = riskLevel;
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

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.code);
                dest.writeValue(this.riskLevel);
                dest.writeValue(this.level);
                dest.writeString(this.name);
                dest.writeString(this.parentId);
            }

            public void readFromParcel(Parcel source) {
                this.code = source.readString();
                this.riskLevel = (Integer) source.readValue(Integer.class.getClassLoader());
                this.level = (Integer) source.readValue(Integer.class.getClassLoader());
                this.name = source.readString();
                this.parentId = source.readString();
            }

            public ChildrenDTO() {
            }

            protected ChildrenDTO(Parcel in) {
                this.code = in.readString();
                this.riskLevel = (Integer) in.readValue(Integer.class.getClassLoader());
                this.level = (Integer) in.readValue(Integer.class.getClassLoader());
                this.name = in.readString();
                this.parentId = in.readString();
            }

            public static final Creator<ChildrenDTO> CREATOR = new Creator<ChildrenDTO>() {
                @Override
                public ChildrenDTO createFromParcel(Parcel source) {
                    return new ChildrenDTO(source);
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
            dest.writeValue(this.level);
            dest.writeString(this.name);
            dest.writeString(this.parentId);
            dest.writeTypedList(this.children);
        }

        public void readFromParcel(Parcel source) {
            this.code = source.readString();
            this.level = (Integer) source.readValue(Integer.class.getClassLoader());
            this.name = source.readString();
            this.parentId = source.readString();
            this.children = source.createTypedArrayList(ChildrenDTO.CREATOR);
        }

        public ChildrenDTOX() {
        }

        protected ChildrenDTOX(Parcel in) {
            this.code = in.readString();
            this.level = (Integer) in.readValue(Integer.class.getClassLoader());
            this.name = in.readString();
            this.parentId = in.readString();
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
        dest.writeString(this.code);
        dest.writeValue(this.level);
        dest.writeString(this.name);
        dest.writeString(this.parentId);
        dest.writeTypedList(this.children);
    }

    public void readFromParcel(Parcel source) {
        this.code = source.readString();
        this.level = (Integer) source.readValue(Integer.class.getClassLoader());
        this.name = source.readString();
        this.parentId = source.readString();
        this.children = source.createTypedArrayList(ChildrenDTOX.CREATOR);
    }

    public OccupationBean() {
    }

    protected OccupationBean(Parcel in) {
        this.code = in.readString();
        this.level = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.parentId = in.readString();
        this.children = in.createTypedArrayList(ChildrenDTOX.CREATOR);
    }

    public static final Creator<OccupationBean> CREATOR = new Creator<OccupationBean>() {
        @Override
        public OccupationBean createFromParcel(Parcel source) {
            return new OccupationBean(source);
        }

        @Override
        public OccupationBean[] newArray(int size) {
            return new OccupationBean[size];
        }
    };
}