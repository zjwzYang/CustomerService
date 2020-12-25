package com.qkd.customerservice.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.ArticleAdapter;
import com.qkd.customerservice.adapter.CommonlyUsedAdapter;
import com.qkd.customerservice.adapter.ProductAdapter;
import com.qkd.customerservice.adapter.YuYinUsedAdapter;
import com.qkd.customerservice.bean.ArticleOutput;
import com.qkd.customerservice.bean.KnowledgeOutput;
import com.qkd.customerservice.bean.ProductOutput;
import com.qkd.customerservice.net.BaseHttp;

/**
 * Created on 12/25/20 13:16
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class SearchActivity extends AppCompatActivity {

    public final static int search_chang = 1;
    public final static int search_yuyin = 2;
    public final static int search_wenzhang = 3;
    public final static int search_caiping = 4;

    private EditText mEditText;
    private TextView mTextView;
    private RecyclerView mRecyclerView;

    private int searchType;
    private int serviceId;

    private int page = 1;
    private int offset = 0;
    private int limit = 100;
    private boolean hasMore = true;
    private boolean loadMoreFlag = false;
    private String searchWord;

    private CommonlyUsedAdapter changAdapter;
    private YuYinUsedAdapter yuyinAdapter;
    private ArticleAdapter wenzhangAdapter;
    private ProductAdapter caipingAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        searchType = getIntent().getIntExtra("searchType", search_chang);
        SharedPreferences sp = getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);

        mEditText = findViewById(R.id.search_edit);
        mTextView = findViewById(R.id.search_btn);
        mRecyclerView = findViewById(R.id.search_recy);

        initView();

    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        switch (searchType) {
            case search_chang:
                changAdapter = new CommonlyUsedAdapter(this);
                mRecyclerView.setAdapter(changAdapter);
                setTitle("搜索常用语");
                break;
            case search_yuyin:
                yuyinAdapter = new YuYinUsedAdapter(this);
                mRecyclerView.setAdapter(yuyinAdapter);
                setTitle("搜索语音");
                break;
            case search_wenzhang:
                wenzhangAdapter = new ArticleAdapter(this);
                mRecyclerView.setAdapter(wenzhangAdapter);
                setTitle("搜索文章库");
                break;
            case search_caiping:
                caipingAdapter = new ProductAdapter(this);
                mRecyclerView.setAdapter(caipingAdapter);
                setTitle("搜索产品库");
                break;
        }
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
//                    RecyclerView.Adapter adapter = null;
//                    switch (searchType) {
//                        case search_chang:
//                            adapter = changAdapter;
//                            break;
//                        case search_yuyin:
//                            adapter = yuyinAdapter;
//                            break;
//                        case search_wenzhang:
//                            adapter = wenzhangAdapter;
//                            break;
//                        case search_caiping:
//                            adapter = caipingAdapter;
//                            break;
//                    }
//                    if (lastVisibleItemPosition == adapter.getItemCount() - 1 && !loadMoreFlag) {
//                        loadMoreFlag = true;
//                        initData(searchWord);
//                    }
//                }
//            }
//        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasMore = true;
                searchWord = mEditText.getText().toString();
                if (TextUtils.isEmpty(searchWord)) {
                    Toast.makeText(SearchActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    initData(searchWord);
                }
            }
        });
    }

    private void initData(String word) {
        switch (searchType) {
            case search_chang:
                searchChange(1, word);
                break;
            case search_yuyin:
                searchChange(3, word);
                break;
            case search_wenzhang:
                searchWenzhang(word);
                break;
            case search_caiping:
                searchCaiping(word);
                break;
        }
    }

    private void searchChange(final int mediaType, String word) {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE)
                .queryKnowledge(mediaType, serviceId, page, word), new BaseHttp.HttpObserver<KnowledgeOutput>() {
            @Override
            public void onSuccess(KnowledgeOutput baseOutput) {
                if (baseOutput.isSuccess()) {
                    if (mediaType == 1) {
                        changAdapter.addAll(baseOutput.getData().getList());
                    } else {
                        yuyinAdapter.addAll(baseOutput.getData().getList());
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void searchWenzhang(String word) {
        if (!hasMore) {
            return;
        }
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).getArtList(offset, limit, word, ""), new BaseHttp.HttpObserver<ArticleOutput>() {
            @Override
            public void onSuccess(ArticleOutput output) {
                wenzhangAdapter.addAll(output.getData());
                if (output.getData().size() == limit) {
                    hasMore = true;
                } else {
                    hasMore = false;
                }
                loadMoreFlag = false;
                offset = wenzhangAdapter.getItemCount();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void searchCaiping(String word) {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).getProductList(offset, limit, word), new BaseHttp.HttpObserver<ProductOutput>() {
            @Override
            public void onSuccess(ProductOutput output) {
                caipingAdapter.addAll(output.getData());
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}