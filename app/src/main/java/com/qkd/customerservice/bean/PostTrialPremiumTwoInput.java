package com.qkd.customerservice.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created on 2021/6/8 15:00
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class PostTrialPremiumTwoInput {

    @SerializedName("oldRestrictGene")
    private PlatformTwoDataBean.PriceArgsDTO.GenesDTO oldRestrictGene;
    @SerializedName("platformId")
    private String platformId;
    @SerializedName("productId")
    private String productId;
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("newRestrictGenes")
    private List<PlatformTwoDataBean.PriceArgsDTO.GenesDTO> newRestrictGenes;

    public PlatformTwoDataBean.PriceArgsDTO.GenesDTO getOldRestrictGene() {
        return oldRestrictGene;
    }

    public void setOldRestrictGene(PlatformTwoDataBean.PriceArgsDTO.GenesDTO oldRestrictGene) {
        this.oldRestrictGene = oldRestrictGene;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<PlatformTwoDataBean.PriceArgsDTO.GenesDTO> getNewRestrictGenes() {
        return newRestrictGenes;
    }

    public void setNewRestrictGenes(List<PlatformTwoDataBean.PriceArgsDTO.GenesDTO> newRestrictGenes) {
        this.newRestrictGenes = newRestrictGenes;
    }

}