package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 12/15/20 10:30
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class ProductOutput extends BaseOutput {

    /**
     * recordsTotal : 4
     * recordsFiltered : 4
     * data : [{"id":59,"productName":"泰康微医保长期医疗险","productCode":"TD777777","productType":1,"productFeatures":"闪赔保险","productSub":4,"productBannerUrl":"http://47.110.250.21:8084/pic/tkwyb-banner.png","productContent":"泰康微医保长期医疗险","companyId":2,"cashValueUrl":"http://47.110.250.21:8084/pic/xjjz-all.png","lowestPrice":25.25,"forPeople":0,"tags":null,"company":{"id":2,"name":"平安保险桐乡分公司","logoUrl":null,"telphone":null},"familyPro":1,"productLeftUrl":"http://47.110.250.21:8084/pic/left-2.jpg","productSameTypeUrl":null,"cashValueRate":null,"highlights":null,"insufficient":null,"precautions":null,"overview":null,"isBoutique":1,"boutiqueUrl":null,"tagId":"9","labelName":"医疗险","labelType":"1","labelDesc":"医疗险"}]
     */

    private int recordsTotal;
    private int recordsFiltered;
    private List<DataBean> data;

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 59
         * productName : 泰康微医保长期医疗险
         * productCode : TD777777
         * productType : 1
         * productFeatures : 闪赔保险
         * productSub : 4
         * productBannerUrl : http://47.110.250.21:8084/pic/tkwyb-banner.png
         * productContent : 泰康微医保长期医疗险
         * companyId : 2
         * cashValueUrl : http://47.110.250.21:8084/pic/xjjz-all.png
         * lowestPrice : 25.25
         * forPeople : 0
         * tags : null
         * company : {"id":2,"name":"平安保险桐乡分公司","logoUrl":null,"telphone":null}
         * familyPro : 1
         * productLeftUrl : http://47.110.250.21:8084/pic/left-2.jpg
         * productSameTypeUrl : null
         * cashValueRate : null
         * highlights : null
         * insufficient : null
         * precautions : null
         * overview : null
         * isBoutique : 1
         * boutiqueUrl : null
         * tagId : 9
         * labelName : 医疗险
         * labelType : 1
         * labelDesc : 医疗险
         */

        private int id;
        private String productName;
        private String productCode;
        private int productType;
        private String productFeatures;
        private int productSub;
        private String productBannerUrl;
        private String productContent;
        private int companyId;
        private String cashValueUrl;
        private double lowestPrice;
        private int forPeople;
        private Object tags;
        private CompanyBean company;
        private int familyPro;
        private String productLeftUrl;
        private Object productSameTypeUrl;
        private Object cashValueRate;
        private Object highlights;
        private Object insufficient;
        private Object precautions;
        private Object overview;
        private int isBoutique;
        private Object boutiqueUrl;
        private String tagId;
        private String labelName;
        private String labelType;
        private String labelDesc;

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

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public int getProductType() {
            return productType;
        }

        public void setProductType(int productType) {
            this.productType = productType;
        }

        public String getProductFeatures() {
            return productFeatures;
        }

        public void setProductFeatures(String productFeatures) {
            this.productFeatures = productFeatures;
        }

        public int getProductSub() {
            return productSub;
        }

        public void setProductSub(int productSub) {
            this.productSub = productSub;
        }

        public String getProductBannerUrl() {
            return productBannerUrl;
        }

        public void setProductBannerUrl(String productBannerUrl) {
            this.productBannerUrl = productBannerUrl;
        }

        public String getProductContent() {
            return productContent;
        }

        public void setProductContent(String productContent) {
            this.productContent = productContent;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCashValueUrl() {
            return cashValueUrl;
        }

        public void setCashValueUrl(String cashValueUrl) {
            this.cashValueUrl = cashValueUrl;
        }

        public double getLowestPrice() {
            return lowestPrice;
        }

        public void setLowestPrice(double lowestPrice) {
            this.lowestPrice = lowestPrice;
        }

        public int getForPeople() {
            return forPeople;
        }

        public void setForPeople(int forPeople) {
            this.forPeople = forPeople;
        }

        public Object getTags() {
            return tags;
        }

        public void setTags(Object tags) {
            this.tags = tags;
        }

        public CompanyBean getCompany() {
            return company;
        }

        public void setCompany(CompanyBean company) {
            this.company = company;
        }

        public int getFamilyPro() {
            return familyPro;
        }

        public void setFamilyPro(int familyPro) {
            this.familyPro = familyPro;
        }

        public String getProductLeftUrl() {
            return productLeftUrl;
        }

        public void setProductLeftUrl(String productLeftUrl) {
            this.productLeftUrl = productLeftUrl;
        }

        public Object getProductSameTypeUrl() {
            return productSameTypeUrl;
        }

        public void setProductSameTypeUrl(Object productSameTypeUrl) {
            this.productSameTypeUrl = productSameTypeUrl;
        }

        public Object getCashValueRate() {
            return cashValueRate;
        }

        public void setCashValueRate(Object cashValueRate) {
            this.cashValueRate = cashValueRate;
        }

        public Object getHighlights() {
            return highlights;
        }

        public void setHighlights(Object highlights) {
            this.highlights = highlights;
        }

        public Object getInsufficient() {
            return insufficient;
        }

        public void setInsufficient(Object insufficient) {
            this.insufficient = insufficient;
        }

        public Object getPrecautions() {
            return precautions;
        }

        public void setPrecautions(Object precautions) {
            this.precautions = precautions;
        }

        public Object getOverview() {
            return overview;
        }

        public void setOverview(Object overview) {
            this.overview = overview;
        }

        public int getIsBoutique() {
            return isBoutique;
        }

        public void setIsBoutique(int isBoutique) {
            this.isBoutique = isBoutique;
        }

        public Object getBoutiqueUrl() {
            return boutiqueUrl;
        }

        public void setBoutiqueUrl(Object boutiqueUrl) {
            this.boutiqueUrl = boutiqueUrl;
        }

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public String getLabelType() {
            return labelType;
        }

        public void setLabelType(String labelType) {
            this.labelType = labelType;
        }

        public String getLabelDesc() {
            return labelDesc;
        }

        public void setLabelDesc(String labelDesc) {
            this.labelDesc = labelDesc;
        }

        public static class CompanyBean {
            /**
             * id : 2
             * name : 平安保险桐乡分公司
             * logoUrl : null
             * telphone : null
             */

            private int id;
            private String name;
            private Object logoUrl;
            private Object telphone;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getLogoUrl() {
                return logoUrl;
            }

            public void setLogoUrl(Object logoUrl) {
                this.logoUrl = logoUrl;
            }

            public Object getTelphone() {
                return telphone;
            }

            public void setTelphone(Object telphone) {
                this.telphone = telphone;
            }
        }
    }
}