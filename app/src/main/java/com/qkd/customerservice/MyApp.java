package com.qkd.customerservice;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig;
import com.tencent.qcloud.tim.uikit.config.GeneralConfig;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created on 11/30/20 13:23
 * .
 *
 * @author yj
 */
public class MyApp extends Application {

    public static final String APP_ID = "your appid";
    public static final String APP_KEY = "your appkey";
    public static final String TAG = "your packagename";

    public static final int TEN_SDKAPPID = 1400456243;

    public static int keyboardHeight = 0;
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        SharedPreferences sp = getSharedPreferences(Constant.APP_DATA, Context.MODE_PRIVATE);
        keyboardHeight = sp.getInt(Constant.KEYBOARDHEIGHT, 0);

        instance = this;
        //初始化push推送服务
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);

        initTencenIm();
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getApplicationInfo().processName;
//        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            Log.i("12345678", "shouldInit: " + info.pid + "   " + info.processName);
            if (mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    private void initTencenIm() {
        V2TIMSDKConfig config = new V2TIMSDKConfig();
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_DEBUG);
        Log.i("12345678", "开始连接: ");
        boolean initSDK = V2TIMManager.getInstance().initSDK(this, TEN_SDKAPPID, config, new V2TIMSDKListener() {
            // 5. 监听 V2TIMSDKListener 回调
            @Override
            public void onConnecting() {
                // 正在连接到腾讯云服务器
                Log.i("12345678", "连接中: ");
            }

            @Override
            public void onConnectSuccess() {
                // 已经成功连接到腾讯云服务器
                Log.i("12345678", "连接成功: ");
            }

            @Override
            public void onConnectFailed(int code, String error) {
                // 连接腾讯云服务器失败
                Log.i("12345678", "连接失败: " + code + "  " + error);
            }

            @Override
            public void onKickedOffline() {
                super.onKickedOffline();
                Log.i("12345678", "当前用户被踢下线");
            }

            @Override
            public void onUserSigExpired() {
                super.onUserSigExpired();
                Log.i("12345678", "登录票据已经过期");
            }

            @Override
            public void onSelfInfoUpdated(V2TIMUserFullInfo info) {
                super.onSelfInfoUpdated(info);
                Log.i("12345678", "当前用户的资料发生了更新");
            }
        });
        Log.i("12345678", "登录initSDK：" + initSDK);

        // 配置 Config，请按需配置
        TUIKitConfigs configs = TUIKit.getConfigs();
        configs.setSdkConfig(new V2TIMSDKConfig());
        configs.setCustomFaceConfig(new CustomFaceConfig());
        configs.setGeneralConfig(new GeneralConfig());

        TUIKit.init(this, TEN_SDKAPPID, configs);
    }
}
