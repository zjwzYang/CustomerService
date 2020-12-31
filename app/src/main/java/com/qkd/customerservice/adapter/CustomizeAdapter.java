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
import com.qkd.customerservice.activity.CustomizedActivity;
import com.qkd.customerservice.bean.QueryCustomizeOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/17/20 15:12
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class CustomizeAdapter extends RecyclerView.Adapter<CustomizeAdapter.CustomizeViewHolder> {
    private Context context;
    private List<QueryCustomizeOutput.DataBean.ListBean> dataList;
    private RequestOptions options;

    public CustomizeAdapter(Context context) {
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
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addAll(List<QueryCustomizeOutput.DataBean.ListBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    static class CustomizeViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public CustomizeViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.mail_customer_head);
            mTextView = itemView.findViewById(R.id.mail_customer_name);
        }
    }
}