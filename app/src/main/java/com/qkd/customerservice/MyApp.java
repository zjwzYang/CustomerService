package com.qkd.customerservice;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * Created on 11/30/20 13:23
 * .
 *
 * @author yj
 */
public class MyApp extends Application {

    public static final String APP_ID = "2882303761518852952";
    public static final String APP_KEY = "5231885256952";
    public static final String TAG = "xiaomipush";

    public static final int TEN_SDKAPPID = 1400481126; // 1400456243

    public static int keyboardHeight = 0;
    private static MyApp instance;

    // 微信
    public static final String WX_APP_ID = "wx88888888";
    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

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
//        if (shouldInit()) {
        MiPushClient.registerPush(this, APP_ID, APP_KEY);
//        }

        initTencenIm();
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

//        //建议动态监听微信启动广播进行注册到微信
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                // 将该app注册到微信
//                api.registerApp(Constants.APP_ID);
//            }
//        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

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
//        TUIKitConfigs configs = TUIKit.getConfigs();
//        configs.setSdkConfig(new V2TIMSDKConfig());
//        configs.setCustomFaceConfig(new CustomFaceConfig());
//        configs.setGeneralConfig(new GeneralConfig());
//
//        TUIKit.init(this, TEN_SDKAPPID, configs);
    }
}
