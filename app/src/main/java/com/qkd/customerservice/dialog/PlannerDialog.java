package com.qkd.customerservice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.PlannerAdapter;
import com.qkd.customerservice.bean.PlannerOutput;
import com.qkd.customerservice.bean.TransferOutput;
import com.qkd.customerservice.net.BaseHttp;

/**
 * Created on 12/23/20 14:48
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class PlannerDialog extends DialogFragment implements PlannerAdapter.OnPlannerClickListener {

    private RecyclerView mRecyclerView;
    private PlannerAdapter mAdapter;
    private String userId;
    private String identifier;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_planner, container, false);
        mRecyclerView = view.findViewById(R.id.planner_recy);
        mAdapter = new PlannerAdapter(getContext());
        mAdapter.setOnPlannerClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        Bundle bundle = getArguments();
        userId = bundle.getString("userId");
        initData();
        return view;
    }

    private void initData() {
        SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        identifier = sp.getString(Constant.USER_IDENTIFIER, "");
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).queryPlannerList(identifier, 1), new BaseHttp.HttpObserver<PlannerOutput>() {
            @Override
            public void onSuccess(PlannerOutput baseOutput) {
                if (baseOutput.isSuccess()) {
                    mAdapter.addAll(baseOutput.getData().getList());
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onPlannerClick(PlannerOutput.DataBean.ListBean listBean) {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE)
                .transferCustomer(userId, identifier, listBean.getId(), listBean.getIdentifier()), new BaseHttp.HttpObserver<TransferOutput>() {
            @Override
            public void onSuccess(TransferOutput baseOutput) {
                Toast.makeText(getContext(), baseOutput.getData(), Toast.LENGTH_SHORT).show();
                dismiss();
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
            window.setGravity(Gravity.CENTER);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //window.setWindowAnimations(R.style.animate_dialog);
            setCancelable(true);
        }
    }

}