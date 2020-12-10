package com.qkd.customerservice.bean;

import com.qkd.customerservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/3/20 13:24
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExpressionManager {
    public static int NORMAL_COUNT_BY_ROW = 7;
    public static ExpressionManager instance = new ExpressionManager();
    private List<Expression> normalExpressionList = new ArrayList<>();

    private ExpressionManager() {
        initExpressionData();
    }

    private void initExpressionData() {
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_1,
                        "1F601"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_2,
                        "1F602"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_3,
                        "1F603"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_4,
                        "1F604"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_5,
                        "1F605"
                )
        );

        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_4,
                        "1F60E"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_5,
                        "1F634"
                )
        );

        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_6,
                        "1F606"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_7,
                        "1F607"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_8,
                        "1F608"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_9,
                        "1F609"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_10,
                        "1F60A"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_11,
                        "1F60B"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_12,
                        "1F60C"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_13,
                        "1F60D"
                )
        );

        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_61,
                        "1F44C"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_62,
                        "1F44D"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_63,
                        "1F44E"
                )
        );

        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_14,
                        "1F60F"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_15,
                        "1F612"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_16,
                        "1F613"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_17,
                        "1F614"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_18,
                        "1F616"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_19,
                        "1F618"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_20,
                        "1F61A"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_21,
                        "1F61C"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_22,
                        "1F61D"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_23,
                        "1F61E"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_24,
                        "1F620"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_25,
                        "1F621"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_26,
                        "1F622"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_27,
                        "1F623"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_28,
                        "1F624"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_29,
                        "1F625"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_30,
                        "1F628"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_31,
                        "1F629"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_32,
                        "1F62A"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_33,
                        "1F62B"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_34,
                        "1F62D"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_35,
                        "1F630"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_36,
                        "1F631"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_37,
                        "1F632"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_38,
                        "1F633"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_39,
                        "1F635"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_40,
                        "1F637"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_41,
                        "1F638"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_42,
                        "1F639"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_43,
                        "1F63A"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_44,
                        "1F63B"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_45,
                        "1F63C"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_46,
                        "1F63D"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_47,
                        "1F63E"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_48,
                        "1F63F"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_49,
                        "1F640"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_50,
                        "1F645"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_51,
                        "1F646"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_52,
                        "1F647"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_53,
                        "1F648"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_54,
                        "1F649"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_55,
                        "1F64A"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_56,
                        "1F64B"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_57,
                        "1F64C"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_58,
                        "1F64D"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_59,
                        "1F64E"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_60,
                        "1F64F"
                )
        );


//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_64,
//                        "e#64^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_65,
//                        "e#65^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_66,
//                        "e#66^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_67,
//                        "e#67^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_68,
//                        "e#68^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_69,
//                        "e#69^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_70,
//                        "e#70^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_71,
//                        "e#71^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_72,
//                        "e#72^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_73,
//                        "e#73^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_74,
//                        "e#74^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_75,
//                        "e#75^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_76,
//                        "e#76^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_77,
//                        "e#77^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_78,
//                        "e#78^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_79,
//                        "e#79^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_80,
//                        "e#80^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_81,
//                        "e#81^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_82,
//                        "e#82^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_83,
//                        "e#83^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_84,
//                        "e#84^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_85,
//                        "e#85^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_86,
//                        "e#86^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_87,
//                        "e#87^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_88,
//                        "e#88^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_89,
//                        "e#89^"
//                )
//        );
        int emptyCount = NORMAL_COUNT_BY_ROW + NORMAL_COUNT_BY_ROW - normalExpressionList.size() % NORMAL_COUNT_BY_ROW;
        for (int i = 0; i < emptyCount; i++) {
            normalExpressionList.add(new Expression(0, null));
        }
    }

    public List<Expression> getNormalExpressionList() {
        return normalExpressionList;
    }
}
