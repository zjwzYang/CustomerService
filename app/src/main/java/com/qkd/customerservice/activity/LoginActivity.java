package com.qkd.customerservice.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.qkd.customerservice.BuildConfig;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.MyApp;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.LoginOutput;
import com.qkd.customerservice.key_library.util.SoftKeyboardStateHelper;
import com.qkd.customerservice.net.BaseHttp;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created on 12/2/20 09:10
 * 登录.
 *
 * @author yj
 */
public class LoginActivity extends AppCompatActivity {

    private SoftKeyboardStateHelper mSoftKeyboardStateHelper;

    private ConstraintLayout mConstraintLayout;
    private EditText mUserName;
    private EditText mPswV;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");
        sp = getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        String identifier = sp.getString(Constant.USER_IDENTIFIER, "");
        String userSig = sp.getString(Constant.USER_SIG, "");
        if (!TextUtils.isEmpty(identifier) && !TextUtils.isEmpty(userSig)) {
            loginIn(identifier, userSig);
            return;
        }

        mConstraintLayout = findViewById(R.id.layout_main);
        mUserName = findViewById(R.id.et_phoneNumber);
        mPswV = findViewById(R.id.et_verifyCode);
        if (BuildConfig.DEBUG) {
            mUserName.setText("yj");
            mPswV.setText("88888888");
        }

        mSoftKeyboardStateHelper = new SoftKeyboardStateHelper(mConstraintLayout);
        mSoftKeyboardStateHelper.setListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeight) {
                MyApp.keyboardHeight = keyboardHeight;
                SharedPreferences sp = getSharedPreferences(Constant.APP_DATA, Context.MODE_PRIVATE);
                sp.edit().putInt(Constant.KEYBOARDHEIGHT, keyboardHeight).apply();
            }

            @Override
            public void onSoftKeyboardClosed() {

            }
        });
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mUserName.getText().toString();
                String password = mPswV.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    mUserName.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    mPswV.requestFocus();
                    return;
                } else {
                    login(userName, password);
                }
            }
        });
    }

    private void login(String userName, String password) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        MediaType parse = MediaType.Companion.parse("multipart/form-data");
        RequestBody userNameBody = RequestBody.Companion.create(userName, parse);
        RequestBody passwordBody = RequestBody.Companion.create(password, parse);
        requestBodyMap.put("username", userNameBody);
        requestBodyMap.put("password", passwordBody);

        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).login(requestBodyMap), new BaseHttp.HttpObserver<LoginOutput>() {
            @Override
            public void onSuccess(LoginOutput loginOutput) {
                if (loginOutput.isSuccess()) {
                    SharedPreferences.Editor edit = sp.edit();
                    LoginOutput.DataBean data = loginOutput.getData();
                    edit.putInt(Constant.USER_SERVICE_ID, data.getServiceId());
                    edit.putString(Constant.USER_IDENTIFIER, data.getIdentifier());
                    edit.putInt(Constant.USER_STATUS, data.getStatus());
                    edit.putString(Constant.USER_SIG, data.getUserSig());
                    edit.putString(Constant.USER_TOKEN, data.getTokenMap().getToken());
                    edit.putString(Constant.USER_CORE_TOKEN, data.getCoreToken());
                    edit.apply();
                    loginIn(data.getIdentifier(), data.getUserSig());
                } else {
                    Toast.makeText(LoginActivity.this, loginOutput.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void loginIn(String identifier, String userSig) {
        V2TIMManager.getInstance().login(identifier, userSig, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                Toast.makeText(LoginActivity.this, desc, Toast.LENGTH_SHORT).show();
                sp.edit().clear().apply();
            }

            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                finish();
            }
        });
    }
}
