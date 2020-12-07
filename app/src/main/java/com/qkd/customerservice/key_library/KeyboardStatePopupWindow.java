package com.qkd.customerservice.key_library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.qkd.customerservice.key_library.util.DensityUtil;

/**
 * Created on 12/3/20 09:07
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class KeyboardStatePopupWindow extends PopupWindow implements ViewTreeObserver.OnGlobalLayoutListener {

    private Context context;
    private View anchorView;

    public KeyboardStatePopupWindow(Context context, final View anchorView) {
        super(context);
        this.context = context;
        this.anchorView = anchorView;

        View contentView = new View(context);
        setContentView(contentView);
        setWidth(0);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setInputMethodMode(INPUT_METHOD_NEEDED);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(this);

        this.anchorView.post(new Runnable() {
            @Override
            public void run() {
                showAtLocation(anchorView, Gravity.NO_GRAVITY, 0, 0);
            }
        });
    }

    private int maxHeight = 0;
    private boolean isSoftKeyboardOpened = false;

    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        getContentView().getWindowVisibleDisplayFrame(rect);
        if (rect.bottom > maxHeight) {
            maxHeight = rect.bottom;
        }
        int screenHeight = DensityUtil.getScreenHeight(context);
        // 键盘的高度
        int keyboardHeight = maxHeight - rect.bottom;
        boolean visible = keyboardHeight > screenHeight / 4;
        if (!isSoftKeyboardOpened && visible) {
            isSoftKeyboardOpened = true;
            if (onKeyboardStateListener != null) {
                onKeyboardStateListener.onOpened(keyboardHeight);
            }
            KeyboardHelper.keyboardHeight = keyboardHeight;
        } else if (isSoftKeyboardOpened && !visible) {
            isSoftKeyboardOpened = false;
            if (onKeyboardStateListener != null) {
                onKeyboardStateListener.onClosed();
            }
        }
    }

    public void release() {
        getContentView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private OnKeyboardStateListener onKeyboardStateListener;

    public void setOnKeyboardStateListener(OnKeyboardStateListener onKeyboardStateListener) {
        this.onKeyboardStateListener = onKeyboardStateListener;
    }

    public interface OnKeyboardStateListener {
        void onOpened(int keyboardHeight);

        void onClosed();
    }
}
