package com.qkd.customerservice.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.CustomizeAdapter;
import com.qkd.customerservice.bean.QueryCustomizeOutput;
import com.qkd.customerservice.key_library.util.UIUtil;
import com.qkd.customerservice.net.BaseHttp;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/**
 * Created on 1/29/21 15:19
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class QueryCustomizeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    private CustomizeAdapter mCustomizeAdapter;

    private int userStatus;
    private int page = 1;
    private String text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_customize);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        userStatus = getIntent().getIntExtra("userStatus", 3);

        initView();
    }

    private void initView() {
        mEditText = findViewById(R.id.search_edit);
        mSmartRefreshLayout = findViewById(R.id.query_refresh);
        findViewById(R.id.search_btn).setOnClickListener(this);
        mRecyclerView = findViewById(R.id.search_recy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mCustomizeAdapter = new CustomizeAdapter(this, userStatus);
        mRecyclerView.setAdapter(mCustomizeAdapter);
        switch (userStatus) {
            case 3:
                setTitle("搜索待定制方案");
                break;
            case 5:
                setTitle("搜索待发送方案");
                break;
            case 4:
                setTitle("搜索已完成方案");
                break;
        }
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                asynData();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                asynData();
            }
        });
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//按下搜索
                    text = mEditText.getText().toString();
                    if (TextUtils.isEmpty(text)) {
                        Toast.makeText(QueryCustomizeActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    } else {
                        page = 1;
                        asynData();
                    }
                }
                return false;//返回true，保留软键盘。false，隐藏软键盘
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_btn:
                text = mEditText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    Toast.makeText(this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                } else {
                    page = 1;
                    asynData();
                }
                break;
        }
    }

    public void asynData() {
        SharedPreferences sp = getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        int serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);
        int orderStatus = 1;
        if (userStatus == 3) {
            orderStatus = 1;
        } else if (userStatus == 4) {
            orderStatus = 3;
        } else if (userStatus == 5) {
            orderStatus = 2;
        }
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).queryCustomizeList(serviceId
                , page, orderStatus, text), new BaseHttp.HttpObserver<QueryCustomizeOutput>() {
            @Override
            public void onSuccess(QueryCustomizeOutput output) {
                UIUtil.hideSoftInput(QueryCustomizeActivity.this, mEditText);
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