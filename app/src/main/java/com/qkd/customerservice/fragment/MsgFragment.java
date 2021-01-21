package com.qkd.customerservice.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.CustomerAdapter;
import com.qkd.customerservice.bean.ConversationBean;
import com.qkd.customerservice.bean.ImageMsg;
import com.qkd.customerservice.bean.TextMsg;
import com.qkd.customerservice.bean.VoiceMsg;
import com.qkd.customerservice.bean.WxchatListOutput;
import com.qkd.customerservice.dialog.OptionDialog;
import com.qkd.customerservice.dialog.PlannerDialog;
import com.qkd.customerservice.net.BaseHttp;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMConversationResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 12/2/20 10:07
 * .
 *
 * @author yj
 */
public class MsgFragment extends Fragment implements OptionDialog.OnClickOptionsListener {

    private RecyclerView mRecyclerView;
    private CustomerAdapter mAdapter;
    private String identifier;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            getConversation();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        EventBus.getDefault().register(this);
        mRecyclerView = view.findViewById(R.id.msg_recy);
        mAdapter = new CustomerAdapter(getContext());
        mAdapter.setOnLongClickListener(new CustomerAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(ConversationBean conversation, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", conversation.getUserId());
                bundle.putString("conversationID", conversation.getConversationID());
                bundle.putInt("clickPosition", position);
                bundle.putBoolean("topFlag", conversation.isTopFlag());
                OptionDialog optionDialog = new OptionDialog();
                optionDialog.setArguments(bundle);
                optionDialog.setOnClickOptionsListener(MsgFragment.this);
                optionDialog.show(getChildFragmentManager(), "optionDialog");
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        identifier = sp.getString(Constant.USER_IDENTIFIER, "");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getConversation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getConversation() {
        V2TIMManager.getConversationManager().getConversationList(0, 100, new V2TIMValueCallback<V2TIMConversationResult>() {
            @Override
            public void onError(int code, String desc) {
                Log.i("12345678", "拉取会话列表出错: " + code + "  " + desc);
            }

            @Override
            public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                List<V2TIMConversation> conversationList = v2TIMConversationResult.getConversationList();
                List<ConversationBean> conversationBeans = new ArrayList<>();
                int unreadNum = 0;
                List<String> userIDList = new ArrayList<>();
                for (V2TIMConversation conversation : conversationList) {
                    if (conversation.getUnreadCount() > 0) {
                        unreadNum++;
                    }
                    userIDList.add(conversation.getUserID());
                    //Log.i("12345678", "会话: " + conversation.getShowName() + "  " + conversation.getUserID());
                    ConversationBean conversationBean = new ConversationBean(conversation);
                    conversationBeans.add(conversationBean);
                }
                if (unreadNum >= 3) {
                    EventBus.getDefault().post(Constant.UPDATE_USER_STATUS);
                }
                SharedPreferences sp = getContext().getSharedPreferences(Constant.SORT_FLAG, Context.MODE_PRIVATE);
                String tops = sp.getString(Constant.SORT_TOP + "_" + identifier, "");
                String deletes = sp.getString(Constant.DELETE_USERID + "_" + identifier, "");
                if (!TextUtils.isEmpty(tops) || !TextUtils.isEmpty(deletes)) {
                    String[] topList = tops.split("/");
                    String[] deleteA = deletes.split("/");
                    for (int i = 0; i < conversationBeans.size(); i++) {
                        ConversationBean bean = conversationBeans.get(i);
                        if (Arrays.asList(deleteA).contains(bean.getUserId())) {
                            conversationBeans.remove(i);
                            continue;
                        }
                        if (Arrays.asList(topList).contains(bean.getUserId())) {
                            bean.setTopFlag(true);
                            conversationBeans.remove(i);
                            conversationBeans.add(0, bean);
                        }
                    }
//                    for (int i = 0; i < topList.length; i++) {
//                        String top = topList[i];
//                        if (!TextUtils.isEmpty(top)) {
//                            for (int j = 0; j < conversationBeans.size(); j++) {
//                                ConversationBean bean = conversationBeans.get(j);
//                                if (top.equals(bean.getUserId())) {
//                                    conversationBeans.remove(j);
//                                    conversationBeans.add(0, bean);
//                                    break;
//                                }
//                            }
//                        }
//                    }
                }
                mAdapter.addAll(conversationBeans);

//                V2TIMManager.getInstance().getUsersInfo(userIDList, new V2TIMValueCallback<List<V2TIMUserFullInfo>>() {
//                    @Override
//                    public void onError(int code, String desc) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(List<V2TIMUserFullInfo> v2TIMUserFullInfos) {
//                        for (V2TIMUserFullInfo info : v2TIMUserFullInfos) {
//                            String userID = info.getUserID();
//                            HashMap<String, byte[]> customInfo = info.getCustomInfo();
//                            Set<Map.Entry<String, byte[]>> entries = customInfo.entrySet();
//                            for (Map.Entry<String, byte[]> entry : entries) {
//                                byte[] value = entry.getValue();
//                                String wxFlag = new String(value);
//                                if ("1".equals(wxFlag)) {
//                                    mAdapter.setWxAdd(userID);
//                                }
//                            }
//                        }
//                    }
//                });
                initWx();
            }
        });
    }

    private void initWx() {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).getAddWechat(identifier), new BaseHttp.HttpObserver<WxchatListOutput>() {
            @Override
            public void onSuccess(WxchatListOutput output) {
                if (output.isSuccess()) {
                    for (WxchatListOutput.DataBean bean : output.getData()) {
                        mAdapter.setWxAdd(bean.getOpenId(), bean.getIsAddWechat());
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshMsg(String msg) {
        if (Constant.REFRESH_CONVERSATION.equals(msg)) {
            getConversation();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetVoiceMsg(VoiceMsg voiceMsg) {
        mHandler.sendMessageDelayed(Message.obtain(), 500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetImageMsg(ImageMsg imageMsg) {
        mHandler.sendMessageDelayed(Message.obtain(), 500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTextMsg(TextMsg textMsg) {
        mHandler.sendMessageDelayed(Message.obtain(), 500);
    }

    @Override
    public void onClickOptionOne(int clickPosition, String userId) {
        if (clickPosition < 0) {
            return;
        }
        mAdapter.refreshTop(clickPosition);
        SharedPreferences sp = getContext().getSharedPreferences(Constant.SORT_FLAG, Context.MODE_PRIVATE);
        String tops = sp.getString(Constant.SORT_TOP + "_" + identifier, "");
        tops = tops + "/" + userId;
        sp.edit().putString(Constant.SORT_TOP + "_" + identifier, tops).apply();
    }

    @Override
    public void onClickOptionTwo(String userId, String conversationID) {
        PlannerDialog plannerDialog = new PlannerDialog();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putString("conversationID", conversationID);
        plannerDialog.setArguments(bundle);
        plannerDialog.show(getChildFragmentManager(), "plannerDialog");
    }

    @Override
    public void onClickOptionThree(int clickPosition, String userId) {
        if (clickPosition < 0) {
            return;
        }
        mAdapter.remove(clickPosition);
        SharedPreferences sp = getContext().getSharedPreferences(Constant.SORT_FLAG, Context.MODE_PRIVATE);
        String deletes = sp.getString(Constant.DELETE_USERID + "_" + identifier, "");
        deletes = deletes + "/" + userId;
        sp.edit().putString(Constant.DELETE_USERID + "_" + identifier, deletes).apply();
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void clearUnreadBuUserId(ClearByUserId bean) {
//        List<ConversationBean> conversationList = mAdapter.getConversationList();
//        for (int i = 0; i < conversationList.size(); i++) {
//            ConversationBean conversationBean = conversationList.get(i);
//            if (bean.getUserId().equals(conversationBean.getUserId())) {
//                conversationBean.setHasUnread(false);
//                mAdapter.notifyItemChanged(i);
//            }
//        }
//    }
//
//    private void checkUnread(String senderId) {
//        List<ConversationBean> conversationList = mAdapter.getConversationList();
//        for (int i = 0; i < conversationList.size(); i++) {
//            ConversationBean bean = conversationList.get(i);
//            if (bean.getUserId().equals(senderId)) {
//                bean.setHasUnread(true);
//                mAdapter.notifyItemChanged(i);
//                break;
//            }
//        }
//    }
}
