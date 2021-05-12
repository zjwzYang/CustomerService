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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.TrialFactorBean;

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
    private List<TrialFactorBean> trialFactorBeans;
    private LayoutInflater inflater;

    public CalcAdapter(Context context) {
        this.context = context;
        this.trialFactorBeans = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        TrialFactorBean bean = trialFactorBeans.get(position);
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
        } else {
            View view = inflater.inflate(R.layout.calc_stepper, parent, false);
            return new StepperViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final TrialFactorBean factorBean = trialFactorBeans.get(position);
        int viewType = getItemViewType(position);
        if (viewType == 1) {
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
                        notifyItemChanged(position);
                    }
                });
                holder.radioLinear.addView(view);
            }
        } else if (viewType == 2) {
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
        }
    }

    @Override
    public int getItemCount() {
        return trialFactorBeans.size();
    }

    public void addAll(List<TrialFactorBean> trialFactorBeans) {
        this.trialFactorBeans.addAll(trialFactorBeans);
        notifyDataSetChanged();
    }

    public List<TrialFactorBean> getAll() {
        return this.trialFactorBeans;
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
}
