package com.qkd.customerservice.audio;


public abstract class IAudioState {
    void enter() {

    }

    abstract void handleMessage(AudioStateMessage message);
}
