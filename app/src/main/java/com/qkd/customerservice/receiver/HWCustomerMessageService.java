package com.qkd.customerservice.receiver;

import android.text.TextUtils;
import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.qkd.customerservice.Constant;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMOfflinePushConfig;

/**
 * Created on 12/24/20 15:13
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class HWCustomerMessageService extends HmsMessageService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        // 获取token
        Log.i("12345678", "received refresh token:" + token);

        // 判断token是否为空
        if (!TextUtils.isEmpty(token)) {
            V2TIMOfflinePushConfig config = new V2TIMOfflinePushConfig(Constant.IM_HW_ID, token);
            V2TIMManager.getOfflinePushManager().setOfflinePushConfig(config, new V2TIMCallback() {
                @Override
                public void onError(int code, String desc) {

                }

                @Override
                public void onSuccess() {
                    Log.i("12345678", "token上次成功 ");
                }
            });
        }
    }
}