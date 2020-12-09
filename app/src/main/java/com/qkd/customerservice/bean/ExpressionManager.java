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
                        "1F604"
                )
        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_3,
//                        "1F603"
//                )
//        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_4,
                        "1F603"
                )
        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_5,
//                        "e#5^"
//                )
//        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_6,
                        "1F60A"
                )
        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_7,
//                        "e#7^"
//                )
//        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_8,
                        "1F606"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_9,
                        "1F605"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_10,
                        "1F620"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_11,
                        "1F60F"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_12,
                        "1F60E"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_13,
                        "1F609"
                )
        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_14,
//                        "e#14^"
//                )
//        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_15,
                        "1F622"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_16,
                        "1F625"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_17,
                        "1F61E"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_18,
                        "1F60B"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_19,
                        "1F633"
                )
        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_20,
//                        "e#20^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_21,
//                        "e#21^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_22,
//                        "e#22^"
//                )
//        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_23,
                        "1F61C"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_24,
                        "1F602"
                )
        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_25,
//                        "e#25^"
//                )
//        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_26,
                        "1F60F"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_27,
                        "1F62D"
                )
        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_28,
//                        "e#28^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_29,
//                        "e#29^"
//                )
//        );
//        normalExpressionList.add(
//                new Expression(
//                        R.drawable.img_expression_30,
//                        "e#30^"
//                )
//        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_31,
                        "1F60D"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_32,
                        "e#32^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_33,
                        "e#33^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_34,
                        "e#34^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_35,
                        "e#35^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_36,
                        "e#36^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_37,
                        "e#37^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_38,
                        "e#38^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_39,
                        "e#39^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_40,
                        "e#40^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_41,
                        "e#41^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_42,
                        "e#42^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_43,
                        "e#43^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_44,
                        "e#44^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_45,
                        "e#45^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_46,
                        "e#46^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_47,
                        "e#47^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_48,
                        "e#48^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_49,
                        "e#49^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_50,
                        "e#50^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_51,
                        "e#51^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_52,
                        "e#52^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_53,
                        "e#53^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_54,
                        "e#54^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_55,
                        "e#55^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_56,
                        "e#56^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_57,
                        "e#57^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_58,
                        "e#58^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_59,
                        "e#59^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_60,
                        "e#60^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_61,
                        "e#61^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_62,
                        "e#62^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_63,
                        "e#63^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_64,
                        "e#64^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_65,
                        "e#65^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_66,
                        "e#66^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_67,
                        "e#67^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_68,
                        "e#68^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_69,
                        "e#69^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_70,
                        "e#70^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_71,
                        "e#71^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_72,
                        "e#72^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_73,
                        "e#73^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_74,
                        "e#74^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_75,
                        "e#75^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_76,
                        "e#76^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_77,
                        "e#77^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_78,
                        "e#78^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_79,
                        "e#79^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_80,
                        "e#80^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_81,
                        "e#81^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_82,
                        "e#82^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_83,
                        "e#83^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_84,
                        "e#84^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_85,
                        "e#85^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_86,
                        "e#86^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_87,
                        "e#87^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_88,
                        "e#88^"
                )
        );
        normalExpressionList.add(
                new Expression(
                        R.drawable.img_expression_89,
                        "e#89^"
                )
        );
        int emptyCount = NORMAL_COUNT_BY_ROW + NORMAL_COUNT_BY_ROW - normalExpressionList.size() % NORMAL_COUNT_BY_ROW;
        for (int i = 0; i < emptyCount; i++) {
            normalExpressionList.add(new Expression(0, null));
        }
    }

    public List<Expression> getNormalExpressionList() {
        return normalExpressionList;
    }
}
