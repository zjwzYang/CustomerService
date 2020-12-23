package com.qkd.customerservice.fragment;

import android.os.Bundle;
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

import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.CustomerAdapter;
import com.qkd.customerservice.bean.ConversationBean;
import com.qkd.customerservice.bean.ImageMsg;
import com.qkd.customerservice.bean.TextMsg;
import com.qkd.customerservice.bean.VoiceMsg;
import com.qkd.customerservice.dialog.PlannerDialog;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMConversationResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/2/20 10:07
 * .
 *
 * @author yj
 */
public class MsgFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CustomerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        EventBus.getDefault().register(this);
        mRecyclerView = view.findViewById(R.id.msg_recy);
        mAdapter = new CustomerAdapter(getContext());
        mAdapter.setOnLongClickListener(new CustomerAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(ConversationBean conversation) {
                PlannerDialog plannerDialog = new PlannerDialog();
                String userId = conversation.getUserId();
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                plannerDialog.setArguments(bundle);
                plannerDialog.show(getChildFragmentManager(), "plannerDialog");
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
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
                for (V2TIMConversation conversation : conversationList) {
                    Log.i("12345678", "会话: " + conversation.getShowName() + "  " + conversation.getUserID());
                    ConversationBean conversationBean = new ConversationBean(conversation);
                    conversationBeans.add(conversationBean);
                }
                mAdapter.addAll(conversationBeans);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetVoiceMsg(VoiceMsg voiceMsg) {
        String senderId = voiceMsg.getSenderId();
        //checkUnread(senderId);
        getConversation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetImageMsg(ImageMsg imageMsg) {
        String senderId = imageMsg.getSenderId();
//        checkUnread(senderId);
        getConversation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTextMsg(TextMsg textMsg) {
//        checkUnread(textMsg.getSenderId());
        getConversation();
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
