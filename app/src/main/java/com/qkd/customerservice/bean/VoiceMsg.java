package com.qkd.customerservice.bean;

import android.net.Uri;

/**
 * Created on 12/7/20 13:22
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class VoiceMsg extends MsgBean {
    private Uri mAudioPath;
    private int duration;
    private boolean playing;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public Uri getAudioPath() {
        return mAudioPath;
    }

    public void setAudioPath(Uri audioPath) {
        mAudioPath = audioPath;
    }
}