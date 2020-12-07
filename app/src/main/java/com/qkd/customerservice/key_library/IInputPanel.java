package com.qkd.customerservice.key_library;

/**
 * Created on 12/3/20 08:54
 * .
 *
 * @author yj
 * @org 趣看点
 */
public interface IInputPanel extends IPanel {
    /**
     * 软键盘打开
     */
    void onSoftKeyboardOpened();

    /**
     * 软件盘关闭
     */
    void onSoftKeyboardClosed();

    /**
     * 设置布局动画处理监听器
     */
    void setOnLayoutAnimatorHandleListener(OnLayoutAnimatorHandleListener listener);

    /**
     * 设置输入面板（包括软键盘、表情、更多等）状态改变监听器
     */
    void setOnInputStateChangedListener(OnInputPanelStateChangedListener listener);

}
