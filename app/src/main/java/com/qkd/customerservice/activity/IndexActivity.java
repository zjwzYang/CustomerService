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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.ArticleMsg;
import com.qkd.customerservice.bean.CustomMessageBean;
import com.qkd.customerservice.bean.ImageMsg;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.TextMsg;
import com.qkd.customerservice.bean.TotalUnreadBean;
import com.qkd.customerservice.bean.VoiceMsg;
import com.qkd.customerservice.fragment.MailFragment;
import com.qkd.customerservice.fragment.MineFragment;
import com.qkd.customerservice.fragment.MsgFragment;
import com.qkd.customerservice.net.BaseHttp;
import com.qkd.customerservice.net.BaseOutput;
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMCustomElem;
import com.tencent.imsdk.v2.V2TIMImageElem;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageReceipt;
import com.tencent.imsdk.v2.V2TIMOfflinePushConfig;
import com.tencent.imsdk.v2.V2TIMSoundElem;
import com.tencent.imsdk.v2.V2TIMTextElem;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM;
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

    private TextView mUnreadCount;

    private MsgFragment mMsgFragment;
    private MailFragment mMailFragment;
    private MineFragment mMineFragment;
    private Fragment currFragment;
    private SharedPreferences sp;
    private V2TIMAdvancedMsgListener mListener;
    private String identifier;

    private int status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        SharedPreferences sp = getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        identifier = sp.getString(Constant.USER_IDENTIFIER, "");

        EventBus.getDefault().register(this);
        mMsgImg = findViewById(R.id.index_msg_img);
        mMsgText = findViewById(R.id.index_msg_text);
        mMailImg = findViewById(R.id.index_mail_img);
        mMailText = findViewById(R.id.index_mail_text);
        mMineImg = findViewById(R.id.index_mine_img);
        mMineText = findViewById(R.id.index_mine_text);
        mUnreadCount = findViewById(R.id.index_unread_count);

        findViewById(R.id.msg_linear).setOnClickListener(this);
        findViewById(R.id.mail_linear).setOnClickListener(this);
        findViewById(R.id.mine_linear).setOnClickListener(this);

        selectImg(0);

        initListener();

        this.sp = getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        status = sp.getInt(Constant.USER_STATUS, 1);
        switch (status) {
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

        getHWToken();
    }

    private void getHWToken() {
        // 创建一个新线程
        new Thread() {
            @Override
            public void run() {
                try {
                    // 从agconnect-service.json文件中读取appId
                    String appId = AGConnectServicesConfig.fromContext(IndexActivity.this).getString("client/app_id");

                    // 输入token标识"HCM"
                    String tokenScope = "HCM";
                    String token = HmsInstanceId.getInstance(IndexActivity.this).getToken(appId, tokenScope);
                    Log.i("12345678", "get token: " + token);

                    // 判断token是否为空
                    if (!TextUtils.isEmpty(token)) {
                        //sendRegTokenToServer(token);
                        V2TIMOfflinePushConfig config = new V2TIMOfflinePushConfig(Constant.IM_HW_ID, token);
                        V2TIMManager.getOfflinePushManager().setOfflinePushConfig(config, new V2TIMCallback() {
                            @Override
                            public void onError(int code, String desc) {

                            }

                            @Override
                            public void onSuccess() {
                                Log.i("12345678", "token上次成功 ");
                            }
                        });
                    }
                } catch (ApiException e) {
                    Log.e("12345678", "get token failed, " + e);
                }
            }
        }.start();
    }

    private void initListener() {
        mListener = new V2TIMAdvancedMsgListener() {
            @Override
            public void onRecvNewMessage(final V2TIMMessage message) {
                // Log.i("12345678", "onRecvNewMessage: 新消息");

                if (AppUtil.isBackground(IndexActivity.this)) {
                    // 在后台
                    sendNotifi();
                }
                // 当删除的用户发信消息过来，在删除表中删除
                String userID = message.getUserID();
                SharedPreferences sp = getSharedPreferences(Constant.SORT_FLAG, Context.MODE_PRIVATE);
                String deletes = sp.getString(Constant.DELETE_USERID + "_" + identifier, "");
//                String[] deleteA = deletes.split("/");
//                if (Arrays.asList(deleteA).contains(userID)) {
//                    return;
//                }
                if (deletes.contains(userID)) {
                    String replace = deletes.replace("/" + userID, "");
                    //Log.i("deletes", "deletes: " + deletes + "  replace:" + replace);
                    sp.edit().putString(Constant.DELETE_USERID + "_" + identifier, replace).apply();
                }

                super.onRecvNewMessage(message);
                int type = message.getElemType();
                final String msgID = message.getMsgID();
                final String timeString = AppUtil.getTimeString(new Date().getTime());
                if (type == V2TIM_ELEM_TYPE_TEXT) {
                    TextMsg textMsg = new TextMsg();
                    V2TIMTextElem textElem = message.getTextElem();
                    textMsg.setMsgId(msgID);
                    String text = textElem.getText();
                    Log.i("12345678", "onRecvNewMessage: 新消息" + text + message.getSender() + "  " + message.getMsgID());
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
                            imageMsg.setMsgId(msgID);
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
                                Log.i("12345678", "获取语音: " + s + "  getDuration:" + soundElem.getDuration() + "  " + message.getNickName());
                                int duration = soundElem.getDuration();
                                VoiceMsg voiceMsg = new VoiceMsg();
                                voiceMsg.setMsgId(msgID);
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
                } else if (type == V2TIM_ELEM_TYPE_CUSTOM) {
                    V2TIMCustomElem customElem = message.getCustomElem();
                    byte[] data = customElem.getData();
                    String url = new String(data);
                    Gson gson = new Gson();
                    CustomMessageBean bean;
                    try {
                        bean = gson.fromJson(url, CustomMessageBean.class);
                    } catch (JsonSyntaxException e) {
                        bean = new CustomMessageBean();
                    }
                    ArticleMsg articleMsg = new ArticleMsg();
                    articleMsg.setTitle(bean.getNickname() + "的专属方案");
                    articleMsg.setDescription("趣看保，守护您的一生平安");
                    articleMsg.setUrl(bean.getUrl());
                    articleMsg.setPicUrl("http://47.114.100.72/files/pic/1611294989682a2e3219f-5a76-4bd2-a9c4-d286ebc62052.jpg");
                    articleMsg.setSendTime(timeString);
                    articleMsg.setType(1);
                    articleMsg.setMsgType(MsgBean.MsgType.ARTICLE);
                    articleMsg.setSenderId(message.getSender());
                    EventBus.getDefault().post(articleMsg);
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
        };
        V2TIMManager.getMessageManager().addAdvancedMsgListener(mListener);
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
        int itemId = item.getItemId();
        if (itemId == status) {
            return true;
        }
        switch (itemId) {
            case 1:
                updateStatus(1);
                break;
            case 2:
                updateStatus(2);
                break;
            case 3:
                updateStatus(3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGettMsg(String msg) {
        if (Constant.UPDATE_USER_STATUS.equals(msg)) {
            updateStatus(2);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetUnreadCount(TotalUnreadBean totalUnreadBean) {
        int unreadTotalCount = totalUnreadBean.getUnreadTotalCount();
        if (unreadTotalCount <= 0) {
            mUnreadCount.setVisibility(View.GONE);
        } else if (unreadTotalCount > 99) {
            mUnreadCount.setText("~");
            mUnreadCount.setVisibility(View.VISIBLE);
        } else {
            mUnreadCount.setText(String.valueOf(unreadTotalCount));
            mUnreadCount.setVisibility(View.VISIBLE);
        }
    }

    private void updateStatus(final int statusInt) {
        String userId = sp.getString(Constant.USER_IDENTIFIER, "");
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).updateStatus(userId, statusInt), new BaseHttp.HttpObserver<BaseOutput>() {
            @Override
            public void onSuccess(BaseOutput baseOutput) {
                if (baseOutput.isSuccess()) {
                    sp.edit().putInt(Constant.USER_STATUS, statusInt).apply();
                    status = statusInt;
                    switch (statusInt) {
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
                }
            }

            @Override
            public void onError() {

            }
        });
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

    @Override
    protected void onDestroy() {
        V2TIMManager.getMessageManager().removeAdvancedMsgListener(mListener);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
