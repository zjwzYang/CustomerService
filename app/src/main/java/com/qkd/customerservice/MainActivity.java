package com.qkd.customerservice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.activity.ChatActivity;
import com.qkd.customerservice.adapter.MainImgAdapter;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMImageElem;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMUserInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import java.util.List;

import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE;
import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_SOUND;
import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_TEXT;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MainImgAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recy_img);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MainImgAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onLogin(View view) {
        String userId = "test_yang";
        String userSig = "eJw1jtEKgjAYRt9lt4b9bnPloCsvuikrFCIIYrQ-W5oNXWFF755oXX7n48B5k2yR*thaUyORLAiZAIBRTx9YE0moD2TYjS6UtUYTGXAAHgrK2fAYjZUzJ9MLDht3eKoq-2sm7yi-LF8Jnuk6Pirrab0Tq7YsynQ7vyncj7N7UkQJZVzF3mb2M525dlGBADGJGGXTzxdmszNJ";
        V2TIMManager.getInstance().login(userId, userSig, new V2TIMCallback() {
            @Override
            public void onError(int code, String desc) {
                Log.i("12345678", "登录出错: " + code + "  " + desc);
            }

            @Override
            public void onSuccess() {
                Log.i("12345678", "登录成功");
            }
        });
        V2TIMManager.getInstance().addSimpleMsgListener(new V2TIMSimpleMsgListener() {
            @Override
            public void onRecvC2CTextMessage(String msgID, V2TIMUserInfo sender, String text) {
                super.onRecvC2CTextMessage(msgID, sender, text);
                Log.i("12345678", "msgID: " + msgID + "   text:" + text);
            }

            @Override
            public void onRecvC2CCustomMessage(String msgID, V2TIMUserInfo sender, byte[] customData) {
                super.onRecvC2CCustomMessage(msgID, sender, customData);
            }

            @Override
            public void onRecvGroupTextMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, String text) {
                super.onRecvGroupTextMessage(msgID, groupID, sender, text);
            }

            @Override
            public void onRecvGroupCustomMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, byte[] customData) {
                super.onRecvGroupCustomMessage(msgID, groupID, sender, customData);
            }
        });
    }

    public void onSend(View view) {
        V2TIMManager.getInstance().sendC2CTextMessage("hi,guan", "test_guan", new V2TIMValueCallback<V2TIMMessage>() {
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

    public void onHistoryMsg(View view) {
        V2TIMManager.getMessageManager().getC2CHistoryMessageList("oMRUWsz5RBAxLanlU1ITgWBEGd6I", 10, null, new V2TIMValueCallback<List<V2TIMMessage>>() {
            @Override
            public void onError(int code, String desc) {
                Log.i("12345678", "发送出错: " + code + "  " + desc);
            }

            @Override
            public void onSuccess(List<V2TIMMessage> v2TIMMessages) {
//                Log.i("12345678", "历史数据");
                for (int i = 0; i < v2TIMMessages.size(); i++) {
                    V2TIMMessage message = v2TIMMessages.get(i);
                    int type = message.getElemType();
                    if (type == V2TIM_ELEM_TYPE_TEXT) {
                        Log.i("12345678", "HistoryMessage: " + message.getTextElem().toString());
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
                            mAdapter.add(url);
                        }
                    } else if (type == V2TIM_ELEM_TYPE_SOUND) {
                        Log.i("12345678", "HistoryMessage: " + message.getSoundElem().toString());
                    }
                }
            }
        });
    }

    public void goToChat(View view) {
        startActivity(new Intent(this, ChatActivity.class));
    }
}