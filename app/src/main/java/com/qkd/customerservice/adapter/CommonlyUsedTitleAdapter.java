package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.KnowledgeOutput;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.TextMsg;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 12/14/20 14:11
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CommonlyUsedTitleAdapter extends RecyclerView.Adapter<CommonlyUsedTitleAdapter.CommonlyUsedViewHolder> {

    private Context context;
    private List<KnowledgeOutput.DataBean.ListBean> dataList;

    public CommonlyUsedTitleAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CommonlyUsedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_commonly_used_title, parent, false);
        return new CommonlyUsedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonlyUsedViewHolder holder, int position) {
        KnowledgeOutput.DataBean.ListBean bean = dataList.get(position);
        final String text = bean.getMediaContent();
        holder.mTextView.setText(bean.getMediaPurpose());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("是否发送？")
                        .setMessage(text)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("发送", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!TextUtils.isEmpty(text)) {
                                    TextMsg msgBean = new TextMsg();
                                    msgBean.setMsgType(MsgBean.MsgType.TEXT);
                                    int sendType;
                                    if (text.endsWith(Constant.TEXT_END_FLAG)) {
                                        sendType = 2;
                                    } else {
                                        sendType = 1;
                                    }
                                    msgBean.setType(sendType);
                                    msgBean.setContent(text);
                                    msgBean.setSendTime(AppUtil.getTimeString(new Date().getTime()));
                                    msgBean.setNickName("我");
                                    EventBus.getDefault().post(msgBean);
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

    static class CommonlyUsedViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public CommonlyUsedViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.used_content);
        }
    }
}