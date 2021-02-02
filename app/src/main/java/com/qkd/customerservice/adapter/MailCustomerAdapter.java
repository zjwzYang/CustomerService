package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qkd.customerservice.R;
import com.qkd.customerservice.activity.ChatActivity;
import com.qkd.customerservice.activity.CustomerInfoActivity;
import com.qkd.customerservice.bean.CustomerBookOutput;
import com.qkd.customerservice.dialog.OptionDialog;
import com.qkd.customerservice.dialog.PlannerDialog;

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
    private FragmentManager manager;

    public MailCustomerAdapter(Context context, FragmentManager manager) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.manager = manager;
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
        final int isAddWechat = dataBean.getIsAddWechat();
        Glide.with(context)
                .load(dataBean.getUserAvatarUrl())
                .apply(options)
                .into(holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomerInfoActivity.class);
                intent.putExtra("openId", dataBean.getOpenId());
                intent.putExtra("showName", dataBean.getUserName());
                if (isAddWechat == 0) {
                    intent.putExtra("addedWx", false);
                } else {
                    intent.putExtra("addedWx", true);
                }
                context.startActivity(intent);
            }
        });
        holder.mTextView.setText(dataBean.getUserName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("UserID", dataBean.getOpenId());
                intent.putExtra("showName", dataBean.getUserName());
                intent.putExtra("faceUrl", dataBean.getUserAvatarUrl());
                if (isAddWechat == 0) {
                    intent.putExtra("addedWx", false);
                } else {
                    intent.putExtra("addedWx", true);
                }
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", dataBean.getOpenId());
                OptionDialog optionDialog = new OptionDialog();
                optionDialog.setArguments(bundle);
                optionDialog.setOnClickOptionsListener(new OptionDialog.OnClickOptionsListener() {
                    @Override
                    public void onClickOptionOne(int clickPosition, String userId) {

                    }

                    @Override
                    public void onClickOptionTwo(String userId, String conversationID) {
                        PlannerDialog plannerDialog = new PlannerDialog();
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", userId);
                        bundle.putString("conversationID", conversationID);
                        plannerDialog.setArguments(bundle);
                        plannerDialog.show(manager, "plannerDialog");
                    }

                    @Override
                    public void onClickOptionThree(int clickPosition, String userId) {

                    }
                });
                optionDialog.show(manager, "optionDialog");
                return false;
            }
        });
        int userStatus = dataBean.getUserStatus();
        if (userStatus == 1) {
            holder.mStatusV.setVisibility(View.VISIBLE);
            holder.mStatusV.setText("关注");
        } else if (userStatus == 2) {
            holder.mStatusV.setVisibility(View.VISIBLE);
            holder.mStatusV.setText("未关注");
        } else {
            holder.mStatusV.setVisibility(View.GONE);
        }
        String remark = dataBean.getRemark();
        if (TextUtils.isEmpty(remark)) {
            holder.mRemarkV.setVisibility(View.INVISIBLE);
        } else {
            holder.mRemarkV.setVisibility(View.VISIBLE);
            holder.mRemarkV.setText("备注：" + remark);
        }
        if (isAddWechat == 1) {
            holder.mWxImg.setVisibility(View.VISIBLE);
        } else {
            holder.mWxImg.setVisibility(View.GONE);
        }
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
        private TextView mStatusV;
        private TextView mRemarkV;
        private ImageView mWxImg;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.mail_customer_head);
            mTextView = itemView.findViewById(R.id.mail_customer_name);
            mStatusV = itemView.findViewById(R.id.mail_customer_status);
            mRemarkV = itemView.findViewById(R.id.mail_customer_remark);
            mWxImg = itemView.findViewById(R.id.mail_customer_wx_added);
        }
    }
}