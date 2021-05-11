package com.qkd.customerservice.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.AddProductAdapter;
import com.qkd.customerservice.bean.AmountInput;
import com.qkd.customerservice.bean.AmountOutput;
import com.qkd.customerservice.bean.CalcSuccessBean;
import com.qkd.customerservice.bean.MySchemeDetailOutput;
import com.qkd.customerservice.bean.PremiumConfigOutput;
import com.qkd.customerservice.bean.ProductListOutput;
import com.qkd.customerservice.bean.SaveSchemeConfigInput;
import com.qkd.customerservice.bean.SchemeConfigOutput;
import com.qkd.customerservice.bean.SchemeCustomizeInfo;
import com.qkd.customerservice.bean.TrialFactorBean;
import com.qkd.customerservice.dialog.InputDialog;
import com.qkd.customerservice.dialog.ProductInputDialog;
import com.qkd.customerservice.dialog.SelectProductDialog;
import com.qkd.customerservice.key_library.util.DensityUtil;
import com.qkd.customerservice.net.BaseHttp;
import com.qkd.customerservice.net.BaseOutput;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created on 12/15/20 14:21
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CustomizedActivity extends AppCompatActivity implements SelectProductDialog.OnSelectProductDialogListener
        , ProductInputDialog.OnSureInputListener {

    private LinearLayout mPersionLinear;
    private TextView benName;
    private TextView benAge;
    private LinearLayout sheBaoLinear;
    private TextView mfemale;
    private TextView mMale;
    private TextView mAge;
    private TextView mYear;
    private TextView benFales;
    private TextView benTrue;
    private TextView benFemale;
    private TextView benMale;
    private TextView benAge2;
    private TextView benYear;
    private ImageView mHeadV;
    private TextView generatePlanV;
    private TextView mSaveDaiV;
    private TextView mTotalMoney;
    private View deleteV;
    private View addProductV;

    private float totalMoney = 0f;

    private RecyclerView mAddRecy;
    private AddProductAdapter mAddProductAdapter;


    private String orderNumber;
    private int userStatus;
    private boolean notShowBtn;

    private SchemeCustomizeInfo.DataBean.ApplyPersonListBean selectPerson;
    private int selcetIndex = 0;
    private SchemeCustomizeInfo.DataBean data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customized);
        EventBus.getDefault().register(this);
        orderNumber = getIntent().getStringExtra("orderNumber");
        int userId = getIntent().getIntExtra("userId", 0);
        userStatus = getIntent().getIntExtra("userStatus", 0);
        if (userStatus == 4) {
            notShowBtn = true;
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("保险方案规划");

        if (userStatus == 3) {
            BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).getSchemeCustomizeInfo(orderNumber), new BaseHttp.HttpObserver<SchemeCustomizeInfo>() {
                @Override
                public void onSuccess(SchemeCustomizeInfo output) {
                    data = output.getData();
                    initData();
                }

                @Override
                public void onError() {

                }
            });
        } else {
//            orderNumber = "549998422751105024";
//            userId = 76;
            BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).getMySchemeDetail(orderNumber, String.valueOf(userId)), new BaseHttp.HttpObserver<MySchemeDetailOutput>() {
                @Override
                public void onSuccess(MySchemeDetailOutput output) {
                    if (output.isSuccess()) {
                        data = new SchemeCustomizeInfo.DataBean();
                        MySchemeDetailOutput.DataBean outData = output.getData();
                        data.setId(outData.getId());
                        data.setOrderNumber(outData.getOrderNumber());
                        data.setSchemeType(outData.getSchemeType());
                        data.setAmount(outData.getAmount());
                        data.setPayFlag(outData.getPayFlag());
                        data.setChargeFlag(outData.getChargeFlag());
                        data.setUserId(outData.getUserId());
                        data.setPlatformId(outData.getPlatformId());
                        data.setNickName(outData.getNickName());
                        data.setGender(outData.getGender());
                        data.setCity(outData.getCity());
                        data.setBirthday(outData.getBirthday());
                        data.setAge(outData.getAge());
                        data.setPhoneNumber(outData.getPhoneNumber());
                        data.setFamily(outData.getFamily());
                        data.setFamilyIncome(outData.getFamilyIncome());
                        data.setFamilyLoan(outData.getFamilyLoan());
                        data.setSonAmount(outData.getSonAmount());
                        data.setDauAmount(outData.getDauAmount());
                        data.setCreateTime(outData.getCreateTime());
                        data.setUpdateTime(outData.getUpdateTime());

                        List<MySchemeDetailOutput.DataBean.ListBean> list = outData.getList();
                        List<SchemeCustomizeInfo.DataBean.ApplyPersonListBean> applyPersonList = new ArrayList<>();
                        if (list != null && list.size() > 0) {
                            for (MySchemeDetailOutput.DataBean.ListBean listBean : list) {
                                SchemeCustomizeInfo.DataBean.ApplyPersonListBean applyBean = new SchemeCustomizeInfo.DataBean.ApplyPersonListBean();
                                applyBean.setId(listBean.getId());
                                applyBean.setOrderNumber(listBean.getOrderNumber());
                                applyBean.setUserId(listBean.getUserId());
                                applyBean.setInsuredPerson(listBean.getInsuredPerson());
                                applyBean.setGender(listBean.getGender());
                                applyBean.setBirthday(listBean.getBirthday());
                                applyBean.setAge(listBean.getAge());
                                applyBean.setMedicalHistory(listBean.getMedicalHistory());
                                applyBean.setMedicalHistoryImg(listBean.getMedicalHistoryImg());
                                applyBean.setProfession(listBean.getProfession());
                                applyBean.setSocialSecurity(listBean.getSocialSecurity());
                                applyBean.setCreateTime(listBean.getCreateTime());
                                applyBean.setUpdateTime(listBean.getUpdateTime());

                                List<MySchemeDetailOutput.DataBean.ListBean.DataListBean> xianzhongList = listBean.getDataList();
                                if (xianzhongList != null && xianzhongList.size() > 0) {
                                    List<ProductListOutput.DataBean> productList = new ArrayList<>();
                                    for (MySchemeDetailOutput.DataBean.ListBean.DataListBean xianzhong : xianzhongList) {
                                        ProductListOutput.DataBean product = new ProductListOutput.DataBean();
                                        product.setId(xianzhong.getProductId());
                                        product.setProductName(xianzhong.getProductName());
                                        product.setProductType(xianzhong.getProductType());
                                        product.setCompanyId(xianzhong.getCompanyId());
                                        product.setCompanyName(xianzhong.getCompanyName());
                                        product.setPremiumNum(xianzhong.getFirstYearPremium());
                                        product.setInsuredAmount(xianzhong.getInsuredAmount());
                                        product.setPaymentPeriod(xianzhong.getPaymentPeriod());
                                        product.setGuaranteePeriod(xianzhong.getGuaranteePeriod());
                                        String[][] arrayData = new String[2][];
                                        String[] list1 = {"投保险种", "保额", "保险期", "交费期", "首年保费"};
                                        String[] list2 = {xianzhong.getProductName(), xianzhong.getInsuredAmount(),
                                                xianzhong.getGuaranteePeriod(), xianzhong.getPaymentPeriod(), xianzhong.getFirstYearPremium()};

                                        arrayData[0] = list1;
                                        arrayData[1] = list2;
                                        product.setArrayData(arrayData);
                                        productList.add(product);
                                        totalMoney += Float.parseFloat(xianzhong.getFirstYearPremium());
                                    }
                                    mTotalMoney.setText(String.valueOf(totalMoney));
                                    applyBean.setProductList(productList);
                                }


                                applyPersonList.add(applyBean);
                            }
                        }
                        data.setApplyPersonList(applyPersonList);
                        initData();

                    } else {
                        Toast.makeText(CustomizedActivity.this, "出错啦", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError() {

                }
            });
        }

        initView();
    }

    private void initView() {
        // 删除
        deleteV = findViewById(R.id.customize_delete);
        deleteV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CustomizedActivity.this);
                builder.setMessage("确定删除当前信息？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (data == null || userStatus != 3) {
                                    return;
                                }
                                List<SchemeCustomizeInfo.DataBean.ApplyPersonListBean> applyPersonList = data.getApplyPersonList();
                                if (applyPersonList.size() <= 1) {
                                    return;
                                }
                                applyPersonList.remove(selcetIndex);
                                selcetIndex = 0;
                                selectPerson = null;
                                initData();
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
            }
        });
        mPersionLinear = findViewById(R.id.customers_head_linear);
        benName = findViewById(R.id.ben_name);
        benAge = findViewById(R.id.ben_age);
        sheBaoLinear = findViewById(R.id.shebao_linear);
        mfemale = findViewById(R.id.sex_female);
        mfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data == null || userStatus != 3) {
                    return;
                }
                String gender = selectPerson.getGender();
                if (TextUtils.isEmpty(gender) || "男".equals(gender)) {
                    selectPerson.setGender("女");
                    data.getApplyPersonList().get(selcetIndex).setGender("女");
                    mfemale.setTextColor(ContextCompat.getColor(CustomizedActivity.this, R.color.white));
                    mfemale.setBackgroundResource(R.drawable.blue2_text_bg);
                    mMale.setTextColor(ContextCompat.getColor(CustomizedActivity.this, R.color.divi_color));
                    mMale.setBackgroundResource(R.drawable.grey_text_bg);
                }
            }
        });
        mMale = findViewById(R.id.sex_male);
        mMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data == null || userStatus != 3) {
                    return;
                }
                String gender = selectPerson.getGender();
                if (TextUtils.isEmpty(gender) || "女".equals(gender)) {
                    selectPerson.setGender("男");
                    data.getApplyPersonList().get(selcetIndex).setGender("男");
                    mMale.setTextColor(ContextCompat.getColor(CustomizedActivity.this, R.color.white));
                    mMale.setBackgroundResource(R.drawable.blue2_text_bg);
                    mfemale.setTextColor(ContextCompat.getColor(CustomizedActivity.this, R.color.divi_color));
                    mfemale.setBackgroundResource(R.drawable.grey_text_bg);
                }
            }
        });
        mAge = findViewById(R.id.age_num);
        mAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data == null || userStatus != 3) {
                    return;
                }
                InputDialog inputDialog = new InputDialog();
                inputDialog.setOnInputSureListener(new InputDialog.OnInputSureListener() {
                    @Override
                    public void onSure(int age) {
                        mAge.setText(String.valueOf(age));
                        selectPerson.setAge(String.valueOf(age));
                        data.getApplyPersonList().get(selcetIndex).setAge(String.valueOf(age));
                    }
                });
                inputDialog.show(getSupportFragmentManager(), "input_dialog");
            }
        });
        mYear = findViewById(R.id.age_year);
        mYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectPerson == null || userStatus != 3) {
                    return;
                }
                String birthday = selectPerson.getBirthday();
                int year;
                int monthOfYear;
                int dayOfMonth;
                if (TextUtils.isEmpty(birthday)) {
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(calendar.YEAR);
                    monthOfYear = calendar.get(calendar.MONTH);
                    dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
                } else {
                    String[] split = birthday.split("-");
                    try {
                        year = Integer.parseInt(split[0]);
                        monthOfYear = Integer.parseInt(split[1]) - 1;
                        dayOfMonth = Integer.parseInt(split[2]);
                    } catch (Exception e) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(calendar.YEAR);
                        monthOfYear = calendar.get(calendar.MONTH);
                        dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
                    }
                }
                DatePickerDialog dialog = new DatePickerDialog(CustomizedActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String newBir = i + "-" + (i1 + 1) + "-" + i2;
                        mYear.setText(newBir);
                        selectPerson.setBirthday(newBir);
                    }
                }, year, monthOfYear, dayOfMonth);
                dialog.show();
            }
        });
        benFales = findViewById(R.id.same_false);
        benTrue = findViewById(R.id.same_true);
        benFemale = findViewById(R.id.sex_female2);
        benFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data == null || userStatus != 3) {
                    return;
                }
                String gender = data.getGender();
                if (TextUtils.isEmpty(gender) || "男".equals(gender)) {
                    data.setGender("女");
                    benFemale.setTextColor(ContextCompat.getColor(CustomizedActivity.this, R.color.white));
                    benFemale.setBackgroundResource(R.drawable.blue2_text_bg);
                    benMale.setTextColor(ContextCompat.getColor(CustomizedActivity.this, R.color.divi_color));
                    benMale.setBackgroundResource(R.drawable.grey_text_bg);
                }
            }
        });
        benMale = findViewById(R.id.sex_male2);
        benMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data == null || userStatus != 3) {
                    return;
                }
                String gender = data.getGender();
                if (TextUtils.isEmpty(gender) || "女".equals(gender)) {
                    data.setGender("男");
                    benMale.setTextColor(ContextCompat.getColor(CustomizedActivity.this, R.color.white));
                    benMale.setBackgroundResource(R.drawable.blue2_text_bg);
                    benFemale.setTextColor(ContextCompat.getColor(CustomizedActivity.this, R.color.divi_color));
                    benFemale.setBackgroundResource(R.drawable.grey_text_bg);
                }
            }
        });
        benAge2 = findViewById(R.id.age_num2);
        benAge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userStatus != 3) {
                    return;
                }
                InputDialog inputDialog = new InputDialog();
                inputDialog.setOnInputSureListener(new InputDialog.OnInputSureListener() {
                    @Override
                    public void onSure(int age) {
                        if (data == null) {
                            return;
                        }
                        benAge2.setText(String.valueOf(age));
                        data.setAge(String.valueOf(age));
                    }
                });
                inputDialog.show(getSupportFragmentManager(), "input_dialog2");
            }
        });
        benYear = findViewById(R.id.age_year2);
        benYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data == null || userStatus != 3) {
                    return;
                }
                String birthday = data.getBirthday();
                int year;
                int monthOfYear;
                int dayOfMonth;
                if (TextUtils.isEmpty(birthday)) {
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(calendar.YEAR);
                    monthOfYear = calendar.get(calendar.MONTH);
                    dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
                } else {
                    String[] split = birthday.split("-");
                    try {
                        year = Integer.parseInt(split[0]);
                        monthOfYear = Integer.parseInt(split[1]) - 1;
                        dayOfMonth = Integer.parseInt(split[2]);
                    } catch (Exception e) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(calendar.YEAR);
                        monthOfYear = calendar.get(calendar.MONTH);
                        dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
                    }
                }
                DatePickerDialog dialog = new DatePickerDialog(CustomizedActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String newBir = i + "-" + (i1 + 1) + "-" + i2;
                        benYear.setText(newBir);
                        data.setBirthday(newBir);
                    }
                }, year, monthOfYear, dayOfMonth);
                dialog.show();
            }
        });
        mHeadV = findViewById(R.id.head_img);

        addProductV = findViewById(R.id.add_product);
        addProductV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userStatus != 3) {
                    return;
                }
                SelectProductDialog dialog = new SelectProductDialog();
                dialog.setOnSelectProductDialogListener(CustomizedActivity.this);
                dialog.show(getSupportFragmentManager(), "SelectProductDialog");
            }
        });
        mAddRecy = findViewById(R.id.add_product_recy);
        mAddRecy.setLayoutManager(new LinearLayoutManager(this));
        mAddRecy.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mAddProductAdapter = new AddProductAdapter(this, userStatus);
        mAddProductAdapter.setOnProductDeleteListener(new AddProductAdapter.OnProductDeleteListener() {
            @Override
            public void onProductDelete() {
                //checkData();
                try {
                    totalMoney = 0f;
                    List<SchemeCustomizeInfo.DataBean.ApplyPersonListBean> applyPersonList = CustomizedActivity.this.data.getApplyPersonList();
                    for (SchemeCustomizeInfo.DataBean.ApplyPersonListBean applyPersonListBean : applyPersonList) {
                        List<ProductListOutput.DataBean> productList = applyPersonListBean.getProductList();
                        if (productList == null) {
                            continue;
                        }
                        for (ProductListOutput.DataBean selectP : productList) {
                            String premiumNum = selectP.getPremiumNum();
                            totalMoney += Float.parseFloat(premiumNum);
                        }
                    }
                    mTotalMoney.setText(String.valueOf(totalMoney));
                } catch (Exception e) {

                }
            }

            @Override
            public void onProductChange(ProductListOutput.DataBean bean) {
                ProductInputDialog productInputDialog = new ProductInputDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("productId", bean.getId());
                bundle.putString("productName", bean.getProductName());
                bundle.putParcelableArrayList("configs", (ArrayList<? extends Parcelable>) configs);
                productInputDialog.setArguments(bundle);
                productInputDialog.setOnSureInputListener(CustomizedActivity.this);
                productInputDialog.show(getSupportFragmentManager(), "productInputDialog");
            }
        });
        mAddRecy.setAdapter(mAddProductAdapter);
        mAddRecy.setNestedScrollingEnabled(false);
        generatePlanV = findViewById(R.id.generate_plan);
        mSaveDaiV = findViewById(R.id.generate_save_dai);
        generatePlanV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (userStatus != 3) {
//                    return;
//                }
                nextAction(1);
            }
        });
        mSaveDaiV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (userStatus != 3) {
//                    return;
//                }
                nextAction(2);
            }
        });
        mTotalMoney = findViewById(R.id.customized_total);
        refreshView();
    }

    private void refreshView() {
        if (userStatus == 3) {
            deleteV.setVisibility(View.VISIBLE);
            addProductV.setVisibility(View.VISIBLE);
            generatePlanV.setVisibility(View.VISIBLE);
            mSaveDaiV.setVisibility(View.VISIBLE);
        } else if (userStatus == 4) {
            deleteV.setVisibility(View.GONE);
            addProductV.setVisibility(View.GONE);
            generatePlanV.setVisibility(View.GONE);
            mSaveDaiV.setVisibility(View.GONE);
        } else {
            deleteV.setVisibility(View.GONE);
            addProductV.setVisibility(View.GONE);
            generatePlanV.setVisibility(View.VISIBLE);
            mSaveDaiV.setVisibility(View.GONE);
        }
        if (notShowBtn) {
            mSaveDaiV.setVisibility(View.GONE);
        }
    }

    private void nextAction(final int type) {
//        if (checkData() && data != null) {
        if (data != null) {

            SaveSchemeConfigInput input = new SaveSchemeConfigInput();
            input.setOrderNumber(orderNumber);
            input.setUserId(data.getUserId());
            input.setNickName(data.getNickName());
            List<SaveSchemeConfigInput.DataListBean> dataList = new ArrayList<>();
            for (SchemeCustomizeInfo.DataBean.ApplyPersonListBean bean : data.getApplyPersonList()) {
                List<ProductListOutput.DataBean> productList = bean.getProductList();
                if (productList != null && productList.size() != 0) {
                    for (ProductListOutput.DataBean product : productList) {
                        SaveSchemeConfigInput.DataListBean configBean = new SaveSchemeConfigInput.DataListBean();
                        configBean.setInsuranceInfoId(String.valueOf(bean.getId()));
                        configBean.setProductId(String.valueOf(product.getId()));
                        configBean.setProductName(product.getProductName());
                        configBean.setProductType(product.getProductType());
                        configBean.setInsuredAmount(product.getInsuredAmount());
                        configBean.setFirstYearPremium(product.getPremiumNum());
                        configBean.setPaymentPeriod(product.getPaymentPeriod());
                        configBean.setGuaranteePeriod(product.getGuaranteePeriod());
                        dataList.add(configBean);
                    }
                }
            }
            input.setDataList(dataList);

            BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).saveSchemeConfig(input), new BaseHttp.HttpObserver<SchemeConfigOutput>() {
                @Override
                public void onSuccess(SchemeConfigOutput baseOutput) {
                    if (type == 1) {
                        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).saveAsHasSend(data.getOrderNumber(), data.getUserId()), new BaseHttp.HttpObserver<BaseOutput>() {
                            @Override
                            public void onSuccess(BaseOutput baseOutput) {
                                if (baseOutput.isSuccess()) {
//                                        Intent intent = new Intent(CustomizedActivity.this, WebActivity.class);
//                                        intent.putExtra("orderNumber", data.getOrderNumber());
//                                        intent.putExtra("userId", data.getUserId());
//                                        startActivity(intent);
                                    Toast.makeText(CustomizedActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                    EventBus.getDefault().post(Constant.REFRESH_CUSTOMIZED_LIST);
                                    finish();
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });
                    } else {
                        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).saveAsToBeSend(data.getOrderNumber(), data.getUserId()), new BaseHttp.HttpObserver<BaseOutput>() {
                            @Override
                            public void onSuccess(BaseOutput baseOutput) {
                                if (baseOutput.isSuccess()) {
                                    Toast.makeText(CustomizedActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    EventBus.getDefault().post(Constant.REFRESH_CUSTOMIZED_LIST);
                                    EventBus.getDefault().post(Constant.REFRESH_CUSTOMIZED_LIST);
                                    finish();
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });
                    }
                }

                @Override
                public void onError() {

                }
            });
        }
    }


    private void initData() {
        LayoutInflater inflater = LayoutInflater.from(this);

        mPersionLinear.removeAllViews();
        sheBaoLinear.removeAllViews();
        List<SchemeCustomizeInfo.DataBean.ApplyPersonListBean> applyPersonList = data.getApplyPersonList();
        for (int i = 0; i < applyPersonList.size(); i++) {
            SchemeCustomizeInfo.DataBean.ApplyPersonListBean listBean = applyPersonList.get(i);

            View view = inflater.inflate(R.layout.item_customized_person, null);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selcetIndex == finalI) {
                        return;
                    } else {
                        selcetIndex = finalI;
                        initData();
                    }
                }
            });
            TextView mPeiou = view.findViewById(R.id.person_peiou);
            TextView mAge = view.findViewById(R.id.persion_age);
            View bottomView = view.findViewById(R.id.blue_bottom);
            mPeiou.setText(listBean.getInsuredPerson());
            mAge.setText(listBean.getAge());

            mPersionLinear.addView(view);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.weight = 1;
            view.setLayoutParams(params);

            View diviView = new View(this);
            diviView.setBackgroundColor(ContextCompat.getColor(this, R.color.divi_color));
            mPersionLinear.addView(diviView);
            ViewGroup.LayoutParams diviP = diviView.getLayoutParams();
            diviP.width = DensityUtil.dp2px(this, 1f);
            diviP.height = DensityUtil.dp2px(this, 20f);
            diviView.setLayoutParams(diviP);


            if (i == selcetIndex) {
                selectPerson = listBean;
                mPeiou.setTextColor(ContextCompat.getColor(this, R.color.app_blue));
                mAge.setBackgroundResource(R.drawable.text_yellow_bg);
                mAge.setTextColor(ContextCompat.getColor(this, R.color.white));
                bottomView.setVisibility(View.VISIBLE);
            } else {
                mPeiou.setTextColor(ContextCompat.getColor(this, R.color.app_black));
                mAge.setBackground(null);
                mAge.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
                bottomView.setVisibility(View.GONE);
            }
        }

        if (selectPerson == null) {
            return;
        }
        String insuredPerson = selectPerson.getInsuredPerson();
        benName.setText(insuredPerson);
        String gender = selectPerson.getGender();
        String age = selectPerson.getAge();
        String benageS = "";
        if (!TextUtils.isEmpty(gender)) {
            benageS += gender + " | ";
        }
        if (!TextUtils.isEmpty(age)) {
            benageS += age + "岁";
        }
        benAge.setText(benageS);
        if ("男".equals(gender)) {
            mHeadV.setImageResource(R.drawable.icon_male);
        } else {
            mHeadV.setImageResource(R.drawable.icon_female);
        }

        String socialSecurity = selectPerson.getSocialSecurity();
        View sheBaoView = inflater.inflate(R.layout.item_shebao, null);
        TextView sblabel = sheBaoView.findViewById(R.id.shebao_label);
        TextView sbValue = sheBaoView.findViewById(R.id.shebao_value);
        sblabel.setText("社保");
        sbValue.setText(socialSecurity);
        sheBaoLinear.addView(sheBaoView);

        String familyIncome = data.getFamilyIncome();
        View inComeView = inflater.inflate(R.layout.item_shebao, null);
        TextView inComelabel = inComeView.findViewById(R.id.shebao_label);
        TextView inComeValue = inComeView.findViewById(R.id.shebao_value);
        inComelabel.setText("家庭年收入");
        inComeValue.setText(familyIncome);
        sheBaoLinear.addView(inComeView);

        String familyLoan = data.getFamilyLoan();
        View loanView = inflater.inflate(R.layout.item_shebao, null);
        TextView loanlabel = loanView.findViewById(R.id.shebao_label);
        TextView loanValue = loanView.findViewById(R.id.shebao_value);
        loanlabel.setText("家庭贷款");
        loanValue.setText(familyLoan);
        sheBaoLinear.addView(loanView);

        String selectBirthday = selectPerson.getBirthday();
        String selectForBirthday;
        if (!TextUtils.isEmpty(selectBirthday)) {
            selectForBirthday = selectBirthday.split("T")[0];
        } else {
            selectForBirthday = "";
        }
        View birthdayView = inflater.inflate(R.layout.item_shebao, null);
        TextView birthdaylabel = birthdayView.findViewById(R.id.shebao_label);
        TextView birthdayValue = birthdayView.findViewById(R.id.shebao_value);
        birthdaylabel.setText("出生日期");
        birthdayValue.setText(selectForBirthday);
        sheBaoLinear.addView(birthdayView);

        String profession = selectPerson.getProfession();
        View professionView = inflater.inflate(R.layout.item_shebao, null);
        TextView professionlabel = professionView.findViewById(R.id.shebao_label);
        TextView professionValue = professionView.findViewById(R.id.shebao_value);
        professionlabel.setText("职业");
        professionValue.setText(profession);
        sheBaoLinear.addView(professionView);

