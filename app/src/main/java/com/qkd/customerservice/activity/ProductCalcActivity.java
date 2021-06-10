package com.qkd.customerservice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.qkd.customerservice.adapter.CalcTwoAdapter;
import com.qkd.customerservice.bean.CalcSuccessBean;
import com.qkd.customerservice.bean.CalcTwoSuccessBean;
import com.qkd.customerservice.bean.PlatformTwoDataBean;
import com.qkd.customerservice.bean.PostCalcBean;
import com.qkd.customerservice.bean.PostTrialPremiumInput;
import com.qkd.customerservice.bean.PostTrialPremiumOutput;
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
import com.qkd.customerservice.net.BaseHttp;

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
public class ProductCalcActivity extends AppCompatActivity implements CalcTwoAdapter.OnClickCalcTwoListener {

    private String platformId;
    private String platformProductId;
    private String templateContent;
    private RecyclerView mRecyclerView;
    private CalcAdapter mAdapter;

    private CalcTwoAdapter mTwoAdapter;

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
            mTwoAdapter = new CalcTwoAdapter(this);
            mTwoAdapter.setOnClickCalcTwoListener(this);
            mRecyclerView.setAdapter(mTwoAdapter);
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
}
