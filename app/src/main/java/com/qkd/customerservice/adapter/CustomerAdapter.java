package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.activity.ChatActivity;
import com.qkd.customerservice.bean.ConversationBean;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMTextElem;

import java.util.ArrayList;
import java.util.List;

import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE;
import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_SOUND;
import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_TEXT;

/**
 * Created on 12/2/20 13:50
 * .
 *
 * @author yj
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ConversationBean> conversationList;
    private RequestOptions options;

    public CustomerAdapter(Context context) {
        this.context = context;
        conversationList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        RoundedCorners roundedCorners = new RoundedCorners(4);
        options = new RequestOptions()
                .transform(new CenterCrop(), roundedCorners)
                .error(R.drawable.ic_head_place)
                .placeholder(R.drawable.ic_head_place);
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, final int position) {
        final ConversationBean conversation = conversationList.get(position);
        holder.mNameV.setText(conversation.getShowName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("UserID", conversation.getUserId());
                intent.putExtra("showName", conversation.getShowName());
                intent.putExtra("faceUrl", conversation.getFaceUrl());
                context.startActivity(intent);
//                conversation.setHasUnread(false);
                notifyItemChanged(position);
            }
        });

        Glide.with(context)
                .load(conversation.getFaceUrl())
                .apply(options)
                .into(holder.mHeadV);
        if (conversation.getUnreadCount() == 0) {
            holder.readV.setVisibility(View.GONE);
        } else {
            holder.readV.setVisibility(View.VISIBLE);
            holder.readV.setText(String.valueOf(conversation.getUnreadCount()));
        }

        V2TIMMessage lastMessage = conversation.getLastMessage();
        if (lastMessage == null) {
            holder.lastTime.setVisibility(View.GONE);
            holder.lastMsg.setVisibility(View.GONE);
        } else {
            holder.lastMsg.setVisibility(View.VISIBLE);
            holder.lastTime.setVisibility(View.VISIBLE);
            int type = lastMessage.getElemType();
            long timestamp = lastMessage.getTimestamp();
            String timeString = AppUtil.getTimeString(timestamp * 1000L);
            holder.lastTime.setText(timeString);
            if (type == V2TIM_ELEM_TYPE_TEXT) {
                V2TIMTextElem textElem = lastMessage.getTextElem();
                String text = textElem.getText();
                if (!TextUtils.isEmpty(text)) {
                    if (text.startsWith(Constant.TEXT_ARTICLE_FLAG)) {
                        String[] strings = text.replace(Constant.TEXT_ARTICLE_FLAG, "").split("&");
                        holder.lastMsg.setText("[链接]" + strings[1]);
                    } else {
                        holder.lastMsg.setText(text);
                    }
                } else {
                    holder.lastMsg.setText(text);
                }
            } else if (type == V2TIM_ELEM_TYPE_IMAGE) {
                holder.lastMsg.setText("[图片]");
            } else if (type == V2TIM_ELEM_TYPE_SOUND) {
                holder.lastMsg.setText("[语音]");
            } else {
                holder.lastTime.setVisibility(View.GONE);
                holder.lastMsg.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    public void addAll(List<ConversationBean> conversationList) {
        this.conversationList.clear();
        this.conversationList.addAll(conversationList);
        notifyDataSetChanged();
    }

    public List<ConversationBean> getConversationList() {
        return conversationList;
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHeadV;
        private TextView mNameV;
        private TextView readV;
        private TextView lastMsg;
        private TextView lastTime;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.customer_head);
            mNameV = itemView.findViewById(R.id.customer_name);
            readV = itemView.findViewById(R.id.customer_read);
            lastMsg = itemView.findViewById(R.id.customer_last_msg);
            lastTime = itemView.findViewById(R.id.customer_last_time);
        }
    }
}
