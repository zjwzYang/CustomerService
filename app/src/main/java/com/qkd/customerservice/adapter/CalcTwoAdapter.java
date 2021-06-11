package com.qkd.customerservice.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
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
    private FragmentManager mFragmentManager;
    private PlatformTwoDataBean twoDataBean;
    private List<PlatformTwoDataBean.RestrictGenesDTO> dataList;
    private LayoutInflater inflater;
    private OnClickCalcTwoListener onClickCalcTwoListener;

    public CalcTwoAdapter(Context context, FragmentManager mFragmentManager) {
        this.context = context;
        this.mFragmentManager = mFragmentManager;
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
                            String valueUnit = value.getUnit();
                            if (TextUtils.isEmpty(valueUnit)) {
                                valueUnit = "";
                            }
                            if ("1".equals(valueType)) {
                                itemList.add(value.getValue() + valueUnit);
                            } else if ("2".equals(valueType)) {
                                Integer min = value.getMin();
                                Integer max = value.getMax();
                                Integer step = value.getStep();
                                String unit = valueUnit;
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
            holder.two_date_select.setText(bean.getDefaultValue());
            holder.two_date_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    SelectSpinnerDialog dialog = new SelectSpinnerDialog();
//                    dialog.show(mFragmentManager, "SelectSpinnerDialog");
                    List<PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO> values = bean.getValues();
                    if (values != null && values.size() > 0) {
                        final List<String> typeOne = new ArrayList<>();
                        final List<String> typeTwo = new ArrayList<>();
                        typeTwo.add("请选择");
                        for (PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO valueBean : values) {
                            String type = valueBean.getType();
                            String value = valueBean.getValue();
                            String unit = valueBean.getUnit();
                            if ("1".equals(type)) {
                                if (TextUtils.isEmpty(unit)) {
                                    typeOne.add(value);
                                } else {
                                    typeOne.add(value + unit);
                                }
                            } else if ("2".equals(type)) {
                                Integer min = valueBean.getMin();
                                Integer max = valueBean.getMax();
                                Integer step = valueBean.getStep();
                                for (int i = min; i <= max; i = i + step) {
                                    if (TextUtils.isEmpty(unit)) {
                                        typeTwo.add(String.valueOf(i));
                                    } else {
                                        typeTwo.add(i + unit);
                                    }
                                }
                            }
                        }

                        View view = inflater.inflate(R.layout.dialog_select_spinner, null);
                        final FlexboxLayout flex_box = view.findViewById(R.id.flex_box);
                        flex_box.removeAllViews();

                        for (String dItem : typeOne) {
                            View viewItem = inflater.inflate(R.layout.dialog_input_item_btn, null);
                            TextView viewBtn = viewItem.findViewById(R.id.input_btn);
                            if (dItem.equals(bean.getDefaultValue())) {
                                viewBtn.setTextColor(ContextCompat.getColor(context, R.color.white));
                                viewBtn.setBackgroundResource(R.drawable.blue2_text_bg);
                            } else {
                                viewBtn.setTextColor(ContextCompat.getColor(context, R.color.divi_color));
                                viewBtn.setBackgroundResource(R.drawable.grey_text_bg);
                            }
                            viewBtn.setText(dItem);
                            viewBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (int i = 0; i < flex_box.getChildCount(); i++) {
                                        View child = flex_box.getChildAt(i);
                                        if (child instanceof Spinner) {
                                        } else {
                                            TextView childText = child.findViewById(R.id.input_btn);
                                            childText.setTextColor(ContextCompat.getColor(context, R.color.divi_color));
                                            childText.setBackgroundResource(R.drawable.grey_text_bg);
                                        }
                                    }
                                    TextView clickText = v.findViewById(R.id.input_btn);
                                    clickText.setTextColor(ContextCompat.getColor(context, R.color.white));
                                    clickText.setBackgroundResource(R.drawable.blue2_text_bg);
                                    flex_box.setTag(clickText.getText().toString());
                                }
                            });
                            flex_box.addView(viewItem);
                        }
                        Spinner mSpinner = new Spinner(context);
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, typeTwo);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpinner.setAdapter(adapter);
                        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    for (int i = 0; i < flex_box.getChildCount(); i++) {
                                        View child = flex_box.getChildAt(i);
                                        if (child instanceof Spinner) {
                                        } else {
                                            TextView childText = child.findViewById(R.id.input_btn);
                                            childText.setTextColor(ContextCompat.getColor(context, R.color.divi_color));
                                            childText.setBackgroundResource(R.drawable.grey_text_bg);
                                        }
                                        flex_box.setTag(typeTwo.get(position));
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
//                        mSpinner.setBackgroundResource(R.drawable.grey_text_bg);

                        flex_box.addView(mSpinner);

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setView(view)
                                .setTitle(bean.getName())
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        bean.setDefaultValue();
                                        String valueStr = (String) flex_box.getTag();
                                        bean.setDefaultValue(valueStr);
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
                                });
                        builder.show();
                    }
                }
            });
        } else if (3 == viewType) {
            ThreeViewHolder holder = (ThreeViewHolder) viewHolder;
            holder.two_label.setText(bean.getName());
            holder.two_date_input.setText(bean.getDefaultValue());
            List<PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO> values = bean.getValues();
            if (values != null && values.size() > 0) {
                PlatformTwoDataBean.RestrictGenesDTO.ValuesDTO valuesDTO = values.get(0);
                final Integer min = valuesDTO.getMin();
                final Integer max = valuesDTO.getMax();
                final Integer step = valuesDTO.getStep();
                holder.step_min_max.setText("范围：" + min + " ~ " + max);
                holder.strp_reduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int valueNum = Integer.parseInt(bean.getDefaultValue());
                            if (valueNum <= min) {
                                Toast.makeText(context, "已经是最小值啦", Toast.LENGTH_SHORT).show();
                            } else {
                                valueNum -= step;
                                bean.setDefaultValue(String.valueOf(valueNum));
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
                        } catch (Exception e) {
                            Toast.makeText(context, "出错", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.strp_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int valueNum = Integer.parseInt(bean.getDefaultValue());
                            if (valueNum >= max) {
                                Toast.makeText(context, "已经是最大值啦", Toast.LENGTH_SHORT).show();
                            } else {
                                valueNum += step;
                                bean.setDefaultValue(String.valueOf(valueNum));
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
                        } catch (Exception e) {
                            Toast.makeText(context, "出错", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                holder.two_date_input.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        if (!s.toString().equals(bean.getDefaultValue())) {
//                            hasChanged = true;
//                            changedKey = bean.getKey();
//                            changedPosition = position;
//                            bean.setDefaultValue(s.toString());
////                            String key = bean.getKey();
////                            if (!TextUtils.isEmpty(key)) {
////                                for (PlatformTwoDataBean.PriceArgsDTO.GenesDTO gene : twoDataBean.getPriceArgs().getGenes()) {
////                                    if (key.equals(gene.getKey())) {
////                                        gene.setValue(s.toString());
////                                    }
////                                }
////                            }
//                        }
//                    }
//                });
            }
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
            if (values != null && values.size() > 0) {
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
        private TextView two_date_input;
        private TextView strp_reduce;
        private TextView strp_plus;
        private TextView step_min_max;

        public ThreeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            two_label = itemView.findViewById(R.id.two_label);
            two_date_input = itemView.findViewById(R.id.two_date_input);
            strp_reduce = itemView.findViewById(R.id.strp_reduce);
            strp_plus = itemView.findViewById(R.id.strp_plus);
            step_min_max = itemView.findViewById(R.id.step_min_max);
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