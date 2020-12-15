package com.qkd.customerservice.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.ImageMsg;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.TextMsg;
import com.qkd.customerservice.bean.TokenBean;
import com.qkd.customerservice.bean.VoiceMsg;
import com.qkd.customerservice.fragment.MailFragment;
import com.qkd.customerservice.fragment.MineFragment;
import com.qkd.customerservice.fragment.MsgFragment;
import com.qkd.customerservice.net.BaseHttp;
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
import com.tencent.imsdk.v2.V2TIMImageElem;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageReceipt;
import com.tencent.imsdk.v2.V2TIMSoundElem;
import com.tencent.imsdk.v2.V2TIMTextElem;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE;
import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_SOUND;
import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_TEXT;

/**
 * Created on 12/2/20 09:22
 * .
 *
 * @author yj
 */
public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mMsgImg;
    private TextView mMsgText;
    private ImageView mMailImg;
    private TextView mMailText;
    private ImageView mMineImg;
    private TextView mMineText;

    private MsgFragment mMsgFragment;
    private MailFragment mMailFragment;
    private MineFragment mMineFragment;
    private Fragment currFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mMsgImg = findViewById(R.id.index_msg_img);
        mMsgText = findViewById(R.id.index_msg_text);
        mMailImg = findViewById(R.id.index_mail_img);
        mMailText = findViewById(R.id.index_mail_text);
        mMineImg = findViewById(R.id.index_mine_img);
        mMineText = findViewById(R.id.index_mine_text);

        findViewById(R.id.msg_linear).setOnClickListener(this);
        findViewById(R.id.mail_linear).setOnClickListener(this);
        findViewById(R.id.mine_linear).setOnClickListener(this);

        selectImg(0);

        initListener();

        setTitle("在线");

        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL).getToken(77), new BaseHttp.HttpObserver<TokenBean>() {
            @Override
            public void onSuccess(TokenBean output) {
                String data = output.getData();
                if (!TextUtils.isEmpty(data)) {
                    SharedPreferences sp = getSharedPreferences(Constant.APP_DATA, Context.MODE_PRIVATE);
                    sp.edit().putString(Constant.USER_TOKEN, data).apply();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initListener() {
//        V2TIMManager.getInstance().addSimpleMsgListener(new V2TIMSimpleMsgListener() {
//            @Override
//            public void onRecvC2CTextMessage(String msgID, V2TIMUserInfo sender, String text) {
//                super.onRecvC2CTextMessage(msgID, sender, text);
//                Log.i("12345678", "msgID: " + msgID + "   text:" + text);
//                TextMsg textMsg = new TextMsg();
//                textMsg.setContent(text);
//                textMsg.setMsgType(MsgBean.MsgType.TEXT);
//                textMsg.setSenderId(sender.getUserID());
//                textMsg.setNickName(sender.getNickName());
//                textMsg.setType(0);
//                EventBus.getDefault().post(textMsg);
//            }
//
//            @Override
//            public void onRecvC2CCustomMessage(String msgID, V2TIMUserInfo sender, byte[] customData) {
//                super.onRecvC2CCustomMessage(msgID, sender, customData);
//            }
//
//            @Override
//            public void onRecvGroupTextMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, String text) {
//                super.onRecvGroupTextMessage(msgID, groupID, sender, text);
//            }
//
//            @Override
//            public void onRecvGroupCustomMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, byte[] customData) {
//                super.onRecvGroupCustomMessage(msgID, groupID, sender, customData);
//            }
//        });
        V2TIMManager.getMessageManager().addAdvancedMsgListener(new V2TIMAdvancedMsgListener() {
            @Override
            public void onRecvNewMessage(final V2TIMMessage message) {
                Log.i("12345678", "onRecvNewMessage: 新消息");

                if (AppUtil.isBackground(IndexActivity.this)) {
                    // 在后台
                    sendNotifi();
                }

                super.onRecvNewMessage(message);
                int type = message.getElemType();
                final String timeString = AppUtil.getTimeString(new Date().getTime());
                if (type == V2TIM_ELEM_TYPE_TEXT) {
                    TextMsg textMsg = new TextMsg();
                    V2TIMTextElem textElem = message.getTextElem();
                    String text = textElem.getText();
                    textMsg.setContent(text);
                    textMsg.setMsgType(MsgBean.MsgType.TEXT);
                    textMsg.setSenderId(message.getSender());
                    textMsg.setNickName(message.getNickName());
                    textMsg.setSendTime(timeString);
                    int sendType;
                    if (text.endsWith(Constant.TEXT_END_FLAG)) {
                        sendType = 2;
                    } else {
                        sendType = 0;
                    }
                    textMsg.setType(sendType);
                    EventBus.getDefault().post(textMsg);
                } else if (type == V2TIM_ELEM_TYPE_IMAGE) {
                    V2TIMImageElem imageElem = message.getImageElem();
                    Log.i("12345678", "HistoryMessage图片: " + imageElem.toString());
                    List<V2TIMImageElem.V2TIMImage> imageList = imageElem.getImageList();
                    for (V2TIMImageElem.V2TIMImage v2TIMImage : imageList) {
                        String url = v2TIMImage.getUrl();
                        if (!TextUtils.isEmpty(url)) {
                            Log.i("12345678", "HistoryMessage图片: " + url);
                            ImageMsg imageMsg = new ImageMsg();
                            imageMsg.setMsgType(MsgBean.MsgType.IMAGE);
                            imageMsg.setType(0);
                            imageMsg.setImgPath(url);
                            imageMsg.setSendTime(timeString);
                            imageMsg.setNickName(message.getNickName());
                            imageMsg.setSenderId(message.getSender());
                            EventBus.getDefault().post(imageMsg);
                        }
                    }

                } else if (type == V2TIM_ELEM_TYPE_SOUND) {
                    Log.i("12345678", "HistoryMessage语音: " + message.getSoundElem().toString());
                    final V2TIMSoundElem soundElem = message.getSoundElem();
                    soundElem.getUrl(new V2TIMValueCallback<String>() {
                        @Override
                        public void onError(int code, String desc) {
                            Log.i("12345678", "获取语音出错: " + code + "  " + desc);
                        }

                        @Override
                        public void onSuccess(String s) {
                            if (!TextUtils.isEmpty(s)) {
                                Log.i("12345678", "获取语音: " + s + "  getDuration:" + soundElem.getDuration());
                                int duration = soundElem.getDuration();
                                VoiceMsg voiceMsg = new VoiceMsg();
                                voiceMsg.setNickName(message.getNickName());
                                voiceMsg.setPlaying(false);
                                voiceMsg.setDuration(duration);
                                voiceMsg.setAudioPath(Uri.parse(s));
                                voiceMsg.setType(0);
                                voiceMsg.setSendTime(timeString);
                                voiceMsg.setMsgType(MsgBean.MsgType.VOICE);
                                voiceMsg.setSenderId(message.getSender());
                                EventBus.getDefault().post(voiceMsg);
                            }
                        }
                    });
                }
            }

            @Override
            public void onRecvC2CReadReceipt(List<V2TIMMessageReceipt> receiptList) {
                super.onRecvC2CReadReceipt(receiptList);
            }

            @Override
            public void onRecvMessageRevoked(String msgID) {
                super.onRecvMessageRevoked(msgID);
            }
        });
    }

    private void sendNotifi() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            Intent intent = new Intent(this, IndexActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "my_channel_ID")
                    .setContentTitle("通知")
                    .setContentText("收到一条新消息，请及时查看！")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.chat_icon)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(100, notification.build());

        } else {
            Intent intent = new Intent(this, IndexActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle("这是标题")
                    .setContentText("我是内容，我是demo")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.chat_icon)
                    .setContentIntent(pi);
            manager.notify(1, builder.build());
        }
    }

    private void selectImg(int index) {
        mMsgText.setTextColor(ContextCompat.getColor(this, R.color.text_gray));
        mMsgImg.setBackgroundResource(R.mipmap.index_msg);
        mMailText.setTextColor(ContextCompat.getColor(this, R.color.text_gray));
        mMailImg.setBackgroundResource(R.mipmap.index_mail);
        mMineText.setTextColor(ContextCompat.getColor(this, R.color.text_gray));
        mMineImg.setImageResource(R.mipmap.index_mine);
        switch (index) {
            case 0:
                mMsgImg.setBackgroundResource(R.mipmap.index_msg_selected);
                mMsgText.setTextColor(ContextCompat.getColor(this, R.color.text_black));
                break;
            case 1:
                mMailImg.setBackgroundResource(R.mipmap.index_mail_selected);
                mMailText.setTextColor(ContextCompat.getColor(this, R.color.text_black));
                break;
            case 2:
                mMineImg.setBackgroundResource(R.mipmap.index_mine_selected);
                mMineText.setTextColor(ContextCompat.getColor(this, R.color.text_black));
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (currFragment != null) {
            ft.hide(currFragment);
        }
        switch (index) {
            case 0:
                if (mMsgFragment == null) {
                    mMsgFragment = new MsgFragment();
                    ft.add(R.id.index_frame, mMsgFragment);
                } else {
                    ft.show(mMsgFragment);
                }
                currFragment = mMsgFragment;
                break;
            case 1:
                if (mMailFragment == null) {
                    mMailFragment = new MailFragment();
                    ft.add(R.id.index_frame, mMailFragment);
                } else {
                    ft.show(mMailFragment);
                }
                currFragment = mMailFragment;
                break;
            case 2:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    ft.add(R.id.index_frame, mMineFragment);
                } else {
                    ft.show(mMineFragment);
                }
                currFragment = mMineFragment;
                break;
        }
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (currFragment instanceof MsgFragment) {
            menu.add(Menu.NONE, 1, 0, "在线").setIcon(R.drawable.ic_online);
            menu.add(Menu.NONE, 2, 0, "忙碌").setIcon(R.drawable.ic_busy);
            menu.add(Menu.NONE, 3, 0, "离线").setIcon(R.drawable.ic_ounline);
            return true;
        } else {
            menu.clear();
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                setTitle("在线");
                break;
            case 2:
                setTitle("忙碌");
                break;
            case 3:
                setTitle("离线");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        invalidateOptionsMenu();
        switch (view.getId()) {
            case R.id.msg_linear:
                selectImg(0);
                break;
            case R.id.mail_linear:
                selectImg(1);
                break;
            case R.id.mine_linear:
                selectImg(2);
                break;
        }
    }
}
