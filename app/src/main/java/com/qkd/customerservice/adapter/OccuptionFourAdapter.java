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

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2021/5/28 09:14
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class OccuptionFourAdapter extends RecyclerView.Adapter<OccuptionFourAdapter.OccuptionViewHolder> {

    private Context context;
    private List<TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX.ChildrenDTO> dateList;
    private LayoutInflater inflater;

    private OnClickFourItem mOnClickFourItem;
    private int currPosition = 0;

    public OccuptionFourAdapter(Context context) {
        this.context = context;
        dateList = new ArrayList<>();
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
        final TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX.ChildrenDTO dto = dateList.get(position);
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
                if (mOnClickFourItem != null) {
                    mOnClickFourItem.onClickFour(dto);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public void addAll(List<TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX.ChildrenDTO> dateList) {
        currPosition = 0;
        this.dateList = dateList;
        notifyDataSetChanged();
    }

    static class OccuptionViewHolder extends RecyclerView.ViewHolder {
        private TextView item_occuption_name;

        public OccuptionViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            item_occuption_name = itemView.findViewById(R.id.item_occuption_name);
        }
    }

    public void setOnClickFourItem(OnClickFourItem onClickFourItem) {
        mOnClickFourItem = onClickFourItem;
    }

    public interface OnClickFourItem {
        void onClickFour(TrialOccupationBean.DetailDTO.ChildrenDTOXX.ChildrenDTOX.ChildrenDTO bean);
    }
} 