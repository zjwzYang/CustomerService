package com.qkd.customerservice;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

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
}