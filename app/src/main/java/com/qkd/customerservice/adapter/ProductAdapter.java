package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.ArticleMsg;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.ProductOutput;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 12/15/20 09:25
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ArticleViewHolder> {

    private Context context;
    private List<ProductOutput.DataBean> dataList;

    public ProductAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        final ProductOutput.DataBean dataBean = dataList.get(position);
        final String productLeftUrl = dataBean.getProductLeftUrl();
        Glide.with(context)
                .load(productLeftUrl)
                .into(holder.mImageView);
        final String articleTitle = dataBean.getProductName();
        holder.mTitleV.setText(articleTitle);

        final String articleContent = dataBean.getProductContent();
        if (TextUtils.isEmpty(articleContent)) {
            holder.mContentV.setVisibility(View.GONE);
        } else {
            holder.mContentV.setVisibility(View.VISIBLE);
            holder.mContentV.setText(articleContent);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示")
                        .setMessage("确定发送？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("发送", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ArticleMsg articleMsg = new ArticleMsg();
                                articleMsg.setTitle(articleTitle);
                                articleMsg.setDescription(articleContent);
                                articleMsg.setUrl(Constant.BASE_URL3 + "introduce?id=" + dataBean.getId());
                                articleMsg.setPicUrl(productLeftUrl);
                                articleMsg.setSendTime(AppUtil.getTimeString(new Date().getTime()));
                                articleMsg.setType(1);
                                articleMsg.setMsgType(MsgBean.MsgType.ARTICLE);
                                EventBus.getDefault().post(articleMsg);
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addAll(List<ProductOutput.DataBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTitleV;
        private TextView mContentV;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.article_img);
            mTitleV = itemView.findViewById(R.id.article_title);
            mContentV = itemView.findViewById(R.id.article_content);
        }
    }
}