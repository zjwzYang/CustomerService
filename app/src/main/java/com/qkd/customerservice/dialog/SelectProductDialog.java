package com.qkd.customerservice.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.DialogProductAdapter;
import com.qkd.customerservice.bean.ProductListOutput;
import com.qkd.customerservice.net.BaseHttp;

/**
 * Created on 12/18/20 10:51
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class SelectProductDialog extends DialogFragment implements DialogProductAdapter.OnClickProductItemListener {

    private FlexboxLayout mFlexboxLayout;
    private RecyclerView mRecyclerView;
    private DialogProductAdapter mAdapter;
    private OnSelectProductDialogListener onSelectProductDialogListener;

    private String productType;
    private String productName;

    public void setOnSelectProductDialogListener(OnSelectProductDialogListener onSelectProductDialogListener) {
        this.onSelectProductDialogListener = onSelectProductDialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_product, container, false);
        mFlexboxLayout = view.findViewById(R.id.product_zhonglei);
        mRecyclerView = view.findViewById(R.id.product_recy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        mAdapter = new DialogProductAdapter(getContext());
        mAdapter.setOnClickProductItemListener(this);
        mRecyclerView.setAdapter(mAdapter);
        view.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        EditText editText = view.findViewById(R.id.product_edit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                productName = input;
                asynData();
            }
        });

        initData();


        return view;
    }

    private void initData() {
        String[] zhonglei = {"全部", "医疗险", "意外险", "重疾", "寿险", "年金险", "教育险"};
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < zhonglei.length; i++) {
            String temType = "";
            if (i != 0) {
                temType = String.valueOf(i);
            }

            View view = inflater.inflate(R.layout.item_zhonglei, null);
            view.setTag(temType);
            TextView name = view.findViewById(R.id.zhonglei_name);
            if (i == 0) {
                name.setBackgroundResource(R.drawable.text_zhonglei_select_bg);
                name.setTextColor(ContextCompat.getColor(getContext(), R.color.app_blue));
            }
            name.setText(zhonglei[i]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productType = view.getTag().toString();
                    asynData();
                    for (int j = 0; j < mFlexboxLayout.getChildCount(); j++) {
                        View childAt = mFlexboxLayout.getChildAt(j);
                        TextView childText = childAt.findViewById(R.id.zhonglei_name);
                        childText.setBackgroundResource(R.drawable.text_zhonglei_bg);
                        childText.setTextColor(ContextCompat.getColor(getContext(), R.color.c_2d2d2d));
                    }
                    TextView selectV = view.findViewById(R.id.zhonglei_name);
                    selectV.setBackgroundResource(R.drawable.text_zhonglei_select_bg);
                    selectV.setTextColor(ContextCompat.getColor(getContext(), R.color.app_blue));
                }
            });
            mFlexboxLayout.addView(view);
        }

        asynData();
    }

    private void asynData() {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).getProductList(productName, productType), new BaseHttp.HttpObserver<ProductListOutput>() {
            @Override
            public void onSuccess(ProductListOutput productListOutput) {
                mAdapter.addAll(productListOutput.getData());
            }

            @Override
            public void onError() {

            }
        });
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
    public void onClickProduct(ProductListOutput.DataBean bean) {
        if (onSelectProductDialogListener != null) {
            onSelectProductDialogListener.selectProduct(bean);
        }
        dismiss();
    }

    public interface OnSelectProductDialogListener {
        void selectProduct(ProductListOutput.DataBean bean);
    }
}