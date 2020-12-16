package com.qkd.customerservice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;

/**
 * Created on 12/16/20 16:28
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class MailIndexFragment extends Fragment {

    private RecyclerView mRecyclerView;

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

    }

    private void initData() {

    }
}