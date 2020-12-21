package com.qkd.customerservice.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 12/18/20 13:06
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class PremiumConfigOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : {"include":1,"config":[{"showData":"sex","showName":"性别","fieldName":"param3","productId":62,"showDesc":"性别","showWay":1,"showOrder":2,"id":39,"dictList":[{"showValue":"男","realValue":"1","showOrder":1,"id":5,"dictKey":"sex"},{"showValue":"女","realValue":"2","showOrder":2,"id":6,"dictKey":"sex"}]},{"showData":"","showName":"岁数","fieldName":"param4","productId":62,"showDesc":"岁数","showWay":2,"showOrder":3,"id":40,"dictList":null}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * include : 1
         * config : [{"showData":"sex","showName":"性别","fieldName":"param3","productId":62,"showDesc":"性别","showWay":1,"showOrder":2,"id":39,"dictList":[{"showValue":"男","realValue":"1","showOrder":1,"id":5,"dictKey":"sex"},{"showValue":"女","realValue":"2","showOrder":2,"id":6,"dictKey":"sex"}]},{"showData":"","showName":"岁数","fieldName":"param4","productId":62,"showDesc":"岁数","showWay":2,"showOrder":3,"id":40,"dictList":null}]
         */

        private int include;
        private List<ConfigBean> config;

        public int getInclude() {
            return include;
        }

        public void setInclude(int include) {
            this.include = include;
        }

        public List<ConfigBean> getConfig() {
            return config;
        }

        public void setConfig(List<ConfigBean> config) {
            this.config = config;
        }

        public static class ConfigBean implements Parcelable {
            /**
             * showData : sex
             * showName : 性别
             * fieldName : param3
             * productId : 62
             * showDesc : 性别
             * showWay : 1
             * showOrder : 2
             * id : 39
             * dictList : [{"showValue":"男","realValue":"1","showOrder":1,"id":5,"dictKey":"sex"},{"showValue":"女","realValue":"2","showOrder":2,"id":6,"dictKey":"sex"}]
             */

            private String showData;
            private String showName;
            private String fieldName;
            private int productId;
            private String showDesc;
            private int showWay;
            private int showOrder;
            private int id;
            private List<DictListBean> dictList;

            public String getShowData() {
                return showData;
            }

            public void setShowData(String showData) {
                this.showData = showData;
            }

            public String getShowName() {
                return showName;
            }

            public void setShowName(String showName) {
                this.showName = showName;
            }

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getShowDesc() {
                return showDesc;
            }

            public void setShowDesc(String showDesc) {
                this.showDesc = showDesc;
            }

            public int getShowWay() {
                return showWay;
            }

            public void setShowWay(int showWay) {
                this.showWay = showWay;
            }

            public int getShowOrder() {
                return showOrder;
            }

            public void setShowOrder(int showOrder) {
                this.showOrder = showOrder;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<DictListBean> getDictList() {
                return dictList;
            }

            public void setDictList(List<DictListBean> dictList) {
                this.dictList = dictList;
            }

            public static class DictListBean implements Parcelable {
                /**
                 * showValue : 男
                 * realValue : 1
                 * showOrder : 1
                 * id : 5
                 * dictKey : sex
                 */

                private String showValue;
                private String realValue;
                private int showOrder;
                private int id;
                private String dictKey;
                private boolean isSelect;

                public boolean isSelect() {
                    return isSelect;
                }

                public void setSelect(boolean select) {
                    isSelect = select;
                }

                public String getShowValue() {
                    return showValue;
                }

                public void setShowValue(String showValue) {
                    this.showValue = showValue;
                }

                public String getRealValue() {
                    return realValue;
                }

                public void setRealValue(String realValue) {
                    this.realValue = realValue;
                }

                public int getShowOrder() {
                    return showOrder;
                }

                public void setShowOrder(int showOrder) {
                    this.showOrder = showOrder;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getDictKey() {
                    return dictKey;
                }

                public void setDictKey(String dictKey) {
                    this.dictKey = dictKey;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.showValue);
                    dest.writeString(this.realValue);
                    dest.writeInt(this.showOrder);
                    dest.writeInt(this.id);
                    dest.writeString(this.dictKey);
                    dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
                }

                public DictListBean() {
                }

                protected DictListBean(Parcel in) {
                    this.showValue = in.readString();
                    this.realValue = in.readString();
                    this.showOrder = in.readInt();
                    this.id = in.readInt();
                    this.dictKey = in.readString();
                    this.isSelect = in.readByte() != 0;
                }

                public static final Creator<DictListBean> CREATOR = new Creator<DictListBean>() {
                    @Override
                    public DictListBean createFromParcel(Parcel source) {
                        return new DictListBean(source);
                    }

                    @Override
                    public DictListBean[] newArray(int size) {
                        return new DictListBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.showData);
                dest.writeString(this.showName);
                dest.writeString(this.fieldName);
                dest.writeInt(this.productId);
                dest.writeString(this.showDesc);
                dest.writeInt(this.showWay);
                dest.writeInt(this.showOrder);
                dest.writeInt(this.id);
                dest.writeTypedList(this.dictList);
            }

            public ConfigBean() {
            }

            protected ConfigBean(Parcel in) {
                this.showData = in.readString();
                this.showName = in.readString();
                this.fieldName = in.readString();
                this.productId = in.readInt();
                this.showDesc = in.readString();
                this.showWay = in.readInt();
                this.showOrder = in.readInt();
                this.id = in.readInt();
                this.dictList = in.createTypedArrayList(DictListBean.CREATOR);
            }

            public static final Creator<ConfigBean> CREATOR = new Creator<ConfigBean>() {
                @Override
                public ConfigBean createFromParcel(Parcel source) {
                    return new ConfigBean(source);
                }

                @Override
                public ConfigBean[] newArray(int size) {
                    return new ConfigBean[size];
                }
            };
        }
    }
}