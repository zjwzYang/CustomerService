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

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.ExpressionListAdapter;
import com.qkd.customerservice.bean.Expression;
import com.qkd.customerservice.bean.ExpressionManager;
import com.qkd.customerservice.key_library.util.DensityUtil;
import com.qkd.customerservice.widget.TopPaddingDecoration;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 12/3/20 13:50
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class NormalExpressionPagerFragment extends Fragment implements View.OnClickListener {
    private static String KEY_EXPRESSION_LIST = "key_expression_list";
    private GridLayoutManager layoutManager;
    private List<Expression> expressionList;

    private RecyclerView mRecyclerView;

    public static NormalExpressionPagerFragment newInstance(List<Expression> expressionList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_EXPRESSION_LIST, (Serializable) expressionList);
        NormalExpressionPagerFragment fragment = new NormalExpressionPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal_expression_pager, container, true);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        view.findViewById(R.id.btn_send).setOnClickListener(this);
        view.findViewById(R.id.btn_delete).setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        //setListeners();
    }

    private void init() {
        Bundle bundle = getArguments();
        expressionList = (List<Expression>) bundle.getSerializable(KEY_EXPRESSION_LIST);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), ExpressionManager.NORMAL_COUNT_BY_ROW);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new TopPaddingDecoration(DensityUtil.dp2px(getContext(), 24.0f)));
        ExpressionListAdapter adapter = new ExpressionListAdapter(getContext(), expressionList);
        mRecyclerView.setAdapter(adapter);
    }

    private void setListeners() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                try {
                    int spanCount = layoutManager.getSpanCount();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if ((lastVisibleItemPosition + 1) % spanCount == 0 || (lastVisibleItemPosition + 2) % spanCount == 0) {
                        View view;
                        if ((lastVisibleItemPosition + 1) % spanCount == 0) {
                            view = layoutManager.findViewByPosition(lastVisibleItemPosition);
                            view.setVisibility(View.GONE);
                        }
                        view = layoutManager.findViewByPosition(lastVisibleItemPosition - 1);
                        view.setVisibility(View.GONE);
                        view = layoutManager.findViewByPosition(lastVisibleItemPosition - spanCount);
                        view.setVisibility(View.GONE);
                        view = layoutManager.findViewByPosition(lastVisibleItemPosition - spanCount - 1);
                        view.setVisibility(View.GONE);
                    }
                    int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    if ((lastCompletelyVisibleItemPosition + 1) % spanCount == 0 || (lastCompletelyVisibleItemPosition + 2) % spanCount == 0) {
                        View view;
                        if ((lastCompletelyVisibleItemPosition + 1) % spanCount == 0) {
                            view = layoutManager.findViewByPosition(lastVisibleItemPosition - spanCount * 2);
                            view.setVisibility(View.VISIBLE);
                        }
                        view = layoutManager.findViewByPosition(lastVisibleItemPosition - spanCount * 2 - 1);
                        view.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                EventBus.getDefault().post(Constant.SEND_FLAG);
                break;
            case R.id.btn_delete:
                EventBus.getDefault().post(Constant.DELETE_FLAG);
                break;
        }
    }
}