//        String city = data.getCity();
//        if (!TextUtils.isEmpty(city)) {
//            View cityView = inflater.inflate(R.layout.item_shebao, null);
//            TextView citylabel = cityView.findViewById(R.id.shebao_label);
//            TextView cityValue = cityView.findViewById(R.id.shebao_value);
//            citylabel.setText("居住省市");
//            cityValue.setText(city);
//            sheBaoLinear.addView(cityView);
//        }
//
//        String phoneNumber = data.getPhoneNumber();
//        View phoneView = inflater.inflate(R.layout.item_shebao, null);
//        TextView phonelabel = phoneView.findViewById(R.id.shebao_label);
//        TextView phoneValue = phoneView.findViewById(R.id.shebao_value);
//        phonelabel.setText("手机号码");
//        phoneValue.setText(phoneNumber);
//        sheBaoLinear.addView(phoneView);

        String medicalHistory = selectPerson.getMedicalHistory();
        View medicalView = inflater.inflate(R.layout.item_shebao, null);
        TextView medicallabel = medicalView.findViewById(R.id.shebao_label);
        TextView medicalValue = medicalView.findViewById(R.id.shebao_value);
        medicallabel.setText("病史");
        medicalValue.setText(medicalHistory);
        sheBaoLinear.addView(medicalView);

        if ("男".equals(gender)) {
            mMale.setTextColor(ContextCompat.getColor(this, R.color.white));
            mMale.setBackgroundResource(R.drawable.blue2_text_bg);
            mfemale.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
            mfemale.setBackgroundResource(R.drawable.grey_text_bg);
        } else if ("女".equals(gender)) {
            mfemale.setTextColor(ContextCompat.getColor(this, R.color.white));
            mfemale.setBackgroundResource(R.drawable.blue2_text_bg);
            mMale.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
            mMale.setBackgroundResource(R.drawable.grey_text_bg);
        }

        mAge.setText(selectPerson.getAge());
        mYear.setText(selectForBirthday);

        if ("本人".equals(insuredPerson)) {
            benTrue.setTextColor(ContextCompat.getColor(this, R.color.white));
            benTrue.setBackgroundResource(R.drawable.blue2_text_bg);
            benFales.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
            benFales.setBackgroundResource(R.drawable.grey_text_bg);
        } else {
            benFales.setTextColor(ContextCompat.getColor(this, R.color.white));
            benFales.setBackgroundResource(R.drawable.blue2_text_bg);
            benTrue.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
            benTrue.setBackgroundResource(R.drawable.grey_text_bg);
        }

        String benGender = data.getGender();
        if ("男".equals(benGender)) {
            benMale.setTextColor(ContextCompat.getColor(this, R.color.white));
            benMale.setBackgroundResource(R.drawable.blue2_text_bg);
            benFemale.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
            benFemale.setBackgroundResource(R.drawable.grey_text_bg);
        } else if ("女".equals(benGender)) {
            benFemale.setTextColor(ContextCompat.getColor(this, R.color.white));
            benFemale.setBackgroundResource(R.drawable.blue2_text_bg);
            benMale.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
            benMale.setBackgroundResource(R.drawable.grey_text_bg);
        } else {
            benFemale.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
            benFemale.setBackgroundResource(R.drawable.grey_text_bg);
            benMale.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
            benMale.setBackgroundResource(R.drawable.grey_text_bg);
        }

        benAge2.setText(data.getAge());
        String benBirthday = data.getBirthday();
        if (!TextUtils.isEmpty(benBirthday)) {
            benYear.setText(benBirthday.split("T")[0]);
        } else {
            benYear.setText("");
        }

        List<ProductListOutput.DataBean> productList = selectPerson.getProductList();
        if (productList != null) {
            mAddProductAdapter.addAll(productList);
        } else {
            mAddProductAdapter.addAll(new ArrayList<ProductListOutput.DataBean>());
        }
