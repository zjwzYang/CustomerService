package com.qkd.customerservice.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qkd.customerservice.R;
import com.qkd.customerservice.activity.CustomizedActivity;
import com.qkd.customerservice.bean.QueryCustomizeOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/17/20 15:12
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CustomizeAdapter extends RecyclerView.Adapter<CustomizeAdapter.CustomizeViewHolder> {
    private Context context;
    private List<QueryCustomizeOutput.DataBean.ListBean> dataList;
    private RequestOptions options;
    private int userStatus;

    public CustomizeAdapter(Context context, int userStatus) {
        this.context = context;
        this.userStatus = userStatus;
        this.dataList = new ArrayList<>();
        RoundedCorners roundedCorners = new RoundedCorners(4);
        options = new RequestOptions()
                .transform(new CenterCrop(), roundedCorners)
                .error(R.drawable.ic_head_place)
                .placeholder(R.drawable.ic_head_place);
    }

    @NonNull
    @Override
    public CustomizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mail_customer, parent, false);
        return new CustomizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomizeViewHolder holder, int position) {
        final QueryCustomizeOutput.DataBean.ListBean bean = dataList.get(position);
        Glide.with(context)
                .load(bean.getUserAvatarUrl())
                .apply(options)
                .into(holder.mImageView);
        holder.mTextView.setText(bean.getUserName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomizedActivity.class);
                intent.putExtra("orderNumber", bean.getOrderNumber());
                intent.putExtra("userId", bean.getUserId());
                intent.putExtra("userStatus", userStatus);
                context.startActivity(intent);
            }
        });
        if (bean.getRed() == 1) {
            holder.flagImg.setImageResource(R.drawable.ic_birthday);
            holder.flagImg.setVisibility(View.VISIBLE);
        } else {
            holder.flagImg.setVisibility(View.GONE);
        }
        if (userStatus == 3) {
            holder.mCopyV.setVisibility(View.GONE);
        } else {
            if (holder.mCopyV.getVisibility() == View.GONE) {
                holder.mCopyV.setVisibility(View.VISIBLE);
            }
            holder.mCopyV.setText("复制链接");
            holder.mCopyV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "http://qukanbao.qukandian573.com/programme?orderNumber="
                            + bean.getOrderNumber() + "&userId=" + bean.getUserId();
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", url);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addAll(List<QueryCustomizeOutput.DataBean.ListBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clear() {
        this.dataList.clear();
    }

    static class CustomizeViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
        private TextView mCopyV;
        private ImageView flagImg;

        public CustomizeViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.mail_customer_head);
            mTextView = itemView.findViewById(R.id.mail_customer_name);
            mCopyV = itemView.findViewById(R.id.mail_customer_status);
            flagImg = itemView.findViewById(R.id.mail_customer_wx_added);
        }
    }
}