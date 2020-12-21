package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 12/18/20 10:45
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class ProductListOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : [{"id":57,"productName":"好医保长期医疗2020好医保长期医疗2020","productType":"1","companyId":"1","companyName":"中国人寿桐乡分公司"},{"id":59,"productName":"泰康微医保长期医疗险","productType":"1","companyId":"2","companyName":"平安保险桐乡分公司"},{"id":60,"productName":"国富医疗定海柱1号","productType":"2","companyId":"3","companyName":"安全保险公司"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 57
         * productName : 好医保长期医疗2020好医保长期医疗2020
         * productType : 1
         * companyId : 1
         * companyName : 中国人寿桐乡分公司
         */

        private int id;
        private String productName;
        private String productType;
        private String companyId;
        private String companyName;
        private int isContainAttachInsurance;
        private String premiumNum;
        private String[][] arrayData;

        public String[][] getArrayData() {
            return arrayData;
        }

        public void setArrayData(String[][] arrayData) {
            this.arrayData = arrayData;
        }

        public String getPremiumNum() {
            return premiumNum;
        }

        public void setPremiumNum(String premiumNum) {
            this.premiumNum = premiumNum;
        }

        public int getIsContainAttachInsurance() {
            return isContainAttachInsurance;
        }

        public void setIsContainAttachInsurance(int isContainAttachInsurance) {
            this.isContainAttachInsurance = isContainAttachInsurance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}