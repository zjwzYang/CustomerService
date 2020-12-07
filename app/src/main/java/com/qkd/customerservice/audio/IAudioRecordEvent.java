package com.qkd.customerservice.audio;

/**
 * Created on 12/7/20 10:42
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class IAudioRecordEvent {
    public final static int AUDIO_RECORD_EVENT_TRIGGER = 1;
    public final static int AUDIO_RECORD_EVENT_SAMPLING = 2;
    public final static int AUDIO_RECORD_EVENT_WILL_CANCEL = 3;
    public final static int AUDIO_RECORD_EVENT_CONTINUE = 4;
    public final static int AUDIO_RECORD_EVENT_RELEASE = 5;
    public final static int AUDIO_RECORD_EVENT_ABORT = 6;
    public final static int AUDIO_RECORD_EVENT_TIME_OUT = 7;
    public final static int AUDIO_RECORD_EVENT_TICKER = 8;
    public final static int AUDIO_RECORD_EVENT_SEND_FILE = 9;
}