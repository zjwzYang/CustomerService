package com.qkd.customerservice.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.DialogInputAdapter;
import com.qkd.customerservice.bean.PremiumConfigOutput;
import com.qkd.customerservice.net.BaseHttp;

import java.util.List;

/**
 * Created on 12/18/20 13:17
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ProductInputDialog extends DialogFragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private DialogInputAdapter mAdapter;
    private OnSureInputListener onSureInputListener;

    private TextView mProductName;
    private TextView mProductName2;
    private int include;

    private List<PremiumConfigOutput.DataBean.ConfigBean> configs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_product_input, container, false);
//        bean = (ProductListOutput.DataBean) getArguments().getSerializable("orignData");
        configs = getArguments().getParcelableArrayList("configs");
        mRecyclerView = view.findViewById(R.id.input_recy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        mAdapter = new DialogInputAdapter(getContext(), configs);
        mRecyclerView.setAdapter(mAdapter);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        view.findViewById(R.id.dialog_product_sure).setOnClickListener(this);
        view.findViewById(R.id.dialog_product_close).setOnClickListener(this);
        mProductName = view.findViewById(R.id.product_name);
        mProductName2 = view.findViewById(R.id.product_name2);

        initData();
        return view;
    }

    private void initData() {
        Bundle bundle = getArguments();
        int productId = bundle.getInt("productId", 0);
        String productName = bundle.getString("productName");

        mProductName.setText(productName);
        mProductName2.setText(productName);
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_WEB).getPremiumConfig(productId), new BaseHttp.HttpObserver<PremiumConfigOutput>() {
            @Override
            public void onSuccess(PremiumConfigOutput premiumConfigOutput) {
                include = premiumConfigOutput.getData().getInclude();
                mAdapter.addAll(premiumConfigOutput.getData().getConfig());
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_product_sure:
                if (onSureInputListener != null) {
                    onSureInputListener.onSoutInput(mAdapter.getAll(), include);
                }
                dismiss();
                break;
            case R.id.dialog_product_close:
                dismiss();
                break;
        }
    }

    public void setOnSureInputListener(OnSureInputListener onSureInputListener) {
        this.onSureInputListener = onSureInputListener;
    }

    public interface OnSureInputListener {
        void onSoutInput(List<PremiumConfigOutput.DataBean.ConfigBean> configs, int include);
    }
}