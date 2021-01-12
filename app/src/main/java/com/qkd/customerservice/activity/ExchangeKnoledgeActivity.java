package com.qkd.customerservice.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.Constant;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.ExchangeTextAdapter;
import com.qkd.customerservice.bean.DeleteKnowledgeOutput;
import com.qkd.customerservice.bean.ExpressionType;
import com.qkd.customerservice.bean.KnowledgeOutput;
import com.qkd.customerservice.dialog.AddKnoledgeDialog;
import com.qkd.customerservice.dialog.AddYuYingDialog;
import com.qkd.customerservice.net.BaseHttp;

import java.util.List;

/**
 * Created on 1/4/21 10:32
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExchangeKnoledgeActivity extends AppCompatActivity implements View.OnClickListener, AddKnoledgeDialog.OnRefreshKnoledge {

    private TextView mPrivateV;
    private TextView mPublicV;

    private RecyclerView mRecyclerView;
    private ExchangeTextAdapter adapter;

    private boolean hasMore = true;
    private String type;
    private int page = 1;
    private boolean loadMoreFlag = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_knoledge);
        setTitle("编辑个人知识库");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mPrivateV = findViewById(R.id.exchage_private);
        mPrivateV.setOnClickListener(this);
        mPublicV = findViewById(R.id.exchage_public);
        mPublicV.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.exchage_used_recy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExchangeTextAdapter(this);
        adapter.setOnClickDeleteListener(new ExchangeTextAdapter.OnClickDeleteListener() {
            @Override
            public void onDelete(int mediaId, int position) {
                deleteMedia(mediaId, position);
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == adapter.getItemCount() - 1 && !loadMoreFlag) {
                        loadMoreFlag = true;
                        page++;
                        initData();
                    }
                }
            }
        });

        type = ExpressionType.EXPRESSION_KNOWLEDGE_TEXT;
        initData();
    }

    private void initData() {
        if (!hasMore) {
            return;
        }
        int serviceId;
        SharedPreferences sp = getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);
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
                    if (page == 1) {
                        adapter.clear();
                    }
                    adapter.addAll(list);
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

    private void deleteMedia(int mediaId, final int position) {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).deleteKnowledge(mediaId), new BaseHttp.HttpObserver<DeleteKnowledgeOutput>() {
            @Override
            public void onSuccess(DeleteKnowledgeOutput baseOutput) {
                if (baseOutput.isSuccess()) {
                    adapter.delete(position);
                    Toast.makeText(ExchangeKnoledgeActivity.this, baseOutput.getData(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        mPrivateV.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
        mPrivateV.setBackgroundResource(R.drawable.text_gray_bg);
        mPublicV.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
        mPublicV.setBackgroundResource(R.drawable.text_gray_bg);
        switch (view.getId()) {
            case R.id.exchage_private:
                mPrivateV.setTextColor(ContextCompat.getColor(this, R.color.app_blue));
                mPrivateV.setBackgroundResource(R.drawable.text_blue_bg);
                type = ExpressionType.EXPRESSION_KNOWLEDGE_TEXT;
                hasMore = true;
                page = 1;
                initData();
                break;
            case R.id.exchage_public:
                mPublicV.setTextColor(ContextCompat.getColor(this, R.color.app_blue));
                mPublicV.setBackgroundResource(R.drawable.text_blue_bg);
                type = ExpressionType.EXPRESSION_KNOWLEDGE_YUYING;
                hasMore = true;
                page = 1;
                initData();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exchange_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        } else if (item.getItemId() == R.id.menu_add) {
            if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type)) {
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                AddKnoledgeDialog addKnoledgeDialog = new AddKnoledgeDialog();
                addKnoledgeDialog.setArguments(bundle);
                addKnoledgeDialog.setOnRefreshKnoledge(this);
                addKnoledgeDialog.show(getSupportFragmentManager(), "addKnoledgeDialog");
            } else if (ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                AddYuYingDialog addYuYingDialog = new AddYuYingDialog();
                addYuYingDialog.setArguments(bundle);
                addYuYingDialog.setOnRefreshKnoledge(this);
                addYuYingDialog.show(getSupportFragmentManager(), "addYuYingDialog");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        hasMore = true;
        page = 1;
        initData();
    }
}