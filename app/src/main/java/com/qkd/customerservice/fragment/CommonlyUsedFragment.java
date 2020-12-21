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
import com.qkd.customerservice.adapter.CommonlyUsedAdapter;
import com.qkd.customerservice.bean.KnowledgeOutput;
import com.qkd.customerservice.net.BaseHttp;

import java.util.List;

/**
 * Created on 12/14/20 14:05
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class CommonlyUsedFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CommonlyUsedAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commonly_used, container, false);
        mRecyclerView = view.findViewById(R.id.commonly_used_recy);

        initView();

        initData();
        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        adapter = new CommonlyUsedAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        int serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE)
                .queryKnowledge(1, serviceId, 1), new BaseHttp.HttpObserver<KnowledgeOutput>() {
            @Override
            public void onSuccess(KnowledgeOutput baseOutput) {
                if (baseOutput.isSuccess()) {
                    List<KnowledgeOutput.DataBean.ListBean> list = baseOutput.getData().getList();
                    adapter.addAll(list);
                }
            }

            @Override
            public void onError() {

            }
        });
    }
}