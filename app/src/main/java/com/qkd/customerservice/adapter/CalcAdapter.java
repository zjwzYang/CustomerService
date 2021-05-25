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
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.TrialFactorBean;
import com.qkd.customerservice.bean.TrialFactorCityBean;
import com.qkd.customerservice.bean.TrialFactorFatherBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created on 2021/5/10 15:36
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class CalcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<TrialFactorFatherBean> trialFactorBeans;
    private LayoutInflater inflater;
    private String A_EXEMPT_VALUE;

    private int oldSize = 0;
    private int oldThreeSize = 0;
    private int oldTwoThreeSize = 0;

    public CalcAdapter(Context context) {
        this.context = context;
        this.trialFactorBeans = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setA_EXEMPT_VALUE(String a_EXEMPT_VALUE) {
        A_EXEMPT_VALUE = a_EXEMPT_VALUE;
    }

    @Override
    public int getItemViewType(int position) {
        TrialFactorFatherBean bean = trialFactorBeans.get(position);
        String widget = bean.getWidget();
        if ("radio".equals(widget)) {
            return 1;
        } else if ("select".equals(widget)) {
            return 2;
        } else if ("datepicker".equals(widget)) {
            return 3;
        } else if ("stepper".equals(widget)) {
            return 4;
        } else if ("input".equals(widget)) {
            return 5;
        } else if ("text".equals(widget)) {
            return 5;
        } else if ("citypiker".equals(widget)) {
            return 6;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = inflater.inflate(R.layout.calc_radio, parent, false);
            return new RadioViewHolder(view);
        } else if (viewType == 2) {
            View view = inflater.inflate(R.layout.calc_select, parent, false);
            return new SelectViewHolder(view);
        } else if (viewType == 3) {
            View view = inflater.inflate(R.layout.calc_date_picker, parent, false);
            return new DatepickerViewHolder(view);
        } else if (viewType == 4) {
            View view = inflater.inflate(R.layout.calc_stepper, parent, false);
            return new StepperViewHolder(view);
        } else if (viewType == 5) {
            View view = inflater.inflate(R.layout.calc_input, parent, false);
            return new InputViewHolder(view);
        } else if (viewType == 6) {
            View view = inflater.inflate(R.layout.calc_citypiker, parent, false);
            return new CityViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.calc_stepper, parent, false);
            return new StepperViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final TrialFactorFatherBean fatherBean = trialFactorBeans.get(position);

        String widget = fatherBean.getWidget();
        String name = fatherBean.getName();
        if ("N".equals(A_EXEMPT_VALUE)) {
            if (TextUtils.isEmpty(name)) {
                viewHolder.itemView.setVisibility(View.VISIBLE);
            } else if ("A_EXEMPT".equals(name)) {
                viewHolder.itemView.setVisibility(View.VISIBLE);
            } else if (name.startsWith("A_")) {
                viewHolder.itemView.setVisibility(View.GONE);
            } else if ("hidden".equals(widget)) {
                viewHolder.itemView.setVisibility(View.GONE);
            } else {
                viewHolder.itemView.setVisibility(View.VISIBLE);
            }
        } else {
            if ("hidden".equals(widget)) {
                viewHolder.itemView.setVisibility(View.GONE);
            } else {
                viewHolder.itemView.setVisibility(View.VISIBLE);
            }
        }
        int viewType = getItemViewType(position);
        if (viewType == 1) {
            final TrialFactorBean factorBean = (TrialFactorBean) fatherBean;
            RadioViewHolder holder = (RadioViewHolder) viewHolder;
            holder.rLabel.setText(factorBean.getLabel());
            List<List<String>> detail = factorBean.getDetail();
            String rValue = factorBean.getValue();
            holder.radioLinear.removeAllViews();
            for (final List<String> dItem : detail) {
                View view = inflater.inflate(R.layout.dialog_input_item_btn, null);
                TextView viewBtn = view.findViewById(R.id.input_btn);
                if (dItem.contains(rValue)) {
                    viewBtn.setTextColor(ContextCompat.getColor(context, R.color.white));
                    viewBtn.setBackgroundResource(R.drawable.blue2_text_bg);
                    factorBean.setSelectList(dItem);
                } else {
                    viewBtn.setTextColor(ContextCompat.getColor(context, R.color.divi_color));
                    viewBtn.setBackgroundResource(R.drawable.grey_text_bg);
                }
                viewBtn.setText(dItem.get(1));
                viewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        factorBean.setValue(dItem.get(0));
                        factorBean.setSelectList(dItem);
                        if ("A_EXEMPT".equals(factorBean.getName())) {
                            A_EXEMPT_VALUE = dItem.get(0);
                            notifyDataSetChanged();
                        } else {
                            notifyItemChanged(position);
                        }
                    }
                });
                holder.radioLinear.addView(view);
            }
        } else if (viewType == 2) {
            final TrialFactorBean factorBean = (TrialFactorBean) fatherBean;
            SelectViewHolder holder = (SelectViewHolder) viewHolder;
            holder.sLabel.setText(factorBean.getLabel());
            final List<List<String>> selectDetails = factorBean.getDetail();
            if (selectDetails != null) {
                String selectValue = factorBean.getValue();
                final String[] items = new String[selectDetails.size()];
                for (int i = 0; i < selectDetails.size(); i++) {
                    List<String> list = selectDetails.get(i);
                    items[i] = list.get(1);
                    if (list.contains(selectValue)) {
                        holder.selectDate.setText(list.get(1));
                        factorBean.setSelectList(list);
                    }
                }
                holder.selectDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<String> list = selectDetails.get(which);
                                factorBean.setValue(list.get(0));
                                factorBean.setSelectList(list);
                                notifyItemChanged(position);
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                });
            }
        } else if (viewType == 3) {
            final TrialFactorBean factorBean = (TrialFactorBean) fatherBean;
            DatepickerViewHolder holder = (DatepickerViewHolder) viewHolder;
            holder.calcLabel.setText(factorBean.getLabel());
            holder.calcPicker.setText(factorBean.getValue());
            holder.calcPicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            factorBean.setValue(year + "-" + (month + 1) + "-" + dayOfMonth);
                            notifyItemChanged(position);
                        }
                    },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dialog.show();
                }
            });
        } else if (viewType == 4) {
            final TrialFactorBean factorBean = (TrialFactorBean) fatherBean;
            final StepperViewHolder holder = (StepperViewHolder) viewHolder;
            holder.calcLabel.setText(factorBean.getLabel());
            final String value = factorBean.getValue();
            holder.stepper.setText(value);
            if (!TextUtils.isEmpty(value)) {
                holder.stepper.setSelection(value.length());
            }
            holder.stepper.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        factorBean.setValue(String.valueOf(s));
                        notifyItemChanged(position);
                    } catch (Exception e) {

                    }
                }
            });
            holder.strp_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int step = Integer.parseInt(value);
                        step -= 1;
                        factorBean.setValue(String.valueOf(step));
                        notifyItemChanged(position);
                    } catch (Exception e) {

                    }
                }
            });
            holder.strp_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int step = Integer.parseInt(value);
                        step += 1;
                        factorBean.setValue(String.valueOf(step));
                        notifyItemChanged(position);
                    } catch (Exception e) {

                    }
                }
            });
        } else if (viewType == 5) {
            final TrialFactorBean factorBean = (TrialFactorBean) fatherBean;
            InputViewHolder holder = (InputViewHolder) viewHolder;
            holder.calcLabel.setText(factorBean.getLabel());
            String value = factorBean.getValue();
            holder.inputE.setText(value);
            if (!TextUtils.isEmpty(value)) {
                holder.inputE.setSelection(value.length());
            }
            holder.inputE.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        factorBean.setValue(String.valueOf(s));
                        notifyItemChanged(position);
                    } catch (Exception e) {

                    }
                }
            });
        } else if (viewType == 6) {
            final TrialFactorCityBean cityBean = (TrialFactorCityBean) fatherBean;
            CityViewHolder holder = (CityViewHolder) viewHolder;

            holder.calcLabel.setText(cityBean.getLabel());
            final TrialFactorCityBean.ValueDTO value = cityBean.getValue();
            if (value != null) {
                holder.selectDate.setText(value.getLabelX());
            }
            final List<TrialFactorCityBean.DetailDTO> detail = cityBean.getDetail();
            if (detail != null && detail.size() > 0) {
                holder.selectDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oldSize = 0;
                        oldThreeSize = 0;
                        oldTwoThreeSize = 0;
                        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_city_select, null);
                        final NumberPicker onePicker = dialogView.findViewById(R.id.sort_one_picker);
                        final NumberPicker twoPicker = dialogView.findViewById(R.id.sort_two_picker);
                        final NumberPicker threePicker = dialogView.findViewById(R.id.sort_three_picker);
                        onePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                        twoPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                        threePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                        String[] ones = new String[detail.size()];
                        for (int i = 0; i < detail.size(); i++) {
                            ones[i] = detail.get(i).getLabelX();
                        }
                        onePicker.setMinValue(0);
                        onePicker.setMaxValue(detail.size() - 1);
                        onePicker.setDisplayedValues(ones);
                        onePicker.setWrapSelectorWheel(false);

                        final List<TrialFactorCityBean.DetailDTO.ChildrenDTOX> oneList = detail.get(0).getChildren();
                        if (oneList != null && oneList.size() != 0) {
                            String[] twos = new String[oneList.size()];
                            for (int j = 0; j < oneList.size(); j++) {
                                twos[j] = oneList.get(j).getLabelX();
                            }
                            twoPicker.setMinValue(0);
                            twoPicker.setMaxValue(twos.length - 1);
                            twoPicker.setDisplayedValues(twos);
                            twoPicker.setWrapSelectorWheel(false);

                            List<TrialFactorCityBean.ChildrenDTO> threeList = oneList.get(0).getChildren();
                            if (threeList != null && threeList.size() != 0) {
                                String[] threes = new String[threeList.size()];
                                for (int i = 0; i < threeList.size(); i++) {
                                    threes[i] = threeList.get(i).getLabelX();
                                }
                                threePicker.setMinValue(0);
                                threePicker.setMaxValue(threes.length - 1);
                                threePicker.setDisplayedValues(threes);
                                threePicker.setWrapSelectorWheel(false);
                            }
                        }
                        onePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker numberPicker, int i, int newVal) {
                                List<TrialFactorCityBean.DetailDTO.ChildrenDTOX> list = detail.get(newVal).getChildren();
                                String[] twos = new String[list.size()];
                                for (int j = 0; j < list.size(); j++) {
                                    twos[j] = list.get(j).getLabelX();
                                }
                                if (twos.length > oldSize) {
                                    twoPicker.setDisplayedValues(twos);
                                    twoPicker.setMinValue(0);
                                    twoPicker.setMaxValue(twos.length - 1);
                                } else {
                                    twoPicker.setMinValue(0);
                                    twoPicker.setMaxValue(twos.length - 1);
                                    twoPicker.setDisplayedValues(twos);
                                }
                                twoPicker.setWrapSelectorWheel(false);
                                oldSize = twos.length;

                                List<TrialFactorCityBean.ChildrenDTO> threeList = list.get(0).getChildren();
                                if (threeList != null && threeList.size() != 0) {
                                    String[] threes = new String[threeList.size()];
                                    for (int r = 0; r < threeList.size(); r++) {
                                        threes[r] = threeList.get(r).getLabelX();
                                    }
                                    if (threes.length > oldThreeSize) {
                                        threePicker.setDisplayedValues(threes);
                                        threePicker.setMinValue(0);
                                        threePicker.setMaxValue(threes.length - 1);
                                    } else {
                                        threePicker.setMinValue(0);
                                        threePicker.setMaxValue(threes.length - 1);
                                        threePicker.setDisplayedValues(threes);
                                    }
                                    threePicker.setWrapSelectorWheel(false);
                                    oldThreeSize = threes.length;
                                }
                            }
                        });
                        twoPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                int one = onePicker.getValue();
                                List<TrialFactorCityBean.ChildrenDTO> list = detail.get(one).getChildren().get(newVal).getChildren();
                                String[] threes = new String[list.size()];
                                for (int r = 0; r < list.size(); r++) {
                                    threes[r] = list.get(r).getLabelX();
                                }
                                if (threes.length > oldTwoThreeSize) {
                                    threePicker.setDisplayedValues(threes);
                                    threePicker.setMinValue(0);
                                    threePicker.setMaxValue(threes.length - 1);
                                } else {
                                    threePicker.setMinValue(0);
                                    threePicker.setMaxValue(threes.length - 1);
                                    threePicker.setDisplayedValues(threes);
                                }
                                threePicker.setWrapSelectorWheel(false);
                                oldTwoThreeSize = threes.length;
                            }
                        });
                        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(dialogView)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int one = onePicker.getValue();
                                        int two = twoPicker.getValue();
                                        int three = threePicker.getValue();
                                        TrialFactorCityBean.DetailDTO bean = detail.get(one);
                                        TrialFactorCityBean.DetailDTO.ChildrenDTOX childrenDTOX = bean.getChildren().get(two);
                                        TrialFactorCityBean.ChildrenDTO childrenDTO = childrenDTOX.getChildren().get(three);
                                        TrialFactorCityBean.ValueDTO valueDTO = new TrialFactorCityBean.ValueDTO();
                                        valueDTO.setLabelX(childrenDTO.getLabelX());
                                        valueDTO.setValue(childrenDTO.getValue());

                                        List<TrialFactorCityBean.ChildrenDTO> parents = new ArrayList<>();
                                        String parent1Label = childrenDTOX.getLabelX();
                                        String parent1Value = childrenDTOX.getValue();
                                        TrialFactorCityBean.ChildrenDTO parent1 = new TrialFactorCityBean.ChildrenDTO();
                                        parent1.setLabelX(parent1Label);
                                        parent1.setValue(parent1Value);
                                        parents.add(parent1);
                                        String parent2Label = bean.getLabelX();
                                        String parent2Value = bean.getValue();
                                        TrialFactorCityBean.ChildrenDTO parent2 = new TrialFactorCityBean.ChildrenDTO();
                                        parent2.setLabelX(parent2Label);
                                        parent2.setValue(parent2Value);
                                        parents.add(parent2);
                                        valueDTO.setParents(parents);

                                        cityBean.setValue(valueDTO);
                                        notifyItemChanged(position);
                                    }
                                }).setNegativeButton("取消", null);
                        builder.show();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return trialFactorBeans.size();
    }

    public void addAll(List<TrialFactorFatherBean> trialFactorBeans) {
        this.trialFactorBeans.addAll(trialFactorBeans);
        notifyDataSetChanged();
    }

    public List<TrialFactorFatherBean> getAll() {
        return this.trialFactorBeans;
    }

    public String getA_EXEMPT_VALUE() {
        return A_EXEMPT_VALUE;
    }

    static class RadioViewHolder extends RecyclerView.ViewHolder {
        private TextView rLabel;
        private FlexboxLayout radioLinear;

        public RadioViewHolder(@NonNull View itemView) {
            super(itemView);
            rLabel = itemView.findViewById(R.id.calc_label);
            radioLinear = itemView.findViewById(R.id.calc_radio);
        }
    }

    static class SelectViewHolder extends RecyclerView.ViewHolder {
        private TextView sLabel;
        private TextView selectDate;

        public SelectViewHolder(@NonNull View itemView) {
            super(itemView);
            sLabel = itemView.findViewById(R.id.calc_label);
            selectDate = itemView.findViewById(R.id.date_select);
        }
    }

    static class DatepickerViewHolder extends RecyclerView.ViewHolder {
        private TextView calcLabel;
        private TextView calcPicker;

        public DatepickerViewHolder(@NonNull View itemView) {
            super(itemView);
            calcLabel = itemView.findViewById(R.id.calc_label);
            calcPicker = itemView.findViewById(R.id.date_picker_str);
        }
    }

    static class StepperViewHolder extends RecyclerView.ViewHolder {
        private TextView calcLabel;
        private EditText stepper;
        private TextView strp_reduce;
        private TextView strp_plus;

        public StepperViewHolder(@NonNull View itemView) {
            super(itemView);
            calcLabel = itemView.findViewById(R.id.calc_label);
            stepper = itemView.findViewById(R.id.date_stepper);
            strp_reduce = itemView.findViewById(R.id.strp_reduce);
            strp_plus = itemView.findViewById(R.id.strp_plus);
        }
    }

    static class InputViewHolder extends RecyclerView.ViewHolder {
        private TextView calcLabel;
        private EditText inputE;

        public InputViewHolder(@NonNull View itemView) {
            super(itemView);
            calcLabel = itemView.findViewById(R.id.calc_label);
            inputE = itemView.findViewById(R.id.date_input);
        }
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView calcLabel;
        private TextView selectDate;

        public CityViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            calcLabel = itemView.findViewById(R.id.calc_label);
            selectDate = itemView.findViewById(R.id.date_select);
        }
    }
}
