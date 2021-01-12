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

import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.KnowledgeOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1/4/21 10:54
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExchangeTextAdapter extends RecyclerView.Adapter<ExchangeTextAdapter.ExchangeTextViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<KnowledgeOutput.DataBean.ListBean> dataList;

    public ExchangeTextAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        dataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExchangeTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_exchange_text, parent, false);
        return new ExchangeTextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeTextViewHolder holder, final int position) {
        final KnowledgeOutput.DataBean.ListBean bean = dataList.get(position);
        String text = bean.getMediaContent();
        holder.mTextView.setText(text);
        holder.used_purpose.setText(bean.getMediaPurpose());
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
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
    }

    static class ExchangeTextViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private TextView used_purpose;
        private ImageView mDelete;

        public ExchangeTextViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.used_content);
            used_purpose = itemView.findViewById(R.id.used_purpose);
            mDelete = itemView.findViewById(R.id.exchange_delete);
        }
    }

    private OnClickDeleteListener onClickDeleteListener;

    public void setOnClickDeleteListener(OnClickDeleteListener onClickDeleteListener) {
        this.onClickDeleteListener = onClickDeleteListener;
    }

    public interface OnClickDeleteListener {
        void onDelete(int mediaId, int position);
    }
}