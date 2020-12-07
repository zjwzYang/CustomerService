package com.qkd.customerservice.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.qkd.customerservice.R;

/**
 * Created on 12/3/20 11:25
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CTextButton extends androidx.appcompat.widget.AppCompatTextView {
    private int normalTextColor = 0;
    private int pressedTextColor = 0;
    private int disabledTextColor = 0;
    private OnVoiceButtonCallBack mOnVoiceButtonCallBack;

    public CTextButton(@NonNull Context context) {
        this(context, null);
    }

    public CTextButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CTextButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CTextButton, defStyleAttr, 0);
        normalTextColor = array.getColor(
                R.styleable.CTextButton_ctb_normal_text_color,
                ContextCompat.getColor(context, R.color.c_333333)
        );
        pressedTextColor = array.getColor(
                R.styleable.CTextButton_ctb_pressed_text_color,
                ContextCompat.getColor(context, R.color.c_666666)
        );
        disabledTextColor = array.getColor(
                R.styleable.CTextButton_ctb_disabled_text_color,
                ContextCompat.getColor(context, R.color.c_999999)
        );
        array.recycle();
        init();
    }

    private void init() {
        setClickable(true);
        setTextColor(normalTextColor);
//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        setTextColor(pressedTextColor);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        setTextColor(normalTextColor);
//                        break;
//                }
//                return false;
//            }
//        });
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.v("onTouchEvent", "开始取消录音");
//                if (mOnVoiceButtonCallBack != null) {
//                    mOnVoiceButtonCallBack.onStartRecord();
//                }
//                setTextColor(pressedTextColor);
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                if (isCancelled(this, event)) {
//                    Log.v("onTouchEvent", "准备取消录音");
//                    if (mOnVoiceButtonCallBack != null) {
//                        mOnVoiceButtonCallBack.onWillCancelRecord();
//                    }
//                } else {
//                    Log.v("onTouchEvent", "继续录音");
//                    if (mOnVoiceButtonCallBack != null) {
//                        mOnVoiceButtonCallBack.onContinueRecord();
//                    }
//                }
//                return true;
//            case MotionEvent.ACTION_UP:
//                Log.v("onTouchEvent", "停止录音");
//                if (mOnVoiceButtonCallBack != null) {
//                    mOnVoiceButtonCallBack.onStopRecord();
//                }
//                setTextColor(normalTextColor);
//                return true;
//            case MotionEvent.ACTION_CANCEL:
//                Log.v("onTouchEvent", "停止录音");
//                if (mOnVoiceButtonCallBack != null) {
//                    mOnVoiceButtonCallBack.onStopRecord();
//                }
//                setTextColor(normalTextColor);
//                return true;
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            setTextColor(normalTextColor);
        } else {
            setTextColor(disabledTextColor);
        }
        super.setEnabled(enabled);
    }

    private boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth() || event.getRawY() < location[1] - 40) {
            return true;
        }
        return false;
    }

    public void setOnVoiceButtonCallBack(OnVoiceButtonCallBack onVoiceButtonCallBack) {
        this.mOnVoiceButtonCallBack = onVoiceButtonCallBack;
    }

    public interface OnVoiceButtonCallBack {
        /**
         * 开始录音
         */
        void onStartRecord();

        /**
         * 停止录音
         */
        void onStopRecord();

        /**
         * 准备取消录音
         */
        void onWillCancelRecord();

        /**
         * 继续录音
         */
        void onContinueRecord();
    }
}
