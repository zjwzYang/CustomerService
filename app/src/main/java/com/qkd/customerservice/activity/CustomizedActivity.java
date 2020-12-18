package com.qkd.customerservice.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.AddProductAdapter;
import com.qkd.customerservice.bean.AmountInput;
import com.qkd.customerservice.bean.PremiumConfigOutput;
import com.qkd.customerservice.bean.ProductListOutput;
import com.qkd.customerservice.bean.SchemeCustomizeInfo;
import com.qkd.customerservice.dialog.ProductInputDialog;
import com.qkd.customerservice.dialog.SelectProductDialog;
import com.qkd.customerservice.key_library.util.DensityUtil;
import com.qkd.customerservice.net.BaseHttp;
import com.qkd.customerservice.net.BaseOutput;

import java.util.List;

/**
 * Created on 12/15/20 14:21
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
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

    private RecyclerView mAddRecy;
    private AddProductAdapter mAddProductAdapter;


    private String orderNumber;

    private SchemeCustomizeInfo.DataBean.ApplyPersonListBean selectPerson;
    private int selcetIndex = 0;
    private SchemeCustomizeInfo.DataBean data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customized);
        orderNumber = getIntent().getStringExtra("orderNumber");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("保险方案规划");

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

        initView();
    }

    private void initView() {
        mPersionLinear = findViewById(R.id.customers_head_linear);
        benName = findViewById(R.id.ben_name);
        benAge = findViewById(R.id.ben_age);
        sheBaoLinear = findViewById(R.id.shebao_linear);
        mfemale = findViewById(R.id.sex_female);
        mMale = findViewById(R.id.sex_male);
        mAge = findViewById(R.id.age_num);
        mYear = findViewById(R.id.age_year);
        benFales = findViewById(R.id.same_false);
        benTrue = findViewById(R.id.same_true);
        benFemale = findViewById(R.id.sex_female2);
        benMale = findViewById(R.id.sex_male2);
        benAge2 = findViewById(R.id.age_num2);
        benYear = findViewById(R.id.age_year2);
        mHeadV = findViewById(R.id.head_img);

        findViewById(R.id.add_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectProductDialog dialog = new SelectProductDialog();
                dialog.setOnSelectProductDialogListener(CustomizedActivity.this);
                dialog.show(getSupportFragmentManager(), "SelectProductDialog");
            }
        });
        mAddRecy = findViewById(R.id.add_product_recy);
        mAddRecy.setLayoutManager(new LinearLayoutManager(this));
        mAddRecy.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mAddProductAdapter = new AddProductAdapter(this);
        mAddRecy.setAdapter(mAddProductAdapter);
        mAddRecy.setNestedScrollingEnabled(false);
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

        String birthday = selectPerson.getBirthday();
        View birthdayView = inflater.inflate(R.layout.item_shebao, null);
        TextView birthdaylabel = birthdayView.findViewById(R.id.shebao_label);
        TextView birthdayValue = birthdayView.findViewById(R.id.shebao_value);
        birthdaylabel.setText("出生日期");
        birthdayValue.setText(birthday);
        sheBaoLinear.addView(birthdayView);

        String profession = selectPerson.getProfession();
        View professionView = inflater.inflate(R.layout.item_shebao, null);
        TextView professionlabel = professionView.findViewById(R.id.shebao_label);
        TextView professionValue = professionView.findViewById(R.id.shebao_value);
        professionlabel.setText("职业");
        professionValue.setText(profession);
        sheBaoLinear.addView(professionView);

        String city = data.getCity();
        if (!TextUtils.isEmpty(city)) {
            View cityView = inflater.inflate(R.layout.item_shebao, null);
            TextView citylabel = cityView.findViewById(R.id.shebao_label);
            TextView cityValue = cityView.findViewById(R.id.shebao_value);
            citylabel.setText("居住省市");
            cityValue.setText(city);
            sheBaoLinear.addView(cityView);
        }

        String phoneNumber = data.getPhoneNumber();
        View phoneView = inflater.inflate(R.layout.item_shebao, null);
        TextView phonelabel = phoneView.findViewById(R.id.shebao_label);
        TextView phoneValue = phoneView.findViewById(R.id.shebao_value);
        phonelabel.setText("手机号码");
        phoneValue.setText(phoneNumber);
        sheBaoLinear.addView(phoneView);

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
        } else {
            mfemale.setTextColor(ContextCompat.getColor(this, R.color.white));
            mfemale.setBackgroundResource(R.drawable.blue2_text_bg);
            mMale.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
            mMale.setBackgroundResource(R.drawable.grey_text_bg);
        }

        mAge.setText(selectPerson.getAge());
        mYear.setText(selectPerson.getBirthday());

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
        benYear.setText(data.getBirthday());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ProductListOutput.DataBean currProduct;

    @Override
    public void selectProduct(ProductListOutput.DataBean bean) {
        this.currProduct = bean;
        ProductInputDialog productInputDialog = new ProductInputDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("productId", bean.getId());
        productInputDialog.setArguments(bundle);
        productInputDialog.setOnSureInputListener(this);
        productInputDialog.show(getSupportFragmentManager(), "productInputDialog");
    }

    @Override
    public void onSoutInput(List<PremiumConfigOutput.DataBean.ConfigBean> configs) {
        AmountInput input = new AmountInput();
        input.setProductType(currProduct.getProductType());
        input.setFromCustAge(data.getAge());
//        input.setFrodmCustHasTss(data.);
        input.setFromCustSex(data.getGender());
        input.setToCustAge(selectPerson.getAge());
        input.setToCustHasTss(selectPerson.getSocialSecurity());
        input.setToCustSex(selectPerson.getGender());
        input.setProductId(String.valueOf(currProduct.getId()));
        input.setIsMain("0");
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).getAmount(input), new BaseHttp.HttpObserver<BaseOutput>() {
            @Override
            public void onSuccess(BaseOutput baseOutput) {

            }

            @Override
            public void onError() {

            }
        });
    }
}