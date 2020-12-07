package com.qkd.customerservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.ExpressionType;

import java.util.List;

/**
 * Created on 12/3/20 13:34
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExpressionTypeListAdapter extends RecyclerView.Adapter<ExpressionTypeListAdapter.ExViewHolder> {

    private Context context;
    private List<ExpressionType> expressionTypeList;

    public ExpressionTypeListAdapter(Context context, List<ExpressionType> expressionTypeList) {
        this.context = context;
        this.expressionTypeList = expressionTypeList;
    }

    @NonNull
    @Override
    public ExViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExViewHolder(LayoutInflater.from(context).inflate(R.layout.item_expression_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExViewHolder holder, int position) {
        holder.expressionIconImageView.setImageResource(expressionTypeList.get(position).getResId());
    }

    @Override
    public int getItemCount() {
        if (expressionTypeList.isEmpty()) {
            return 0;
        } else {
            return expressionTypeList.size();
        }
    }

    static class ExViewHolder extends RecyclerView.ViewHolder {
        private ImageView expressionIconImageView;

        public ExViewHolder(@NonNull View itemView) {
            super(itemView);
            expressionIconImageView = itemView.findViewById(R.id.iv_expression_icon);
        }
    }
}
