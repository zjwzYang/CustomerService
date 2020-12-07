package com.qkd.customerservice.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import com.qkd.customerservice.R;

/**
 * Created on 12/3/20 11:09
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CImageButton extends AppCompatImageButton {
    private int normalImageResId = 0;
    private int pressedImageResId = 0;
    private int disabledImageResId = 0;

    public CImageButton(@NonNull Context context) {
        this(context, null);
    }

    public CImageButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CImageButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CImageButton, defStyleAttr, 0);
        normalImageResId = array.getResourceId(R.styleable.CImageButton_cib_normal_image_res_id, 0);
        pressedImageResId = array.getResourceId(R.styleable.CImageButton_cib_pressed_image_res_id, 0);
        disabledImageResId = array.getResourceId(R.styleable.CImageButton_cib_disabled_image_res_id, 0);
        array.recycle();
        init();
    }

    private void init() {
        setClickable(true);
        setImageResource(normalImageResId);
        setOnTouchListener(mOnTouchListener);
        setScaleType(ScaleType.CENTER_INSIDE);
    }

    public void setNormalImageResId(int normalImageResId) {
        this.normalImageResId = normalImageResId;
        setImageResource(normalImageResId);
    }

    public void setPressedImageResId(int pressedImageResId) {
        this.pressedImageResId = pressedImageResId;
        setImageResource(pressedImageResId);
    }

    public void setDisabledImageResId(int disabledImageResId) {
        this.disabledImageResId = disabledImageResId;
        setImageResource(disabledImageResId);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            setImageResource(normalImageResId);
        } else {
            setImageResource(disabledImageResId);
        }
        super.setEnabled(enabled);
    }

    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (!isEnabled()) {
                return false;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setImageResource(pressedImageResId);
                    break;
                case MotionEvent.ACTION_UP:
                    setImageResource(normalImageResId);
            }
            return false;
        }
    };
}
