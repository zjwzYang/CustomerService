package com.qkd.customerservice.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.MyApp;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.MsgAdapter;
import com.qkd.customerservice.bean.ImageMsg;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.TextMsg;
import com.qkd.customerservice.bean.VoiceMsg;
import com.qkd.customerservice.key_library.KeyboardHelper;
import com.qkd.customerservice.key_library.util.DensityUtil;
import com.qkd.customerservice.widget.CExpressionPanel;
import com.qkd.customerservice.widget.CInputPanel;
import com.qkd.customerservice.widget.CMorePanel;
import com.qkd.customerservice.widget.GlideSimpleLoader;
import com.tencent.imsdk.v2.V2TIMImageElem;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMSoundElem;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE;
import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_SOUND;
import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_TEXT;

/**
 * Created on 12/1/20 09:30
 * 聊天.
 *
 * @author yj
 */
public class ChatActivity extends AppCompatActivity {

    public static final int IMAGE_REQUEST = 110;

    private List<MsgBean> msgList;
    private KeyboardHelper keyboardHelper;

    private ConstraintLayout layout_main;
    private RecyclerView recycler_view;
    private MsgAdapter adapter;
    private CInputPanel chat_input_panel;
    private CExpressionPanel expression_panel;
    private CMorePanel more_panel;

    private ImageWatcherHelper imageWatcher;

    private String UserID;
    private String showName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        EventBus.getDefault().register(this);
        layout_main = findViewById(R.id.layout_main);
        recycler_view = findViewById(R.id.recycler_view);
        chat_input_panel = findViewById(R.id.chat_input_panel);
        expression_panel = findViewById(R.id.expression_panel);
        more_panel = findViewById(R.id.more_panel);

        init();

