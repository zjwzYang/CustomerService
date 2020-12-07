package com.qkd.customerservice.key_library;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 12/3/20 09:05
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class KeyboardHelper {
    private Context context;
    private ViewGroup rootLayout;
    private RecyclerView recyclerView;
    private IInputPanel inputPanel;
    private IPanel expressionPanel;
    private IPanel morePanel;
    private KeyboardStatePopupWindow keyboardStatePopupWindow;
    private boolean scrollBodyLayout = false;


    public static int keyboardHeight = 0;
    public static int inputPanelHeight = 0;
    public static int expressionPanelHeight = 0;
    public static int morePanelHeight = 0;


    public KeyboardHelper(Context context) {
        this.context = context;
    }

    public void reset() {
        if (inputPanel != null) {
            inputPanel.reset();
        }
        if (expressionPanel != null) {
            expressionPanel.reset();
        }
        if (morePanel != null) {
            morePanel.reset();
        }
    }

    public void release() {
        reset();
        inputPanel = null;
        expressionPanel = null;
        morePanel = null;
        if (keyboardStatePopupWindow != null) {
            keyboardStatePopupWindow.dismiss();
            keyboardStatePopupWindow = null;
        }
    }

    public void setKeyboardHeight(int keyboardHeight) {
        KeyboardHelper.keyboardHeight = keyboardHeight;
        if (inputPanelHeight == 0) {
            inputPanelHeight = keyboardHeight;
        }
    }

    public void bindRootLayout(ViewGroup rootLayout) {
        this.rootLayout = rootLayout;
        this.keyboardStatePopupWindow = new KeyboardStatePopupWindow(context, this.rootLayout);
        this.keyboardStatePopupWindow.setOnKeyboardStateListener(new KeyboardStatePopupWindow.OnKeyboardStateListener() {
            @Override
            public void onOpened(int keyboardHeight) {
                KeyboardHelper.keyboardHeight = keyboardHeight;
                if (inputPanel != null) {
                    inputPanel.onSoftKeyboardOpened();
                    inputPanelHeight = inputPanel.getPanelHeight();
                }
                if (onKeyboardStateListener != null) {
                    onKeyboardStateListener.onOpened(keyboardHeight);
                }
                if (expressionPanel != null) {
                    expressionPanelHeight = expressionPanel.getPanelHeight();
                }
                if (morePanel != null) {
                    morePanelHeight = morePanel.getPanelHeight();
                }

            }

            @Override
            public void onClosed() {
                if (inputPanel != null) {
                    inputPanel.onSoftKeyboardClosed();
                }
                if (onKeyboardStateListener != null) {
                    onKeyboardStateListener.onClosed();
                }
            }
        });
    }

    public void bindInputPanel(IInputPanel panel) {
        this.inputPanel = panel;
        KeyboardHelper.inputPanelHeight = panel.getPanelHeight();
        panel.setOnInputStateChangedListener(new OnInputPanelStateChangedListener() {
            @Override
            public void onShowVoicePanel() {
                if (expressionPanel == null || morePanel == null
                        || !(expressionPanel instanceof ViewGroup) || !(morePanel instanceof ViewGroup)) {
                    return;
                }
                ((ViewGroup) expressionPanel).setVisibility(View.GONE);
                ((ViewGroup) morePanel).setVisibility(View.GONE);
            }

            @Override
            public void onShowInputMethodPanel() {
                if (expressionPanel == null || morePanel == null
                        || !(expressionPanel instanceof ViewGroup) || !(morePanel instanceof ViewGroup)) {
                    return;
                }
                ((ViewGroup) expressionPanel).setVisibility(View.GONE);
                ((ViewGroup) morePanel).setVisibility(View.GONE);
            }

            @Override
            public void onShowExpressionPanel() {
                if (expressionPanel == null || !(expressionPanel instanceof ViewGroup)) {
                    return;
                }
                ((ViewGroup) expressionPanel).setVisibility(View.VISIBLE);
            }

            @Override
            public void onShowMorePanel() {
                if (morePanel == null || !(morePanel instanceof ViewGroup)) {
                    return;
                }
                ((ViewGroup) morePanel).setVisibility(View.VISIBLE);
            }
        });
        panel.setOnLayoutAnimatorHandleListener(new OnLayoutAnimatorHandleListener() {
            @Override
            public void onLayoutAnimatorHandle(PanelType panelType, PanelType lastPanelType, Float fromValue, Float toValue) {
                handlePanelMoveAnimator(panelType, lastPanelType, fromValue, toValue);
            }
        });
    }

    public void bindExpressionPanel(IPanel panel) {
        this.expressionPanel = panel;
        expressionPanelHeight = panel.getPanelHeight();
    }

    public void bindMorePanel(IPanel panel) {
        this.morePanel = panel;
        morePanelHeight = panel.getPanelHeight();
    }

    public void setScrollBodyLayout(boolean scrollBodyLayout) {
        this.scrollBodyLayout = scrollBodyLayout;
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void handlePanelMoveAnimator(PanelType panelType, PanelType lastPanelType, Float fromValue, Float toValue) {
        ObjectAnimator recyclerViewTranslationYAnimator = ObjectAnimator.ofFloat(recyclerView, "translationY", fromValue, toValue);
        ObjectAnimator inputPanelTranslationYAnimator = ObjectAnimator.ofFloat(inputPanel, "translationY", fromValue, toValue);
        ObjectAnimator panelTranslationYAnimator = null;
        switch (panelType) {
            case INPUT_MOTHOD:
                if (expressionPanel != null) {
                    expressionPanel.reset();
                }
                if (morePanel != null) {
                    morePanel.reset();
                }
                break;
            case VOICE:
                if (expressionPanel != null) {
                    expressionPanel.reset();
                }
                if (morePanel != null) {
                    morePanel.reset();
                }
                break;
            case EXPRESSION:
                if (morePanel != null) {
                    morePanel.reset();
                }
                panelTranslationYAnimator = ObjectAnimator.ofFloat(expressionPanel, "translationY", fromValue, toValue);
                break;
            case MORE:
                if (expressionPanel != null) {
                    expressionPanel.reset();
                }
                panelTranslationYAnimator = ObjectAnimator.ofFloat(morePanel, "translationY", fromValue, toValue);
                break;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(250);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        if (panelTranslationYAnimator == null) {
            if (scrollBodyLayout) {
                animatorSet.play(inputPanelTranslationYAnimator)
                        .with(recyclerViewTranslationYAnimator);
            } else {
                animatorSet.play(inputPanelTranslationYAnimator);
            }
        } else {
            if (scrollBodyLayout) {
                animatorSet.play(inputPanelTranslationYAnimator)
                        .with(recyclerViewTranslationYAnimator).with(panelTranslationYAnimator);
            } else {
                animatorSet.play(inputPanelTranslationYAnimator).with(panelTranslationYAnimator);
            }
        }
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (recyclerView != null) {
                    recyclerView.requestLayout();
                }
                if (expressionPanel != null && expressionPanel instanceof ViewGroup) {
                    ((ViewGroup) expressionPanel).requestLayout();
                }
                if (morePanel != null && morePanel instanceof ViewGroup) {
                    ((ViewGroup) morePanel).requestLayout();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }


    public void bindRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
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
