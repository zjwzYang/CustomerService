package com.qkd.customerservice.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.MainActivity;
import com.qkd.customerservice.R;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMOfflinePushConfig;

/**
 * Created on 12/2/20 09:10
 * 登录.
 *
 * @author yj
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");
        final String userId = "test_yang";
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userSig = "eJw1jtEKgjAYRt9lt4b9bnPloCsvuikrFCIIYrQ-W5oNXWFF755oXX7n48B5k2yR*thaUyORLAiZAIBRTx9YE0moD2TYjS6UtUYTGXAAHgrK2fAYjZUzJ9MLDht3eKoq-2sm7yi-LF8Jnuk6Pirrab0Tq7YsynQ7vyncj7N7UkQJZVzF3mb2M525dlGBADGJGGXTzxdmszNJ";
                V2TIMManager.getInstance().login(userId, userSig, new V2TIMCallback() {
                    @Override
                    public void onError(int code, String desc) {
                    }

                    @Override
                    public void onSuccess() {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                        finish();
                    }
                });
            }
        });
        findViewById(R.id.history_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        SharedPreferences sp = getSharedPreferences(Constant.APP_DATA, Context.MODE_PRIVATE);
        String regId = sp.getString(Constant.MI_PUSH_REGID, "");
        Log.d("12345678", "regId: " + regId);
        // 注册离线推送
        V2TIMManager.getOfflinePushManager().setOfflinePushConfig(new V2TIMOfflinePushConfig(14332, regId), new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                Log.i("12345678", "离线推送失败: " + code + "  " + desc);
            }

            @Override
            public void onSuccess() {
                Log.i("12345678", "离线推送成功: ");
            }
        });
    }
}
