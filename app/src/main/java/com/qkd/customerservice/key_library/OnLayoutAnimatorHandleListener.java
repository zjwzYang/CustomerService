package com.qkd.customerservice.key_library;

/**
 * Created on 12/3/20 08:59
 * .
 *
 * @author yj
 * @org 趣看点
 */
public interface OnLayoutAnimatorHandleListener {

    void onLayoutAnimatorHandle(PanelType panelType, PanelType lastPanelType, Float fromValue, Float toValue);
}
