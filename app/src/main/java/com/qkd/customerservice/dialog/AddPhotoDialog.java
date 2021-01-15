package com.qkd.customerservice.dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.AddKnowledgeInput;
import com.qkd.customerservice.bean.AddKnowledgeOutput;
import com.qkd.customerservice.net.BaseHttp;
import com.qkd.customerservice.widget.LocalGlideEngine;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

import static com.qkd.customerservice.activity.ChatActivity.IMAGE_REQUEST;

/**
 * Created on 12/19/20 11:44
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class AddPhotoDialog extends DialogFragment implements View.OnClickListener {

    private EditText purposeEd;
    private ImageView mImageView;

    private String imageUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_photo, container, false);
        purposeEd = view.findViewById(R.id.dialog_purpose_input);
        mImageView = view.findViewById(R.id.photo_add_img);
        mImageView.setOnClickListener(this);
        view.findViewById(R.id.dialog_input_sure).setOnClickListener(this);

        initView();
        return view;
    }

    private void initView() {

    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        Glide.with(getContext())
                .load(imageUrl)
                .into(mImageView);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo_add_img:
                String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                AndPermission.with(getContext())
                        .runtime()
                        .permission(permissions)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                Matisse.from((Activity) getContext())
                                        .choose(MimeType.ofImage())
                                        .showSingleMediaType(true)
                                        .countable(true)
                                        .maxSelectable(1)
                                        .thumbnailScale(0.8f)
                                        .theme(R.style.Matisse_Dracula)
                                        .imageEngine(new LocalGlideEngine())
                                        .forResult(IMAGE_REQUEST);
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                            }
                        })
                        .start();
                break;
            case R.id.dialog_input_sure:
                String purpose = purposeEd.getText().toString();
                if (TextUtils.isEmpty(imageUrl)) {
                    Toast.makeText(getContext(), "请上次照片", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(purpose)) {
                    Toast.makeText(getContext(), "请输入素材用途", Toast.LENGTH_SHORT).show();
                    purposeEd.requestFocus();
                } else {
                    SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
                    int serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);
                    AddKnowledgeInput input = new AddKnowledgeInput();
                    input.setServiceId(serviceId);
                    input.setMediaPurpose(purpose);
                    input.setMediaContent(imageUrl);
                    input.setMediaType(2);
                    BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).addKnowledge(input),
                            new BaseHttp.HttpObserver<AddKnowledgeOutput>() {
                                @Override
                                public void onSuccess(AddKnowledgeOutput output) {
                                    dismiss();
                                    if (output.isSuccess()) {
                                        Toast.makeText(getContext(), output.getData(), Toast.LENGTH_SHORT).show();
                                        if (onRefreshKnoledge != null) {
                                            onRefreshKnoledge.onRefresh();
                                        }
                                    }
                                }

                                @Override
                                public void onError() {

                                }
                            });
                }
                break;
        }
    }

    private AddKnoledgeDialog.OnRefreshKnoledge onRefreshKnoledge;

    public void setOnRefreshKnoledge(AddKnoledgeDialog.OnRefreshKnoledge onRefreshKnoledge) {
        this.onRefreshKnoledge = onRefreshKnoledge;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setWindowAnimations(R.style.animate_dialog);
            setCancelable(true);
        }
    }
}