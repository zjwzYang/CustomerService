package com.qkd.customerservice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.NetUtil;
import com.qkd.customerservice.R;
import com.qkd.customerservice.audio.AudioPlayManager;
import com.qkd.customerservice.audio.AudioRecordManager;
import com.qkd.customerservice.audio.ConversationType;
import com.qkd.customerservice.bean.AddKnowledgeInput;
import com.qkd.customerservice.bean.AddKnowledgeOutput;
import com.qkd.customerservice.bean.ExpressionType;
import com.qkd.customerservice.bean.FileUploadBean;
import com.qkd.customerservice.bean.VoiceMsg;
import com.qkd.customerservice.net.BaseHttp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created on 12/19/20 11:44
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class AddYuYingDialog extends DialogFragment implements View.OnClickListener, View.OnTouchListener {

    private TextView addTitle;
    private TextView contentEd;
    private EditText purposeEd;
    private Button mButton;
    private RelativeLayout mRootV;

    private String type;
    // 录音相关
    private float mLastTouchY;
    private boolean mUpDirection;
    private float mOffsetLimit;
    private boolean videoFlag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_yuyin, container, false);
        EventBus.getDefault().register(this);
        addTitle = view.findViewById(R.id.dialog_add_title);
        contentEd = view.findViewById(R.id.dialog_content_input);
        purposeEd = view.findViewById(R.id.dialog_purpose_input);
        mButton = view.findViewById(R.id.dialog_content_btn);
        mRootV = view.findViewById(R.id.dialog_yuyin_root);
        view.findViewById(R.id.dialog_input_sure).setOnClickListener(this);
        view.findViewById(R.id.dialog_input_cancle).setOnClickListener(this);
        mButton.setOnTouchListener(this);
        mOffsetLimit = 70 * getContext().getResources().getDisplayMetrics().density;

        initView();
        return view;
    }

    private void initView() {
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type)) {
            addTitle.setText("新增个人常用语");
        } else if (ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
            addTitle.setText("新增个人语音");
        }
    }

    @Override
    public void dismiss() {
        EventBus.getDefault().unregister(this);
        super.dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_input_sure:
                String content = contentEd.getText().toString();
                String purpose = purposeEd.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(getContext(), "请输入素材内容", Toast.LENGTH_SHORT).show();
                    contentEd.requestFocus();
                } else if (TextUtils.isEmpty(purpose)) {
                    Toast.makeText(getContext(), "请输入素材用途", Toast.LENGTH_SHORT).show();
                    purposeEd.requestFocus();
                } else {
                    SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
                    int serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);
                    AddKnowledgeInput input = new AddKnowledgeInput();
                    input.setServiceId(serviceId);
                    input.setMediaContent(content);
                    input.setMediaPurpose(purpose);
                    if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type)) {
                        input.setMediaType(1);
                    } else if (ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
                        input.setMediaType(3);
                    }
                    BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).addKnowledge(input),
                            new BaseHttp.HttpObserver<AddKnowledgeOutput>() {
                                @Override
                                public void onSuccess(AddKnowledgeOutput output) {
                                    dismiss();
                                    if (output.isSuccess()) {
                                        Toast.makeText(getContext(), output.getData(), Toast.LENGTH_SHORT).show();
                                        if (onRefreshKnoledge != null) {
                                            onRefreshKnoledge.onRefresh();
                                        }
                                    }
                                }

                                @Override
                                public void onError() {

                                }
                            });
                }
                break;
            case R.id.dialog_input_cancle:
                dismiss();
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (videoFlag) {
            Toast.makeText(getContext(), "文件上传中，请稍等", Toast.LENGTH_SHORT).show();
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (AudioPlayManager.getInstance().isPlaying()) {
                AudioPlayManager.getInstance().stopPlay();
            }
            if (!AudioPlayManager.getInstance().isInNormalMode(getContext())) {
                Toast.makeText(getContext(), "声音通道被占用，请稍后再试",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            AudioRecordManager.getInstance().startRecord(mRootV, ConversationType.PRIVATE, "mTargetId");
            mLastTouchY = event.getY();
            mUpDirection = false;
            ((TextView) v).setText("录制中...");
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (mLastTouchY - event.getY() > mOffsetLimit && !mUpDirection) {
                AudioRecordManager.getInstance().willCancelRecord();
                mUpDirection = true;
                ((TextView) v).setText("按住开始录制");
            } else if (event.getY() - mLastTouchY > -mOffsetLimit && mUpDirection) {
                AudioRecordManager.getInstance().continueRecord();
                mUpDirection = false;
                ((TextView) v).setText("松开开始上传");
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            AudioRecordManager.getInstance().stopRecord();
            ((TextView) v).setText("按住开始录制");
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetVoiceMsg(VoiceMsg voiceMsg) {
        videoFlag = true;
        NetUtil.upLoadVideoFile(new File(voiceMsg.getAudioPath().getPath()), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("12345678", "onFailure" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String str = response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            FileUploadBean uploadBean = gson.fromJson(str, FileUploadBean.class);
                            contentEd.setText(uploadBean.getData());
                            videoFlag = false;
                        }
                    });

                } else {
                    Log.i("12345678", response.message() + " error : body " + response.body().string());
                }
            }
        });
    }

    private AddKnoledgeDialog.OnRefreshKnoledge onRefreshKnoledge;

    public void setOnRefreshKnoledge(AddKnoledgeDialog.OnRefreshKnoledge onRefreshKnoledge) {
        this.onRefreshKnoledge = onRefreshKnoledge;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setWindowAnimations(R.style.animate_dialog);
            setCancelable(true);
        }
    }
}