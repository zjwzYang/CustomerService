package com.qkd.customerservice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.CalcAdapter;
import com.qkd.customerservice.adapter.CalcThreeAdapter;
import com.qkd.customerservice.adapter.CalcTwoAdapter;
import com.qkd.customerservice.bean.CalcSuccessBean;
import com.qkd.customerservice.bean.CalcTwoSuccessBean;
import com.qkd.customerservice.bean.CovereAreaBean;
import com.qkd.customerservice.bean.OccupationBean;
import com.qkd.customerservice.bean.PlatformThreeDataBean;
import com.qkd.customerservice.bean.PlatformThreeOutput;
import com.qkd.customerservice.bean.PlatformTwoDataBean;
import com.qkd.customerservice.bean.PostCalcBean;
import com.qkd.customerservice.bean.PostTrialPremiumInput;
import com.qkd.customerservice.bean.PostTrialPremiumOutput;
import com.qkd.customerservice.bean.PostTrialPremiumThreeInput;
import com.qkd.customerservice.bean.PostTrialPremiumTwoInput;
import com.qkd.customerservice.bean.PrialPremiumTwoOutput;
import com.qkd.customerservice.bean.TrialFactorBean;
import com.qkd.customerservice.bean.TrialFactorCityBean;
import com.qkd.customerservice.bean.TrialFactorCityStringBean;
import com.qkd.customerservice.bean.TrialFactorFatherBean;
import com.qkd.customerservice.bean.TrialFactorOutput;
import com.qkd.customerservice.bean.TrialOccupationBean;
import com.qkd.customerservice.bean.TrialOccupationStringBean;
import com.qkd.customerservice.dialog.CityPickerDialog;
import com.qkd.customerservice.dialog.OccupationThreepickerDialog;
import com.qkd.customerservice.net.BaseHttp;
import com.qkd.customerservice.widget.CalcItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 2021/5/10 13:24
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class ProductCalcActivity extends AppCompatActivity implements CalcTwoAdapter.OnClickCalcTwoListener
        , CalcThreeAdapter.OnPlaceListener {

    private String platformId;
    private String platformProductId;
    private String templateContent;
    private RecyclerView mRecyclerView;
    private CalcAdapter mAdapter;

    private CalcTwoAdapter mTwoAdapter;

    private CalcThreeAdapter mThreeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_calc);
        EventBus.getDefault().register(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mRecyclerView = findViewById(R.id.calc_recy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        Intent intent = getIntent();
        platformId = intent.getStringExtra("platformId");
        platformProductId = intent.getStringExtra("platformProductId");
        templateContent = intent.getStringExtra("templateContent");

        if ("1".equals(platformId)) {
            mAdapter = new CalcAdapter(this, getSupportFragmentManager());
            mRecyclerView.setAdapter(mAdapter);
        } else if ("2".equals(platformId)) {
            mTwoAdapter = new CalcTwoAdapter(this, getSupportFragmentManager());
            mTwoAdapter.setOnClickCalcTwoListener(this);
            mRecyclerView.setAdapter(mTwoAdapter);
        } else if ("3".equals(platformId)) {
            mThreeAdapter = new CalcThreeAdapter(this);
            mThreeAdapter.setOnPlaceListener(this);
            mRecyclerView.setAdapter(mThreeAdapter);
            mRecyclerView.addItemDecoration(new CalcItemDecoration(mThreeAdapter, this));
        }

        setTitle(intent.getStringExtra("productName") + " - 保费试算");

        Observable<TrialFactorOutput> factor = BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).getTrialFactor(platformProductId, platformId);
        factor.subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .subscribe(new Observer<TrialFactorOutput>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TrialFactorOutput output) {
                        Gson gson = new Gson();
                        if ("1".equals(platformId)) {
                            JsonParser jsonParser = new JsonParser();
                            JsonArray jsonElements = jsonParser.parse(output.getData()).getAsJsonArray();//获取JsonArray对象
                            List<TrialFactorFatherBean> trialFactorBeans = new ArrayList<>();
                            for (JsonElement bean : jsonElements) {
                                try {
                                    TrialFactorFatherBean fatherBean = gson.fromJson(bean, TrialFactorFatherBean.class);
                                    if ("citypiker".equals(fatherBean.getWidget())) {
                                        try {
                                            TrialFactorCityBean cityBean = gson.fromJson(bean, TrialFactorCityBean.class);
                                            trialFactorBeans.add(cityBean);
                                        } catch (Exception e) {
                                            TrialFactorCityStringBean cityStrBean = gson.fromJson(bean, TrialFactorCityStringBean.class);
                                            String strValue = cityStrBean.getValue();
                                            TrialFactorCityBean.ValueDTO valueDTO = gson.fromJson(strValue, TrialFactorCityBean.ValueDTO.class);
                                            TrialFactorCityBean cityBean = new TrialFactorCityBean();
                                            cityBean.setWidget(cityStrBean.getWidget());
                                            cityBean.setName(cityStrBean.getName());
                                            cityBean.setLabel(cityStrBean.getLabel());
                                            cityBean.setType(cityStrBean.getType());
                                            cityBean.setValue(valueDTO);
                                            cityBean.setDetail(cityStrBean.getDetail());
                                            trialFactorBeans.add(cityBean);
                                        }
                                    } else if ("occupationpicker".equals(fatherBean.getWidget())) {
                                        try {
                                            TrialOccupationBean trialOccupationBean = gson.fromJson(bean, TrialOccupationBean.class);
                                            trialFactorBeans.add(trialOccupationBean);
                                        } catch (Exception e) {
                                            TrialOccupationStringBean trialOccupationStringBean = gson.fromJson(bean, TrialOccupationStringBean.class);
                                            String value = trialOccupationStringBean.getValue();
                                            TrialOccupationBean.ValueBean valueBean = gson.fromJson(value, TrialOccupationBean.ValueBean.class);
                                            TrialOccupationBean occupationBean = new TrialOccupationBean();
                                            occupationBean.setWidget(trialOccupationStringBean.getWidget());
                                            occupationBean.setName(trialOccupationStringBean.getName());
                                            occupationBean.setLabel(trialOccupationStringBean.getLabel());
                                            occupationBean.setType(trialOccupationStringBean.getType());
                                            occupationBean.setValue(valueBean);
                                            occupationBean.setDetail(trialOccupationStringBean.getDetail());
                                            trialFactorBeans.add(occupationBean);
                                        }
                                    } else {
                                        TrialFactorBean bean1 = gson.fromJson(bean, TrialFactorBean.class);//解析
                                        String widget = bean1.getWidget();
                                        if (!"cash".equals(widget)) {
                                            trialFactorBeans.add(bean1);
                                        }
                                        if ("A_EXEMPT".equals(bean1.getName())) {
                                            mAdapter.setA_EXEMPT_VALUE(bean1.getValue());
                                        }
                                    }
                                } catch (Exception e) {
                                    Log.i("12345678", "getItemViewType: " + e.getMessage());
                                }
                            }
                            doWithCityDetail(trialFactorBeans);
                            mAdapter.addAll(trialFactorBeans);
                            //initView(trialFactorBeans);
                        } else if ("2".equals(platformId)) {
                            PlatformTwoDataBean twoDataBean = gson.fromJson(output.getData(), PlatformTwoDataBean.class);
                            mTwoAdapter.addAll(twoDataBean);
                        } else if ("3".equals(platformId)) {
                            JsonParser jsonParser = new JsonParser();
                            JsonArray jsonElements = jsonParser.parse(output.getData()).getAsJsonArray();//获取JsonArray对象
                            List<PlatformThreeDataBean.ChildrenDTO.DataDTO> datas = new ArrayList<>();
                            List<PlatformThreeDataBean> threeDataBeans = new ArrayList<>();
                            for (JsonElement bean : jsonElements) {
                                PlatformThreeDataBean platThreeBean = gson.fromJson(bean, PlatformThreeDataBean.class);
                                String moduleCode = platThreeBean.getModuleCode();
                                String moduleName = platThreeBean.getModuleName();
                                if ("addInsureder".equals(moduleCode)) {
                                    continue;
                                }
                                List<PlatformThreeDataBean.ChildrenDTO> children = platThreeBean.getChildren();
                                threeDataBeans.add(platThreeBean);
                                if (children != null) {
                                    for (int i = 0; i < children.size(); i++) {
                                        PlatformThreeDataBean.ChildrenDTO child = children.get(i);
                                        List<PlatformThreeDataBean.ChildrenDTO.DataDTO> childDatas = child.getData();
                                        for (PlatformThreeDataBean.ChildrenDTO.DataDTO childData : childDatas) {
                                            childData.setModuleCode(moduleCode);
                                            childData.setModuleName(moduleName);
                                            childData.setIndex(i);
                                            datas.add(childData);
                                        }
                                    }
                                }
                                List<PlatformThreeDataBean.ChildrenDTO.DataDTO> dataValues = platThreeBean.getData();
                                if (dataValues != null) {
                                    for (PlatformThreeDataBean.ChildrenDTO.DataDTO dataValue : dataValues) {
                                        dataValue.setModuleCode(moduleCode);
                                        dataValue.setModuleName(moduleName);
                                        datas.add(dataValue);
                                    }
                                }
                            }
                            mThreeAdapter.setThreeDataBeans(threeDataBeans);
                            mThreeAdapter.addAll(datas);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void doWithCityDetail(List<TrialFactorFatherBean> beans) {
        List<TrialFactorCityBean.DetailDTO> temDetail = new ArrayList<>();
        for (TrialFactorFatherBean bean : beans) {
            String widget = bean.getWidget();
            if ("citypiker".equals(widget)) {
                TrialFactorCityBean cityBean = (TrialFactorCityBean) bean;
                List<TrialFactorCityBean.DetailDTO> detail = cityBean.getDetail();
                if (detail != null && detail.size() > 0) {
                    temDetail = detail;
                }
            }
        }
        for (TrialFactorFatherBean bean : beans) {
            String widget = bean.getWidget();
            if ("citypiker".equals(widget)) {
                TrialFactorCityBean cityBean = (TrialFactorCityBean) bean;
                List<TrialFactorCityBean.DetailDTO> detail = cityBean.getDetail();
                if (detail == null || detail.size() == 0) {
                    ((TrialFactorCityBean) bean).setDetail(temDetail);
                }
            }
        }
    }

    private void productCalc() {
        PostTrialPremiumInput input = new PostTrialPremiumInput();
        input.setPlatformId(platformId);
        input.setProductId(platformProductId);
        Map<String, Object> map = new HashMap<>();
        final List<TrialFactorFatherBean> fatherBeans = mAdapter.getAll();
        String a_exempt_value = mAdapter.getA_EXEMPT_VALUE();
        for (TrialFactorFatherBean fatherBean : fatherBeans) {
            if (fatherBean instanceof TrialFactorBean) {
                TrialFactorBean factorBean = (TrialFactorBean) fatherBean;
                String value = factorBean.getValue();
                String name = factorBean.getName();

                if (TextUtils.isEmpty(value)) {
                } else {
                    if ("N".equals(a_exempt_value)) {
                        if ("A_EXEMPT".equals(name)) {
                            map.put(name, value);
                        } else if (name.startsWith("A_")) {

                        } else {
                            map.put(name, value);
                        }
                    } else {
                        map.put(name, value);
                    }
                }
            } else if (fatherBean instanceof TrialFactorCityBean) {
                TrialFactorCityBean cityBean = (TrialFactorCityBean) fatherBean;
                String name = cityBean.getName();
                TrialFactorCityBean.ValueDTO valueBean = cityBean.getValue();
                map.put(name, valueBean);
            } else if (fatherBean instanceof TrialOccupationBean) {
                TrialOccupationBean occBean = (TrialOccupationBean) fatherBean;
                String name = occBean.getName();
                TrialOccupationBean.ValueBean valueBean = occBean.getValue();
                map.put(name, valueBean);
            }
        }
        input.setFactorParams(map);
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).postTrialPremium(input), new BaseHttp.HttpObserver<PostTrialPremiumOutput>() {
            @Override
            public void onSuccess(PostTrialPremiumOutput output) {
                if (output.isSuccess()) {
                    if (!TextUtils.isEmpty(output.getData())) {
                        Gson gson = new Gson();
                        PostCalcBean postCalcBean = gson.fromJson(output.getData(), PostCalcBean.class);
                        CalcSuccessBean bean = new CalcSuccessBean();
                        bean.setPrice(String.valueOf(postCalcBean.getPrice()));
                        bean.setFactorBeans(fatherBeans);
                        bean.setTemplateContent(templateContent);
                        EventBus.getDefault().post(bean);
                        finish();
                    }
                } else {
                    Toast.makeText(ProductCalcActivity.this, output.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError() {

            }
        });
    }

    private void productCalcTwo() {
        CalcTwoSuccessBean bean = new CalcTwoSuccessBean();
        PlatformTwoDataBean twoDataBean = mTwoAdapter.getTwoDataBean();
        List<PlatformTwoDataBean.PriceArgsDTO.GenesDTO> genes = twoDataBean.getPriceArgs().getGenes();
        bean.setValueBao("--");
        bean.setValueQi("--");
        bean.setValueNian("--");
        for (PlatformTwoDataBean.PriceArgsDTO.GenesDTO gene : genes) {
            if (!TextUtils.isEmpty(gene.getProtectItemId())) {
                bean.setValueBao(gene.getValue());
            } else if ("insurantDateLimit".equals(gene.getKey())) {
                bean.setValueQi(gene.getValue());
            } else if ("insureAgeLimit".equals(gene.getKey())) {
                bean.setValueNian(gene.getValue());
            }
        }
        bean.setPrice(changeF2Y(twoDataBean.getPrice()));
        EventBus.getDefault().post(bean);
        finish();
    }

    /**
     * 分转元，转换为bigDecimal在toString
     *
     * @return
     */
    public String changeF2Y(int price) {
        return BigDecimal.valueOf(Long.valueOf(price)).divide(new BigDecimal(100)).toString();
    }

    private void productCalcThree() {
        // 保额
        String coverage = "-";
        // 保险期
        String insurancePeriod = "-";
        // 交费期
        String paymentPeriod = "-";

        PostTrialPremiumThreeInput input = new PostTrialPremiumThreeInput();
        Map<String, Object> factorParams = new HashMap<>();
        List<PlatformThreeDataBean> threeDataBeans = mThreeAdapter.getThreeDataBeans();
        List<PlatformThreeDataBean.ChildrenDTO.DataDTO> dataDTOS = mThreeAdapter.getAll();
        for (PlatformThreeDataBean threeDataBean : threeDataBeans) {

            String moduleCode = threeDataBean.getModuleCode();

            List<PlatformThreeDataBean.ChildrenDTO> children = threeDataBean.getChildren();
            List<PlatformThreeDataBean.ChildrenDTO.DataDTO> datas = threeDataBean.getData();
            if (children != null && children.size() > 0) {
                List<Map<String, Object>> maps = new ArrayList<>();
                for (int i = 0; i < children.size(); i++) {
                    Map<String, Object> childMap = new HashMap<>();
                    PlatformThreeDataBean.ChildrenDTO child = children.get(i);
                    List<PlatformThreeDataBean.ChildrenDTO.DataDTO> childDatas = child.getData();
                    for (PlatformThreeDataBean.ChildrenDTO.DataDTO childData : childDatas) {
                        for (PlatformThreeDataBean.ChildrenDTO.DataDTO dataDTO : dataDTOS) {
                            if (moduleCode.equals(dataDTO.getModuleCode()) && i == dataDTO.getIndex()) {
                                String dateCode = dataDTO.getElementCode();
                                Object dateValue = dataDTO.getElementValue();
                                if ("coverage".equals(dateCode)) {
                                    coverage = dataDTO.getShowValue();
                                } else if ("insurancePeriod".equals(dateCode)) {
                                    insurancePeriod = dataDTO.getShowValue();
                                } else if ("paymentPeriod".equals(dateCode)) {
                                    paymentPeriod = dataDTO.getShowValue();
                                }
                                childMap.put(dateCode, dateValue);
                            }
                        }
                    }
                    maps.add(childMap);
                }
                factorParams.put(moduleCode, maps);
            } else if (datas != null) {
                Map<String, Object> dateMap = new HashMap<>();
                for (PlatformThreeDataBean.ChildrenDTO.DataDTO dataDTO : dataDTOS) {
                    if (moduleCode.equals(dataDTO.getModuleCode())) {
                        String dateCode = dataDTO.getElementCode();
                        Object dateValue = dataDTO.getElementValue();
                        if ("coverage".equals(dateCode)) {
                            coverage = dataDTO.getShowValue();
                        } else if ("insurancePeriod".equals(dateCode)) {
                            insurancePeriod = dataDTO.getShowValue();
                        } else if ("paymentPeriod".equals(dateCode)) {
                            paymentPeriod = dataDTO.getShowValue();
                        }
                        dateMap.put(dateCode, dateValue);
                    }
                }
                factorParams.put(moduleCode, dateMap);
            }
        }
        input.setFactorParams(factorParams);
        input.setPlatformId(platformId);
        input.setProductId(platformProductId);

        final String finalCoverage = coverage;
        final String finalInsurancePeriod = insurancePeriod;
        final String finalPaymentPeriod = paymentPeriod;
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).postTrialPremiumTwo(input), new BaseHttp.HttpObserver<PrialPremiumTwoOutput>() {
            @Override
            public void onSuccess(PrialPremiumTwoOutput output) {
                if (output.isSuccess()) {
                    if (!TextUtils.isEmpty(output.getData())) {
                        Gson gson = new Gson();
                        PlatformThreeOutput platformThreeOutput = gson.fromJson(output.getData(), PlatformThreeOutput.class);
                        CalcTwoSuccessBean calcTwoSuccessBean = new CalcTwoSuccessBean();
                        calcTwoSuccessBean.setPrice(platformThreeOutput.getData().getFee());
                        calcTwoSuccessBean.setValueBao(finalCoverage);
                        calcTwoSuccessBean.setValueQi(finalInsurancePeriod);
                        calcTwoSuccessBean.setValueNian(finalPaymentPeriod);
                        EventBus.getDefault().post(calcTwoSuccessBean);
                        finish();
                    }
                } else {
                    Toast.makeText(ProductCalcActivity.this, output.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_calc:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示")
                        .setMessage("是否提交？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if ("1".equals(platformId)) {
                                    productCalc();
                                } else if ("2".equals(platformId)) {
                                    productCalcTwo();
                                } else if ("3".equals(platformId)) {
                                    productCalcThree();
                                }
                            }
                        }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_calc_menu, menu);
        return true;
    }

    @Override
    public void onSelect(final String key, final int position) {
        PostTrialPremiumTwoInput input = new PostTrialPremiumTwoInput();
        final PlatformTwoDataBean twoDataBean = mTwoAdapter.getTwoDataBean();
        List<PlatformTwoDataBean.PriceArgsDTO.GenesDTO> genes = twoDataBean.getPriceArgs().getGenes();
        PlatformTwoDataBean.PriceArgsDTO.GenesDTO oldGene = null;
        for (int i = 0; i < genes.size(); i++) {
            PlatformTwoDataBean.PriceArgsDTO.GenesDTO genesDTO = genes.get(i);
            String key1 = genesDTO.getKey();
            String protectItemId = genesDTO.getProtectItemId();
            if (!TextUtils.isEmpty(key1)) {
                if (key1.equals(key)) {
                    oldGene = genesDTO;
                    break;
                }
            } else if (!TextUtils.isEmpty(protectItemId)) {
                if (protectItemId.equals(key)) {
                    oldGene = genesDTO;
                }
            }
        }
        input.setOldRestrictGene(oldGene);
        input.setPlatformId(platformId);
        input.setProductId(twoDataBean.getCaseCode());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        input.setStartDate(format.format(new Date()));

        List<PlatformTwoDataBean.PriceArgsDTO.GenesDTO> newRestrictGenes = new ArrayList<>();
        final List<PlatformTwoDataBean.RestrictGenesDTO> restrictGenes = twoDataBean.getRestrictGenes();
        for (PlatformTwoDataBean.RestrictGenesDTO restrictGene : restrictGenes) {
            PlatformTwoDataBean.PriceArgsDTO.GenesDTO gene = new PlatformTwoDataBean.PriceArgsDTO.GenesDTO();
            gene.setKey(restrictGene.getKey());
            gene.setSort(restrictGene.getSort());
            gene.setProtectItemId(restrictGene.getProtectItemId());
            gene.setValue(restrictGene.getDefaultValue());
            newRestrictGenes.add(gene);
            List<PlatformTwoDataBean.RestrictGenesDTO.SubRestrictGeneBean> subRestrictGenes = restrictGene.getSubRestrictGenes();
            if (subRestrictGenes != null && subRestrictGenes.size() > 0) {
                for (PlatformTwoDataBean.RestrictGenesDTO.SubRestrictGeneBean subRestrictGene : subRestrictGenes) {
                    PlatformTwoDataBean.PriceArgsDTO.GenesDTO subGene = new PlatformTwoDataBean.PriceArgsDTO.GenesDTO();
                    subGene.setValue(subRestrictGene.getDefaultValue());
                    subGene.setKey(subRestrictGene.getKey());
                    newRestrictGenes.add(subGene);
                }
            }
        }
        input.setNewRestrictGenes(newRestrictGenes);

        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).postTrialPremiumTwo(input), new BaseHttp.HttpObserver<PrialPremiumTwoOutput>() {
            @Override
            public void onSuccess(PrialPremiumTwoOutput output) {
                if (!output.isSuccess()) {
                    Toast.makeText(ProductCalcActivity.this, output.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
                Gson gson = new Gson();
                PlatformTwoDataBean bean = gson.fromJson(output.getData(), PlatformTwoDataBean.class);
                twoDataBean.setPrice(bean.getPrice());
                twoDataBean.setPriceArgs(bean.getPriceArgs());
                List<PlatformTwoDataBean.RestrictGenesDTO> returnList = bean.getRestrictGenes();
                if (returnList != null && returnList.size() > 0) {
                    for (PlatformTwoDataBean.RestrictGenesDTO newGene : returnList) {
                        String key1 = newGene.getKey();
                        String protectItemId1 = newGene.getProtectItemId();
                        for (int i = 0; i < restrictGenes.size(); i++) {
                            PlatformTwoDataBean.RestrictGenesDTO oldGene = restrictGenes.get(i);
                            String key2 = oldGene.getKey();
                            String protectItemId2 = oldGene.getProtectItemId();
                            if (!TextUtils.isEmpty(key1)) {
                                if (key1.equals(key2)) {
                                    newGene.setPosition(i);
                                    if ("area".equals(key1) || "city".equals(key1)) {
                                        EventBus.getDefault().post(newGene);
                                    }
                                    restrictGenes.set(i, newGene);
                                }
                            } else if (!TextUtils.isEmpty(protectItemId1)) {
                                if (protectItemId1.equals(protectItemId2)) {
                                    newGene.setPosition(i);
                                    if ("area".equals(key1) || "city".equals(key1)) {
                                        EventBus.getDefault().post(newGene);
                                    }
                                    restrictGenes.set(i, newGene);
                                }
                            }
                        }
                    }
                }
                mTwoAdapter.notifyDataSetChanged();
//                if ("province".equals(key) && position >= 0) {
//                    mTwoAdapter.selectCity(position);
//                }

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onSelectCity(PlatformTwoDataBean.RestrictGenesDTO bean, int position) {
        CityPickerDialog dialog = new CityPickerDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("bean", bean);
        bundle.putInt("position", position);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "CityPickerDialog");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetAreaMsg(PlatformTwoDataBean.RestrictGenesDTO bean) {
        if (TextUtils.isEmpty(bean.getCityChangeKey())) {
            return;
        }
        PlatformTwoDataBean twoDataBean = mTwoAdapter.getTwoDataBean();
        List<PlatformTwoDataBean.RestrictGenesDTO> restrictGenes = twoDataBean.getRestrictGenes();
        restrictGenes.set(bean.getPosition(), bean);
        onSelect(bean.getCityChangeKey(), bean.getPosition());
    }

    @Override
    public void onPlaceClick(final int position, final PlatformThreeDataBean.ChildrenDTO.DataDTO bean) {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).getCoveredArea(platformProductId), new BaseHttp.HttpObserver<PrialPremiumTwoOutput>() {
            @Override
            public void onSuccess(PrialPremiumTwoOutput output) {
                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonElements = jsonParser.parse(output.getData()).getAsJsonArray();
                List<CovereAreaBean> covereAreaBeans = new ArrayList<>();
                for (JsonElement json : jsonElements) {
                    CovereAreaBean covereAreaBean = gson.fromJson(json, CovereAreaBean.class);
                    covereAreaBeans.add(covereAreaBean);
                }
                showCityDialog(covereAreaBeans, position, bean);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onOccupationClick(final int position, final PlatformThreeDataBean.ChildrenDTO.DataDTO bean) {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).getProfession(platformProductId), new BaseHttp.HttpObserver<PrialPremiumTwoOutput>() {
            @Override
            public void onSuccess(PrialPremiumTwoOutput output) {
                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();
                JsonArray jsonElements = jsonParser.parse(output.getData()).getAsJsonArray();
                List<OccupationBean> occupationBeans = new ArrayList<>();
                for (JsonElement json : jsonElements) {
                    OccupationBean occupationBean = gson.fromJson(json, OccupationBean.class);
                    occupationBeans.add(occupationBean);
                }
                //showOccupationDialog(occupationBeans, position, bean);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("detailList", (ArrayList<? extends Parcelable>) occupationBeans);
                OccupationThreepickerDialog dialog = new OccupationThreepickerDialog();
                dialog.setOnSelectOccpationListener(new OccupationThreepickerDialog.OnSelectOccpationListener() {
                    @Override
                    public void selectOccpation(List<Map<String, Object>> maps) {
                        bean.setElementValue(maps);
                        mThreeAdapter.notifyItemChanged(position);
                    }
                });
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "OccupationThreepickerDialog");
            }

            @Override
            public void onError() {

            }
        });
    }

    private int oldSize = 0;
    private int oldThreeSize = 0;
    private int oldTwoThreeSize = 0;

    private void showCityDialog(final List<CovereAreaBean> covereAreaBeans, final int position
            , final PlatformThreeDataBean.ChildrenDTO.DataDTO dateBean) {
        oldSize = 0;
        oldThreeSize = 0;
        oldTwoThreeSize = 0;
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_city_select, null);
        final NumberPicker onePicker = dialogView.findViewById(R.id.sort_one_picker);
        final NumberPicker twoPicker = dialogView.findViewById(R.id.sort_two_picker);
        final NumberPicker threePicker = dialogView.findViewById(R.id.sort_three_picker);
        onePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        twoPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        threePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        String[] ones = new String[covereAreaBeans.size()];
        for (int i = 0; i < covereAreaBeans.size(); i++) {
            ones[i] = covereAreaBeans.get(i).getName();
        }
        onePicker.setMinValue(0);
        onePicker.setMaxValue(covereAreaBeans.size() - 1);
        onePicker.setDisplayedValues(ones);
        onePicker.setWrapSelectorWheel(false);

        List<CovereAreaBean.ChildrenDTOX> oneList = covereAreaBeans.get(0).getChildren();
        if (oneList != null && oneList.size() != 0) {
            String[] twos = new String[oneList.size()];
            for (int j = 0; j < oneList.size(); j++) {
                twos[j] = oneList.get(j).getName();
            }
            twoPicker.setMinValue(0);
            twoPicker.setMaxValue(twos.length - 1);
            twoPicker.setDisplayedValues(twos);
            twoPicker.setWrapSelectorWheel(false);

            List<CovereAreaBean.ChildrenDTOX.ChildrenDTO> threeList = oneList.get(0).getChildren();
            if (threeList != null && threeList.size() != 0) {
                String[] threes = new String[threeList.size()];
                for (int i = 0; i < threeList.size(); i++) {
                    threes[i] = threeList.get(i).getName();
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
                List<CovereAreaBean.ChildrenDTOX> list = covereAreaBeans.get(newVal).getChildren();
                String[] twos = new String[list.size()];
                for (int j = 0; j < list.size(); j++) {
                    twos[j] = list.get(j).getName();
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

                List<CovereAreaBean.ChildrenDTOX.ChildrenDTO> threeList = list.get(0).getChildren();
                if (threeList != null && threeList.size() != 0) {
                    String[] threes = new String[threeList.size()];
                    for (int r = 0; r < threeList.size(); r++) {
                        threes[r] = threeList.get(r).getName();
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
                List<CovereAreaBean.ChildrenDTOX.ChildrenDTO> list = covereAreaBeans.get(one).getChildren().get(newVal).getChildren();
                String[] threes = new String[list.size()];
                for (int r = 0; r < list.size(); r++) {
                    threes[r] = list.get(r).getName();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(dialogView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int one = onePicker.getValue();
                        int two = twoPicker.getValue();
                        int three = threePicker.getValue();
                        CovereAreaBean bean = covereAreaBeans.get(one);
                        CovereAreaBean.ChildrenDTOX childrenDTOX = bean.getChildren().get(two);
                        CovereAreaBean.ChildrenDTOX.ChildrenDTO childrenDTO = childrenDTOX.getChildren().get(three);

                        Map<String, Object> oneMap = new HashMap<>();
                        oneMap.put("code", bean.getCode());
                        oneMap.put("level", bean.getLevel());
                        oneMap.put("name", bean.getName());

                        Map<String, Object> twoMap = new HashMap<>();
                        twoMap.put("code", childrenDTOX.getCode());
                        twoMap.put("level", childrenDTOX.getLevel());
                        twoMap.put("name", childrenDTOX.getName());

                        Map<String, Object> threeMap = new HashMap<>();
                        threeMap.put("code", childrenDTO.getCode());
                        threeMap.put("level", childrenDTO.getLevel());
                        threeMap.put("name", childrenDTO.getName());

                        List<Map<String, Object>> maps = new ArrayList<>();
                        maps.add(oneMap);
                        maps.add(twoMap);
                        maps.add(threeMap);
                        dateBean.setElementValue(maps);
                        mThreeAdapter.notifyItemChanged(position);

                    }
                }).setNegativeButton("取消", null);
        builder.show();
    }

}
