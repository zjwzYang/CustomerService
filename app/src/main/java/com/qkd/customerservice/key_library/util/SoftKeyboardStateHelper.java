package com.qkd.customerservice.key_library.util;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created on 12/3/20 16:31
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class SoftKeyboardStateHelper implements ViewTreeObserver.OnGlobalLayoutListener {

    private View activityRootView;
    private int lastSoftKeyboardHeightInPx;
    private boolean isSoftKeyboardOpened = false;

    private int maxHeight = 0;

    private SoftKeyboardStateListener listener;

    public SoftKeyboardStateHelper(View activityRootView) {
        this(activityRootView, false);
    }

    public SoftKeyboardStateHelper(View activityRootView, boolean isSoftKeyboardOpened) {
        this.activityRootView = activityRootView;
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
        this.activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        Rect rect = new Rect();
        activityRootView.getWindowVisibleDisplayFrame(rect);
        if (rect.bottom > maxHeight) {
            maxHeight = rect.bottom;
        }
        int screenHeight = DensityUtil.getScreenHeight(activityRootView.getContext());
        int heightDifference = maxHeight - rect.bottom;
        boolean visible = heightDifference > screenHeight / 4;
        if (!isSoftKeyboardOpened && visible) {
            isSoftKeyboardOpened = true;
            notifyOnSoftKeyboardOpened(heightDifference);
        } else if (isSoftKeyboardOpened && !visible) {
            isSoftKeyboardOpened = false;
            notifyOnSoftKeyboardClosed();
        }
    }

    private void notifyOnSoftKeyboardOpened(int keyboardHeightInPx) {
        lastSoftKeyboardHeightInPx = keyboardHeightInPx;
        if (listener != null) {
            listener.onSoftKeyboardOpened(keyboardHeightInPx);
        }
    }

    private void notifyOnSoftKeyboardClosed() {
        if (listener != null) {
            listener.onSoftKeyboardClosed();
        }
    }

    public void setIsSoftKeyboardOpened(boolean isSoftKeyboardOpened){
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
    }

    public boolean isSoftKeyboardOpened() {
        return isSoftKeyboardOpened;
    }

    public int getLastSoftKeyboardHeightInPx() {
        return lastSoftKeyboardHeightInPx;
    }

    public void setListener(SoftKeyboardStateListener listener) {
        this.listener = listener;
    }

    public interface SoftKeyboardStateListener {
        void onSoftKeyboardOpened(int keyboardHeight);

        void onSoftKeyboardClosed();
    }
}
