package com.qkd.customerservice.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.CommonlyUsedAdapter;
import com.qkd.customerservice.adapter.YuYinUsedAdapter;
import com.qkd.customerservice.bean.ExpressionType;
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
public class CommonlyUsedFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private CommonlyUsedAdapter adapter;
    private YuYinUsedAdapter mYinUsedAdapter;
    private String type;
    private int page = 1;
    private boolean hasMore = true;
    private boolean loadMoreFlag = false;

    private TextView mPrivateV;
    private TextView mPublicV;

    private boolean isPrivate = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commonly_used, container, false);
        mRecyclerView = view.findViewById(R.id.commonly_used_recy);
        mPrivateV = view.findViewById(R.id.commonly_private);
        mPrivateV.setOnClickListener(this);
        mPublicV = view.findViewById(R.id.commonly_public);
        mPublicV.setOnClickListener(this);
        Bundle bundle = getArguments();
        type = bundle.getString("type");

        initView();

        isPrivate = true;
        initData();
        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type)) {
            adapter = new CommonlyUsedAdapter(getContext());
            mRecyclerView.setAdapter(adapter);
        } else if (ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
            mYinUsedAdapter = new YuYinUsedAdapter(getContext());
            mRecyclerView.setAdapter(mYinUsedAdapter);
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    RecyclerView.Adapter mAdapter = null;
                    if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type)) {
                        mAdapter = adapter;
                    } else if (ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
                        mAdapter = mYinUsedAdapter;
                    }
                    if (lastVisibleItemPosition == mAdapter.getItemCount() - 1 && !loadMoreFlag) {
                        loadMoreFlag = true;
                        page++;
                        initData();
                    }
                }
            }
        });
    }

    private void initData() {
        if (!hasMore) {
            return;
        }
        int serviceId;
        if (isPrivate) {
            SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
            serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);
        } else {
            serviceId = 0;
        }
        int mediaType = 1;
        if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type)) {
            mediaType = 1;
        } else if (ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
            mediaType = 3;
        }
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE)
                .queryKnowledge(mediaType, serviceId, page, ""), new BaseHttp.HttpObserver<KnowledgeOutput>() {
            @Override
            public void onSuccess(KnowledgeOutput baseOutput) {
                loadMoreFlag = false;
                if (baseOutput.isSuccess()) {
                    List<KnowledgeOutput.DataBean.ListBean> list = baseOutput.getData().getList();
                    if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type)) {
                        Log.i("CommonlyUsedFragment", "onSuccess: 文本长度" + list.size());
                        if (page == 1) {
                            adapter.clear();
                        }
                        adapter.addAll(list);
                    } else if (ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
                        Log.i("CommonlyUsedFragment", "onSuccess: 语音长度" + list.size());
                        if (page == 1) {
                            mYinUsedAdapter.clear();
                        }
                        mYinUsedAdapter.addAll(list);
                    }
                    if (list.size() == baseOutput.getData().getPageSize()) {
                        hasMore = true;
                    } else {
                        hasMore = false;
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        mPrivateV.setTextColor(ContextCompat.getColor(getContext(), R.color.divi_color));
        mPrivateV.setBackgroundResource(R.drawable.text_gray_bg);
        mPublicV.setTextColor(ContextCompat.getColor(getContext(), R.color.divi_color));
        mPublicV.setBackgroundResource(R.drawable.text_gray_bg);
        switch (view.getId()) {
            case R.id.commonly_private:
                mPrivateV.setTextColor(ContextCompat.getColor(getContext(), R.color.app_blue));
                mPrivateV.setBackgroundResource(R.drawable.text_blue_bg);
                isPrivate = true;
                hasMore = true;
                page = 1;
                initData();
                break;
            case R.id.commonly_public:
                mPublicV.setTextColor(ContextCompat.getColor(getContext(), R.color.app_blue));
                mPublicV.setBackgroundResource(R.drawable.text_blue_bg);
                isPrivate = false;
                hasMore = true;
                page = 1;
                initData();
                break;
        }
    }
}