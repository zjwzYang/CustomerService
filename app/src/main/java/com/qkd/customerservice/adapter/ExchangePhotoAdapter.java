package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.KnowledgeOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1/15/21 13:32
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class ExchangePhotoAdapter extends RecyclerView.Adapter<ExchangePhotoAdapter.PhotoUsedViewHolder> {

    private Context context;
    private List<KnowledgeOutput.DataBean.ListBean> dataList;
    private RequestOptions mOptions;

    public ExchangePhotoAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        mOptions = new RequestOptions()
                .error(R.drawable.bg_defaule_img)
                .placeholder(R.drawable.bg_defaule_img);
    }

    @NonNull
    @Override
    public PhotoUsedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exchange_photo, parent, false);
        return new PhotoUsedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoUsedViewHolder holder, final int position) {
        final KnowledgeOutput.DataBean.ListBean bean = dataList.get(position);
        Glide.with(context)
                .load(bean.getMediaContent())
                .apply(mOptions)
                .into(holder.mImageView);
        holder.mTextView.setText(bean.getMediaPurpose());
        holder.mPhotoDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示")
                        .setMessage("确定删除？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (onClickDeleteListener != null) {
                                    onClickDeleteListener.onDelete(bean.getId(), position);
                                }
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

    public void addAll(List<KnowledgeOutput.DataBean.ListBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clear() {
        this.dataList.clear();
    }


    public void delete(int position) {
        this.dataList.remove(position);
        notifyItemRemoved(position);
        if (position != dataList.size()) {
            notifyItemRangeChanged(position, dataList.size() - position);
        }
    }

    static class PhotoUsedViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
        private ImageView mPhotoDele;

        public PhotoUsedViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.used_photo_img);
            mTextView = itemView.findViewById(R.id.used_photo_text);
            mPhotoDele = itemView.findViewById(R.id.exchange_photo_delete);
        }
    }

    private ExchangeTextAdapter.OnClickDeleteListener onClickDeleteListener;

    public void setOnClickDeleteListener(ExchangeTextAdapter.OnClickDeleteListener onClickDeleteListener) {
        this.onClickDeleteListener = onClickDeleteListener;
    }
}