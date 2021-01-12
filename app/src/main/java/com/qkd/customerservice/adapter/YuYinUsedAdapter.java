package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.NetUtil;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.KnowledgeOutput;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.VoiceMsg;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 12/25/20 09:50
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class YuYinUsedAdapter extends RecyclerView.Adapter<YuYinUsedAdapter.YuYinViewHolder> {

    private Context context;
    private List<KnowledgeOutput.DataBean.ListBean> list;

    public YuYinUsedAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public YuYinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yuyin_used, parent, false);
        return new YuYinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final YuYinViewHolder holder, int position) {
        final KnowledgeOutput.DataBean.ListBean bean = list.get(position);
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
                                NetUtil.get().download(context, bean.getMediaContent(), new NetUtil.OnDownloadListener() {
                                    @Override
                                    public void onDownloadSuccess(File file) {
                                        VoiceMsg voiceMsg = new VoiceMsg();
                                        voiceMsg.setNickName("我");
                                        voiceMsg.setPlaying(false);
                                        voiceMsg.setDuration(-1);
                                        voiceMsg.setAudioPath(Uri.parse(file.getAbsolutePath()));
                                        voiceMsg.setType(1);
                                        voiceMsg.setSendTime(AppUtil.getTimeString(new Date().getTime()));
                                        voiceMsg.setMsgType(MsgBean.MsgType.VOICE);
                                        EventBus.getDefault().post(voiceMsg);
                                    }

                                    @Override
                                    public void onDownloading(int progress) {

                                    }

                                    @Override
                                    public void onDownloadFailed(Exception e) {
                                        Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<KnowledgeOutput.DataBean.ListBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        this.list.clear();
    }

    static class YuYinViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public YuYinViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.used_content);
        }
    }
}