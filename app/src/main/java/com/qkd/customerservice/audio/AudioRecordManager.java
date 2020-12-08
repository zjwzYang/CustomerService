package com.qkd.customerservice.audio;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qkd.customerservice.MyApp;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.VoiceMsg;

import org.greenrobot.eventbus.EventBus;

import java.io.File;


public class AudioRecordManager implements Handler.Callback {

    private final static String TAG = "12345678";

    private int RECORD_INTERVAL = 60;

    private IAudioState mCurAudioState;
    private View mRootView;
    private Context mContext;
    private ConversationType mConversationType;
    private String mTargetId;

    private Handler mHandler;

    private AudioManager mAudioManager;
    private MediaRecorder mMediaRecorder;
    private Uri mAudioPath;
    private long smStartRecTime;
    private AudioManager.OnAudioFocusChangeListener mAfChangeListener;

    private PopupWindow mRecordWindow;
    private ImageView mStateIV;
    private TextView mStateTV;
    private TextView mTimerTV;

    private static final int RC_SAMPLE_RATE_8000 = 8000;
    private static final int RC_SAMPLE_RATE_16000 = 16000;
    private static final String VOICE_PATH = "/voice/";


    static class SingletonHolder {
        static AudioRecordManager sInstance = new AudioRecordManager();
    }

