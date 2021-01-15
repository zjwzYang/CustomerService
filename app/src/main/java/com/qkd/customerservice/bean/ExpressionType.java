package com.qkd.customerservice.bean;

/**
 * Created on 12/3/20 13:15
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExpressionType {
    private int resId;
    private String type;

    public ExpressionType(int resId, String type) {
        this.resId = resId;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return "ExpressionType{" +
                "resId=" + resId;
    }

    public static final String EXPRESSION_EMOJI = "expression_emoji";
    public static final String EXPRESSION_KNOWLEDGE_TEXT = "expression_knowledge_text";
    public static final String EXPRESSION_KNOWLEDGE_PHOTO = "expression_knowledge_photo";
    public static final String EXPRESSION_KNOWLEDGE_YUYING = "expression_knowledge_yuying";
    public static final String EXPRESSION_PRODUCT_ONE = "expression_product_one";
    public static final String EXPRESSION_PRODUCT_TWO = "expression_product_two";
}
