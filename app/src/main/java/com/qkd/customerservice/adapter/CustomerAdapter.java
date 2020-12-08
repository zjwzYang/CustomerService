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
import com.qkd.customerservice.R;
import com.qkd.customerservice.activity.ChatActivity;
import com.tencent.imsdk.v2.V2TIMConversation;

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
    private List<V2TIMConversation> conversationList;

    public CustomerAdapter(Context context) {
        this.context = context;
        conversationList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        final V2TIMConversation conversation = conversationList.get(position);
        holder.mNameV.setText(conversation.getShowName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("UserID", conversation.getUserID());
                intent.putExtra("showName",conversation.getShowName());
                context.startActivity(intent);
            }
        });
        Glide.with(context)
                .load(conversation.getFaceUrl())
                .into(holder.mHeadV);
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    public void addAll(List<V2TIMConversation> conversationList) {
        this.conversationList.addAll(conversationList);
        notifyDataSetChanged();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mHeadV;
        private TextView mNameV;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeadV = itemView.findViewById(R.id.customer_head);
            mNameV = itemView.findViewById(R.id.customer_name);
        }
    }
}
