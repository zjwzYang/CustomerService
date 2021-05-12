package com.qkd.customerservice.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.ChangeIntroductionBean;

import org.greenrobot.eventbus.EventBus;

/**
 * Created on 12/19/20 11:44
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class IntroductionDialog extends DialogFragment {

    private String introductionDialog;
    private TextView intro_text;
    private EditText intro_text_et;
    private TextView intro_text_save;
    private int userStatus;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_introduction, container, false);
        intro_text = view.findViewById(R.id.intro_text);
        intro_text_et = view.findViewById(R.id.intro_text_et);
        intro_text_save = view.findViewById(R.id.intro_text_save);

        Bundle bundle = getArguments();
        introductionDialog = bundle.getString("productIntroduction", "");
        userStatus = bundle.getInt("userStatus", 0);
        position = bundle.getInt("position");

        // 编辑状态
        if (userStatus == 3) {
            intro_text_et.setVisibility(View.VISIBLE);
            intro_text_save.setVisibility(View.VISIBLE);
            intro_text.setVisibility(View.GONE);
            intro_text_et.setText(Html.fromHtml(introductionDialog, null, null));
            intro_text_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Editable text = intro_text_et.getText();
                    ChangeIntroductionBean changeIntroductionBean = new ChangeIntroductionBean();
                    changeIntroductionBean.setPosition(position);
                    changeIntroductionBean.setIntroduction(text.toString());
                    EventBus.getDefault().post(changeIntroductionBean);
                    dismiss();
                }
            });
        } else {
            intro_text.setVisibility(View.VISIBLE);
            intro_text_et.setVisibility(View.GONE);
            intro_text_save.setVisibility(View.GONE);
            intro_text.setText(Html.fromHtml(introductionDialog, null, null));
        }
        return view;
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

            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}