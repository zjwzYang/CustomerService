package com.qkd.customerservice.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;

/**
 * Created on 1/27/21 13:34
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class LoadingActivity extends AppCompatActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sp = getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        String identifier = sp.getString(Constant.USER_IDENTIFIER, "");
        String userSig = sp.getString(Constant.USER_SIG, "");
        if (!TextUtils.isEmpty(identifier) && !TextUtils.isEmpty(userSig)) {
            loginIn(identifier, userSig);
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void loginIn(String identifier, String userSig) {
        V2TIMManager.getInstance().login(identifier, userSig, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                Toast.makeText(LoadingActivity.this, desc, Toast.LENGTH_SHORT).show();
                sp.edit().clear().apply();
                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
            }

            @Override
            public void onSuccess() {
                Toast.makeText(LoadingActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoadingActivity.this, IndexActivity.class));
                finish();
            }
        });
    }
}