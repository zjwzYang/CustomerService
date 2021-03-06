package com.qkd.customerservice.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.PremiumConfigOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/18/20 14:11
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class DialogInputAdapter extends RecyclerView.Adapter<DialogInputAdapter.DialogInputViewHolder> {

    private Context context;
    private List<PremiumConfigOutput.DataBean.ConfigBean> configs;
    private LayoutInflater mInflater;
    private List<PremiumConfigOutput.DataBean.ConfigBean> localConfigs;

    public DialogInputAdapter(Context context, List<PremiumConfigOutput.DataBean.ConfigBean> localConfigs) {
        this.context = context;
        this.configs = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        this.localConfigs = localConfigs;
    }

    @NonNull
    @Override
    public DialogInputViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.dialog_product_input_item, parent, false);
        return new DialogInputViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogInputViewHolder holder, final int position) {
        PremiumConfigOutput.DataBean.ConfigBean configBean = configs.get(position);

        if (localConfigs != null && localConfigs.size() > 0) {
            String fieldName = configBean.getFieldName();
            for (PremiumConfigOutput.DataBean.ConfigBean localConfig : localConfigs) {
                String localFieldName = localConfig.getFieldName();
                if (fieldName.equals(localFieldName)) {
                    configBean.setDictList(localConfig.getDictList());
                    break;
                }
            }
        }


        holder.input_label.setText(configBean.getShowName());
        final List<PremiumConfigOutput.DataBean.ConfigBean.DictListBean> dictList = configBean.getDictList();
        if (dictList == null || dictList.size() == 0) {
            holder.input_flex.setVisibility(View.GONE);
            holder.dialog_input_amount.setVisibility(View.GONE);
        } else {
            final int showWay = configBean.getShowWay();
            if (dictList.size() > 6) {
                holder.input_flex.setVisibility(View.GONE);
                holder.dialog_input_amount.setVisibility(View.VISIBLE);
                final String[] items = new String[dictList.size()];
                String showValue = "";
                for (int i = 0; i < dictList.size(); i++) {
                    PremiumConfigOutput.DataBean.ConfigBean.DictListBean bean = dictList.get(i);
                    items[i] = bean.getShowValue();
                    if (bean.isSelect()) {
                        showValue = bean.getShowValue();
                    }
                }
                if (TextUtils.isEmpty(showValue)) {
                    holder.dialog_input_amount.setHint(configBean.getShowName());
                } else {
                    holder.dialog_input_amount.setHint(showValue);
                }
                holder.dialog_input_amount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (showWay == 4) {
                                    for (PremiumConfigOutput.DataBean.ConfigBean.DictListBean bean : dictList) {
                                        bean.setSelect(false);
                                    }
                                }
                                dictList.get(i).setSelect(true);
                                notifyItemChanged(position);
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setPositiveButton("确定", null);
                        builder.show();
                    }
                });

            } else {
                holder.input_flex.removeAllViews();
                holder.input_flex.setVisibility(View.VISIBLE);
                holder.dialog_input_amount.setVisibility(View.GONE);
                for (int i = 0; i < dictList.size(); i++) {
                    View view = mInflater.inflate(R.layout.dialog_input_item_btn, null);
                    TextView viewBtn = view.findViewById(R.id.input_btn);
                    final PremiumConfigOutput.DataBean.ConfigBean.DictListBean dictListBean = dictList.get(i);
                    viewBtn.setText(dictListBean.getShowValue());
                    if (dictListBean.isSelect()) {
                        viewBtn.setTextColor(ContextCompat.getColor(context, R.color.white));
                        viewBtn.setBackgroundResource(R.drawable.blue2_text_bg);
                    } else {
                        viewBtn.setTextColor(ContextCompat.getColor(context, R.color.divi_color));
                        viewBtn.setBackgroundResource(R.drawable.grey_text_bg);
                    }
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 多选
                            if (showWay == 1) {
                                boolean select = dictListBean.isSelect();
                                dictListBean.setSelect(!select);
                                notifyItemChanged(position);
                            } else if (showWay == 4) { // 单选
                                for (PremiumConfigOutput.DataBean.ConfigBean.DictListBean bean : dictList) {
                                    bean.setSelect(false);
                                }
                                dictListBean.setSelect(true);
                                notifyItemChanged(position);
                            }
                        }
                    });
                    holder.input_flex.addView(view);
                }

            }
        }
    }

    @Override
    public int getItemCount() {
        return configs.size();
    }

    public void addAll(List<PremiumConfigOutput.DataBean.ConfigBean> configs) {
        this.configs.addAll(configs);
        notifyDataSetChanged();
    }

    public List<PremiumConfigOutput.DataBean.ConfigBean> getAll() {
        return this.configs;
    }

    static class DialogInputViewHolder extends RecyclerView.ViewHolder {
        private TextView input_label;
        private TextView dialog_input_amount;
        private FlexboxLayout input_flex;

        public DialogInputViewHolder(@NonNull View itemView) {
            super(itemView);
            input_label = itemView.findViewById(R.id.input_label);
            dialog_input_amount = itemView.findViewById(R.id.dialog_input_amount);
            input_flex = itemView.findViewById(R.id.input_flex);
        }
    }
}