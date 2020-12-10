package com.qkd.customerservice.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.EmojiBean;
import com.qkd.customerservice.bean.Expression;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created on 12/3/20 14:06
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExpressionListAdapter extends RecyclerView.Adapter<ExpressionListAdapter.ListViewHolder> {

    private Context context;
    private List<Expression> expressionList;

    public ExpressionListAdapter(Context context, List<Expression> expressionList) {
        this.context = context;
        this.expressionList = expressionList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(context).inflate(R.layout.item_expression, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final Expression expression = expressionList.get(position);
        if (!TextUtils.isEmpty(expression.getUnique())) {
            holder.expressionImageView.setText(String.valueOf(Character.toChars(Integer.parseInt(expression.getUnique(), 16))));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new EmojiBean(expression.getUnique()));
                }
            });
        } else {
            holder.expressionImageView.setText("");
            holder.itemView.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        if (expressionList == null) {
            return 0;
        } else {
            return expressionList.size();
        }
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView expressionImageView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            expressionImageView = itemView.findViewById(R.id.iv_expression);
        }
    }
}
