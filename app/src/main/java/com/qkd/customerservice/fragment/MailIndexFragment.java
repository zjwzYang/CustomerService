package com.qkd.customerservice.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.MailCustomerAdapter;
import com.qkd.customerservice.bean.CustomerBookOutput;
import com.qkd.customerservice.net.BaseHttp;

/**
 * Created on 12/16/20 16:28
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class MailIndexFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MailCustomerAdapter mAdapter;
    private int userStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail_index, container, false);
        mRecyclerView = view.findViewById(R.id.mail_index_recy);
        userStatus = getArguments().getInt("userStatus", 1);
        initView();
        initData();
        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        mAdapter = new MailCustomerAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        String userId = sp.getString(Constant.USER_IDENTIFIER, "");
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).getCustomerBook(userId, userStatus), new BaseHttp.HttpObserver<CustomerBookOutput>() {
            @Override
            public void onSuccess(CustomerBookOutput customerBookOutput) {
                mAdapter.addAll(customerBookOutput.getData());
            }

            @Override
            public void onError() {

            }
        });
    }
}