package com.qkd.customerservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.TrialOccupationBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created on 2021/5/28 09:14
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class OccuptionOneAdapter extends RecyclerView.Adapter<OccuptionOneAdapter.OccuptionViewHolder> {

    private Context context;
    private List<TrialOccupationBean.DetailDTO> detail;
    private LayoutInflater inflater;
    private OnClickOneItem mOnClickOneItem;

    private int currPosition = 0;

    public OccuptionOneAdapter(Context context, List<TrialOccupationBean.DetailDTO> detail) {
        this.context = context;
        this.detail = detail;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @NotNull
    @Override
    public OccuptionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_occuption, parent, false);
        return new OccuptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OccuptionViewHolder holder, final int position) {
        final TrialOccupationBean.DetailDTO dto = detail.get(position);
        String labelX = dto.getLabelX();
        holder.item_occuption_name.setText(labelX);
        if (position == currPosition) {
            holder.item_occuption_name.setTextColor(ContextCompat.getColor(context, R.color.app_blue));
        } else {
            holder.item_occuption_name.setTextColor(ContextCompat.getColor(context, R.color.app_black));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currPosition = position;
                notifyDataSetChanged();
                if (mOnClickOneItem != null) {
                    mOnClickOneItem.onClickOne(dto);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return detail.size();
    }

    public TrialOccupationBean.DetailDTO getCurrBean() {
        return detail.get(currPosition);
    }

    static class OccuptionViewHolder extends RecyclerView.ViewHolder {
        private TextView item_occuption_name;

        public OccuptionViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            item_occuption_name = itemView.findViewById(R.id.item_occuption_name);
        }
    }

    public void setOnClickOneItem(OnClickOneItem onClickOneItem) {
        mOnClickOneItem = onClickOneItem;
    }

    public interface OnClickOneItem {
        void onClickOne(TrialOccupationBean.DetailDTO bean);
    }
} 