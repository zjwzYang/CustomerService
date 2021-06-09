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
import com.qkd.customerservice.bean.PlatformTwoDataBean;

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
public class CityPickProvinceAdapter extends RecyclerView.Adapter<CityPickProvinceAdapter.OccuptionViewHolder> {

    private Context context;
    private PlatformTwoDataBean.RestrictGenesDTO.SubRestrictGeneBean bean;
    private List<PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO> dataList;
    private LayoutInflater inflater;
    private OnclickProvinceListener onclickProvinceListener;
    private String currItem = "";

    public CityPickProvinceAdapter(Context context) {
        this.context = context;
        this.bean = new PlatformTwoDataBean.RestrictGenesDTO.SubRestrictGeneBean();
        this.dataList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setBean(PlatformTwoDataBean.RestrictGenesDTO.SubRestrictGeneBean bean) {
        this.bean = bean;
        this.currItem = bean.getDefaultValue();
        this.dataList = bean.getValues();
        notifyDataSetChanged();
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
        final PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO dto = dataList.get(position);
        String labelX = dto.getName();
        holder.item_occuption_name.setText(labelX);
        final String value = dto.getValue();
        if (currItem.equals(value)) {
            holder.item_occuption_name.setTextColor(ContextCompat.getColor(context, R.color.app_blue));
        } else {
            holder.item_occuption_name.setTextColor(ContextCompat.getColor(context, R.color.app_black));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currItem = value;
                bean.setDefaultValue(value);
                notifyDataSetChanged();
                if (onclickProvinceListener != null) {
                    onclickProvinceListener.onClickProvince(bean.getKey());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    static class OccuptionViewHolder extends RecyclerView.ViewHolder {
        private TextView item_occuption_name;

        public OccuptionViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            item_occuption_name = itemView.findViewById(R.id.item_occuption_name);
        }
    }

    public OnclickProvinceListener getOnclickProvinceListener() {
        return onclickProvinceListener;
    }

    public void setOnclickProvinceListener(OnclickProvinceListener onclickProvinceListener) {
        this.onclickProvinceListener = onclickProvinceListener;
    }

    public interface OnclickProvinceListener {
        void onClickProvince(String key);
    }
}