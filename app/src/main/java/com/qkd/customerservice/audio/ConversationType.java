package com.qkd.customerservice.audio;

/**
 * Created on 12/7/20 10:41
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public enum ConversationType {
    NONE(0, "none"),
    PRIVATE(1, "private"),
    DISCUSSION(2, "discussion"),
    GROUP(3, "group"),
    CHATROOM(4, "chatroom"),
    CUSTOMER_SERVICE(5, "customer_service"),
    SYSTEM(6, "system"),
    APP_PUBLIC_SERVICE(7, "app_public_service"),
    PUBLIC_SERVICE(8, "public_service"),
    PUSH_SERVICE(9, "push_service"),
    ENCRYPTED(11, "encrypted"),
    RTC_ROOM(12, "rtc_room");

    private int value = 1;
    private String name = "";

    private ConversationType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static ConversationType setValue(int code) {
        ConversationType[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ConversationType c = var1[var3];
            if (code == c.getValue()) {
                return c;
            }
        }

        return PRIVATE;
    }
}