    public static AudioRecordManager getInstance() {
        return SingletonHolder.sInstance;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private AudioRecordManager() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {
                TelephonyManager manager = (TelephonyManager) MyApp.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
                manager.listen(new PhoneStateListener() {
                    @Override
                    public void onCallStateChanged(int state, String incomingNumber) {
                        switch (state) {
                            case TelephonyManager.CALL_STATE_IDLE:
                                break;
                            case TelephonyManager.CALL_STATE_RINGING:
                                sendEmptyMessage(IAudioRecordEvent.AUDIO_RECORD_EVENT_ABORT);
                                break;
                            case TelephonyManager.CALL_STATE_OFFHOOK:
                                break;
                        }
                        super.onCallStateChanged(state, incomingNumber);
                    }
                }, PhoneStateListener.LISTEN_CALL_STATE);
            } catch (SecurityException e) {
            }
        }
        mCurAudioState = idleState;
        idleState.enter();
    }

    IAudioState idleState = new IdleState();

    class IdleState extends IAudioState {
        public IdleState() {
        }

        @Override
        void enter() {
            super.enter();
            if (mHandler != null) {
                mHandler.removeMessages(IAudioRecordEvent.AUDIO_RECORD_EVENT_TIME_OUT);
                mHandler.removeMessages(IAudioRecordEvent.AUDIO_RECORD_EVENT_TICKER);
                mHandler.removeMessages(IAudioRecordEvent.AUDIO_RECORD_EVENT_SAMPLING);
            }
        }

        @Override
        void handleMessage(AudioStateMessage msg) {
            switch (msg.what) {
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_TRIGGER:
                    initView(mRootView);
                    setRecordingView();
                    startRec();
                    smStartRecTime = SystemClock.elapsedRealtime();
                    mCurAudioState = recordState;
                    sendEmptyMessage(IAudioRecordEvent.AUDIO_RECORD_EVENT_SAMPLING);
                    break;
            }
        }
    }

    IAudioState recordState = new RecordState();

    class RecordState extends IAudioState {
        @Override
        void handleMessage(AudioStateMessage msg) {
            switch (msg.what) {
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_SAMPLING:
                    audioDBChanged();
                    mHandler.sendEmptyMessageDelayed(IAudioRecordEvent.AUDIO_RECORD_EVENT_SAMPLING, 150);
                    break;
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_WILL_CANCEL:
                    setCancelView();
                    mCurAudioState = cancelState;
                    break;
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_RELEASE:
                    final boolean checked = checkAudioTimeLength();
                    boolean activityFinished = false;
                    if (msg.obj != null) {
                        activityFinished = (boolean) msg.obj;
                    }
                    if (checked && !activityFinished) {
                        mStateIV.setImageResource(R.drawable.rc_ic_volume_wraning);
                        mStateTV.setText("录音时间太短");
                        mHandler.removeMessages(IAudioRecordEvent.AUDIO_RECORD_EVENT_SAMPLING);
                    }
                    if (!activityFinished && mHandler != null) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AudioStateMessage message = AudioStateMessage.obtain();
                                message.what = IAudioRecordEvent.AUDIO_RECORD_EVENT_SEND_FILE;
                                message.obj = !checked;
                                sendMessage(message);
                            }
                        }, 500);
                        mCurAudioState = sendingState;
                    } else {
                        stopRec();
                        if (!checked && activityFinished) {
                            sendAudioFile();
                        }
                        destroyView();
                        mCurAudioState = idleState;
                    }
                    break;
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_TIME_OUT:
                    int counter = (int) msg.obj;
                    setTimeoutView(counter);
                    mCurAudioState = timerState;

                    if (counter >= 0) {
                        android.os.Message message = android.os.Message.obtain();
                        message.what = IAudioRecordEvent.AUDIO_RECORD_EVENT_TICKER;
                        message.obj = counter - 1;
                        mHandler.sendMessageDelayed(message, 1000);
                    } else {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stopRec();
                                sendAudioFile();
                                destroyView();
                            }
                        }, 500);
                        mCurAudioState = idleState;

                    }
                    break;
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_ABORT:
                    stopRec();
                    destroyView();
                    deleteAudioFile();
                    mCurAudioState = idleState;
                    idleState.enter();
                    break;
            }
        }
    }

    IAudioState sendingState = new SendingState();

    class SendingState extends IAudioState {
        @Override
        void handleMessage(AudioStateMessage message) {
            Log.d(TAG, "SendingState handleMessage " + message.what);
            switch (message.what) {
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_SEND_FILE:
                    stopRec();
                    if ((boolean) message.obj) sendAudioFile();
                    destroyView();
                    mCurAudioState = idleState;
                    break;
            }
        }
    }

    IAudioState cancelState = new CancelState();

    class CancelState extends IAudioState {
        @Override
        void handleMessage(AudioStateMessage msg) {
            switch (msg.what) {
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_TRIGGER:
                    break;
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_CONTINUE:
                    setRecordingView();
                    mCurAudioState = recordState;
                    sendEmptyMessage(IAudioRecordEvent.AUDIO_RECORD_EVENT_SAMPLING);
                    break;
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_ABORT:
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_RELEASE:
                    stopRec();
                    destroyView();
                    deleteAudioFile();
                    mCurAudioState = idleState;
                    idleState.enter();
                    break;
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_TIME_OUT:
                    final int counter = (int) msg.obj;
                    if (counter > 0) {
                        android.os.Message message = android.os.Message.obtain();
                        message.what = IAudioRecordEvent.AUDIO_RECORD_EVENT_TICKER;
                        message.obj = counter - 1;
                        mHandler.sendMessageDelayed(message, 1000);
                    } else {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stopRec();
                                sendAudioFile();
                                destroyView();
                            }
                        }, 500);
                        mCurAudioState = idleState;
                        idleState.enter();
                    }
                    break;
            }
        }
    }

    IAudioState timerState = new TimerState();

    class TimerState extends IAudioState {
        @Override
        void handleMessage(AudioStateMessage msg) {
            switch (msg.what) {
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_TIME_OUT:
                    final int counter = (int) msg.obj;
                    if (counter >= 0) {
                        android.os.Message message = android.os.Message.obtain();
                        message.what = IAudioRecordEvent.AUDIO_RECORD_EVENT_TICKER;
                        message.obj = counter - 1;
                        mHandler.sendMessageDelayed(message, 1000);
                        setTimeoutView(counter);
                    } else {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stopRec();
                                sendAudioFile();
                                destroyView();
                            }
                        }, 500);
                        mCurAudioState = idleState;
                    }
                    break;
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_RELEASE:
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopRec();
                            sendAudioFile();
                            destroyView();
                        }
                    }, 500);
                    mCurAudioState = idleState;
                    idleState.enter();
                    break;
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_ABORT:
                    stopRec();
                    destroyView();
                    deleteAudioFile();
                    mCurAudioState = idleState;
                    idleState.enter();
                    break;
                case IAudioRecordEvent.AUDIO_RECORD_EVENT_WILL_CANCEL:
                    setCancelView();
                    mCurAudioState = cancelState;
                    break;
            }
        }
    }

    @Override
    final public boolean handleMessage(android.os.Message msg) {
        switch (msg.what) {
            case IAudioRecordEvent.AUDIO_RECORD_EVENT_TIME_OUT:
                AudioStateMessage m = AudioStateMessage.obtain();
                m.what = msg.what;
                m.obj = msg.obj;
                sendMessage(m);
                break;
            case IAudioRecordEvent.AUDIO_RECORD_EVENT_TICKER:
                m = AudioStateMessage.obtain();
                m.what = IAudioRecordEvent.AUDIO_RECORD_EVENT_TIME_OUT;
                m.obj = msg.obj;
                sendMessage(m);
                break;
            case IAudioRecordEvent.AUDIO_RECORD_EVENT_SAMPLING:
                sendEmptyMessage(IAudioRecordEvent.AUDIO_RECORD_EVENT_SAMPLING);
                break;
        }
        return false;
    }

    private void initView(View root) {
        mHandler = new Handler(root.getHandler().getLooper(), this);

        LayoutInflater inflater = LayoutInflater.from(root.getContext());
        View view = inflater.inflate(R.layout.rc_wi_vo_popup, null);

        mStateIV = (ImageView) view.findViewById(R.id.rc_audio_state_image);
        mStateTV = (TextView) view.findViewById(R.id.rc_audio_state_text);
        mTimerTV = (TextView) view.findViewById(R.id.rc_audio_timer);

        mRecordWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecordWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
        mRecordWindow.setFocusable(true);
        mRecordWindow.setOutsideTouchable(false);
        mRecordWindow.setTouchable(false);
    }

    private void setTimeoutView(int counter) {
        if (counter > 0) {
            if (mRecordWindow != null) {
                mStateIV.setVisibility(View.GONE);
                mStateTV.setVisibility(View.VISIBLE);
                mStateTV.setText("手指上滑，取消发送");
                mStateTV.setBackgroundResource(android.R.color.transparent);
                mTimerTV.setText(String.format("%s", counter));
                mTimerTV.setVisibility(View.VISIBLE);
            }
        } else {
            if (mRecordWindow != null) {
                mStateIV.setVisibility(View.VISIBLE);
                mStateIV.setImageResource(R.drawable.rc_ic_volume_wraning);
                mStateTV.setText("录制时间超长");
                mStateTV.setBackgroundResource(android.R.color.transparent);
                mTimerTV.setVisibility(View.GONE);
            }
        }

    }

    private void setRecordingView() {

        if (mRecordWindow != null) {
            mStateIV.setVisibility(View.VISIBLE);
            mStateIV.setImageResource(R.drawable.rc_ic_volume_1);
            mStateTV.setVisibility(View.VISIBLE);
            mStateTV.setText("手指上滑，取消发送");
            mStateTV.setBackgroundResource(android.R.color.transparent);
            mTimerTV.setVisibility(View.GONE);
        }
    }

    private void setCancelView() {
        if (mRecordWindow != null) {
            mTimerTV.setVisibility(View.GONE);
            mStateIV.setVisibility(View.VISIBLE);
            mStateIV.setImageResource(R.drawable.rc_ic_volume_cancel);
            mStateTV.setVisibility(View.VISIBLE);
            mStateTV.setText("松开手指，取消发送");
            mStateTV.setBackgroundResource(R.drawable.rc_corner_voice_style);
        }
    }

    private void destroyView() {
        if (mRecordWindow != null) {
            mHandler.removeMessages(IAudioRecordEvent.AUDIO_RECORD_EVENT_TIME_OUT);
            mHandler.removeMessages(IAudioRecordEvent.AUDIO_RECORD_EVENT_TICKER);
            mHandler.removeMessages(IAudioRecordEvent.AUDIO_RECORD_EVENT_SAMPLING);
            mRecordWindow.dismiss();
            mRecordWindow = null;
            mStateIV = null;
            mStateTV = null;
            mTimerTV = null;
            mHandler = null;
            mContext = null;
            mRootView = null;
        }
    }

    public void setMaxVoiceDuration(int maxVoiceDuration) {
        RECORD_INTERVAL = maxVoiceDuration;
    }

    public int getMaxVoiceDuration() {
        return RECORD_INTERVAL;
    }

    public void startRecord(View rootView, ConversationType conversationType, String targetId) {
        this.mRootView = rootView;
        this.mContext = rootView.getContext().getApplicationContext();
        this.mConversationType = conversationType;
        this.mTargetId = targetId;
        this.mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        if (rootView == null) return;

        if (this.mAfChangeListener != null) {
            mAudioManager.abandonAudioFocus(mAfChangeListener);
            mAfChangeListener = null;
        }
        this.mAfChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    mAudioManager.abandonAudioFocus(mAfChangeListener);
                    mAfChangeListener = null;
                    sendEmptyMessage(IAudioRecordEvent.AUDIO_RECORD_EVENT_ABORT);
                }
            }
        };
        sendEmptyMessage(IAudioRecordEvent.AUDIO_RECORD_EVENT_TRIGGER);

