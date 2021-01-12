package com.qkd.customerservice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.MoreActionAdapter;
import com.qkd.customerservice.bean.MoreAction;

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
        MoreActionAdapter adapter = new MoreActionAdapter(getContext(), mActionList);
        mRecyclerView.setAdapter(adapter);
    }
}