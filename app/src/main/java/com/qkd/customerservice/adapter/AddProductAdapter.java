package com.qkd.customerservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.key_library.util.DensityUtil;

/**
 * Created on 12/18/20 15:26
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.AddAdapter> {

    private Context context;
    private LayoutInflater inflate;

    public AddProductAdapter(Context context) {
        this.context = context;
        inflate = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AddAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflate.inflate(R.layout.item_add_product, parent, false);
        return new AddAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddAdapter holder, int position) {

        for (int i = 0; i < 3; i++) {
            View view = this.inflate.inflate(R.layout.item_item_add_product, null);
            holder.add_linear.addView(view);
            if (i != 0) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.topMargin = DensityUtil.dp2px(context, -1.5f);
                view.setLayoutParams(params);
            } else {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.topMargin = 0;
                view.setLayoutParams(params);
            }
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class AddAdapter extends RecyclerView.ViewHolder {
        private TextView add_name;
        private TextView add_change;
        private TextView add_delete;
        private TextView add_total_money;
        private LinearLayout add_linear;

        public AddAdapter(@NonNull View itemView) {
            super(itemView);
            add_name = itemView.findViewById(R.id.add_name);
            add_change = itemView.findViewById(R.id.add_change);
            add_delete = itemView.findViewById(R.id.add_delete);
            add_total_money = itemView.findViewById(R.id.add_total_money);
            add_linear = itemView.findViewById(R.id.add_linear);
        }
    }
}