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

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.ArticleAdapter;
import com.qkd.customerservice.bean.ArticleOutput;
import com.qkd.customerservice.net.BaseHttp;

/**
 * Created on 12/15/20 09:15
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class ArticleFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;
    private int offset = 0;
    private int limit = 15;
    private boolean hasMore = true;
    private boolean loadMoreFlag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        mRecyclerView = view.findViewById(R.id.article_recy);

        initView();
        initData();
        return view;

    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticleAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == mAdapter.getItemCount() - 1 && !loadMoreFlag) {
                        loadMoreFlag = true;
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
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).getArtList(offset, limit), new BaseHttp.HttpObserver<ArticleOutput>() {
            @Override
            public void onSuccess(ArticleOutput output) {
                loadMoreFlag = false;
                mAdapter.addAll(output.getData());
                int size = output.getData().size();
                if (size == limit) {
                    hasMore = true;
                } else {
                    hasMore = false;
                }
                offset = size;
            }

            @Override
            public void onError() {

            }
        });
    }
}