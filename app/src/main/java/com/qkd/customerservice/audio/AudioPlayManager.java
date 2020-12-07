package com.qkd.customerservice.audio;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;


public class AudioPlayManager implements SensorEventListener {
    private final static String TAG = "12345678";

    private MediaPlayer mMediaPlayer;
    private IAudioPlayListener _playListener;
    private Uri mUriPlaying;
    private Sensor _sensor;
    private SensorManager _sensorManager;
    private AudioManager mAudioManager;
    private PowerManager _powerManager;
    private PowerManager.WakeLock _wakeLock;
    private AudioManager.OnAudioFocusChangeListener afChangeListener;

    static class SingletonHolder {
        static AudioPlayManager sInstance = new AudioPlayManager();
    }

    public static AudioPlayManager getInstance() {
        return SingletonHolder.sInstance;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onSensorChanged(SensorEvent event) {
        float range = event.values[0];
        Log.d(TAG, "onSensorChanged. range:" + range + "; max range:" + event.sensor.getMaximumRange());
        double rangeJudgeValue = 0.0;
        boolean judge;
        if (_sensor == null || mMediaPlayer == null) {
            return;
        }
        judge = judgeCondition(event, range, rangeJudgeValue);

        if (mMediaPlayer.isPlaying()) {
            if (judge) {
                //处理 sensor 出现异常后，持续回调 sensor 变化，导致声音播放卡顿
                if (mAudioManager.getMode() == AudioManager.MODE_NORMAL) return;
                mAudioManager.setMode(AudioManager.MODE_NORMAL);
                mAudioManager.setSpeakerphoneOn(true);
                final int positions = mMediaPlayer.getCurrentPosition();
                try {
                    mMediaPlayer.reset();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        AudioAttributes attributes = new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build();
                        mMediaPlayer.setAudioAttributes(attributes);
                    } else {
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    }
                    mMediaPlayer.setVolume(1, 1);
                    FileInputStream fis = new FileInputStream(mUriPlaying.getPath());
                    mMediaPlayer.setDataSource(fis.getFD());
                    mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.seekTo(positions);
                        }
                    });
                    mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                        @Override
                        public void onSeekComplete(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                    mMediaPlayer.prepareAsync();
                } catch (IOException e) {
                    Log.e(TAG, "onSensorChanged", e);
                }

                setScreenOn();
            } else {
                if (!(android.os.Build.BRAND.equals("samsung") && Build.MODEL.equals("SM-N9200"))) {
                    setScreenOff();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    if (mAudioManager.getMode() == AudioManager.MODE_IN_COMMUNICATION) return;
                    mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                } else {
                    if (mAudioManager.getMode() == AudioManager.MODE_IN_CALL) return;
                    mAudioManager.setMode(AudioManager.MODE_IN_CALL);
                }
                mAudioManager.setSpeakerphoneOn(false);
                replay();
            }
        } else {
            if (range > 0.0) {
                if (mAudioManager.getMode() == AudioManager.MODE_NORMAL) return;
                mAudioManager.setMode(AudioManager.MODE_NORMAL);
                mAudioManager.setSpeakerphoneOn(true);
                setScreenOn();
            }
        }
    }

    private boolean judgeCondition(SensorEvent event, float range, double rangeJudgeValue) {
        boolean judge;
        if (Build.BRAND.equalsIgnoreCase("HUAWEI")) {
            judge = (range >= event.sensor.getMaximumRange());
        } else {
            if (Build.BRAND.equalsIgnoreCase("ZTE")) {
                rangeJudgeValue = 1.0;
            } else if (Build.BRAND.equalsIgnoreCase("nubia")) {
                rangeJudgeValue = 3.0;
            }
            judge = (range > rangeJudgeValue);
        }
        return judge;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setScreenOff() {
        if (_wakeLock == null) {
            _wakeLock = _powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
        }
        if (_wakeLock != null && !_wakeLock.isHeld()) {
            _wakeLock.acquire();
        }
    }

    private void setScreenOn() {
        if (_wakeLock != null && _wakeLock.isHeld()) {
            _wakeLock.setReferenceCounted(false);
            _wakeLock.release();
            _wakeLock = null;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void replay() {
        try {
            mMediaPlayer.reset();
            if (android.os.Build.BRAND.equals("samsung") && Build.MODEL.equals("SM-N9200")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AudioAttributes attributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                            .build();
                    mMediaPlayer.setAudioAttributes(attributes);
                } else {
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AudioAttributes attributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build();
                    mMediaPlayer.setAudioAttributes(attributes);
                } else {
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                }
            }
            mMediaPlayer.setVolume(1, 1);
            FileInputStream fis = new FileInputStream(mUriPlaying.getPath());
            mMediaPlayer.setDataSource(fis.getFD());
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 装载完毕回调
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "replay", e);
                    }
                    mp.start();
                }
            });
            // 通过异步的方式装载媒体资源
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e(TAG, "replay", e);
        }
    }

    public void startPlay(final Context context, Uri audioUri, IAudioPlayListener playListener) {
        if (context == null || audioUri == null) {
            Log.e(TAG, "startPlay context or audioUri is null.");
            return;
        }

        if (_playListener != null && mUriPlaying != null) {
            _playListener.onStop(mUriPlaying);
        }
        resetMediaPlayer();

        this.afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                Log.d(TAG, "OnAudioFocusChangeListener " + focusChange);
                if (mAudioManager != null && focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    mAudioManager.abandonAudioFocus(afChangeListener);
                    afChangeListener = null;

                    if (_playListener != null) {
                        _playListener.onComplete(mUriPlaying);
                        _playListener = null;
                    }
                    reset();
                }
            }
        };

        try {
            _powerManager = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
            mAudioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            if (!mAudioManager.isWiredHeadsetOn()) {
                _sensorManager = (SensorManager) context.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
                _sensor = _sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                _sensorManager.registerListener(this, _sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
            muteAudioFocus(mAudioManager, true);

            _playListener = playListener;
            mUriPlaying = audioUri;
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (_playListener != null) {
                        _playListener.onComplete(mUriPlaying);
                        _playListener = null;
                    }
                    reset();
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    reset();
                    return true;
                }
            });
            FileInputStream fis = new FileInputStream(audioUri.getPath());
            mMediaPlayer.setDataSource(fis.getFD());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build();
                mMediaPlayer.setAudioAttributes(attributes);
            } else {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
            //mMediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            if (_playListener != null)
                _playListener.onStart(mUriPlaying);
        } catch (Exception e) {
            Log.e(TAG, "startPlay", e);
            if (_playListener != null) {
                _playListener.onStop(audioUri);
                _playListener = null;
            }
            reset();
        }
    }

    public void setPlayListener(IAudioPlayListener listener) {
        this._playListener = listener;
    }

    public void stopPlay() {
        if (_playListener != null && mUriPlaying != null) {
            _playListener.onStop(mUriPlaying);
        }
        reset();
    }

    private void reset() {
        resetMediaPlayer();
        resetAudioPlayManager();
    }

    private void resetAudioPlayManager() {
        if (mAudioManager != null) {
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            muteAudioFocus(mAudioManager, false);
        }
        if (_sensorManager != null) {
            setScreenOn();
            _sensorManager.unregisterListener(this);
        }
        _sensorManager = null;
        _sensor = null;
        _powerManager = null;
        mAudioManager = null;
        _wakeLock = null;
        mUriPlaying = null;
        _playListener = null;
    }

    private void resetMediaPlayer() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            } catch (IllegalStateException e) {
                Log.e(TAG, "resetMediaPlayer", e);
            }
        }
    }

    public Uri getPlayingUri() {
        return mUriPlaying != null ? mUriPlaying : Uri.EMPTY;
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private void muteAudioFocus(AudioManager audioManager, boolean bMute) {
        if (bMute) {
            audioManager.requestAudioFocus(afChangeListener, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        } else {
            audioManager.abandonAudioFocus(afChangeListener);
            afChangeListener = null;
        }
    }

    /**
     * 检查AudioPlayManager是否处于通道正常的状态。
     *
     * @param context
     * @return
     */
    public boolean isInNormalMode(Context context) {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        }
        return mAudioManager != null && mAudioManager.getMode() == AudioManager.MODE_NORMAL;
    }

    private boolean isVOIPMode = false;

    public boolean isInVOIPMode(Context context) {
        return isVOIPMode;
    }

    public void setInVoipMode(boolean isVOIPMode) {
        this.isVOIPMode = isVOIPMode;
    }

    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }
}
