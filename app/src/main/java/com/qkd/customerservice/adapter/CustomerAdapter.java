package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.activity.ChatActivity;
import com.qkd.customerservice.activity.CustomerInfoActivity;
import com.qkd.customerservice.bean.ConversationBean;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMTextElem;

import java.util.ArrayList;
import java.util.List;

import static com.tencent.imsdk.v2.V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM;
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
    private OnLongClickListener onLongClickListener;

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
        if (conversation.isTopFlag()) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.app_backgroud));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
        holder.mNameV.setText(conversation.getShowName());
        holder.customer_item_rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("UserID", conversation.getUserId());
                intent.putExtra("showName", conversation.getShowName());
                intent.putExtra("faceUrl", conversation.getFaceUrl());
                intent.putExtra("addedWx", conversation.isAddedWx());
                context.startActivity(intent);
//                conversation.setHasUnread(false);
                notifyItemChanged(position);
            }
        });
        // 长按
        holder.customer_item_rela.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onLongClickListener != null) {
                    onLongClickListener.onLongClick(conversation, position);
                }
                return true;
            }
        });

        holder.customer_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLongClickListener != null) {
                    onLongClickListener.onDelete(conversation, position);
                }
            }
        });

        Glide.with(context)
                .load(conversation.getFaceUrl())
                .apply(options)
                .into(holder.mHeadV);
        holder.mHeadV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomerInfoActivity.class);
                intent.putExtra("openId", conversation.getUserId());
                intent.putExtra("showName", conversation.getShowName());
                intent.putExtra("addedWx", conversation.isAddedWx());
                context.startActivity(intent);
            }
        });
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
            Log.i("Http请求参数", "onBindViewHolder: type:" + type);
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
            } else if (type == V2TIM_ELEM_TYPE_CUSTOM) {
                holder.lastMsg.setText("[链接]");
            } else {
                holder.lastTime.setVisibility(View.GONE);
                holder.lastMsg.setVisibility(View.GONE);
            }
        }

        if (conversation.isAddedWx()) {
            holder.wxAddFlag.setVisibility(View.VISIBLE);
        } else {
            holder.wxAddFlag.setVisibility(View.GONE);
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

    public void refreshTop(int topIndex) {
        ConversationBean topBean = this.conversationList.get(topIndex);
        topBean.setTopFlag(true);
        this.conversationList.remove(topIndex);
        this.conversationList.add(0, topBean);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        this.conversationList.remove(index);
        notifyItemRemoved(index);
        if (index != conversationList.size()) {
            notifyItemRangeChanged(index, conversationList.size() - index);
        }
    }

    public void setWxAdd(String userId, int isAddWechat) {
        if (TextUtils.isEmpty(userId)) {
            return;
        }
        for (int i = 0; i < this.conversationList.size(); i++) {
            ConversationBean bean = this.conversationList.get(i);
            if (userId.equals(bean.getUserId())) {
                if (isAddWechat == 0) {
                    bean.setAddedWx(false);
                } else {
                    bean.setAddedWx(true);
                }
                notifyItemChanged(i);
                break;
            }
        }
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
        private ImageView wxAddFlag;
        private TextView customer_delete;
        private RelativeLayout customer_item_rela;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.customer_head);
            mNameV = itemView.findViewById(R.id.customer_name);
            readV = itemView.findViewById(R.id.customer_read);
            lastMsg = itemView.findViewById(R.id.customer_last_msg);
            lastTime = itemView.findViewById(R.id.customer_last_time);
            wxAddFlag = itemView.findViewById(R.id.customer_wx_added);
            customer_delete = itemView.findViewById(R.id.customer_delete);
            customer_item_rela = itemView.findViewById(R.id.customer_item_rela);
        }
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public interface OnLongClickListener {
        void onLongClick(ConversationBean conversation, int position);

        void onDelete(ConversationBean conversation, int position);
    }
}
