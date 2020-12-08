package com.qkd.customerservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qkd.customerservice.MainActivity;
import com.qkd.customerservice.R;
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMDownloadCallback;
import com.tencent.imsdk.v2.V2TIMElem;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMImageElem;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageReceipt;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMUserInfo;

import java.io.File;
import java.util.List;

import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE;
import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_SOUND;
import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_TEXT;

/**
 * Created on 12/2/20 09:10
 * 登录.
 *
 * @author yj
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");
        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, IndexActivity.class));
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
                V2TIMManager.getMessageManager().addAdvancedMsgListener(new V2TIMAdvancedMsgListener() {
                    @Override
                    public void onRecvNewMessage(V2TIMMessage message) {
                        super.onRecvNewMessage(message);
                        int type = message.getElemType();
                        if (type == V2TIM_ELEM_TYPE_TEXT) {
                            Log.i("12345678", "HistoryMessage文本: " + message.getTextElem().toString());
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
                                // 设置图片下载路径 imagePath，这里可以用 uuid 作为标识，避免重复下载
                                String imagePath = "/sdcard/im/image/" + "myUserID" + uuid;
                                File imageFile = new File(imagePath);
                                if (imageFile.exists()) {
                                    v2TIMImage.downloadImage(imagePath, new V2TIMDownloadCallback() {
                                        @Override
                                        public void onProgress(V2TIMElem.V2ProgressInfo progressInfo) {
                                            // 图片下载进度：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                                            Log.i("12345678", "onProgress进度: " + progressInfo.getCurrentSize() + "/" + progressInfo.getTotalSize());
                                        }

                                        @Override
                                        public void onError(int code, String desc) {
                                            // 图片下载失败
                                        }

                                        @Override
                                        public void onSuccess() {
                                            // 图片下载完成
                                            Log.i("12345678", "onSuccess: 下载完成");
                                        }
                                    });
                                } else {
                                    // 图片已存在
                                }
                            }

                        } else if (type == V2TIM_ELEM_TYPE_SOUND) {
                            Log.i("12345678", "HistoryMessage语音: " + message.getSoundElem().toString());
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
        });
        findViewById(R.id.history_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }
}
