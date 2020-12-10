package com.qkd.customerservice;

import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created on 12/8/20 09:49
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class AppUtil {
    public static List<Uri> convert(List<String> data) {
        List<Uri> list = new ArrayList<>();
        for (String d : data) list.add(Uri.parse(d));
        return list;
    }

    public static String getTimeString(Long timestamp) {
        String result = "";
        String[] weekNames = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String hourTimeFormat = "HH:mm";
        String monthTimeFormat = "M月d日 HH:mm";
        String yearTimeFormat = "yyyy年M月d日 HH:mm";
        try {
            Calendar todayCalendar = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);

            if (todayCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {//当年
                if (todayCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {//当月
                    int temp = todayCalendar.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);
                    switch (temp) {
                        case 0://今天
                            result = getTime(timestamp, hourTimeFormat);
                            break;
                        case 1://昨天
                            result = "昨天 " + getTime(timestamp, hourTimeFormat);
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                            result = weekNames[dayOfWeek - 1] + " " + getTime(timestamp, hourTimeFormat);
                            break;
                        default:
                            result = getTime(timestamp, monthTimeFormat);
                            break;
                    }
                } else {
                    result = getTime(timestamp, monthTimeFormat);
                }
            } else {
                result = getTime(timestamp, yearTimeFormat);
            }
            return result;
        } catch (Exception e) {
            Log.e("getTimeString", e.getMessage());
            return "";
        }
    }

    public static String getTime(long time, String pattern) {
        Date date = new Date(time);
        return dateFormat(date, pattern);
    }

    public static String dateFormat(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


    public static String getTimeStringAutoShort2(Date srcDate, boolean mustIncludeTime) {

        String ret = "";
        try {

            GregorianCalendar gcCurrent = new GregorianCalendar();

            gcCurrent.setTime(new Date());

            int currentYear = gcCurrent.get(GregorianCalendar.YEAR);

            int currentMonth = gcCurrent.get(GregorianCalendar.MONTH) + 1;

            int currentDay = gcCurrent.get(GregorianCalendar.DAY_OF_MONTH);


            GregorianCalendar gcSrc = new GregorianCalendar();

            gcSrc.setTime(srcDate);

            int srcYear = gcSrc.get(GregorianCalendar.YEAR);

            int srcMonth = gcSrc.get(GregorianCalendar.MONTH) + 1;

            int srcDay = gcSrc.get(GregorianCalendar.DAY_OF_MONTH);


            // 要额外显示的时间分钟

            String timeExtraStr = (mustIncludeTime ? " " + getTimeString(srcDate, "HH:mm") : "");


            // 当年

            if (currentYear == srcYear) {

                long currentTimestamp = gcCurrent.getTimeInMillis();

                long srcTimestamp = gcSrc.getTimeInMillis();


                // 相差时间（单位：毫秒）

                long delta = (currentTimestamp - srcTimestamp);


                // 当天（月份和日期一致才是）

                if (currentMonth == srcMonth && currentDay == srcDay) {

                    // 时间相差60秒以内

                    if (delta < 60 * 1000)

                        ret = "刚刚";

                        // 否则当天其它时间段的，直接显示“时:分”的形式

                    else

                        ret = getTimeString(srcDate, "HH:mm");

                }

                // 当年 && 当天之外的时间（即昨天及以前的时间）

                else {

                    // 昨天（以“现在”的时候为基准-1天）

                    GregorianCalendar yesterdayDate = new GregorianCalendar();

                    yesterdayDate.add(GregorianCalendar.DAY_OF_MONTH, -1);


                    // 前天（以“现在”的时候为基准-2天）

                    GregorianCalendar beforeYesterdayDate = new GregorianCalendar();

                    beforeYesterdayDate.add(GregorianCalendar.DAY_OF_MONTH, -2);


                    // 用目标日期的“月”和“天”跟上方计算出来的“昨天”进行比较，是最为准确的（如果用时间戳差值

                    // 的形式，是不准确的，比如：现在时刻是2019年02月22日1:00、而srcDate是2019年02月21日23:00，

                    // 这两者间只相差2小时，直接用“delta/(3600 * 1000)” > 24小时来判断是否昨天，就完全是扯蛋的逻辑了）

                    if (srcMonth == (yesterdayDate.get(GregorianCalendar.MONTH) + 1)

                            && srcDay == yesterdayDate.get(GregorianCalendar.DAY_OF_MONTH)) {

                        ret = "昨天" + timeExtraStr;// -1d

                    }

                    // “前天”判断逻辑同上

                    else if (srcMonth == (beforeYesterdayDate.get(GregorianCalendar.MONTH) + 1)

                            && srcDay == beforeYesterdayDate.get(GregorianCalendar.DAY_OF_MONTH)) {

                        ret = "前天" + timeExtraStr;// -2d

                    } else {

                        // 跟当前时间相差的小时数

                        long deltaHour = (delta / (3600 * 1000));


                        // 如果小于 7*24小时就显示星期几

                        if (deltaHour < 7 * 24) {

                            String[] weekday = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};


                            // 取出当前是星期几

                            String weedayDesc = weekday[gcSrc.get(GregorianCalendar.DAY_OF_WEEK) - 1];

                            ret = weedayDesc + timeExtraStr;

                        }

                        // 否则直接显示完整日期时间

                        else

                            ret = getTimeString(srcDate, "yyyy/M/d") + timeExtraStr;

                    }

                }

            } else

                ret = getTimeString(srcDate, "yyyy/M/d") + timeExtraStr;

        } catch (Exception e) {

            System.err.println("【DEBUG-getTimeStringAutoShort】计算出错：" + e.getMessage() + " 【NO】");

        }


        return ret;

    }

    public static String getTimeString(Date dt, String pattern) {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(pattern);//"yyyy-MM-dd HH:mm:ss"

            sdf.setTimeZone(TimeZone.getDefault());

            return sdf.format(dt);

        } catch (Exception e) {

            return "";

        }

    }


    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }
}