//        List<ProductListOutput.DataBean> productList2 = data.getApplyPersonList().get(selcetIndex).getProductList();
//        for (int i = 0; i < productList2.size(); i++) {
//            ProductListOutput.DataBean dataBean = productList2.get(i);
//            Log.i("Http请求参数", "onSuccess: " + dataBean.getProductName());
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_change:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("确定需要修改？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userStatus = 3;
                        invalidateOptionsMenu();
                        refreshView();
                        mAddProductAdapter.setUserStatus(userStatus);
                    }
                });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (userStatus == 3) {
            menu.clear();
            return super.onCreateOptionsMenu(menu);
        } else {
            getMenuInflater().inflate(R.menu.exchange_xiugai, menu);
            return true;
        }
    }

    private ProductListOutput.DataBean currProduct;
    private List<PremiumConfigOutput.DataBean.ConfigBean> configs;

    @Override
    public void selectProduct(ProductListOutput.DataBean bean) {
        this.currProduct = bean;
//        ProductInputDialog productInputDialog = new ProductInputDialog();
//        Bundle bundle = new Bundle();
//        bundle.putInt("productId", bean.getId());
//        bundle.putString("productName", bean.getProductName());
//        bundle.putString("platformId",bean.getPlatformId());
//        bundle.putString("platformProductId",bean.getPlatformProductId());
//        productInputDialog.setArguments(bundle);
//        productInputDialog.setOnSureInputListener(this);
//        productInputDialog.show(getSupportFragmentManager(), "productInputDialog");
        Intent intent = new Intent(this, ProductCalcActivity.class);
        intent.putExtra("productName", bean.getProductName());
        intent.putExtra("platformId", bean.getPlatformId());
        intent.putExtra("platformProductId", bean.getPlatformProductId());
        startActivityForResult(intent, 1111);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMsgList(CalcSuccessBean bean) {
        currProduct.setPremiumNum(bean.getPrice());
        String[][] arrayData = new String[2][];
        String[] list1 = {"投保险种", "保额", "保险期", "缴费期", "首年保费"};
        String valueBao = "--";
        String valueQi = "--";
        String valueNian = "--";

        List<TrialFactorBean> factorBeans = bean.getFactorBeans();
        for (TrialFactorBean factorBean : factorBeans) {
            String name = factorBean.getName();
            String value = factorBean.getValue();
            List<String> selectList = factorBean.getSelectList();
            if ("PRE".equals(name)) {
                valueBao = value;
            } else if ("INSURE".equals(name)) {
                if (selectList != null && selectList.size() > 1) {
                    valueQi = selectList.get(1);
                }
            } else if ("PAY".equals(name)) {
                if (selectList != null && selectList.size() > 1) {
                    valueNian = selectList.get(1);
                }
            }


//            String fieldName = config.getFieldName();
//            List<PremiumConfigOutput.DataBean.ConfigBean.DictListBean> dictList = config.getDictList();
//            String showValue = "";
//            for (PremiumConfigOutput.DataBean.ConfigBean.DictListBean bean : dictList) {
//                if (bean.isSelect()) {
//                    showValue = bean.getShowValue();
//                    break;
//                }
//            }
//            if ("param2".equals(fieldName)) {
//                valueBao = showValue;
//                currProduct.setInsuredAmount(showValue);
//            } else if ("param5".equals(fieldName)) {
//                valueQi = showValue;
//                currProduct.setGuaranteePeriod(showValue);
//            } else if ("param3".equals(fieldName)) {
//                valueNian = showValue;
//                currProduct.setPaymentPeriod(showValue);
//            }
        }
        String[] list2 = {currProduct.getProductName(), valueBao, valueQi, valueNian, bean.getPrice()};
        arrayData[0] = list1;
        arrayData[1] = list2;
        currProduct.setArrayData(arrayData);


        mAddProductAdapter.add(currProduct);
        List<ProductListOutput.DataBean> allProduct = mAddProductAdapter.getAll();
        List<SchemeCustomizeInfo.DataBean.ApplyPersonListBean> applyPersonList = CustomizedActivity.this.data.getApplyPersonList();
        applyPersonList.get(selcetIndex).setProductList(allProduct);
        selectPerson.setProductList(allProduct);

        try {
            totalMoney = 0f;
            for (SchemeCustomizeInfo.DataBean.ApplyPersonListBean applyPersonListBean : applyPersonList) {
                List<ProductListOutput.DataBean> productList = applyPersonListBean.getProductList();
                if (productList == null) {
                    continue;
                }
                for (ProductListOutput.DataBean selectP : productList) {
                    String premiumNum = selectP.getPremiumNum();
                    totalMoney += Float.parseFloat(premiumNum);
                }
            }
            mTotalMoney.setText(String.valueOf(totalMoney));
        } catch (Exception e) {

        }
    }

    @Override
    public void onSoutInput(final List<PremiumConfigOutput.DataBean.ConfigBean> configs, int include) {
        this.configs = configs;
        AmountInput input = new AmountInput();
        input.setProductType(currProduct.getProductType());
        input.setFromCustAge(data.getAge());
//        input.setFrodmCustHasTss(data.);
        String benGender = data.getGender();
        if ("男".equals(benGender)) {
            input.setFromCustSex("1");
        } else {
            input.setFromCustSex("0");
        }
        input.setToCustAge(selectPerson.getAge());
        String socialSecurity = selectPerson.getSocialSecurity();
        if ("有".equals(socialSecurity)) {
            input.setToCustHasTss("1");
        } else {
            input.setToCustHasTss("0");
        }

        input.setProductId(String.valueOf(currProduct.getId()));
        input.setIsMain(String.valueOf(include));

        // 周岁
        input.setParam1(selectPerson.getAge());

        String gender = selectPerson.getGender();
        if ("男".equals(gender)) {
            input.setParam4("1");
            input.setToCustSex("1");
        } else {
            input.setParam4("2");
            input.setToCustSex("2");
        }
        for (int i = 0; i < configs.size(); i++) {
            PremiumConfigOutput.DataBean.ConfigBean configBean = configs.get(i);
            List<PremiumConfigOutput.DataBean.ConfigBean.DictListBean> selectBeans = new ArrayList<>();
            for (int j = 0; j < configBean.getDictList().size(); j++) {
                PremiumConfigOutput.DataBean.ConfigBean.DictListBean bean = configBean.getDictList().get(j);
                if (bean.isSelect()) {
                    selectBeans.add(bean);
                }
            }

            String realValue = "";
            if (selectBeans.size() > 0) {
                realValue = selectBeans.get(0).getRealValue();
            }
            if (TextUtils.isEmpty(realValue)) {
                continue;
            }
            String fieldName = configBean.getFieldName();
            if ("param2".equals(fieldName)) {
                input.setParam2(realValue);
            } else if ("param3".equals(fieldName)) {
                input.setParam3(realValue);
            } else if ("param5".equals(fieldName)) {
                input.setParam5(realValue);
            } else if ("param6".equals(fieldName)) {
                input.setParam6(realValue);
            } else if ("param7".equals(fieldName)) {
                input.setParam7(realValue);
            } else if ("param8".equals(fieldName)) {
                input.setParam8(realValue);
            } else if ("param9".equals(fieldName)) {
                input.setParam9(realValue);
            } else if ("param10".equals(fieldName)) {
                input.setParam10(realValue);
            } else if ("param11".equals(fieldName)) {
                input.setParam11(realValue);
            } else if ("param12".equals(fieldName)) {
                input.setParam12(realValue);
            } else if ("param13".equals(fieldName)) {
                input.setParam13(realValue);
            } else if ("param14".equals(fieldName)) {
                input.setParam14(realValue);
            } else if ("param15".equals(fieldName)) {
                input.setParam15(realValue);
            } else if ("param16".equals(fieldName)) {
                input.setParam16(realValue);
            } else if ("param17".equals(fieldName)) {
                input.setParam17(realValue);
            } else if ("param18".equals(fieldName)) {
                input.setParam18(realValue);
            } else if ("param19".equals(fieldName)) {
                input.setParam19(realValue);
            } else if ("param20".equals(fieldName)) {
                input.setParam20(realValue);
            }

        }
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).getAmount(input), new BaseHttp.HttpObserver<AmountOutput>() {
            @Override
            public void onSuccess(AmountOutput baseOutput) {
                if (baseOutput.isSuccess()) {
                    String dataNum = baseOutput.getData();
                    if (TextUtils.isEmpty(dataNum)) {
                        Toast.makeText(CustomizedActivity.this, "添加出错", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    currProduct.setPremiumNum(dataNum);
                    String[][] arrayData = new String[2][];
                    String[] list1 = {"投保险种", "保额", "保险期", "交费期", "首年保费"};
                    String valueBao = "";
                    String valueQi = "";
                    String valueNian = "";
                    for (PremiumConfigOutput.DataBean.ConfigBean config : configs) {
                        String fieldName = config.getFieldName();
                        List<PremiumConfigOutput.DataBean.ConfigBean.DictListBean> dictList = config.getDictList();
                        String showValue = "";
                        for (PremiumConfigOutput.DataBean.ConfigBean.DictListBean bean : dictList) {
                            if (bean.isSelect()) {
                                showValue = bean.getShowValue();
                                break;
                            }
                        }
                        if ("param2".equals(fieldName)) {
                            valueBao = showValue;
                            currProduct.setInsuredAmount(showValue);
                        } else if ("param5".equals(fieldName)) {
                            valueQi = showValue;
                            currProduct.setGuaranteePeriod(showValue);
                        } else if ("param3".equals(fieldName)) {
                            valueNian = showValue;
                            currProduct.setPaymentPeriod(showValue);
                        }
                    }
                    String[] list2 = {currProduct.getProductName(), valueBao, valueQi, valueNian, dataNum};
                    arrayData[0] = list1;
                    arrayData[1] = list2;
                    currProduct.setArrayData(arrayData);


                    mAddProductAdapter.add(currProduct);
                    List<ProductListOutput.DataBean> allProduct = mAddProductAdapter.getAll();
                    List<SchemeCustomizeInfo.DataBean.ApplyPersonListBean> applyPersonList = CustomizedActivity.this.data.getApplyPersonList();
                    applyPersonList.get(selcetIndex).setProductList(allProduct);
                    selectPerson.setProductList(allProduct);

                    try {
                        totalMoney = 0f;
                        for (SchemeCustomizeInfo.DataBean.ApplyPersonListBean applyPersonListBean : applyPersonList) {
                            List<ProductListOutput.DataBean> productList = applyPersonListBean.getProductList();
                            if (productList == null) {
                                continue;
                            }
                            for (ProductListOutput.DataBean selectP : productList) {
                                String premiumNum = selectP.getPremiumNum();
                                totalMoney += Float.parseFloat(premiumNum);
                            }
                        }
                        mTotalMoney.setText(String.valueOf(totalMoney));
                    } catch (Exception e) {

                    }

                    //checkData();

                    //currProduct = null;
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}