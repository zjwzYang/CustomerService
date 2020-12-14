package com.qkd.customerservice.fragment;

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

import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.CommonlyUsedAdapter;

import java.util.ArrayList;
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
    private List<String> commonlyUsedList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commonly_used, container, false);
        mRecyclerView = view.findViewById(R.id.commonly_used_recy);

        initView();
        return view;
    }

    private void initView() {
        commonlyUsedList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            commonlyUsedList.add("第" + (i + 1) + "条");
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        CommonlyUsedAdapter adapter = new CommonlyUsedAdapter(getContext(), commonlyUsedList);
        mRecyclerView.setAdapter(adapter);
    }
}