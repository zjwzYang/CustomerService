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
import com.qkd.customerservice.adapter.CustomizeAdapter;
import com.qkd.customerservice.adapter.MailCustomerAdapter;
import com.qkd.customerservice.bean.CustomerBookOutput;
import com.qkd.customerservice.bean.QueryCustomizeOutput;
import com.qkd.customerservice.net.BaseHttp;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/**
 * Created on 12/16/20 16:28
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class MailIndexFragment extends Fragment {

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private MailCustomerAdapter mAdapter;

    private CustomizeAdapter mCustomizeAdapter;
    private int userStatus;

    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail_index, container, false);
        mRecyclerView = view.findViewById(R.id.mail_index_recy);
        mSmartRefreshLayout = view.findViewById(R.id.mail_index_refresh);
        userStatus = getArguments().getInt("userStatus", 1);
        initView();
//        initData();
        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        if (userStatus == 1) {
            mAdapter = new MailCustomerAdapter(getContext(), getChildFragmentManager());
            mRecyclerView.setAdapter(mAdapter);
            mSmartRefreshLayout.setEnableLoadMore(false);
        } else {
            mCustomizeAdapter = new CustomizeAdapter(getContext(), userStatus);
            mRecyclerView.setAdapter(mCustomizeAdapter);
        }
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                initData();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();
            }
        });
        mSmartRefreshLayout.autoRefresh();
    }

    private void initData() {
        SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        String userId = sp.getString(Constant.USER_IDENTIFIER, "");
        int serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);
        if (userStatus == 1) {
            BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).getCustomerBook(userId), new BaseHttp.HttpObserver<CustomerBookOutput>() {
                @Override
                public void onSuccess(CustomerBookOutput customerBookOutput) {
                    mAdapter.addAll(customerBookOutput.getData());
                    mSmartRefreshLayout.finishRefresh();
                    mSmartRefreshLayout.finishLoadMore();
                }

                @Override
                public void onError() {
                    mSmartRefreshLayout.finishRefresh();
                    mSmartRefreshLayout.finishLoadMore();
                }
            });
        } else { // 待定制方案
            int orderStatus = 1;
            if (userStatus == 3) {
                orderStatus = 1;
            } else if (userStatus == 4) {
                orderStatus = 3;
            } else if (userStatus == 5) {
                orderStatus = 2;
            }
            BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).queryCustomizeList(serviceId
                    , page, orderStatus, null), new BaseHttp.HttpObserver<QueryCustomizeOutput>() {
                @Override
                public void onSuccess(QueryCustomizeOutput output) {
                    mSmartRefreshLayout.finishRefresh();
                    mSmartRefreshLayout.finishLoadMore();
                    if (page == 1) {
                        mCustomizeAdapter.clear();
                    }
                    if (output.isSuccess()) {
                        mCustomizeAdapter.addAll(output.getData().getList());
                    }
                    if (page >= output.getData().getLastPage()) {
                        mSmartRefreshLayout.setNoMoreData(true);
                    } else {
                        mSmartRefreshLayout.setNoMoreData(false);
                    }
                }

                @Override
                public void onError() {
                    mSmartRefreshLayout.finishRefresh();
                    mSmartRefreshLayout.finishLoadMore();
                }
            });
        }
//        else if (userStatus == 4) { // 已完成方案
//            BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).queryCustomizeList(serviceId, page, 3), new BaseHttp.HttpObserver<QueryCustomizeOutput>() {
//                @Override
//                public void onSuccess(QueryCustomizeOutput output) {
//                    if (output.isSuccess()) {
//                        mCustomizeAdapter.addAll(output.getData().getList());
//                    }
//                }
//
//                @Override
//                public void onError() {
//
//                }
//            });
//        } else if (userStatus == 5) { // -待发送方案
//            BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).queryCustomizeList(serviceId, page, 2), new BaseHttp.HttpObserver<QueryCustomizeOutput>() {
//                @Override
//                public void onSuccess(QueryCustomizeOutput output) {
//                    if (output.isSuccess()) {
//                        mCustomizeAdapter.addAll(output.getData().getList());
//                    }
//                }
//
//                @Override
//                public void onError() {
//
//                }
//            });
//        }
    }

    public void refresh() {
        page = 1;
        initData();
    }

    public int getUserStatus() {
        return userStatus;
    }
}