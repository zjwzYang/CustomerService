package com.qkd.customerservice.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.UpdateUserTagInput;
import com.qkd.customerservice.bean.UpdateWechatInput;
import com.qkd.customerservice.bean.UserTagOutput;
import com.qkd.customerservice.bean.UserTagsBean;
import com.qkd.customerservice.net.BaseHttp;
import com.qkd.customerservice.net.BaseOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1/4/21 15:11
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CustomerInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private String openId;

    private TextView mWxFlag;
    private TextView mTags;
    private boolean addedWx;
    private View wxChangeV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        mWxFlag = findViewById(R.id.customer_info_wx);
        mTags = findViewById(R.id.customer_tags);
        wxChangeV = findViewById(R.id.customer_info_wx_change);
        wxChangeV.setOnClickListener(this);
        findViewById(R.id.customer_info_tag_change).setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        openId = intent.getStringExtra("openId");
        addedWx = intent.getBooleanExtra("addedWx", false);
        setTitle(intent.getStringExtra("showName"));

        if (addedWx) {
            mWxFlag.setText("是否已经添加微信：是");
            wxChangeV.setVisibility(View.GONE);
        } else {
            mWxFlag.setText("是否已经添加微信：否");
            wxChangeV.setVisibility(View.VISIBLE);
        }

        initData();
    }

    private void initData() {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).queryUserTag(openId), new BaseHttp.HttpObserver<UserTagsBean>() {
            @Override
            public void onSuccess(UserTagsBean output) {
                if (output.isSuccess()) {
                    List<String> data = output.getData();
                    String tags = "";
                    for (String s : data) {
                        tags += s + "/";
                    }
                    if (!TextUtils.isEmpty(tags)) {
                        mTags.setText(tags.substring(0, tags.length() - 1));
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.customer_info_wx_change:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示")
                        .setMessage("确定修改？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                UpdateWechatInput input = new UpdateWechatInput();
                                input.setOpenId(openId);
                                if (addedWx) {
                                    input.setIsAddWechat(0);
                                } else {
                                    input.setIsAddWechat(1);
                                }
                                BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE)
                                        .updateIsAddWechat(input), new BaseHttp.HttpObserver<BaseOutput>() {
                                    @Override
                                    public void onSuccess(BaseOutput baseOutput) {
                                        if (baseOutput.isSuccess()) {
                                            Toast.makeText(CustomerInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            addedWx = !addedWx;
                                            if (addedWx) {
                                                mWxFlag.setText("是否已经添加微信：是");
                                                wxChangeV.setVisibility(View.GONE);
                                            } else {
                                                mWxFlag.setText("是否已经添加微信：否");
                                                wxChangeV.setVisibility(View.GONE);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                            }
                        });
                builder.create().show();
                break;

            case R.id.customer_info_tag_change:
                getUserTags();
                break;
        }
    }

    private void getUserTags() {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).getUserTags(1), new BaseHttp.HttpObserver<UserTagOutput>() {
            @Override
            public void onSuccess(UserTagOutput output) {
                final List<UserTagOutput.DataBean.ListBean> list = output.getData().getList();
                String[] items = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    items[i] = list.get(i).getTagName();
                }
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CustomerInfoActivity.this);
                alertBuilder.setTitle("选择标签");
                alertBuilder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        list.get(i).setCheck(b);
                    }
                });
                alertBuilder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateUserTag(list);
                    }
                });
                alertBuilder.setNegativeButton("取消", null);
                alertBuilder.show();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void updateUserTag(List<UserTagOutput.DataBean.ListBean> list) {
        UpdateUserTagInput input = new UpdateUserTagInput();
        input.setOpenId(openId);
        List<Integer> ids = new ArrayList<>();
        for (UserTagOutput.DataBean.ListBean bean : list) {
            if (bean.isCheck()) {
                ids.add(bean.getId());
            }
        }
        int[] tagIds = new int[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            tagIds[i] = ids.get(i);
        }
        input.setTagIds(tagIds);

        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).updateUserTag(input), new BaseHttp.HttpObserver<BaseOutput>() {
            @Override
            public void onSuccess(BaseOutput baseOutput) {
                if (baseOutput.isSuccess()) {
                    initData();
                    Toast.makeText(CustomerInfoActivity.this, "用户标签修改成功", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("addedWx", addedWx);
        setResult(RESULT_OK, intent);
        super.finish();
    }
}