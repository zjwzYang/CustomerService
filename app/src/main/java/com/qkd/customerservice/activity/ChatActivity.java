package com.qkd.customerservice.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.MyApp;
import com.qkd.customerservice.NetUtil;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.MsgAdapter;
import com.qkd.customerservice.bean.ArticleMsg;
import com.qkd.customerservice.bean.ImageMsg;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.NewMessageInput;
import com.qkd.customerservice.bean.NewMessageOutput;
import com.qkd.customerservice.bean.TextMsg;
import com.qkd.customerservice.bean.VoiceMsg;
import com.qkd.customerservice.key_library.KeyboardHelper;
import com.qkd.customerservice.key_library.util.DensityUtil;
import com.qkd.customerservice.net.BaseHttp;
import com.qkd.customerservice.widget.CExpressionPanel;
import com.qkd.customerservice.widget.CInputPanel;
import com.qkd.customerservice.widget.CMorePanel;
import com.qkd.customerservice.widget.GlideSimpleLoader;
import com.qkd.customerservice.widget.PaddingDecoration;
import com.tencent.imsdk.v2.V2TIMCustomElem;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM;
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

    private static String TAG = "ChatActivity";

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
    private String faceUrl;
    private boolean addedWx;

    private boolean getMsgFlag = false;
    private V2TIMMessage lastMsg = null;

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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        showName = intent.getStringExtra("showName");
        addedWx = intent.getBooleanExtra("addedWx", false);
        setTitle(showName);

        init();

        initConversation();
    }

    private void initConversation() {
        V2TIMManager.getMessageManager().getC2CHistoryMessageList(UserID, 30, lastMsg, new V2TIMValueCallback<List<V2TIMMessage>>() {
            @Override
            public void onError(int code, String desc) {
                Log.i("12345678", "拉取历史会话: " + code + "  " + desc);
            }

            @Override
            public void onSuccess(List<V2TIMMessage> v2TIMMessages) {
                V2TIMManager.getMessageManager().markC2CMessageAsRead(UserID, null);
                for (int i = 0; i < v2TIMMessages.size(); i++) {
                    if (i == v2TIMMessages.size() - 1) {
                        lastMsg = v2TIMMessages.get(i);
                        getMsgFlag = false;
                    }
                    V2TIMMessage message = v2TIMMessages.get(i);

                    final String timeString = AppUtil.getTimeString(message.getTimestamp() * 1000);

                    String sender = message.getSender();
                    final int sendType;
                    if (UserID.equals(sender)) {
                        sendType = 0;
                    } else {
                        sendType = 1;
                    }
                    int type = message.getElemType();
                    final String msgID = message.getMsgID();
                    if (type == V2TIM_ELEM_TYPE_TEXT) {
                        String content = message.getTextElem().getText().trim();
                        if (!TextUtils.isEmpty(content)) {
                            if (content.startsWith(Constant.TEXT_ARTICLE_FLAG)) {
                                String[] strings = content.replace(Constant.TEXT_ARTICLE_FLAG, "").split("&");
                                ArticleMsg articleMsg = new ArticleMsg();
                                articleMsg.setMsgId(msgID);
                                articleMsg.setPicUrl(strings[0]);
                                articleMsg.setTitle(strings[1]);
                                articleMsg.setDescription(strings[2]);
                                articleMsg.setUrl(strings[3]);
                                articleMsg.setType(1);
                                articleMsg.setMsgType(MsgBean.MsgType.ARTICLE);
                                articleMsg.setSendTime(timeString);
                                adapter.addMsg(articleMsg);

                            } else {
                                TextMsg textMsg = new TextMsg();
                                textMsg.setMsgId(msgID);
                                int temType = sendType;
                                if (content.endsWith(Constant.TEXT_END_FLAG)) {
                                    temType = 2;
                                }
                                textMsg.setMsgType(MsgBean.MsgType.TEXT);
                                textMsg.setType(temType);
                                textMsg.setContent(content);
                                textMsg.setNickName(showName);
                                textMsg.setSendTime(timeString);
                                adapter.addMsg(textMsg);
                            }
                        }

                    } else if (type == V2TIM_ELEM_TYPE_IMAGE) {
                        V2TIMImageElem imageElem = message.getImageElem();
                        List<V2TIMImageElem.V2TIMImage> imageList = imageElem.getImageList();
                        boolean hasJoin = false;
                        for (V2TIMImageElem.V2TIMImage v2TIMImage : imageList) {
                            String url = v2TIMImage.getUrl();
                            if (!TextUtils.isEmpty(url) && !hasJoin) {
                                hasJoin = true;
                                ImageMsg imageMsg = new ImageMsg();
                                imageMsg.setMsgId(msgID);
                                imageMsg.setMsgType(MsgBean.MsgType.IMAGE);
                                imageMsg.setType(sendType);
                                imageMsg.setImgPath(url);
                                imageMsg.setNickName(showName);
                                imageMsg.setSendTime(timeString);
                                adapter.addMsg(imageMsg);
                            }
                        }
                    } else if (type == V2TIM_ELEM_TYPE_SOUND) {
                        final V2TIMSoundElem soundElem = message.getSoundElem();
                        soundElem.getUrl(new V2TIMValueCallback<String>() {
                            @Override
                            public void onError(int code, String desc) {
                            }

                            @Override
                            public void onSuccess(String s) {
                                if (!TextUtils.isEmpty(s)) {
                                    if (s.startsWith("http:") || s.startsWith("https:")) {
                                        int duration = soundElem.getDuration();
                                        VoiceMsg voiceMsg = new VoiceMsg();
                                        voiceMsg.setMsgId(msgID);
                                        voiceMsg.setNickName(showName);
                                        voiceMsg.setPlaying(false);
                                        voiceMsg.setDuration(duration);
                                        voiceMsg.setAudioPath(Uri.parse(s));
                                        voiceMsg.setType(sendType);
                                        voiceMsg.setMsgType(MsgBean.MsgType.VOICE);
                                        voiceMsg.setSendTime(timeString);
                                        adapter.addMsg(voiceMsg);
                                    }
                                }
                            }
                        });
                    } else if (type == V2TIM_ELEM_TYPE_CUSTOM) {
                        V2TIMCustomElem customElem = message.getCustomElem();
                        byte[] data = customElem.getData();
                        String url = new String(data);
                        ArticleMsg articleMsg = new ArticleMsg();
                        articleMsg.setTitle("测试标题");
                        articleMsg.setDescription("测试内容");
                        articleMsg.setUrl(url);
                        articleMsg.setPicUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fs9.sinaimg.cn%2Fbmiddle%2F5ceba31bg5d6503750788&refer=http%3A%2F%2Fs9.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613876881&t=de4228f0b81e77da48ce399e54f321fc");
                        articleMsg.setSendTime(timeString);
                        articleMsg.setType(1);
                        articleMsg.setMsgType(MsgBean.MsgType.ARTICLE);
                        articleMsg.setSenderId(message.getSender());
                        EventBus.getDefault().post(articleMsg);

                        Log.i("Http请求参数", "onRecvNewMessage: " + customElem.toString());
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
                SharedPreferences sp = getSharedPreferences(Constant.APP_DATA, Context.MODE_PRIVATE);
                sp.edit().putInt(Constant.KEYBOARDHEIGHT, keyboardHeight).apply();
            }

            @Override
            public void onClosed() {

            }
        });

//        recycler_view.setHasFixedSize(true);
        faceUrl = getIntent().getStringExtra("faceUrl");
        adapter = new MsgAdapter(this, msgList, faceUrl);
        adapter.setOnClickImageListener(new MsgAdapter.OnClickImageListener() {
            @Override
            public void onClickImage(ImageView imageView, SparseArray<ImageView> mappingViews, List<String> urlList) {
                if (imageWatcher != null) {
                    imageWatcher.show(imageView, mappingViews, AppUtil.convert(urlList));
                }
            }
        });
//        recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        layout.setReverseLayout(true);
        recycler_view.setLayoutManager(layout);
        recycler_view.setAdapter(adapter);
        recycler_view.addItemDecoration(new PaddingDecoration());
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

        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                View lastChildView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
                int lastChildTop = lastChildView.getTop();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition = layoutManager.getPosition(lastChildView);
                if (lastPosition == adapter.getItemCount() - 1 && lastChildTop == 30 && !getMsgFlag) {
                    getMsgFlag = true;
                    initConversation();
                }
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
        if (!TextUtils.isEmpty(voiceMsg.getSenderId())) {
            if (UserID.equals(voiceMsg.getSenderId())) {
                adapter.addMsgTop(voiceMsg);
                V2TIMManager.getMessageManager().markC2CMessageAsRead(UserID, null);
            }
        } else {
            adapter.addMsgTop(voiceMsg);

            HashMap<String, Object> map = new HashMap<>();
            map.put("msgType", String.valueOf(5));
            map.put("openId", UserID);
            NetUtil.upLoadFile(map, new File(voiceMsg.getAudioPath().getPath()));

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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetImageMsg(ImageMsg imageMsg) {
        if (!TextUtils.isEmpty(imageMsg.getSenderId())) {
            if (UserID.equals(imageMsg.getSenderId())) {
                adapter.addMsgTop(imageMsg);
                V2TIMManager.getMessageManager().markC2CMessageAsRead(UserID, null);
            }
        } else {
            adapter.addMsgTop(imageMsg);

            final String path = imageMsg.getImgPath();

            NetUtil.get().download(ChatActivity.this, path, new NetUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess(final File file) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("msgType", String.valueOf(6));
                    map.put("openId", UserID);
                    NetUtil.upLoadFile(map, file);

                    // 创建图片消息
                    V2TIMMessage v2TIMMessage = V2TIMManager.getMessageManager().createImageMessage(file.getAbsolutePath());
                    // 发送图片消息
                    V2TIMManager.getMessageManager().sendMessage(v2TIMMessage, UserID,
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
                                    List<V2TIMImageElem.V2TIMImage> imageList = v2TIMMessage.getImageElem().getImageList();
                                    for (V2TIMImageElem.V2TIMImage image : imageList) {
                                        String url = image.getUrl();
                                        adapter.notifyImageItem(file.getAbsolutePath(), url);
                                    }
                                    Log.i("12345678", "onSuccess: " + v2TIMMessage.getImageElem().toString());
                                }
                            });
                }

                @Override
                public void onDownloading(int progress) {

                }

                @Override
                public void onDownloadFailed(Exception e) {

                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetArticletMsg(ArticleMsg articleMsg) {
        adapter.addMsgTop(articleMsg);
        if (!TextUtils.isEmpty(articleMsg.getSenderId())) {
            return;
        }
        String picUrl = articleMsg.getPicUrl();
        String title = articleMsg.getTitle();
        String description = articleMsg.getDescription();
        String url = articleMsg.getUrl();

        NewMessageInput input = new NewMessageInput();
        input.setOpenId(UserID);
        input.setTitle(title);
        input.setDescription(description);
        input.setUrl(url);
        input.setPicUrl(picUrl);
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WX_CHETER).postNewMessage(input), new BaseHttp.HttpObserver<NewMessageOutput>() {
            @Override
            public void onSuccess(NewMessageOutput newMessageOutput) {

            }

            @Override
            public void onError() {

            }
        });
        String articleMsgStr = Constant.TEXT_ARTICLE_FLAG + picUrl + "&" + title + "&" + description + "&" + url;
        // 腾讯云发送文字
        V2TIMManager.getInstance().sendC2CTextMessage(articleMsgStr, UserID, new V2TIMValueCallback<V2TIMMessage>() {
            @Override
            public void onError(int code, String desc) {
                Log.i("12345678", "发送出错: " + code + "  " + desc);
            }

            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                Log.i("12345678", "发送成功");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTextMsg(TextMsg textMsg) {
        Log.i("12345678", "onGetTextMsg: " + textMsg.getContent());
        if (!TextUtils.isEmpty(textMsg.getSenderId())) {
            if (UserID.equals(textMsg.getSenderId())) {
                adapter.addMsgTop(textMsg);
                V2TIMManager.getMessageManager().markC2CMessageAsRead(UserID, null);
            }
        } else {
            adapter.addMsgTop(textMsg);

            HashMap<String, Object> map = new HashMap<>();
            map.put("message", textMsg.getContent());
            map.put("msgType", String.valueOf(1));
            map.put("openId", UserID);
            NetUtil.upLoadFile(map, null);

            // 腾讯云发送文字
            V2TIMManager.getInstance().sendC2CTextMessage(textMsg.getContent(), UserID, new V2TIMValueCallback<V2TIMMessage>() {
                @Override
                public void onError(int code, String desc) {
                    Log.i("12345678", "发送出错: " + code + "  " + desc);
                }

                @Override
                public void onSuccess(V2TIMMessage v2TIMMessage) {
                    Log.i("12345678", "发送成功");
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            List<String> imgStrs = Matisse.obtainPathResult(data);
            for (final String path : imgStrs) {
                ImageMsg imageMsg = new ImageMsg();
                imageMsg.setNickName("我");
                imageMsg.setType(1);
                imageMsg.setMsgType(MsgBean.MsgType.IMAGE);
                imageMsg.setImgPath(path);
                imageMsg.setSendTime(AppUtil.getTimeString(new Date().getTime()));
                adapter.addMsgTop(imageMsg);

                Log.i("12345678", "onActivityResult: " + path);

                HashMap<String, Object> map = new HashMap<>();
                map.put("msgType", String.valueOf(6));
                map.put("openId", UserID);
                NetUtil.upLoadFile(map, new File(path));

                // 创建图片消息
                V2TIMMessage v2TIMMessage = V2TIMManager.getMessageManager().createImageMessage(path);
                // 发送图片消息
                V2TIMManager.getMessageManager().sendMessage(v2TIMMessage, UserID,
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
                                List<V2TIMImageElem.V2TIMImage> imageList = v2TIMMessage.getImageElem().getImageList();
                                for (V2TIMImageElem.V2TIMImage image : imageList) {
                                    String url = image.getUrl();
                                    adapter.notifyImageItem(path, url);
                                }
                                Log.i("12345678", "onSuccess: " + v2TIMMessage.getImageElem().toString());
                            }
                        });
            }
        } else if (requestCode == 1011 && resultCode == RESULT_OK) {
            addedWx = data.getBooleanExtra("addedWx", false);
        }
    }

    @Override
    public void finish() {
        //EventBus.getDefault().post(Constant.REFRESH_CONVERSATION);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        chat_input_panel.onActivityDestory();
        keyboardHelper.release();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        menu.add(Menu.NONE, 1, 0, "搜索常用语");
//        menu.add(Menu.NONE, 2, 0, "搜索语音");
//        menu.add(Menu.NONE, 3, 0, "搜索文章库");
//        menu.add(Menu.NONE, 4, 0, "搜索产品库");
        getMenuInflater().inflate(R.menu.exchange_biaoqian, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else if (item.getItemId() == R.id.menu_biao) {
            Intent intent = new Intent(this, CustomerInfoActivity.class);
            intent.putExtra("openId", UserID);
            intent.putExtra("showName", showName);
            intent.putExtra("addedWx", addedWx);
            startActivityForResult(intent, 1011);
            return true;
        }
        Intent intent = new Intent(this, SearchActivity.class);
        switch (item.getItemId()) {
            case 1:
                intent.putExtra("searchType", SearchActivity.search_chang);
                break;
            case 2:
                intent.putExtra("searchType", SearchActivity.search_yuyin);
                break;
            case 3:
                intent.putExtra("searchType", SearchActivity.search_wenzhang);
                break;
            case 4:
                intent.putExtra("searchType", SearchActivity.search_caiping);
                break;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}
