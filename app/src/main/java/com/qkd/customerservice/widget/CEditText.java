package com.qkd.customerservice.widget;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.qkd.customerservice.R;
import com.qkd.customerservice.key_library.util.DensityUtil;

/**
 * Created on 12/3/20 11:50
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CEditText extends AppCompatEditText {
    public CEditText(@NonNull Context context) {
        this(context, null);
    }

    public CEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setImeOptions(EditorInfo.IME_ACTION_SEND);
        resetInputType();
        setGravity(Gravity.CENTER_VERTICAL);
        setPadding(DensityUtil.dp2px(context, 14.0f),
                DensityUtil.dp2px(context, 8.0f),
                DensityUtil.dp2px(context, 14.0f),
                DensityUtil.dp2px(context, 8.0f));
        setTextColor(ContextCompat.getColor(context, R.color.c_000000));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        setHorizontallyScrolling(false);
        setMaxLines(5);
    }

    public void resetInputType() {
        setInputType(InputType.TYPE_CLASS_TEXT);
    }
}
