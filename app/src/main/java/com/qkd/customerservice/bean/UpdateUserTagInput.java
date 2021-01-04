package com.qkd.customerservice.bean;

/**
 * Created on 1/4/21 16:17
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class UpdateUserTagInput {
    private String openId;
    private int[] tagIds;

    public int[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(int[] tagIds) {
        this.tagIds = tagIds;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}