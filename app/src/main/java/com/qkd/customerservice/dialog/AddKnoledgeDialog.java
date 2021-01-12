package com.qkd.customerservice.dialog;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.AddKnowledgeInput;
import com.qkd.customerservice.bean.AddKnowledgeOutput;
import com.qkd.customerservice.bean.ExpressionType;
import com.qkd.customerservice.net.BaseHttp;

/**
 * Created on 12/19/20 11:44
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class AddKnoledgeDialog extends DialogFragment implements View.OnClickListener {

    private TextView addTitle;
    private EditText contentEd;
    private EditText purposeEd;

    private String type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_knoledge, container, false);
        addTitle = view.findViewById(R.id.dialog_add_title);
        contentEd = view.findViewById(R.id.dialog_content_input);
        purposeEd = view.findViewById(R.id.dialog_purpose_input);
        view.findViewById(R.id.dialog_input_sure).setOnClickListener(this);

        initView();
        return view;
    }

    private void initView() {
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type)) {
            addTitle.setText("新增个人常用语");
        } else if (ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
            addTitle.setText("新增个人语音");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_input_sure:
                String content = contentEd.getText().toString();
                String purpose = purposeEd.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(getContext(), "请输入素材内容", Toast.LENGTH_SHORT).show();
                    contentEd.requestFocus();
                } else if (TextUtils.isEmpty(purpose)) {
                    Toast.makeText(getContext(), "请输入素材用途", Toast.LENGTH_SHORT).show();
                    purposeEd.requestFocus();
                } else {
                    SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
                    int serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);
                    AddKnowledgeInput input = new AddKnowledgeInput();
                    input.setServiceId(serviceId);
                    input.setMediaContent(content);
                    input.setMediaPurpose(purpose);
                    if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type)) {
                        input.setMediaType(1);
                    } else if (ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
                        input.setMediaType(3);
                    }
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

    private OnRefreshKnoledge onRefreshKnoledge;

    public void setOnRefreshKnoledge(OnRefreshKnoledge onRefreshKnoledge) {
        this.onRefreshKnoledge = onRefreshKnoledge;
    }

    public interface OnRefreshKnoledge {
        void onRefresh();
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