package com.qkd.customerservice.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.qkd.customerservice.R;

/**
 * Created on 12/23/20 14:48
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class OptionDialog extends DialogFragment implements View.OnClickListener {

    private OnClickOptionsListener onClickOptionsListener;
    private String userId;
    private String conversationID;
    private int clickPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_option, container, false);
        Bundle bundle = getArguments();
        userId = bundle.getString("userId");
        conversationID = bundle.getString("conversationID");
        clickPosition = bundle.getInt("clickPosition", -1);
        view.findViewById(R.id.option_one).setOnClickListener(this);
        view.findViewById(R.id.option_two).setOnClickListener(this);
        view.findViewById(R.id.option_three).setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //window.setWindowAnimations(R.style.animate_dialog);
            setCancelable(true);
        }
    }

    @Override
    public void onClick(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.option_one:
                if (onClickOptionsListener != null) {
                    onClickOptionsListener.onClickOptionOne(clickPosition, userId);
                }
                break;
            case R.id.option_two:
                if (onClickOptionsListener != null) {
                    onClickOptionsListener.onClickOptionTwo(userId, conversationID);
                }
                break;
            case R.id.option_three:
                if (onClickOptionsListener != null) {
                    onClickOptionsListener.onClickOptionThree(clickPosition, userId);
                }
                break;
        }
    }

    public void setOnClickOptionsListener(OnClickOptionsListener onClickOptionsListener) {
        this.onClickOptionsListener = onClickOptionsListener;
    }

    public interface OnClickOptionsListener {
        void onClickOptionOne(int clickPosition, String userId);

        void onClickOptionTwo(String userId, String conversationID);

        void onClickOptionThree(int clickPosition, String userId);
    }
}