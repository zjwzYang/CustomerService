package com.qkd.customerservice.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.qkd.customerservice.Constant;
import com.qkd.customerservice.NetUtil;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.ExchangePhotoAdapter;
import com.qkd.customerservice.adapter.ExchangeTextAdapter;
import com.qkd.customerservice.audio.AudioPlayManager;
import com.qkd.customerservice.audio.AudioRecordManager;
import com.qkd.customerservice.bean.DeleteKnowledgeOutput;
import com.qkd.customerservice.bean.ExpressionType;
import com.qkd.customerservice.bean.FileUploadBean;
import com.qkd.customerservice.bean.KnowledgeOutput;
import com.qkd.customerservice.dialog.AddKnoledgeDialog;
import com.qkd.customerservice.dialog.AddPhotoDialog;
import com.qkd.customerservice.dialog.AddYuYingDialog;
import com.qkd.customerservice.net.BaseHttp;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.zhihu.matisse.Matisse;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.qkd.customerservice.activity.ChatActivity.IMAGE_REQUEST;

/**
 * Created on 1/4/21 10:32
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExchangeKnoledgeActivity extends AppCompatActivity implements View.OnClickListener, AddKnoledgeDialog.OnRefreshKnoledge {

    private TextView mPrivateV;
    private TextView mPhotoV;
    private TextView mPublicV;

    private RecyclerView mRecyclerView;
    private ExchangeTextAdapter adapter;
    private ExchangePhotoAdapter mPhotoAdapter;

    private boolean hasMore = true;
    private String type;
    private int page = 1;
    private boolean loadMoreFlag = false;

    private AddPhotoDialog addPhotoDialog;

    @SuppressLint("WrongConstant")
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
        mPhotoV = findViewById(R.id.exchage_private2);
        mPhotoV.setOnClickListener(this);
        mPublicV = findViewById(R.id.exchage_public);
        mPublicV.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.exchage_used_recy);
        type = ExpressionType.EXPRESSION_KNOWLEDGE_TEXT;
        initView();
        initData();

        String[] permissions = {Manifest.permission.RECORD_AUDIO};
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(ExchangeKnoledgeActivity.this, "请先获取权限", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

    private void initView() {
        if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type) || ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
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
        } else if (ExpressionType.EXPRESSION_KNOWLEDGE_PHOTO.equals(type)) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            mPhotoAdapter = new ExchangePhotoAdapter(this);
            mPhotoAdapter.setOnClickDeleteListener(new ExchangeTextAdapter.OnClickDeleteListener() {
                @Override
                public void onDelete(int mediaId, int position) {
                    deleteMedia(mediaId, position);
                }
            });
            mRecyclerView.setAdapter(mPhotoAdapter);
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                    RecyclerView.Adapter temAdapter;
                    if (ExpressionType.EXPRESSION_KNOWLEDGE_PHOTO.equals(type)) {
                        temAdapter = mPhotoAdapter;
                    } else {
                        temAdapter = adapter;
                    }
                    if (lastVisibleItemPosition == temAdapter.getItemCount() - 1 && !loadMoreFlag) {
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
        SharedPreferences sp = getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);
        int mediaType = 1;
        if (ExpressionType.EXPRESSION_KNOWLEDGE_TEXT.equals(type)) {
            mediaType = 1;
            adapter.setMediaType(mediaType);
        } else if (ExpressionType.EXPRESSION_KNOWLEDGE_PHOTO.equals(type)) {
            mediaType = 2;
        } else if (ExpressionType.EXPRESSION_KNOWLEDGE_YUYING.equals(type)) {
            mediaType = 3;
            adapter.setMediaType(mediaType);
        }
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE)
                .queryKnowledge(mediaType, serviceId, page, ""), new BaseHttp.HttpObserver<KnowledgeOutput>() {
            @Override
            public void onSuccess(KnowledgeOutput baseOutput) {
                loadMoreFlag = false;
                if (baseOutput.isSuccess()) {
                    List<KnowledgeOutput.DataBean.ListBean> list = baseOutput.getData().getList();
                    if (list.size() == baseOutput.getData().getPageSize()) {
                        hasMore = true;
                    } else {
                        hasMore = false;
                    }
                    if (ExpressionType.EXPRESSION_KNOWLEDGE_PHOTO.equals(type)) {
                        if (page == 1) {
                            mPhotoAdapter.clear();
                        }
                        mPhotoAdapter.addAll(list);
                    } else {
                        if (page == 1) {
                            adapter.clear();
                        }
                        adapter.addAll(list);
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
                    if (ExpressionType.EXPRESSION_KNOWLEDGE_PHOTO.equals(type)) {
                        mPhotoAdapter.delete(position);
                    } else {
                        adapter.delete(position);
                    }
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
        AudioPlayManager.getInstance().stopPlay();
        AudioRecordManager.getInstance().destroyRecord();
        mPrivateV.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
        mPrivateV.setBackgroundResource(R.drawable.text_gray_bg);
        mPhotoV.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
        mPhotoV.setBackgroundResource(R.drawable.text_gray_bg);
        mPublicV.setTextColor(ContextCompat.getColor(this, R.color.divi_color));
        mPublicV.setBackgroundResource(R.drawable.text_gray_bg);
        switch (view.getId()) {
            case R.id.exchage_private:
                mPrivateV.setTextColor(ContextCompat.getColor(this, R.color.app_blue));
                mPrivateV.setBackgroundResource(R.drawable.text_blue_bg);
                type = ExpressionType.EXPRESSION_KNOWLEDGE_TEXT;
                hasMore = true;
                page = 1;
                initView();
                initData();
                break;
            case R.id.exchage_private2:
                mPhotoV.setTextColor(ContextCompat.getColor(this, R.color.app_blue));
                mPhotoV.setBackgroundResource(R.drawable.text_blue_bg);
                type = ExpressionType.EXPRESSION_KNOWLEDGE_PHOTO;
                hasMore = true;
                page = 1;
                initView();
                initData();
                break;
            case R.id.exchage_public:
                mPublicV.setTextColor(ContextCompat.getColor(this, R.color.app_blue));
                mPublicV.setBackgroundResource(R.drawable.text_blue_bg);
                type = ExpressionType.EXPRESSION_KNOWLEDGE_YUYING;
                hasMore = true;
                page = 1;
                initView();
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
            } else if (ExpressionType.EXPRESSION_KNOWLEDGE_PHOTO.equals(type)) {
                addPhotoDialog = new AddPhotoDialog();
                addPhotoDialog.setOnRefreshKnoledge(this);
                addPhotoDialog.show(getSupportFragmentManager(), "addPhotoDialog");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK) {
            List<String> imgStrs = Matisse.obtainPathResult(data);
            for (final String path : imgStrs) {
                NetUtil.upLoadVideoFile(new File(path), new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String str = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Gson gson = new Gson();
                                    FileUploadBean uploadBean = gson.fromJson(str, FileUploadBean.class);
                                    if (addPhotoDialog != null) {
                                        addPhotoDialog.setImageUrl(uploadBean.getData());
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onRefresh() {
        hasMore = true;
        page = 1;
        initData();
    }

    @Override
    protected void onDestroy() {
        AudioRecordManager.getInstance().destroyRecord();
        AudioPlayManager.getInstance().stopPlay();
        super.onDestroy();
    }
}