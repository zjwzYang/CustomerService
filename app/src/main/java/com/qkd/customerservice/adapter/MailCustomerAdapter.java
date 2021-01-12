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
import com.qkd.customerservice.bean.CustomerBookOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/17/20 08:48
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class MailCustomerAdapter extends RecyclerView.Adapter<MailCustomerAdapter.MailViewHolder> {

    private Context context;
    private List<CustomerBookOutput.DataBean> dataList;
    private RequestOptions options;

    public MailCustomerAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        RoundedCorners roundedCorners = new RoundedCorners(4);
        options = new RequestOptions()
                .transform(new CenterCrop(), roundedCorners)
                .error(R.drawable.ic_head_place)
                .placeholder(R.drawable.ic_head_place);
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mail_customer, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MailViewHolder holder, int position) {
        final CustomerBookOutput.DataBean dataBean = dataList.get(position);
        Glide.with(context)
                .load(dataBean.getUserAvatarUrl())
                .apply(options)
                .into(holder.mImageView);
        holder.mTextView.setText(dataBean.getUserName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("UserID", dataBean.getOpenId());
                intent.putExtra("showName", dataBean.getUserName());
                intent.putExtra("faceUrl", dataBean.getUserAvatarUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addAll(List<CustomerBookOutput.DataBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    static class MailViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.mail_customer_head);
            mTextView = itemView.findViewById(R.id.mail_customer_name);
        }
    }
}