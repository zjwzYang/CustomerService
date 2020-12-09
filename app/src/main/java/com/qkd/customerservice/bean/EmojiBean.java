package com.qkd.customerservice.bean;

/**
 * Created on 12/9/20 15:48
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class EmojiBean {
    private String emojiUnique;

    public EmojiBean(String emojiUnique) {
        this.emojiUnique = emojiUnique;
    }

    public String getEmojiUnique() {
        return emojiUnique;
    }

    public void setEmojiUnique(String emojiUnique) {
        this.emojiUnique = emojiUnique;
    }
}