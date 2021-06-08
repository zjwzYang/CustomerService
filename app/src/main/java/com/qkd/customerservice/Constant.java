package com.qkd.customerservice;

/**
 * Created on 12/7/20 13:24
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class Constant {
    public static final String APP_DATA = "app_data";
    public static final String KEYBOARDHEIGHT = "keyboardHeight";
    public static final String MI_PUSH_REGID = "mi_push_regid";

    public static final String DELETE_FLAG = "delete_flag";
    public static final String SEND_FLAG = "send_flag";

    public static final String TEXT_END_FLAG = "#";
    public static final String TEXT_ARTICLE_FLAG = "##";

    public static final String USER_TOKEN = "user_token";
    public static final String USER_CORE_TOKEN = "user_core_token";

    // 测试 http://qkbdev.qukandian573.com
    // 正式 http://qukanbao.qukandian573.com
    private static final String BASE_BASE_URL = "http://qkbdev.qukandian573.com";
    public static final String BASE_URL_CORE = BASE_BASE_URL + ":8082/";
    public static final String BASE_URL_WX_CHETER = BASE_BASE_URL + ":8081/";
    public static final String BASE_URL_WEB = BASE_BASE_URL + ":8083/";
    public static final String BASE_URL3 = BASE_BASE_URL + "/";


    public static final String USER_INFO = "user_info";
    public static final String USER_SERVICE_ID = "service_id";
    public static final String USER_IDENTIFIER = "user_identifier";
    public static final String USER_STATUS = "user_status";
    public static final String USER_SIG = "user_user_sig";

    public static final String REFRESH_CONVERSATION = "refresh_conversation";

    public static final long IM_HW_ID = 14478;

    public static final String SORT_FLAG = "sort_flag";
    public static final String SORT_TOP = "sort_top";
    public static final String DELETE_USERID = "delete_userid";


    public static final String REFRESH_CUSTOMIZED_LIST = "refresh_customized_list";
    public static final String UPDATE_USER_STATUS = "update_user_status";
}