package com.qkd.customerservice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.qkd.customerservice.bean.CalcSuccessBean;
import com.qkd.customerservice.bean.PostCalcBean;
import com.qkd.customerservice.bean.PostTrialPremiumInput;
import com.qkd.customerservice.bean.PostTrialPremiumOutput;
import com.qkd.customerservice.bean.TrialFactorBean;
import com.qkd.customerservice.bean.TrialFactorCityBean;
import com.qkd.customerservice.bean.TrialFactorCityStringBean;
import com.qkd.customerservice.bean.TrialFactorFatherBean;
import com.qkd.customerservice.bean.TrialFactorOutput;
import com.qkd.customerservice.net.BaseHttp;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
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
public class ProductCalcActivity extends AppCompatActivity {

    private String platformId;
    private String platformProductId;
    private String templateContent;
    private RecyclerView mRecyclerView;
    private CalcAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_calc);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mRecyclerView = findViewById(R.id.calc_recy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CalcAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        Intent intent = getIntent();
        platformId = intent.getStringExtra("platformId");
        platformProductId = intent.getStringExtra("platformProductId");
        templateContent = intent.getStringExtra("templateContent");

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
                                } else {
                                    TrialFactorBean bean1 = gson.fromJson(bean, TrialFactorBean.class);//解析
                                    String widget = bean1.getWidget();
                                    if (!"hidden".equals(widget) && !"cash".equals(widget)) {
                                        trialFactorBeans.add(bean1);
                                    }
                                    if ("A_EXEMPT".equals(bean1.getName())) {
                                        mAdapter.setA_EXEMPT_VALUE(bean1.getValue());
                                    }
                                }
                            } catch (Exception e) {

                            }
                        }
                        doWithCityDetail(trialFactorBeans);
                        mAdapter.addAll(trialFactorBeans);
                        //initView(trialFactorBeans);
                    }
                });
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
                                productCalc();
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
}
