package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.qkd.customerservice.R;
import com.qkd.customerservice.activity.ChatActivity;
import com.qkd.customerservice.bean.ConversationBean;

import java.util.ArrayList;
import java.util.List;

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
                .error(R.drawable.ic_image_error)
                .placeholder(R.mipmap.ic_launcher);
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
                conversation.setHasUnread(false);
                notifyItemChanged(position);
            }
        });

        Glide.with(context)
                .load(conversation.getFaceUrl())
                .apply(options)
                .into(holder.mHeadV);
        if (conversation.isHasUnread()) {
            holder.readV.setVisibility(View.VISIBLE);
        } else {
            holder.readV.setVisibility(View.GONE);
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
        private ImageView readV;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.customer_head);
            mNameV = itemView.findViewById(R.id.customer_name);
            readV = itemView.findViewById(R.id.customer_read);
        }
    }
}
