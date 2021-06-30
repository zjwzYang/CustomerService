package com.qkd.customerservice.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.PlatformThreeDataBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created on 2021/6/22 09:14
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class CalcThreeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<PlatformThreeDataBean.ChildrenDTO.DataDTO> dataList;
    private List<PlatformThreeDataBean> threeDataBeans;

    private OnPlaceListener onPlaceListener;

    public CalcThreeAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.dataList = new ArrayList<>();
        threeDataBeans = new ArrayList<>();
    }

    public void setOnPlaceListener(OnPlaceListener onPlaceListener) {
        this.onPlaceListener = onPlaceListener;
    }

    @Override
    public int getItemViewType(int position) {
        PlatformThreeDataBean.ChildrenDTO.DataDTO dto = this.dataList.get(position);
        switch (dto.getElementType()) {
            case "2":
                return 1;
            case "4":
                return 2;
            case "6":
                return 3;
            case "33":
                return 4;
            case "1":
                return 5;
            case "5":
            case "25":
                return 6;
            case "8":
                return 7;
            case "29":
                return 8;
            case "3":
                return 9;
            default:
                return 0;
        }
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View view = inflater.inflate(R.layout.calc_three_two, parent, false);
                return new RadioViewHolder(view);
            case 2:
                View viewTwo = inflater.inflate(R.layout.calc_three_four, parent, false);
                return new DateViewHolder(viewTwo);
            case 3:
                View viewThree = inflater.inflate(R.layout.calc_three_siz, parent, false);
                return new PlaceViewHolder(viewThree);
            case 4:
                View viewFour = inflater.inflate(R.layout.calc_three_threethree, parent, false);
                return new SwitchViewHolder(viewFour);
            case 5:
                View viewFive = inflater.inflate(R.layout.calc_three_one, parent, false);
                return new SpinnerViewHolder(viewFive);
            case 6:
                View viewSix = inflater.inflate(R.layout.calc_three_five, parent, false);
                return new TextViewHolder(viewSix);
            case 7:
                View viewSeven = inflater.inflate(R.layout.calc_three_eight, parent, false);
                return new OccupationViewHolder(viewSeven);
            case 8:
                View viewEight = inflater.inflate(R.layout.calc_three_twonine, parent, false);
                return new StepViewHolder(viewEight);
            case 9:
                View viewNine = inflater.inflate(R.layout.calc_three_input, parent, false);
                return new InputViewHolder(viewNine);
            default:
                View viewD = inflater.inflate(R.layout.item_two_default, parent, false);
                return new DefaultViewHolder(viewD);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, final int position) {
        final PlatformThreeDataBean.ChildrenDTO.DataDTO bean = dataList.get(position);

        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        if ("12".equals(bean.getElementType())) {
            viewHolder.itemView.setVisibility(View.GONE);
            layoutParams.height = 0;
            layoutParams.width = 0;
        } else {
            viewHolder.itemView.setVisibility(View.VISIBLE);
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        viewHolder.itemView.setLayoutParams(layoutParams);

        int itemViewType = getItemViewType(position);
        if (itemViewType == 1) {
            RadioViewHolder holder = (RadioViewHolder) viewHolder;
            holder.calc_label.setText(bean.getElementName());
            holder.calc_radio.removeAllViews();
            String value = "";
            Object elementValue = bean.getElementValue();
            if (elementValue instanceof String) {
                value = (String) elementValue;
            }
            List<PlatformThreeDataBean.ChildrenDTO.DataDTO.ElementItemsDTO> elementItems = bean.getElementItems();
            if (elementItems != null) {
                for (final PlatformThreeDataBean.ChildrenDTO.DataDTO.ElementItemsDTO item : elementItems) {
                    View view = inflater.inflate(R.layout.dialog_input_item_btn, null);
                    TextView viewBtn = view.findViewById(R.id.input_btn);
                    if (value.equals(item.getValue())) {
                        viewBtn.setTextColor(ContextCompat.getColor(context, R.color.white));
                        viewBtn.setBackgroundResource(R.drawable.blue2_text_bg);
                        bean.setShowValue(item.getName());
                    } else {
                        viewBtn.setTextColor(ContextCompat.getColor(context, R.color.divi_color));
                        viewBtn.setBackgroundResource(R.drawable.grey_text_bg);
                    }
                    viewBtn.setText(item.getName());
                    viewBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bean.setElementValue(item.getValue());
                            bean.setShowValue(item.getName());
                            notifyItemChanged(position);
                        }
                    });
                    holder.calc_radio.addView(view);
                }
            }

        } else if (itemViewType == 2) {
            DateViewHolder holder = (DateViewHolder) viewHolder;
            holder.calc_label.setText(bean.getElementName());
            String value = "";
            Object elementValue = bean.getElementValue();
            if (elementValue instanceof String) {
                value = (String) elementValue;
            }
            holder.date_picker_str.setText(value);
            holder.date_picker_str.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    Object elementValue = bean.getElementValue();
                    if (elementValue instanceof String) {
                        String[] strings = ((String) elementValue).split("-");
                        try {
                            year = Integer.parseInt(strings[0]);
                            month = Integer.parseInt(strings[1]) - 1;
                            dayOfMonth = Integer.parseInt(strings[2]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            bean.setElementValue(year + "-" + (month + 1) + "-" + dayOfMonth);
                            notifyItemChanged(position);
                        }
                    }, year, month, dayOfMonth);
                    dialog.show();
                }
            });
        } else if (itemViewType == 3) {
            PlaceViewHolder holder = (PlaceViewHolder) viewHolder;
            holder.calc_label.setText(bean.getElementName());
            Object elementValue = bean.getElementValue();
            if (elementValue instanceof ArrayList) {
                ArrayList<Map<String, Object>> values = (ArrayList<Map<String, Object>>) elementValue;
                String showPlace = "";
                for (Map<String, Object> value : values) {
                    showPlace += value.get("name") + "-";
                }
                if (showPlace.length() > 0) {
                    holder.three_date_place.setText(showPlace.substring(0, showPlace.length() - 1));
                }
            }
            holder.three_date_place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPlaceListener != null) {
                        onPlaceListener.onPlaceClick(position, bean);
                    }
                }
            });
        } else if (itemViewType == 4) {
            final SwitchViewHolder holder = (SwitchViewHolder) viewHolder;
            holder.calc_label.setText(bean.getElementName());
            Object elementValue = bean.getElementValue();
            if (elementValue instanceof String) {
                String vaule = (String) elementValue;
                if ("2".equals(vaule)) {
                    holder.three_date_switch.setChecked(false);
                } else {
                    holder.three_date_switch.setChecked(true);
                }
                holder.three_date_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            bean.setElementValue("1");
                        } else {
                            bean.setElementValue("2");
                        }
                    }
                });
            }
        } else if (itemViewType == 5) {
            SpinnerViewHolder holder = (SpinnerViewHolder) viewHolder;
            holder.calc_label.setText(bean.getElementName());
            final List<PlatformThreeDataBean.ChildrenDTO.DataDTO.ElementItemsDTO> elementItems = bean.getElementItems();
            String value = "";
            Object elementValue = bean.getElementValue();
            if (elementValue instanceof String) {
                value = (String) elementValue;
            }
            List<String> itemStrs = new ArrayList<>();
            int selectIndex = -1;
            for (int i = 0; i < elementItems.size(); i++) {
                PlatformThreeDataBean.ChildrenDTO.DataDTO.ElementItemsDTO itemsDTO = elementItems.get(i);
                if (value.equals(itemsDTO.getValue())) {
                    bean.setShowValue(itemsDTO.getName());
                    selectIndex = i;
                }
                itemStrs.add(itemsDTO.getName());
            }
            ArrayAdapter<String> adapter
                    = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, itemStrs);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.three_date_spinner.setAdapter(adapter);
            holder.three_date_spinner.setSelection(selectIndex);
            holder.three_date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    PlatformThreeDataBean.ChildrenDTO.DataDTO.ElementItemsDTO selectItem = elementItems.get(pos);
                    bean.setElementValue(selectItem.getValue());
                    bean.setShowValue(selectItem.getName());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else if (itemViewType == 6) {
            TextViewHolder holder = (TextViewHolder) viewHolder;
            holder.calc_label.setText(bean.getElementName());
            String elementDescribe = bean.getElementDescribe();
            if (!TextUtils.isEmpty(elementDescribe)) {
                holder.date_text_show.setText(elementDescribe);
            } else {
                String value = "-";
                Object elementValue = bean.getElementValue();
                if (elementValue instanceof String) {
                    value = (String) elementValue;
                }
                holder.date_text_show.setText(value);
            }
        } else if (itemViewType == 7) {
            OccupationViewHolder holder = (OccupationViewHolder) viewHolder;
            holder.calc_label.setText(bean.getElementName());
            Object elementValue = bean.getElementValue();
            String occuptionName = "";
            if (elementValue instanceof ArrayList) {
                ArrayList<Map<String, String>> maps = (ArrayList<Map<String, String>>) elementValue;
                for (Map<String, String> map : maps) {
                    occuptionName += map.get("name") + "-";
                }
                if (occuptionName.length() > 0) {
                    holder.three_date_occupation.setText(occuptionName.substring(0, occuptionName.length() - 1));
                }
            }
            holder.three_date_occupation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPlaceListener != null) {
                        onPlaceListener.onOccupationClick(position, bean);
                    }
                }
            });
        } else if (itemViewType == 8) {
            StepViewHolder holder = (StepViewHolder) viewHolder;
            holder.calc_label.setText(bean.getElementName());
            final String min = bean.getMin();
            final String max = bean.getMax();
            final String step = bean.getStep();
            String suffix = bean.getSuffix();
            holder.step_min_max.setText("范围：" + min + " ~ " + max + suffix);
            String value = "";
            Object elementValue = bean.getElementValue();
            if (elementValue instanceof String) {
                value = (String) elementValue;
            }
            holder.calc_date_input.setText(value + suffix);
            holder.strp_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Integer minI = Integer.parseInt(min);
                        Integer stepI = Integer.parseInt(step);
                        Integer valueI = 0;
                        Object elementValue = bean.getElementValue();
                        if (elementValue instanceof String) {
                            valueI = Integer.parseInt((String) elementValue);
                        }
                        if (valueI <= minI) {
                            Toast.makeText(context, "已经是最小值啦", Toast.LENGTH_SHORT).show();
                        } else {
                            valueI -= stepI;
                            bean.setElementValue(String.valueOf(valueI));
                            notifyItemChanged(position);
                        }
                    } catch (Exception e) {

                    }
                }
            });
            holder.strp_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Integer maxI = Integer.parseInt(max);
                        Integer stepI = Integer.parseInt(step);
                        Integer valueI = 0;
                        Object elementValue = bean.getElementValue();
                        if (elementValue instanceof String) {
                            valueI = Integer.parseInt((String) elementValue);
                        }
                        if (valueI >= maxI) {
                            Toast.makeText(context, "已经是最大值啦", Toast.LENGTH_SHORT).show();
                        } else {
                            valueI += stepI;
                            bean.setElementValue(String.valueOf(valueI));
                            notifyItemChanged(position);
                        }
                    } catch (Exception e) {

                    }
                }
            });
        } else if (itemViewType == 9) {
            InputViewHolder holder = (InputViewHolder) viewHolder;
            holder.calc_label.setText(bean.getElementName());
            String value = "";
            Object elementValue = bean.getElementValue();
            if (elementValue instanceof String) {
                value = (String) elementValue;
                holder.three_date_input.setText(value);
            }
            holder.three_date_input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setElementValue(s.toString());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addAll(List<PlatformThreeDataBean.ChildrenDTO.DataDTO> data) {
        this.dataList = data;
        notifyDataSetChanged();
    }

    public List<PlatformThreeDataBean.ChildrenDTO.DataDTO> getAll() {
        return this.dataList;
    }

    public void setThreeDataBeans(List<PlatformThreeDataBean> threeDataBeans) {
        this.threeDataBeans = threeDataBeans;
    }

    public List<PlatformThreeDataBean> getThreeDataBeans() {
        return threeDataBeans;
    }

    static class RadioViewHolder extends RecyclerView.ViewHolder {
        private TextView calc_label;
        private FlexboxLayout calc_radio;

        public RadioViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            calc_label = itemView.findViewById(R.id.calc_label);
            calc_radio = itemView.findViewById(R.id.calc_radio);
        }
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        private TextView calc_label;
        private TextView date_picker_str;

        public DateViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            calc_label = itemView.findViewById(R.id.calc_label);
            date_picker_str = itemView.findViewById(R.id.date_picker_str);
        }
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        private TextView calc_label;
        private TextView three_date_place;

        public PlaceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            calc_label = itemView.findViewById(R.id.calc_label);
            three_date_place = itemView.findViewById(R.id.three_date_place);
        }
    }

    static class SwitchViewHolder extends RecyclerView.ViewHolder {
        private TextView calc_label;
        private Switch three_date_switch;

        public SwitchViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            calc_label = itemView.findViewById(R.id.calc_label);
            three_date_switch = itemView.findViewById(R.id.three_date_switch);
        }
    }

    static class SpinnerViewHolder extends RecyclerView.ViewHolder {
        private TextView calc_label;
        private Spinner three_date_spinner;

        public SpinnerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            calc_label = itemView.findViewById(R.id.calc_label);
            three_date_spinner = itemView.findViewById(R.id.three_date_spinner);
        }
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        private TextView calc_label;
        private TextView date_text_show;

        public TextViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            calc_label = itemView.findViewById(R.id.calc_label);
            date_text_show = itemView.findViewById(R.id.date_text_show);
        }
    }

    static class OccupationViewHolder extends RecyclerView.ViewHolder {
        private TextView calc_label;
        private TextView three_date_occupation;

        public OccupationViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            calc_label = itemView.findViewById(R.id.calc_label);
            three_date_occupation = itemView.findViewById(R.id.three_date_occupation);
        }
    }

    static class StepViewHolder extends RecyclerView.ViewHolder {
        private TextView calc_label;
        private TextView step_min_max;
        private TextView strp_reduce;
        private TextView calc_date_input;
        private TextView strp_plus;

        public StepViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            calc_label = itemView.findViewById(R.id.calc_label);
            step_min_max = itemView.findViewById(R.id.step_min_max);
            strp_reduce = itemView.findViewById(R.id.strp_reduce);
            calc_date_input = itemView.findViewById(R.id.calc_date_input);
            strp_plus = itemView.findViewById(R.id.strp_plus);
        }
    }

    static class InputViewHolder extends RecyclerView.ViewHolder {
        private TextView calc_label;
        private EditText three_date_input;

        public InputViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            calc_label = itemView.findViewById(R.id.calc_label);
            three_date_input = itemView.findViewById(R.id.three_date_input);
        }
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        public DefaultViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }

    public interface OnPlaceListener {
        void onPlaceClick(int position, PlatformThreeDataBean.ChildrenDTO.DataDTO bean);

        void onOccupationClick(int position, PlatformThreeDataBean.ChildrenDTO.DataDTO bean);
    }
}