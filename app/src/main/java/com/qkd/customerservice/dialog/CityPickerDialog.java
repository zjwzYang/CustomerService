package com.qkd.customerservice.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.CityPickAreaAdapter;
import com.qkd.customerservice.adapter.CityPickCityAdapter;
import com.qkd.customerservice.adapter.CityPickProvinceAdapter;
import com.qkd.customerservice.bean.PlatformTwoDataBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created on 2021/6/9 09:25
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class CityPickerDialog extends DialogFragment implements CityPickAreaAdapter.OnclickAreaListener,
        CityPickProvinceAdapter.OnclickProvinceListener, CityPickCityAdapter.OnclickCityListener {

    private RecyclerView city_recy_province;
    private RecyclerView city_recy_city;
    private RecyclerView city_recy_area;
    private CityPickProvinceAdapter provinceAdapter;
    private CityPickCityAdapter cityAdapter;
    private CityPickAreaAdapter areaAdapter;

    private PlatformTwoDataBean.RestrictGenesDTO bean;
    private int position;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_city_picker, container, false);
        EventBus.getDefault().register(this);
        city_recy_province = view.findViewById(R.id.city_recy_one);
        city_recy_province.setLayoutManager(new LinearLayoutManager(getContext()));
        provinceAdapter = new CityPickProvinceAdapter(getContext());
        provinceAdapter.setOnclickProvinceListener(this);
        city_recy_province.setAdapter(provinceAdapter);

        city_recy_city = view.findViewById(R.id.city_recy_two);
        city_recy_city.setLayoutManager(new LinearLayoutManager(getContext()));
        cityAdapter = new CityPickCityAdapter(getContext());
        cityAdapter.setOnclickCityListener(this);
        city_recy_city.setAdapter(cityAdapter);

        city_recy_area = view.findViewById(R.id.city_recy_three);
        city_recy_area.setLayoutManager(new LinearLayoutManager(getContext()));
        areaAdapter = new CityPickAreaAdapter(getContext());
        areaAdapter.setOnclickAreaListener(this);
        city_recy_area.setAdapter(areaAdapter);

        Bundle bundle = getArguments();
        bean = bundle.getParcelable("bean");
        position = bundle.getInt("position");
        formatData();

        return view;
    }

    private void formatData() {
        List<PlatformTwoDataBean.RestrictGenesDTO.SubRestrictGeneBean> subGenes = bean.getSubRestrictGenes();
        if (subGenes == null || subGenes.size() == 0) {
            city_recy_province.setVisibility(View.GONE);
            city_recy_city.setVisibility(View.GONE);
            city_recy_area.setVisibility(View.VISIBLE);
            areaAdapter.setBean(bean);

        } else if (subGenes.size() == 1) {
            city_recy_province.setVisibility(View.VISIBLE);
            provinceAdapter.setBean(subGenes.get(0));
            city_recy_city.setVisibility(View.GONE);
            city_recy_area.setVisibility(View.VISIBLE);
            areaAdapter.setBean(bean);

        } else if (subGenes.size() == 2) {
            city_recy_province.setVisibility(View.VISIBLE);
            provinceAdapter.setBean(subGenes.get(0));
            city_recy_city.setVisibility(View.VISIBLE);
            cityAdapter.setBean(subGenes.get(1));
            city_recy_area.setVisibility(View.VISIBLE);
            areaAdapter.setBean(bean);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetAreaMsg(PlatformTwoDataBean.RestrictGenesDTO bean) {
        if (TextUtils.isEmpty(bean.getCityChangeKey())) {
            this.bean = bean;
            this.position = bean.getPosition();
            formatData();
        }
    }

    @Override
    public void dismiss() {
        EventBus.getDefault().unregister(this);
        super.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setWindowAnimations(R.style.animate_dialog);
            setCancelable(true);
        }
    }

    @Override
    public void onClickArea(String key) {
        bean.setPosition(position);
        bean.setCityChangeKey(key);
        EventBus.getDefault().post(bean);
        dismiss();
    }

    @Override
    public void onClickProvince(String key) {
        bean.setPosition(position);
        bean.setCityChangeKey(key);
        EventBus.getDefault().post(bean);
    }

    @Override
    public void onClickCity(String key) {
        bean.setPosition(position);
        bean.setCityChangeKey(key);
        EventBus.getDefault().post(bean);
    }
}