package com.qkd.customerservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.PlannerOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/23/20 14:52
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.PlannerViewHolder> {

    private Context context;
    private RequestOptions options;
    private List<PlannerOutput.DataBean.ListBean> list;
    private OnPlannerClickListener onPlannerClickListener;

    public PlannerAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        RoundedCorners roundedCorners = new RoundedCorners(4);
        options = new RequestOptions()
                .transform(new CenterCrop(), roundedCorners)
                .error(R.drawable.ic_head_place)
                .placeholder(R.drawable.ic_head_place);
    }

    @NonNull
    @Override
    public PlannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_planner, parent, false);
        return new PlannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlannerViewHolder holder, int position) {
        final PlannerOutput.DataBean.ListBean listBean = list.get(position);
        Glide.with(context)
                .load(listBean.getFaceUrl())
                .apply(options)
                .into(holder.mImageView);
        holder.mTextView.setText(listBean.getNick());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPlannerClickListener != null) {
                    onPlannerClickListener.onPlannerClick(listBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<PlannerOutput.DataBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnPlannerClickListener(OnPlannerClickListener onPlannerClickListener) {
        this.onPlannerClickListener = onPlannerClickListener;
    }

    public interface OnPlannerClickListener {
        void onPlannerClick(PlannerOutput.DataBean.ListBean listBean);
    }
    static class PlannerViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextView;

        public PlannerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.customer_head);
            mTextView = itemView.findViewById(R.id.customer_name);
        }
    }
}