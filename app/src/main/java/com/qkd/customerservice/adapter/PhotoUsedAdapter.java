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
import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.ImageMsg;
import com.qkd.customerservice.bean.KnowledgeOutput;
import com.qkd.customerservice.bean.MsgBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 1/15/21 13:32
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class PhotoUsedAdapter extends RecyclerView.Adapter<PhotoUsedAdapter.PhotoUsedViewHolder> {

    private Context context;
    private List<KnowledgeOutput.DataBean.ListBean> dataList;
    private RequestOptions mOptions;

    public PhotoUsedAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        mOptions = new RequestOptions()
                .error(R.drawable.bg_defaule_img)
                .placeholder(R.drawable.bg_defaule_img);
    }

    @NonNull
    @Override
    public PhotoUsedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo_used, parent, false);
        return new PhotoUsedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoUsedViewHolder holder, int position) {
        final KnowledgeOutput.DataBean.ListBean bean = dataList.get(position);
        Glide.with(context)
                .load(bean.getMediaContent())
                .apply(mOptions)
                .into(holder.mImageView);
        holder.mTextView.setText(bean.getMediaPurpose());
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
                                ImageMsg imageMsg = new ImageMsg();
                                imageMsg.setNickName("我");
                                imageMsg.setType(1);
                                imageMsg.setMsgType(MsgBean.MsgType.IMAGE);
                                imageMsg.setImgPath(bean.getMediaContent());
                                imageMsg.setSendTime(AppUtil.getTimeString(new Date().getTime()));
                                EventBus.getDefault().post(imageMsg);
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
        notifyDataSetChanged();
    }

    static class PhotoUsedViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public PhotoUsedViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.used_photo_img);
            mTextView = itemView.findViewById(R.id.used_photo_text);
        }
    }
}