//        if (TypingMessageManager.getInstance().isShowMessageTyping()) {
//            RongIMClient.getInstance().sendTypingStatus(conversationType, targetId, "RC:VcMsg");
//        }
    }

    public void willCancelRecord() {
        sendEmptyMessage(IAudioRecordEvent.AUDIO_RECORD_EVENT_WILL_CANCEL);
    }

    public void continueRecord() {
        sendEmptyMessage(IAudioRecordEvent.AUDIO_RECORD_EVENT_CONTINUE);
    }

    public void stopRecord() {
        sendEmptyMessage(IAudioRecordEvent.AUDIO_RECORD_EVENT_RELEASE);
    }


    public void destroyRecord() {
        AudioStateMessage msg = new AudioStateMessage();
        msg.obj = true;
        msg.what = IAudioRecordEvent.AUDIO_RECORD_EVENT_RELEASE;
        sendMessage(msg);
    }

    void sendMessage(AudioStateMessage message) {
        mCurAudioState.handleMessage(message);
    }

    void sendEmptyMessage(int event) {
        AudioStateMessage message = AudioStateMessage.obtain();
        message.what = event;
        mCurAudioState.handleMessage(message);
    }

    private void startRec() {
        try {
            muteAudioFocus(mAudioManager, true);
            mAudioManager.setMode(android.media.AudioManager.MODE_NORMAL);
            mMediaRecorder = new MediaRecorder();
            try {
                Resources resources = mContext.getResources();
//                int bpsNb = resources.getInteger(resources.getIdentifier("rc_audio_encoding_bit_rate", "integer", mContext.getPackageName()));
//                int bpsWb = resources.getInteger(resources.getIdentifier("rc_audio_wb_encoding_bit_rate", "integer", mContext.getPackageName()));
                int bpsAAC = resources.getInteger(resources.getIdentifier("rc_audio_aac_encoding_bit_rate", "integer", mContext.getPackageName()));
//                if (RongIM.getInstance().getVoiceMessageType() == RongIM.VoiceMessageType.HighQuality) {
                mMediaRecorder.setAudioSamplingRate(44100);
                mMediaRecorder.setAudioEncodingBitRate(bpsAAC);
//                } else {
//                    mMediaRecorder.setAudioSamplingRate(RongIM.getInstance().getSamplingRate());
//                    if (RongIM.getInstance().getSamplingRate() == RC_SAMPLE_RATE_8000) {
//                        mMediaRecorder.setAudioEncodingBitRate(bpsNb);
//                    } else {
//                        mMediaRecorder.setAudioEncodingBitRate(bpsWb);
//                    }
//                }
            } catch (Resources.NotFoundException e) {
                Log.e(TAG, "startRec", e);
            }
            mMediaRecorder.setAudioChannels(1);
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            if (RongIM.getInstance().getVoiceMessageType() == RongIM.VoiceMessageType.HighQuality) {
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            if (android.os.Build.VERSION.SDK_INT >= 28) {
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);
            } else {
                mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            }
//            } else {
//                if (RongIM.getInstance().getSamplingRate() == RC_SAMPLE_RATE_8000) {
//                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
//                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//                } else {
//                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
//                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
//                }
//            }
            File savePath = mContext.getCacheDir();
            mAudioPath = Uri.fromFile(new File(savePath, System.currentTimeMillis() + "temp.mp3"));
            mMediaRecorder.setOutputFile(mAudioPath.getPath());
            mMediaRecorder.prepare();
            mMediaRecorder.start();

            android.os.Message message = android.os.Message.obtain();
            message.what = IAudioRecordEvent.AUDIO_RECORD_EVENT_TIME_OUT;
            message.obj = 10;
            mHandler.sendMessageDelayed(message, RECORD_INTERVAL * 1000 - 10 * 1000);
        } catch (Exception e) {
        }
    }

    private boolean checkAudioTimeLength() {
        long delta = SystemClock.elapsedRealtime() - smStartRecTime;
        return (delta < 1000);
    }

    private void stopRec() {
        try {
            muteAudioFocus(mAudioManager, false);
            if (mMediaRecorder != null) {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        } catch (Exception e) {
        }
    }

    private void deleteAudioFile() {

        if (mAudioPath != null) {
            File file = new File(mAudioPath.getPath());
            if (file.exists()) {
                boolean deleteResult = file.delete();
                if (!deleteResult) {
                    Log.e(TAG, "deleteAudioFile delete file failed. path :" + mAudioPath.getPath());
                }
            }
        }
    }

    private void sendAudioFile() {
        Log.d(TAG, "sendAudioFile 地址" + mAudioPath);
        if (mAudioPath != null) {
            File file = new File(mAudioPath.getPath());
            if (!file.exists() || file.length() == 0) {
                Log.e(TAG, "sendAudioFile fail cause of file length 0 or audio permission denied");
                return;
            }
            int duration = (int) (SystemClock.elapsedRealtime() - smStartRecTime) / 1000;
            Log.d(TAG, "sendAudioFile 时长" + duration);
            // 发送语音信息
            VoiceMsg msgBean = new VoiceMsg();
            msgBean.setNickName("我");
            msgBean.setDuration(duration);
            msgBean.setMsgType(MsgBean.MsgType.VOICE);
            msgBean.setType(1);
            msgBean.setAudioPath(mAudioPath);
            EventBus.getDefault().post(msgBean);
        }
    }

    private void audioDBChanged() {
        if (mMediaRecorder != null) {
            int db = mMediaRecorder.getMaxAmplitude() / 600;
            switch (db / 5) {
                case 0:
                    mStateIV.setImageResource(R.drawable.rc_ic_volume_1);
                    break;
                case 1:
                    mStateIV.setImageResource(R.drawable.rc_ic_volume_2);
                    break;
                case 2:
                    mStateIV.setImageResource(R.drawable.rc_ic_volume_3);
                    break;
                case 3:
                    mStateIV.setImageResource(R.drawable.rc_ic_volume_4);
                    break;
                case 4:
                    mStateIV.setImageResource(R.drawable.rc_ic_volume_5);
                    break;
                case 5:
                    mStateIV.setImageResource(R.drawable.rc_ic_volume_6);
                    break;
                case 6:
                    mStateIV.setImageResource(R.drawable.rc_ic_volume_7);
                    break;
                default:
                    mStateIV.setImageResource(R.drawable.rc_ic_volume_8);
                    break;
            }
        }
    }

    private void muteAudioFocus(AudioManager audioManager, boolean bMute) {
        if (Build.VERSION.SDK_INT < 8) {
            // 2.1以下的版本不支持下面的API：requestAudioFocus和abandonAudioFocus
            return;
        }
        if (bMute) {
            audioManager.requestAudioFocus(mAfChangeListener, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        } else {
            audioManager.abandonAudioFocus(mAfChangeListener);
            mAfChangeListener = null;
        }
    }
}