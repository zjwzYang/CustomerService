package com.qkd.customerservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.ProductListOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/18/20 12:43
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class DialogProductAdapter extends RecyclerView.Adapter<DialogProductAdapter.DialogProductViewHolder> {

    private Context context;
    private List<ProductListOutput.DataBean> data;
    private OnClickProductItemListener onClickProductItemListener;

    public DialogProductAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public DialogProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dialog_product, parent, false);
        return new DialogProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogProductViewHolder holder, int position) {
        final ProductListOutput.DataBean dataBean = data.get(position);
        holder.mName.setText(dataBean.getProductName());
        holder.mDesc.setText(dataBean.getCompanyName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickProductItemListener != null) {
                    onClickProductItemListener.onClickProduct(dataBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<ProductListOutput.DataBean> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    static class DialogProductViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mDesc;

        public DialogProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.product_name);
            mDesc = itemView.findViewById(R.id.product_desc);
        }
    }

    public void setOnClickProductItemListener(OnClickProductItemListener onClickProductItemListener) {
        this.onClickProductItemListener = onClickProductItemListener;
    }

    public interface OnClickProductItemListener {
        void onClickProduct(ProductListOutput.DataBean bean);
    }
}