        initConversation();
    }

    private void initConversation() {
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        showName = intent.getStringExtra("showName");
        Log.i("12345678", "当前会话: " + UserID);
        V2TIMManager.getMessageManager().getC2CHistoryMessageList(UserID, 20, null, new V2TIMValueCallback<List<V2TIMMessage>>() {
            @Override
            public void onError(int code, String desc) {
                Log.i("12345678", "拉取历史会话: " + code + "  " + desc);
            }

            @Override
            public void onSuccess(List<V2TIMMessage> v2TIMMessages) {
                for (int i = 0; i < v2TIMMessages.size(); i++) {
                    V2TIMMessage message = v2TIMMessages.get(i);
                    int type = message.getElemType();
                    if (type == V2TIM_ELEM_TYPE_TEXT) {
                        Log.i("12345678", "HistoryMessage: " + message.getTextElem().toString());
                        TextMsg textMsg = new TextMsg();
                        textMsg.setMsgType(MsgBean.MsgType.TEXT);
                        textMsg.setType(0);
                        textMsg.setContent(message.getTextElem().getText());
                        textMsg.setNickName(showName);
                        adapter.addMsg(textMsg);

                    } else if (type == V2TIM_ELEM_TYPE_IMAGE) {
                        V2TIMImageElem imageElem = message.getImageElem();
                        Log.i("12345678", "HistoryMessage图片: " + imageElem.toString());
                        List<V2TIMImageElem.V2TIMImage> imageList = imageElem.getImageList();
                        for (V2TIMImageElem.V2TIMImage v2TIMImage : imageList) {
                            String uuid = v2TIMImage.getUUID(); // 图片 ID
                            int imageType = v2TIMImage.getType(); // 图片类型
                            int size = v2TIMImage.getSize(); // 图片大小（字节）
                            int width = v2TIMImage.getWidth(); // 图片宽度
                            int height = v2TIMImage.getHeight(); // 图片高度
                            String url = v2TIMImage.getUrl();
                            ImageMsg imageMsg = new ImageMsg();
                            imageMsg.setMsgType(MsgBean.MsgType.IMAGE);
                            imageMsg.setType(0);
                            imageMsg.setImgPath(url);
                            imageMsg.setNickName(showName);
                            adapter.addMsg(imageMsg);
                        }
                    } else if (type == V2TIM_ELEM_TYPE_SOUND) {
                        final V2TIMSoundElem soundElem = message.getSoundElem();
                        soundElem.getUrl(new V2TIMValueCallback<String>() {
                            @Override
                            public void onError(int code, String desc) {
                                Log.i("12345678", "获取语音出错: " + code + "  " + desc);
                            }

                            @Override
                            public void onSuccess(String s) {
                                Log.i("12345678", "获取语音: " + s + "  getDuration:" + soundElem.getDuration());
                                int duration = soundElem.getDuration();
                                VoiceMsg voiceMsg = new VoiceMsg();
                                voiceMsg.setNickName(showName);
                                voiceMsg.setPlaying(false);
                                voiceMsg.setDuration(duration);
                                voiceMsg.setAudioPath(Uri.parse(s));
                                voiceMsg.setType(0);
                                voiceMsg.setMsgType(MsgBean.MsgType.VOICE);
                                adapter.addMsg(voiceMsg);
                            }
                        });
                    }
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void init() {
        imageWatcher = ImageWatcherHelper.with(this, new GlideSimpleLoader());
        msgList = new ArrayList<>();
        keyboardHelper = new KeyboardHelper(this);
        keyboardHelper.bindRootLayout(layout_main);
        keyboardHelper.bindRecyclerView(recycler_view);
        keyboardHelper.bindInputPanel(chat_input_panel);
        keyboardHelper.bindExpressionPanel(expression_panel);
        keyboardHelper.bindMorePanel(more_panel);
//        keyboardHelper.setScrollBodyLayout(msgList.size() > 15);
        keyboardHelper.setScrollBodyLayout(true);
        int keyboardHeight;
        if (MyApp.keyboardHeight == 0)
            keyboardHeight = DensityUtil.getScreenHeight(this) / 5 * 2;
        else
            keyboardHeight = MyApp.keyboardHeight;
        keyboardHelper.setKeyboardHeight(keyboardHeight);
        keyboardHelper.setOnKeyboardStateListener(new KeyboardHelper.OnKeyboardStateListener() {
            @Override
            public void onOpened(int keyboardHeight) {
                MyApp.keyboardHeight = keyboardHeight;
            }

            @Override
            public void onClosed() {

            }
        });

        recycler_view.setHasFixedSize(true);
        adapter = new MsgAdapter(this, msgList);
        adapter.setOnClickImageListener(new MsgAdapter.OnClickImageListener() {
            @Override
            public void onClickImage(ImageView imageView, SparseArray<ImageView> mappingViews, List<String> urlList) {
                if (imageWatcher != null) {
                    imageWatcher.show(imageView, mappingViews, AppUtil.convert(urlList));
                }
            }
        });
//        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(adapter);
        recycler_view.smoothScrollToPosition(adapter.getItemCount());
        ((SimpleItemAnimator) recycler_view.getItemAnimator()).setSupportsChangeAnimations(false);
        recycler_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    keyboardHelper.reset();
                }
                return false;
            }
        });

        String[] permissions = {Manifest.permission.RECORD_AUDIO};
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(ChatActivity.this, "请先获取权限", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetVoiceMsg(VoiceMsg voiceMsg) {
        adapter.addMsg(voiceMsg);
        recycler_view.smoothScrollToPosition(adapter.getItemCount());

        // 腾讯云发送语音
        V2TIMMessage soundMessage = V2TIMManager.getMessageManager().createSoundMessage(voiceMsg.getAudioPath().getPath(), voiceMsg.getDuration());
        V2TIMManager.getMessageManager().sendMessage(soundMessage, UserID
                , null, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null, new V2TIMSendCallback<V2TIMMessage>() {
                    @Override
                    public void onProgress(int progress) {
                        Log.i("12345678", "onProgress: " + progress);
                    }

                    @Override
                    public void onError(int code, String desc) {
                        Log.i("12345678", "发送出错: " + code + "  " + desc);
                    }

                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {
                        Log.i("12345678", "onSuccess: " + v2TIMMessage.getSoundElem().toString());
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTextMsg(TextMsg textMsg) {
        adapter.addMsg(textMsg);
        recycler_view.smoothScrollToPosition(adapter.getItemCount());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            List<String> imgStrs = Matisse.obtainPathResult(data);
            for (String path : imgStrs) {
                ImageMsg imageMsg = new ImageMsg();
                imageMsg.setNickName("我");
                imageMsg.setType(1);
                imageMsg.setMsgType(MsgBean.MsgType.IMAGE);
                imageMsg.setImgPath(path);
                adapter.addMsg(imageMsg);
                recycler_view.smoothScrollToPosition(adapter.getItemCount());

                Log.i("12345678", "onActivityResult: " + path);
                // 创建图片消息
                V2TIMMessage v2TIMMessage = V2TIMManager.getMessageManager().createImageMessage(path);
                // 发送图片消息
                V2TIMManager.getMessageManager().sendMessage(v2TIMMessage, "test_guan",
                        null, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null, new V2TIMSendCallback<V2TIMMessage>() {
                            @Override
                            public void onProgress(int progress) {
                                Log.i("12345678", "onProgress: " + progress);
                            }

                            @Override
                            public void onError(int code, String desc) {
                                Log.i("12345678", "发送出错: " + code + "  " + desc);
                            }

                            @Override
                            public void onSuccess(V2TIMMessage v2TIMMessage) {
                                Log.i("12345678", "onSuccess: " + v2TIMMessage.getImageElem().toString());
                            }
                        });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        chat_input_panel.onActivityDestory();
        keyboardHelper.release();
    }
}
