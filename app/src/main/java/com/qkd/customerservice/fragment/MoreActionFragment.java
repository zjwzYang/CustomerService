package com.qkd.customerservice.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.MoreActionAdapter;
import com.qkd.customerservice.bean.MoreAction;
import com.qkd.customerservice.bean.QrCodeOutput;
import com.qkd.customerservice.net.BaseHttp;

import java.util.List;

/**
 * Created on 12/4/20 13:41
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class MoreActionFragment extends Fragment {

    public static final String ACTION_LIST = "action_list";

    private RecyclerView mRecyclerView;

    private List<MoreAction> mActionList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_action, container, false);
        mRecyclerView = view.findViewById(R.id.more_recycler_view);

        init();
        return view;
    }

    private void init() {
        Bundle bundle = getArguments();
        mActionList = (List<MoreAction>) bundle.getSerializable(ACTION_LIST);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        final MoreActionAdapter adapter = new MoreActionAdapter(getContext(), mActionList);
        mRecyclerView.setAdapter(adapter);

        SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        String identifier = sp.getString(Constant.USER_IDENTIFIER, "");
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).getQrCode(identifier), new BaseHttp.HttpObserver<QrCodeOutput>() {
            @Override
            public void onSuccess(QrCodeOutput output) {
                String qrCode = output.getData();
                adapter.setQrCode(qrCode);
            }

            @Override
            public void onError() {

            }
        });
    }
}