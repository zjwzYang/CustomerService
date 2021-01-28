package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.ProductListOutput;
import com.qkd.customerservice.key_library.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/18/20 15:26
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class AddProductAdapter extends RecyclerView.Adapter<AddProductAdapter.AddAdapter> {

    private Context context;
    private int userStatus;
    private LayoutInflater inflate;
    private List<ProductListOutput.DataBean> dataList;
    private OnProductDeleteListener onProductDeleteListener;

    public AddProductAdapter(Context context, int userStatus) {
        this.context = context;
        this.userStatus = userStatus;
        this.dataList = new ArrayList<>();
        inflate = LayoutInflater.from(context);
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflate.inflate(R.layout.item_add_product, parent, false);
        return new AddAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddAdapter holder, final int position) {
        final ProductListOutput.DataBean dataBean = dataList.get(position);
        holder.add_name.setText(dataBean.getProductName());

        holder.add_total_money.setText("首年保费：" + dataBean.getPremiumNum());

        if (userStatus == 3) {
            holder.add_delete.setVisibility(View.VISIBLE);
            holder.add_change.setVisibility(View.VISIBLE);
            holder.add_change_divi.setVisibility(View.VISIBLE);
            holder.add_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("确定删除？")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dataList.remove(position);
                                    notifyItemRemoved(position);
                                    if (position != dataList.size()) {
                                        notifyItemRangeChanged(position, dataList.size() - position);
                                    }
                                    if (onProductDeleteListener != null) {
                                        onProductDeleteListener.onProductDelete();
                                    }
                                }
                            });
                    builder.show();
                }
            });
            holder.add_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("确定修改？")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (onProductDeleteListener != null) {
                                        onProductDeleteListener.onProductChange(dataBean);
                                    }
                                }
                            });
                    builder.show();
                }
            });
        } else {
            holder.add_delete.setVisibility(View.GONE);
            holder.add_change.setVisibility(View.GONE);
            holder.add_change_divi.setVisibility(View.GONE);
        }

        String[][] arrayData = dataBean.getArrayData();
        if (arrayData == null) {
            holder.add_linear.setVisibility(View.GONE);
        } else {
            holder.add_linear.setVisibility(View.VISIBLE);
            holder.add_linear.removeAllViews();
            for (int i = 0; i < arrayData.length; i++) {
                String[] list = arrayData[i];
                View view = this.inflate.inflate(R.layout.item_item_add_product, null);
                TextView textView1 = view.findViewById(R.id.tv_sheetRow1);
                textView1.setText(list[0]);
                TextView textView2 = view.findViewById(R.id.tv_sheetRow2);
                textView2.setText(list[1]);
                TextView textView3 = view.findViewById(R.id.tv_sheetRow3);
                textView3.setText(list[2]);
                TextView textView4 = view.findViewById(R.id.tv_sheetRow4);
                textView4.setText(list[3]);
                TextView textView5 = view.findViewById(R.id.tv_sheetRow5);
                textView5.setText(list[4]);

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

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void add(ProductListOutput.DataBean bean) {
        int id = bean.getId();
        boolean hasSame = false;
        for (int i = 0; i < dataList.size(); i++) {
            ProductListOutput.DataBean orignBean = dataList.get(i);
            if (orignBean.getId() == id) {
                dataList.remove(i);
                dataList.add(i, bean);
                hasSame = true;
                break;
            }
        }
        if (!hasSame) {
            dataList.add(bean);
        }
        notifyDataSetChanged();
    }

    public void addAll(List<ProductListOutput.DataBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<ProductListOutput.DataBean> getAll() {
        return this.dataList;
    }

    public void setOnProductDeleteListener(OnProductDeleteListener onProductDeleteListener) {
        this.onProductDeleteListener = onProductDeleteListener;
    }

    public interface OnProductDeleteListener {
        void onProductDelete();

        void onProductChange(ProductListOutput.DataBean dataBean);
    }

    static class AddAdapter extends RecyclerView.ViewHolder {
        private TextView add_name;
        private TextView add_change;
        private View add_change_divi;
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
            add_change_divi = itemView.findViewById(R.id.add_change_divi);
        }
    }
}