package com.qkd.customerservice.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.qkd.customerservice.R;
import com.qkd.customerservice.audio.AudioPlayManager;
import com.qkd.customerservice.audio.AudioRecordManager;
import com.qkd.customerservice.audio.ConversationType;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.TextMsg;
import com.qkd.customerservice.key_library.IInputPanel;
import com.qkd.customerservice.key_library.KeyboardHelper;
import com.qkd.customerservice.key_library.OnInputPanelStateChangedListener;
import com.qkd.customerservice.key_library.OnLayoutAnimatorHandleListener;
import com.qkd.customerservice.key_library.PanelType;
import com.qkd.customerservice.key_library.util.DensityUtil;
import com.qkd.customerservice.key_library.util.UIUtil;

import org.greenrobot.eventbus.EventBus;

import static com.qkd.customerservice.key_library.PanelType.INPUT_MOTHOD;
import static com.qkd.customerservice.key_library.PanelType.NONE;

/**
 * Created on 12/3/20 11:05
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CInputPanel extends LinearLayout implements IInputPanel {

    private PanelType panelType = NONE;
    private PanelType lastPanelType = panelType;
    private boolean isKeyboardOpened = false;

    private CEditText mCEditText;
    private CImageButton mCImageButton;
    private CImageButton voiceBtn;
    private CTextButton voicePressBtn;
    private CImageButton moreBtn;
    private CImageButton mVoiceKey;

    private boolean isActive = false;

    private OnInputPanelStateChangedListener onInputPanelStateChangedListener;
    private OnLayoutAnimatorHandleListener onLayoutAnimatorHandleListener;

    // 录音相关
    private float mLastTouchY;
    private boolean mUpDirection;
    private float mOffsetLimit;

    public CInputPanel(Context context) {
        this(context, null);
    }

    public CInputPanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CInputPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_input_panel, this, true);
        init(context);
        setListeners(context);
    }

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//
//        }
//    };

    private void init(final Context context) {
        mOffsetLimit = 70 * context.getResources().getDisplayMetrics().density;
        setOrientation(HORIZONTAL);
        setPadding(DensityUtil.dp2px(context, 10.0f),
                DensityUtil.dp2px(context, 6.0f),
                DensityUtil.dp2px(context, 10.0f),
                DensityUtil.dp2px(context, 6.0f));
        setGravity(Gravity.BOTTOM);
        setBackgroundColor(ContextCompat.getColor(context, R.color.c_cbcbcb));
        mCEditText = findViewById(R.id.et_content);
        mCImageButton = findViewById(R.id.btn_expression);
        voiceBtn = findViewById(R.id.btn_voice);
        voicePressBtn = findViewById(R.id.btn_voice_pressed);
        moreBtn = findViewById(R.id.btn_more);
        mVoiceKey = findViewById(R.id.btn_voice);

        mCEditText.setInputType(InputType.TYPE_NULL);
        mCEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (!isKeyboardOpened) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                UIUtil.requestFocus(mCEditText);
                                UIUtil.showSoftInput(context, mCEditText);
                            }
                        }, 100);
                        mCEditText.resetInputType();
                        mCImageButton.setNormalImageResId(R.drawable.ic_chat_expression_normal);
                        mCImageButton.setPressedImageResId(R.drawable.ic_chat_expression_pressed);
                        handleAnimator(INPUT_MOTHOD);
                        if (onInputPanelStateChangedListener != null) {
                            onInputPanelStateChangedListener.onShowInputMethodPanel();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        // 录音按钮监听
        voicePressBtn.setOnTouchListener(new OnTouchListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (AudioPlayManager.getInstance().isPlaying()) {
                        AudioPlayManager.getInstance().stopPlay();
                    }
                    if (!AudioPlayManager.getInstance().isInNormalMode(getContext())) {
                        Toast.makeText(getContext(), "声音通道被占用，请稍后再试",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    AudioRecordManager.getInstance().startRecord(v.getRootView(), ConversationType.PRIVATE, "mTargetId");
                    mLastTouchY = event.getY();
                    mUpDirection = false;
                    ((TextView) v).setText("松开 发送");
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (mLastTouchY - event.getY() > mOffsetLimit && !mUpDirection) {
                        AudioRecordManager.getInstance().willCancelRecord();
                        mUpDirection = true;
                        ((TextView) v).setText("按住 说话");
                    } else if (event.getY() - mLastTouchY > -mOffsetLimit && mUpDirection) {
                        AudioRecordManager.getInstance().continueRecord();
                        mUpDirection = false;
                        ((TextView) v).setText("松开 发送");
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    AudioRecordManager.getInstance().stopRecord();
                    ((TextView) v).setText("按住 说话");
                }

                return false;
            }
        });
    }

    private void setListeners(final Context context) {
        voiceBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCImageButton.setNormalImageResId(R.drawable.ic_chat_expression_normal);
                mCImageButton.setPressedImageResId(R.drawable.ic_chat_expression_pressed);
                if (lastPanelType == PanelType.VOICE) {
                    voicePressBtn.setVisibility(GONE);
                    mVoiceKey.setNormalImageResId(R.drawable.ic_chat_voice_normal);
                    mVoiceKey.setPressedImageResId(R.drawable.ic_chat_voice_pressed);
                    mCEditText.setVisibility(VISIBLE);
                    UIUtil.requestFocus(mCEditText);
                    UIUtil.showSoftInput(context, mCEditText);
                    handleAnimator(PanelType.INPUT_MOTHOD);
                    mCEditText.resetInputType();
                } else {
                    voicePressBtn.setVisibility(VISIBLE);
                    mVoiceKey.setNormalImageResId(R.drawable.ic_chat_keyboard_normal);
                    mVoiceKey.setPressedImageResId(R.drawable.ic_chat_keyboard_pressed);
                    mCEditText.setVisibility(GONE);
                    UIUtil.loseFocus(mCEditText);
                    UIUtil.hideSoftInput(context, mCEditText);
                    handleAnimator(PanelType.VOICE);
                    if (onInputPanelStateChangedListener != null) {
                        onInputPanelStateChangedListener.onShowVoicePanel();
                    }
                }
            }
        });
        mCImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                voicePressBtn.setVisibility(GONE);
                mVoiceKey.setNormalImageResId(R.drawable.ic_chat_voice_normal);
                mVoiceKey.setPressedImageResId(R.drawable.ic_chat_voice_pressed);
                mCEditText.setVisibility(VISIBLE);
                if (lastPanelType == PanelType.EXPRESSION) {
                    mCImageButton.setNormalImageResId(R.drawable.ic_chat_expression_normal);
                    mCImageButton.setPressedImageResId(R.drawable.ic_chat_expression_pressed);
                    UIUtil.requestFocus(mCEditText);
                    UIUtil.showSoftInput(context, mCEditText);
                    handleAnimator(PanelType.INPUT_MOTHOD);
                    mCEditText.resetInputType();
                } else {
                    mCImageButton.setNormalImageResId(R.drawable.ic_chat_keyboard_normal);
                    mCImageButton.setPressedImageResId(R.drawable.ic_chat_keyboard_pressed);
                    UIUtil.loseFocus(mCEditText);
                    UIUtil.hideSoftInput(context, mCEditText);
                    handleAnimator(PanelType.EXPRESSION);
                    if (onInputPanelStateChangedListener != null) {
                        onInputPanelStateChangedListener.onShowExpressionPanel();
                    }
                }
            }
        });
        moreBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCImageButton.setNormalImageResId(R.drawable.ic_chat_expression_normal);
                mCImageButton.setPressedImageResId(R.drawable.ic_chat_expression_pressed);
                mVoiceKey.setNormalImageResId(R.drawable.ic_chat_voice_normal);
                mVoiceKey.setPressedImageResId(R.drawable.ic_chat_voice_pressed);
                voicePressBtn.setVisibility(GONE);
                mCEditText.setVisibility(VISIBLE);
                if (lastPanelType == PanelType.MORE) {
                    UIUtil.requestFocus(mCEditText);
                    UIUtil.showSoftInput(context, mCEditText);
                    handleAnimator(PanelType.INPUT_MOTHOD);
                    mCEditText.resetInputType();
                } else {
                    UIUtil.loseFocus(mCEditText);
                    UIUtil.hideSoftInput(context, mCEditText);
                    handleAnimator(PanelType.MORE);
                    if (onInputPanelStateChangedListener != null) {
                        onInputPanelStateChangedListener.onShowMorePanel();
                    }
                }
            }
        });
        mCEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    TextMsg msgBean = new TextMsg();
                    msgBean.setMsgType(MsgBean.MsgType.TEXT);
                    msgBean.setType(1);
                    msgBean.setContent(textView.getText().toString());
                    msgBean.setNickName("我");
                    EventBus.getDefault().post(msgBean);
                    textView.setText("");
                    Log.i("12345678", "onEditorAction: 发送文本：" + textView.getText());
                    return true;
                }
                return false;
            }
        });
    }

    private void handleAnimator(PanelType panelType) {
        if (lastPanelType == panelType) {
            return;
        }
        isActive = true;
        this.panelType = panelType;
        float fromValue = 0.0f;
        float toValue = 0.0f;
        switch (panelType) {
            case VOICE:
                switch (lastPanelType) {
                    case INPUT_MOTHOD:
                        fromValue = -KeyboardHelper.inputPanelHeight;
                        toValue = 0.0f;
                        break;
                    case EXPRESSION:
                        fromValue = -KeyboardHelper.expressionPanelHeight;
                        toValue = 0.0f;
                        break;
                    case MORE:
                        fromValue = -KeyboardHelper.morePanelHeight;
                        toValue = 0.0f;
                        break;
                    case NONE:
                        fromValue = 0.0f;
                        toValue = 0.0f;
                        break;
                }
                break;
            case INPUT_MOTHOD:
                switch (lastPanelType) {
                    case VOICE:
                        fromValue = 0.0f;
                        toValue = -KeyboardHelper.inputPanelHeight;
                        break;
                    case EXPRESSION:
                        fromValue = -KeyboardHelper.expressionPanelHeight;
                        toValue = -KeyboardHelper.inputPanelHeight;
                        break;
                    case MORE:
                        fromValue = -KeyboardHelper.morePanelHeight;
                        toValue = -KeyboardHelper.inputPanelHeight;
                        break;
                    case NONE:
                        fromValue = 0.0f;
                        toValue = -KeyboardHelper.inputPanelHeight;
                        break;
                }
                break;
            case EXPRESSION:
                switch (lastPanelType) {
                    case INPUT_MOTHOD:
                        fromValue = -KeyboardHelper.inputPanelHeight;
                        toValue = -KeyboardHelper.expressionPanelHeight;
                        break;
                    case VOICE:
                        fromValue = 0.0f;
                        toValue = -KeyboardHelper.expressionPanelHeight;
                        break;
                    case MORE:
                        fromValue = -KeyboardHelper.morePanelHeight;
                        toValue = -KeyboardHelper.expressionPanelHeight;
                        break;
                    case NONE:
                        fromValue = 0.0f;
                        toValue = -KeyboardHelper.expressionPanelHeight;
                        break;
                }
                break;
            case MORE:
                switch (lastPanelType) {
                    case INPUT_MOTHOD:
                        fromValue = -KeyboardHelper.inputPanelHeight;
                        toValue = -KeyboardHelper.morePanelHeight;
                        break;
                    case VOICE:
                        fromValue = 0.0f;
                        toValue = -KeyboardHelper.morePanelHeight;
                        break;
                    case EXPRESSION:
                        fromValue = -KeyboardHelper.expressionPanelHeight;
                        toValue = -KeyboardHelper.morePanelHeight;
                        break;
                    case NONE:
                        fromValue = 0.0f;
                        toValue = -KeyboardHelper.morePanelHeight;
                        break;
                }
                break;
            case NONE:
                switch (lastPanelType) {
                    case VOICE:
                        break;
                    case INPUT_MOTHOD:
                        fromValue = -KeyboardHelper.inputPanelHeight;
                        toValue = 0.0f;
                        break;
                    case EXPRESSION:
                        fromValue = -KeyboardHelper.expressionPanelHeight;
                        toValue = 0.0f;
                        break;
                    case MORE:
                        fromValue = -KeyboardHelper.morePanelHeight;
                        toValue = 0.0f;
                        break;
                }
                break;
        }
        if (onLayoutAnimatorHandleListener != null) {
            onLayoutAnimatorHandleListener.onLayoutAnimatorHandle(panelType, lastPanelType, fromValue, toValue);
        }
        lastPanelType = panelType;
    }

    @Override
    public void onSoftKeyboardOpened() {
        isKeyboardOpened = true;
        mCEditText.resetInputType();
    }

    @Override
    public void onSoftKeyboardClosed() {
        isKeyboardOpened = false;
        mCEditText.setInputType(InputType.TYPE_NULL);
        if (lastPanelType == PanelType.INPUT_MOTHOD) {
            UIUtil.loseFocus(mCEditText);
            UIUtil.hideSoftInput(getContext(), mCEditText);
            handleAnimator(PanelType.NONE);
        }
    }

    @Override
    public void setOnLayoutAnimatorHandleListener(OnLayoutAnimatorHandleListener listener) {
        this.onLayoutAnimatorHandleListener = listener;
    }

    @Override
    public void setOnInputStateChangedListener(OnInputPanelStateChangedListener listener) {
        this.onInputPanelStateChangedListener = listener;
    }

    @Override
    public void reset() {
        if (!isActive) {
            return;
        }
        UIUtil.loseFocus(mCEditText);
        UIUtil.hideSoftInput(getContext(), mCEditText);
        mCImageButton.setNormalImageResId(R.drawable.ic_chat_expression_normal);
        mCImageButton.setPressedImageResId(R.drawable.ic_chat_expression_pressed);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handleAnimator(PanelType.NONE);
            }
        }, 100);
        isActive = true;
    }

    @Override
    public int getPanelHeight() {
        return KeyboardHelper.keyboardHeight;
    }

    public void onActivityDestory() {
        AudioRecordManager.getInstance().destroyRecord();
    }
}
