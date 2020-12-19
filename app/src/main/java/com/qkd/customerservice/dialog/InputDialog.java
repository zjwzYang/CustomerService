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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.qkd.customerservice.R;

/**
 * Created on 12/19/20 11:44
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class InputDialog extends DialogFragment {
    private OnInputSureListener onInputSureListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_input, container, false);
        final EditText editText = view.findViewById(R.id.dialog_input_ed);
        view.findViewById(R.id.dialog_input_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editText.getText().toString();
                try {
                    int age = Integer.parseInt(input);
                    if (age > 100 || age < 0) {
                        Toast.makeText(getContext(), "输入有误", Toast.LENGTH_SHORT).show();
                    } else {
                        if (onInputSureListener != null) {
                            onInputSureListener.onSure(age);
                        }
                        dismiss();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "输入有误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void setOnInputSureListener(OnInputSureListener onInputSureListener) {
        this.onInputSureListener = onInputSureListener;
    }

    public interface OnInputSureListener {
        void onSure(int age);
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