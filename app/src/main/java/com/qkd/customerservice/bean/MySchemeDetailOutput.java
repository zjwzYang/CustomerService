package com.qkd.customerservice.bean;

import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 1/22/21 12:44
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class MySchemeDetailOutput extends BaseOutput {

    /**
     * errorMsg : null
     * data : {"id":34,"orderNumber":"549946495447257088","userId":"76","nickName":"百家讲坛","premium":"2592","familyInsureNumber":1,"familyInsurePerson":"本人","createTime":"2021-01-22 10:52:30","updateTime":"2021-01-22 10:52:30","list":[{"id":107,"orderNumber":"549946495447257088","userId":"76","insuredPerson":"本人","gender":"男","birthday":"1990/01/01","age":"31","medicalHistory":"无","medicalHistoryImg":"","profession":"无","socialSecurity":"有","createTime":"2021-01-22T02:52:08.000+0000","updateTime":"2021-01-22T02:52:08.000+0000","totalPremium":"2592.0","dataList":[{"id":231,"orderNumber":"549946495447257088","schemeId":34,"userId":"76","insuranceInfoId":107,"productId":92,"productName":"倍吉星","productType":"重疾险","productByUrl":"","insuredAmount":"10万","firstYearPremium":"2592.00","guaranteePeriod":"保至70岁","paymentPeriod":"15年","createTime":"2021-01-22 10:52:30","updateTime":"2021-01-22 10:52:30","companyId":"5","companyName":"复星联合健康保险","companyLogo":"http://qukanbao.qukandian573.com/files/pic/159073525666213f98616-5722-4ae7-861e-405867bc3ac8.png","companyDesc":null,"productItemDTOList":[{"name":"主险条款","value":"http://qukanbao.qukandian573.com/files/pdf/159073575043850728ae3-430c-4ec5-b922-fa8b52bfc8b1.pdf"},{"name":"保险条款（附加）","value":"http://qukanbao.qukandian573.com/files/pdf/15907357529524b9c713b-d6a1-4c26-ae69-89ae559177f9.pdf"},{"name":"查看费率表","value":"http://qukanbao.qukandian573.com/files/pdf/15907357584281adddead-ff00-4768-8e53-2dd0263bb228.pdf"},{"name":"增值服务","value":"http://qukanbao.qukandian573.com/files/pdf/1590735761355d56aa1e7-d9b2-4b20-aff7-2cc7b3e7f943.pdf"},{"name":"责任免除","value":"http://qukanbao.qukandian573.com/files/pdf/1590735763819f8d5bae0-1679-47d1-b255-9a1258953057.pdf"},{"name":"职业类别表","value":"http://qukanbao.qukandian573.com/files/pdf/15907357758647273db78-80b6-45bf-b774-aa8ba9d16a54.pdf"},{"name":"增值服务一","value":"http://qukanbao.qukandian573.com/files/pdf/15907357857282bdb02a8-ad22-4970-b01c-9564eb88465a.pdf"},{"name":"理赔流程指引","value":"http://qukanbao.qukandian573.com/files/pdf/159073578907173da91b4-1e48-4b04-8251-5b9f42551720.pdf"},{"name":"健康告知","value":"http://qukanbao.qukandian573.com/files/pdf/1591595442719c8792e84-076d-44d2-82e2-3a07448a2246.pdf"}],"productDetailDTOList":[]}]}],"productVideoUrl":null,"qrCodeUrl":"http://47.114.100.72/files/pic/qrCode67.jpg","nick":"管管客服","realName":null,"phoneNumber":null,"schemeType":2,"amount":19.9,"payFlag":0,"chargeFlag":0,"platformId":1,"gender":"男","city":"北京市,北京市","age":"31","birthday":"1990/01/01","socialSecurity":"","orderPhone":"15866993258","family":"本人","familyIncome":"20~30万","familyLoan":"无","sonAmount":"0","dauAmount":"0"}
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
         * id : 34
         * orderNumber : 549946495447257088
         * userId : 76
         * nickName : 百家讲坛
         * premium : 2592
         * familyInsureNumber : 1
         * familyInsurePerson : 本人
         * createTime : 2021-01-22 10:52:30
         * updateTime : 2021-01-22 10:52:30
         * list : [{"id":107,"orderNumber":"549946495447257088","userId":"76","insuredPerson":"本人","gender":"男","birthday":"1990/01/01","age":"31","medicalHistory":"无","medicalHistoryImg":"","profession":"无","socialSecurity":"有","createTime":"2021-01-22T02:52:08.000+0000","updateTime":"2021-01-22T02:52:08.000+0000","totalPremium":"2592.0","dataList":[{"id":231,"orderNumber":"549946495447257088","schemeId":34,"userId":"76","insuranceInfoId":107,"productId":92,"productName":"倍吉星","productType":"重疾险","productByUrl":"","insuredAmount":"10万","firstYearPremium":"2592.00","guaranteePeriod":"保至70岁","paymentPeriod":"15年","createTime":"2021-01-22 10:52:30","updateTime":"2021-01-22 10:52:30","companyId":"5","companyName":"复星联合健康保险","companyLogo":"http://qukanbao.qukandian573.com/files/pic/159073525666213f98616-5722-4ae7-861e-405867bc3ac8.png","companyDesc":null,"productItemDTOList":[{"name":"主险条款","value":"http://qukanbao.qukandian573.com/files/pdf/159073575043850728ae3-430c-4ec5-b922-fa8b52bfc8b1.pdf"},{"name":"保险条款（附加）","value":"http://qukanbao.qukandian573.com/files/pdf/15907357529524b9c713b-d6a1-4c26-ae69-89ae559177f9.pdf"},{"name":"查看费率表","value":"http://qukanbao.qukandian573.com/files/pdf/15907357584281adddead-ff00-4768-8e53-2dd0263bb228.pdf"},{"name":"增值服务","value":"http://qukanbao.qukandian573.com/files/pdf/1590735761355d56aa1e7-d9b2-4b20-aff7-2cc7b3e7f943.pdf"},{"name":"责任免除","value":"http://qukanbao.qukandian573.com/files/pdf/1590735763819f8d5bae0-1679-47d1-b255-9a1258953057.pdf"},{"name":"职业类别表","value":"http://qukanbao.qukandian573.com/files/pdf/15907357758647273db78-80b6-45bf-b774-aa8ba9d16a54.pdf"},{"name":"增值服务一","value":"http://qukanbao.qukandian573.com/files/pdf/15907357857282bdb02a8-ad22-4970-b01c-9564eb88465a.pdf"},{"name":"理赔流程指引","value":"http://qukanbao.qukandian573.com/files/pdf/159073578907173da91b4-1e48-4b04-8251-5b9f42551720.pdf"},{"name":"健康告知","value":"http://qukanbao.qukandian573.com/files/pdf/1591595442719c8792e84-076d-44d2-82e2-3a07448a2246.pdf"}],"productDetailDTOList":[]}]}]
         * productVideoUrl : null
         * qrCodeUrl : http://47.114.100.72/files/pic/qrCode67.jpg
         * nick : 管管客服
         * realName : null
         * phoneNumber : null
         * schemeType : 2
         * amount : 19.9
         * payFlag : 0
         * chargeFlag : 0
         * platformId : 1
         * gender : 男
         * city : 北京市,北京市
         * age : 31
         * birthday : 1990/01/01
         * socialSecurity :
         * orderPhone : 15866993258
         * family : 本人
         * familyIncome : 20~30万
         * familyLoan : 无
         * sonAmount : 0
         * dauAmount : 0
         */

        private int id;
        private long orderNumber;
        private String userId;
        private String nickName;
        private String premium;
        private int familyInsureNumber;
        private String familyInsurePerson;
        private String createTime;
        private String updateTime;
        private Object productVideoUrl;
        private String qrCodeUrl;
        private String nick;
        private Object realName;
        private String phoneNumber;
        private int schemeType;
        private String amount;
        private int payFlag;
        private int chargeFlag;
        private String platformId;
        private String gender;
        private String city;
        private String age;
        private String birthday;
        private String socialSecurity;
        private String orderPhone;
        private String family;
        private String familyIncome;
        private String familyLoan;
        private String sonAmount;
        private String dauAmount;
        private List<ListBean> list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(long orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPremium() {
            return premium;
        }

        public void setPremium(String premium) {
            this.premium = premium;
        }

        public int getFamilyInsureNumber() {
            return familyInsureNumber;
        }

        public void setFamilyInsureNumber(int familyInsureNumber) {
            this.familyInsureNumber = familyInsureNumber;
        }

        public String getFamilyInsurePerson() {
            return familyInsurePerson;
        }

        public void setFamilyInsurePerson(String familyInsurePerson) {
            this.familyInsurePerson = familyInsurePerson;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Object getProductVideoUrl() {
            return productVideoUrl;
        }

        public void setProductVideoUrl(Object productVideoUrl) {
            this.productVideoUrl = productVideoUrl;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public void setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
            this.realName = realName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public int getSchemeType() {
            return schemeType;
        }

        public void setSchemeType(int schemeType) {
            this.schemeType = schemeType;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getPayFlag() {
            return payFlag;
        }

        public void setPayFlag(int payFlag) {
            this.payFlag = payFlag;
        }

        public int getChargeFlag() {
            return chargeFlag;
        }

        public void setChargeFlag(int chargeFlag) {
            this.chargeFlag = chargeFlag;
        }

        public String getPlatformId() {
            return platformId;
        }

        public void setPlatformId(String platformId) {
            this.platformId = platformId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSocialSecurity() {
            return socialSecurity;
        }

        public void setSocialSecurity(String socialSecurity) {
            this.socialSecurity = socialSecurity;
        }

        public String getOrderPhone() {
            return orderPhone;
        }

        public void setOrderPhone(String orderPhone) {
            this.orderPhone = orderPhone;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public String getFamilyIncome() {
            return familyIncome;
        }

        public void setFamilyIncome(String familyIncome) {
            this.familyIncome = familyIncome;
        }

        public String getFamilyLoan() {
            return familyLoan;
        }

        public void setFamilyLoan(String familyLoan) {
            this.familyLoan = familyLoan;
        }

        public String getSonAmount() {
            return sonAmount;
        }

        public void setSonAmount(String sonAmount) {
            this.sonAmount = sonAmount;
        }

        public String getDauAmount() {
            return dauAmount;
        }

        public void setDauAmount(String dauAmount) {
            this.dauAmount = dauAmount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 107
             * orderNumber : 549946495447257088
             * userId : 76
             * insuredPerson : 本人
             * gender : 男
             * birthday : 1990/01/01
             * age : 31
             * medicalHistory : 无
             * medicalHistoryImg :
             * profession : 无
             * socialSecurity : 有
             * createTime : 2021-01-22T02:52:08.000+0000
             * updateTime : 2021-01-22T02:52:08.000+0000
             * totalPremium : 2592.0
             * dataList : [{"id":231,"orderNumber":"549946495447257088","schemeId":34,"userId":"76","insuranceInfoId":107,"productId":92,"productName":"倍吉星","productType":"重疾险","productByUrl":"","insuredAmount":"10万","firstYearPremium":"2592.00","guaranteePeriod":"保至70岁","paymentPeriod":"15年","createTime":"2021-01-22 10:52:30","updateTime":"2021-01-22 10:52:30","companyId":"5","companyName":"复星联合健康保险","companyLogo":"http://qukanbao.qukandian573.com/files/pic/159073525666213f98616-5722-4ae7-861e-405867bc3ac8.png","companyDesc":null,"productItemDTOList":[{"name":"主险条款","value":"http://qukanbao.qukandian573.com/files/pdf/159073575043850728ae3-430c-4ec5-b922-fa8b52bfc8b1.pdf"},{"name":"保险条款（附加）","value":"http://qukanbao.qukandian573.com/files/pdf/15907357529524b9c713b-d6a1-4c26-ae69-89ae559177f9.pdf"},{"name":"查看费率表","value":"http://qukanbao.qukandian573.com/files/pdf/15907357584281adddead-ff00-4768-8e53-2dd0263bb228.pdf"},{"name":"增值服务","value":"http://qukanbao.qukandian573.com/files/pdf/1590735761355d56aa1e7-d9b2-4b20-aff7-2cc7b3e7f943.pdf"},{"name":"责任免除","value":"http://qukanbao.qukandian573.com/files/pdf/1590735763819f8d5bae0-1679-47d1-b255-9a1258953057.pdf"},{"name":"职业类别表","value":"http://qukanbao.qukandian573.com/files/pdf/15907357758647273db78-80b6-45bf-b774-aa8ba9d16a54.pdf"},{"name":"增值服务一","value":"http://qukanbao.qukandian573.com/files/pdf/15907357857282bdb02a8-ad22-4970-b01c-9564eb88465a.pdf"},{"name":"理赔流程指引","value":"http://qukanbao.qukandian573.com/files/pdf/159073578907173da91b4-1e48-4b04-8251-5b9f42551720.pdf"},{"name":"健康告知","value":"http://qukanbao.qukandian573.com/files/pdf/1591595442719c8792e84-076d-44d2-82e2-3a07448a2246.pdf"}],"productDetailDTOList":[]}]
             */

            private int id;
            private long orderNumber;
            private String userId;
            private String insuredPerson;
            private String gender;
            private String birthday;
            private String age;
            private String medicalHistory;
            private String medicalHistoryImg;
            private String profession;
            private String socialSecurity;
            private String createTime;
            private String updateTime;
            private String totalPremium;
            private List<DataListBean> dataList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(long orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getInsuredPerson() {
                return insuredPerson;
            }

            public void setInsuredPerson(String insuredPerson) {
                this.insuredPerson = insuredPerson;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getMedicalHistory() {
                return medicalHistory;
            }

            public void setMedicalHistory(String medicalHistory) {
                this.medicalHistory = medicalHistory;
            }

            public String getMedicalHistoryImg() {
                return medicalHistoryImg;
            }

            public void setMedicalHistoryImg(String medicalHistoryImg) {
                this.medicalHistoryImg = medicalHistoryImg;
            }

            public String getProfession() {
                return profession;
            }

            public void setProfession(String profession) {
                this.profession = profession;
            }

            public String getSocialSecurity() {
                return socialSecurity;
            }

            public void setSocialSecurity(String socialSecurity) {
                this.socialSecurity = socialSecurity;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getTotalPremium() {
                return totalPremium;
            }

            public void setTotalPremium(String totalPremium) {
                this.totalPremium = totalPremium;
            }

            public List<DataListBean> getDataList() {
                return dataList;
            }

            public void setDataList(List<DataListBean> dataList) {
                this.dataList = dataList;
            }

            public static class DataListBean {
                /**
                 * id : 231
                 * orderNumber : 549946495447257088
                 * schemeId : 34
                 * userId : 76
                 * insuranceInfoId : 107
                 * productId : 92
                 * productName : 倍吉星
                 * productType : 重疾险
                 * productByUrl :
                 * insuredAmount : 10万
                 * firstYearPremium : 2592.00
                 * guaranteePeriod : 保至70岁
                 * paymentPeriod : 15年
                 * createTime : 2021-01-22 10:52:30
                 * updateTime : 2021-01-22 10:52:30
                 * companyId : 5
                 * companyName : 复星联合健康保险
                 * companyLogo : http://qukanbao.qukandian573.com/files/pic/159073525666213f98616-5722-4ae7-861e-405867bc3ac8.png
                 * companyDesc : null
                 * productItemDTOList : [{"name":"主险条款","value":"http://qukanbao.qukandian573.com/files/pdf/159073575043850728ae3-430c-4ec5-b922-fa8b52bfc8b1.pdf"},{"name":"保险条款（附加）","value":"http://qukanbao.qukandian573.com/files/pdf/15907357529524b9c713b-d6a1-4c26-ae69-89ae559177f9.pdf"},{"name":"查看费率表","value":"http://qukanbao.qukandian573.com/files/pdf/15907357584281adddead-ff00-4768-8e53-2dd0263bb228.pdf"},{"name":"增值服务","value":"http://qukanbao.qukandian573.com/files/pdf/1590735761355d56aa1e7-d9b2-4b20-aff7-2cc7b3e7f943.pdf"},{"name":"责任免除","value":"http://qukanbao.qukandian573.com/files/pdf/1590735763819f8d5bae0-1679-47d1-b255-9a1258953057.pdf"},{"name":"职业类别表","value":"http://qukanbao.qukandian573.com/files/pdf/15907357758647273db78-80b6-45bf-b774-aa8ba9d16a54.pdf"},{"name":"增值服务一","value":"http://qukanbao.qukandian573.com/files/pdf/15907357857282bdb02a8-ad22-4970-b01c-9564eb88465a.pdf"},{"name":"理赔流程指引","value":"http://qukanbao.qukandian573.com/files/pdf/159073578907173da91b4-1e48-4b04-8251-5b9f42551720.pdf"},{"name":"健康告知","value":"http://qukanbao.qukandian573.com/files/pdf/1591595442719c8792e84-076d-44d2-82e2-3a07448a2246.pdf"}]
                 * productDetailDTOList : []
                 */

                private int id;
                private String orderNumber;
                private int schemeId;
                private String userId;
                private int insuranceInfoId;
                private int productId;
                private String productName;
                private String productType;
                private String productByUrl;
                private String insuredAmount;
                private String firstYearPremium;
                private String guaranteePeriod;
                private String paymentPeriod;
                private String createTime;
                private String updateTime;
                private String companyId;
                private String companyName;
                private String companyLogo;
                private Object companyDesc;
                private List<ProductItemDTOListBean> productItemDTOList;
                private List<?> productDetailDTOList;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getOrderNumber() {
                    return orderNumber;
                }

                public void setOrderNumber(String orderNumber) {
                    this.orderNumber = orderNumber;
                }

                public int getSchemeId() {
                    return schemeId;
                }

                public void setSchemeId(int schemeId) {
                    this.schemeId = schemeId;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public int getInsuranceInfoId() {
                    return insuranceInfoId;
                }

                public void setInsuranceInfoId(int insuranceInfoId) {
                    this.insuranceInfoId = insuranceInfoId;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
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

                public String getProductByUrl() {
                    return productByUrl;
                }

                public void setProductByUrl(String productByUrl) {
                    this.productByUrl = productByUrl;
                }

                public String getInsuredAmount() {
                    return insuredAmount;
                }

                public void setInsuredAmount(String insuredAmount) {
                    this.insuredAmount = insuredAmount;
                }

                public String getFirstYearPremium() {
                    return firstYearPremium;
                }

                public void setFirstYearPremium(String firstYearPremium) {
                    this.firstYearPremium = firstYearPremium;
                }

                public String getGuaranteePeriod() {
                    return guaranteePeriod;
                }

                public void setGuaranteePeriod(String guaranteePeriod) {
                    this.guaranteePeriod = guaranteePeriod;
                }

                public String getPaymentPeriod() {
                    return paymentPeriod;
                }

                public void setPaymentPeriod(String paymentPeriod) {
                    this.paymentPeriod = paymentPeriod;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
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

                public String getCompanyLogo() {
                    return companyLogo;
                }

                public void setCompanyLogo(String companyLogo) {
                    this.companyLogo = companyLogo;
                }

                public Object getCompanyDesc() {
                    return companyDesc;
                }

                public void setCompanyDesc(Object companyDesc) {
                    this.companyDesc = companyDesc;
                }

                public List<ProductItemDTOListBean> getProductItemDTOList() {
                    return productItemDTOList;
                }

                public void setProductItemDTOList(List<ProductItemDTOListBean> productItemDTOList) {
                    this.productItemDTOList = productItemDTOList;
                }

                public List<?> getProductDetailDTOList() {
                    return productDetailDTOList;
                }

                public void setProductDetailDTOList(List<?> productDetailDTOList) {
                    this.productDetailDTOList = productDetailDTOList;
                }

                public static class ProductItemDTOListBean {
                    /**
                     * name : 主险条款
                     * value : http://qukanbao.qukandian573.com/files/pdf/159073575043850728ae3-430c-4ec5-b922-fa8b52bfc8b1.pdf
                     */

                    private String name;
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
}