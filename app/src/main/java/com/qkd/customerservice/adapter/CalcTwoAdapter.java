package com.qkd.customerservice.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.PlatformTwoDataBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created on 2021/6/8 09:45
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class CalcTwoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private PlatformTwoDataBean twoDataBean;
    private List<PlatformTwoDataBean.RestrictGenesDTO> dataList;
    private LayoutInflater inflater;
    private OnClickCalcTwoListener onClickCalcTwoListener;

    public CalcTwoAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        PlatformTwoDataBean.RestrictGenesDTO dto = dataList.get(position);
        Integer type = dto.getType();
        if (0 == type) {
            return 0;
        } else if (1 == type) {
            return 1;
        } else if (2 == type) {
            return 2;
        } else if (3 == type) {
            return 3;
        } else if (4 == type) {
            return 4;
        } else if (5 == type) {
            return 5;
        } else if (6 == type) {
            return 6;
        } else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int type) {
        if (0 == type) {
            View view = inflater.inflate(R.layout.item_two_zero, parent, false);
            return new ZeroViewHolder(view);
        } else if (1 == type) {
            View view = inflater.inflate(R.layout.item_two_one, parent, false);
            return new OneViewHolder(view);
        } else if (2 == type) {
            View view = inflater.inflate(R.layout.item_two_two, parent, false);
            return new TwoViewHolder(view);
        } else if (3 == type) {
            View view = inflater.inflate(R.layout.item_two_three, parent, false);
            return new ThreeViewHolder(view);
        } else if (4 == type) {
            View view = inflater.inflate(R.layout.item_two_four, parent, false);
            return new FourViewHolder(view);
        } else if (5 == type) {
            View view = inflater.inflate(R.layout.item_two_five, parent, false);
            return new FiveViewHolder(view);
        } else if (6 == type) {
            View view = inflater.inflate(R.layout.item_two_six, parent, false);
            return new SixViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_two_default, parent, false);
            return new DefaultViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, final int position) {
        int viewType = getItemViewType(position);
        final PlatformTwoDataBean.RestrictGenesDTO bean = dataList.get(position);
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        if (bean.isDisplay()) {
            viewHolder.itemView.setVisibility(View.VISIBLE);
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            viewHolder.itemView.setVisibility(View.GONE);
            layoutParams.height = 0;
            layoutParams.width = 0;
        }
        viewHolder.itemView.setLayoutParams(layoutParams);
        if (0 == viewType) {
            ZeroViewHolder holder = (ZeroViewHolder) viewHolder;
            holder.two_label.setText(bean.getName());
            holder.two_date_select.setText(bean.getDefaultValue());
            holder.two_date_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final List<PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO> values = bean.getValues();
                    if (values != null && values.size() > 0) {
                        List<String> itemList = new ArrayList<>();
                        for (PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO value : values) {
                            String valueType = value.getType();
                            if ("1".equals(valueType)) {
                                itemList.add(value.getValue() + value.getUnit());
                            } else if ("2".equals(valueType)) {
                                Integer min = value.getMin();
                                Integer max = value.getMax();
                                Integer step = value.getStep();
                                String unit = value.getUnit();
                                for (int i = min; i <= max; i = i + step) {
                                    itemList.add(i + unit);
                                }
                            }
                        }
                        final String[] items = itemList.toArray(new String[]{});
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (items[which].equals(bean.getDefaultValue())) {
                                    dialog.dismiss();
                                    return;
                                }
                                bean.setDefaultValue(items[which]);
                                dialog.dismiss();
                                if (onClickCalcTwoListener != null) {
                                    String keyV = "";
                                    if (!TextUtils.isEmpty(bean.getKey())) {
                                        keyV = bean.getKey();
                                    } else if (!TextUtils.isEmpty(bean.getProtectItemId())) {
                                        keyV = bean.getProtectItemId();
                                    }
                                    onClickCalcTwoListener.onSelect(keyV, position);
                                }
                            }
                        }).setNegativeButton("取消", null);
                        builder.show();
                    }
                }
            });
        } else if (1 == viewType) {
            OneViewHolder holder = (OneViewHolder) viewHolder;
            holder.two_label.setText(bean.getName());
            holder.two_date_picker_str.setText(bean.getDefaultValue());
            holder.two_date_picker_str.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            bean.setDefaultValue(year + "-" + (month + 1) + "-" + dayOfMonth);
                            if (onClickCalcTwoListener != null) {
                                String keyV = "";
                                if (!TextUtils.isEmpty(bean.getKey())) {
                                    keyV = bean.getKey();
                                } else if (!TextUtils.isEmpty(bean.getProtectItemId())) {
                                    keyV = bean.getProtectItemId();
                                }
                                onClickCalcTwoListener.onSelect(keyV, position);
                            }
                        }
                    },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                }
            });
        } else if (2 == viewType) {
            TwoViewHolder holder = (TwoViewHolder) viewHolder;
            holder.two_label.setText(bean.getName());
        } else if (3 == viewType) {
            ThreeViewHolder holder = (ThreeViewHolder) viewHolder;
            holder.two_label.setText(bean.getName());
            holder.two_date_input.setText(bean.getDefaultValue());
            holder.two_date_input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setDefaultValue(s.toString());
                    String key = bean.getKey();
                    if (!TextUtils.isEmpty(key)) {
                        for (PlatformTwoDataBean.PriceArgsDTO.GenesDTO gene : twoDataBean.getPriceArgs().getGenes()) {
                            if (key.equals(gene.getKey())) {
                                gene.setValue(s.toString());
                            }
                        }
                    }
                }
            });
        } else if (4 == viewType) {
            FourViewHolder holder = (FourViewHolder) viewHolder;
            holder.two_label.setText(bean.getName());
            String defaultValue = bean.getDefaultValue();
            List<PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO> values = bean.getValues();
            List<PlatformTwoDataBean.RestrictGenesDTO.SubRestrictGeneBean> subRestrictGenes = bean.getSubRestrictGenes();
            String showText = "";
            if (subRestrictGenes != null && subRestrictGenes.size() > 0) {
                for (PlatformTwoDataBean.RestrictGenesDTO.SubRestrictGeneBean subGene : subRestrictGenes) {
                    List<PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO> subValues = subGene.getValues();
                    String subDefaultValue = subGene.getDefaultValue();
                    for (PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO subValue : subValues) {
                        if (subValue.getValue().equals(subDefaultValue)) {
                            if (TextUtils.isEmpty(showText)) {
                                showText += subValue.getName();
                            } else {
                                showText += "-" + subValue.getName();
                            }
                            break;
                        }
                    }
                }
            }
            for (PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO value : values) {
                if (value.getValue().equals(defaultValue)) {
                    if (TextUtils.isEmpty(showText)) {
                        showText += value.getName();
                    } else {
                        showText += "-" + value.getName();
                    }
                    break;
                }
            }
            holder.two_date_city.setText(showText);
            holder.two_date_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickCalcTwoListener != null) {
                        onClickCalcTwoListener.onSelectCity(bean, position);
                    }
                }
            });

        } else if (5 == viewType) {
            FiveViewHolder holder = (FiveViewHolder) viewHolder;
            holder.two_label.setText(bean.getName());
        } else if (6 == viewType) {
            SixViewHolder holder = (SixViewHolder) viewHolder;
            holder.two_label.setText(bean.getName());
            holder.two_date_text.setText(bean.getDefaultValue());
        } else {
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addAll(PlatformTwoDataBean twoDataBean) {
        this.twoDataBean = twoDataBean;
        this.dataList = twoDataBean.getRestrictGenes();
        notifyDataSetChanged();
    }

    public void selectCity(final int position) {
        final PlatformTwoDataBean.RestrictGenesDTO restrictGenesDTO = this.dataList.get(position);
        final List<PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO> values = restrictGenesDTO.getValues();
        String[] items = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            items[i] = values.get(i).getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO valuesDTO = values.get(which);
                restrictGenesDTO.setDefaultValue(valuesDTO.getValue());
                dialog.dismiss();
                if (onClickCalcTwoListener != null) {
                    String key = restrictGenesDTO.getKey();
                    if (TextUtils.isEmpty(key)) {
                        key = restrictGenesDTO.getProtectItemId();
                    }
                    onClickCalcTwoListener.onSelect(key, position);
                }
            }
        }).setNegativeButton("取消", null);
        builder.show();
    }

    public PlatformTwoDataBean getTwoDataBean() {
        return this.twoDataBean;
    }

    static class ZeroViewHolder extends RecyclerView.ViewHolder {
        private TextView two_label;
        private TextView two_date_select;

        public ZeroViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            two_label = itemView.findViewById(R.id.two_label);
            two_date_select = itemView.findViewById(R.id.two_date_select);
        }
    }

    static class OneViewHolder extends RecyclerView.ViewHolder {
        private TextView two_label;
        private TextView two_date_picker_str;

        public OneViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            two_label = itemView.findViewById(R.id.two_label);
            two_date_picker_str = itemView.findViewById(R.id.two_date_picker_str);
        }
    }

    static class TwoViewHolder extends RecyclerView.ViewHolder {
        private TextView two_label;
        private TextView two_date_select;

        public TwoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            two_label = itemView.findViewById(R.id.two_label);
            two_date_select = itemView.findViewById(R.id.two_date_select);
        }
    }

    static class ThreeViewHolder extends RecyclerView.ViewHolder {
        private TextView two_label;
        private EditText two_date_input;

        public ThreeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            two_label = itemView.findViewById(R.id.two_label);
            two_date_input = itemView.findViewById(R.id.two_date_input);
        }
    }

    static class FourViewHolder extends RecyclerView.ViewHolder {
        private TextView two_label;
        private TextView two_date_city;

        public FourViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            two_label = itemView.findViewById(R.id.two_label);
            two_date_city = itemView.findViewById(R.id.two_date_city);
        }
    }

    static class FiveViewHolder extends RecyclerView.ViewHolder {
        private TextView two_label;
        private TextView two_date_job;

        public FiveViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            two_label = itemView.findViewById(R.id.two_label);
            two_date_job = itemView.findViewById(R.id.two_date_job);
        }
    }

    static class SixViewHolder extends RecyclerView.ViewHolder {
        private TextView two_label;
        private TextView two_date_text;

        public SixViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            two_label = itemView.findViewById(R.id.two_label);
            two_date_text = itemView.findViewById(R.id.two_date_text);
        }
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        public DefaultViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }

    public OnClickCalcTwoListener getOnClickCalcTwoListener() {
        return onClickCalcTwoListener;
    }

    public void setOnClickCalcTwoListener(OnClickCalcTwoListener onClickCalcTwoListener) {
        this.onClickCalcTwoListener = onClickCalcTwoListener;
    }

    public interface OnClickCalcTwoListener {
        void onSelect(String key, int position);

        void onSelectCity(PlatformTwoDataBean.RestrictGenesDTO bean, int position);
    }
}