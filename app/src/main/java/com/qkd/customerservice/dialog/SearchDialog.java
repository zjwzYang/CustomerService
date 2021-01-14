package com.qkd.customerservice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
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

import java.util.List;

/**
 * Created on 1/12/21 12:35
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class SearchDialog extends DialogFragment implements View.OnClickListener {

    public final static int search_chang = 1;
    public final static int search_yuyin = 2;
    public final static int search_wenzhang = 3;
    public final static int search_caiping = 4;

    private TextView mTypeOne;
    private TextView mTypeTwo;
    private TextView mTypeThree;
    private TextView mTypeFour;

    private EditText mEditText;
    private TextView mTextView;
    private RecyclerView mRecyclerView;

    private String searchWord;

    private int searchType;
    private int serviceId;

    private CommonlyUsedAdapter changAdapter;
    private YuYinUsedAdapter yuyinAdapter;
    private ArticleAdapter wenzhangAdapter;
    private ProductAdapter caipingAdapter;

    private int page = 1;
    private int offset = 0;
    private int limit = 100;
    private boolean hasMore = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mTypeOne = view.findViewById(R.id.search_type_one);
        mTypeOne.setOnClickListener(this);
        mTypeTwo = view.findViewById(R.id.search_type_two);
        mTypeTwo.setOnClickListener(this);
        mTypeThree = view.findViewById(R.id.search_type_three);
        mTypeThree.setOnClickListener(this);
        mTypeFour = view.findViewById(R.id.search_type_four);
        mTypeFour.setOnClickListener(this);

        mEditText = view.findViewById(R.id.search_edit);
        mTextView = view.findViewById(R.id.search_btn);
        mRecyclerView = view.findViewById(R.id.search_recy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        SharedPreferences sp = getContext().getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        serviceId = sp.getInt(Constant.USER_SERVICE_ID, 0);
        searchType = search_chang;
        changAdapter = new CommonlyUsedAdapter(getContext());
        mRecyclerView.setAdapter(changAdapter);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchWord = mEditText.getText().toString();
                if (TextUtils.isEmpty(searchWord)) {
                    Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    initData(searchWord);
                }
            }
        });
    }

    private void initData(String word) {
        if (TextUtils.isEmpty(word)) {
            return;
        }
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

    @Override
    public void onClick(View view) {
        mTypeOne.setTextColor(ContextCompat.getColor(getContext(), R.color.text_black));
        mTypeOne.setBackgroundResource(R.drawable.search_text_grey_bg);
        mTypeTwo.setTextColor(ContextCompat.getColor(getContext(), R.color.text_black));
        mTypeTwo.setBackgroundResource(R.drawable.search_text_grey_bg);
        mTypeThree.setTextColor(ContextCompat.getColor(getContext(), R.color.text_black));
        mTypeThree.setBackgroundResource(R.drawable.search_text_grey_bg);
        mTypeFour.setTextColor(ContextCompat.getColor(getContext(), R.color.text_black));
        mTypeFour.setBackgroundResource(R.drawable.search_text_grey_bg);
        switch (view.getId()) {
            case R.id.search_type_one:
                if (searchType != search_chang) {
                    mTypeOne.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mTypeOne.setBackgroundResource(R.drawable.search_text_blue_bg);
                    searchType = search_chang;
                    if (changAdapter == null) {
                        changAdapter = new CommonlyUsedAdapter(getContext());
                    }
                    mRecyclerView.setAdapter(changAdapter);
                    initData(searchWord);
                }
                break;
            case R.id.search_type_two:
                if (searchType != search_yuyin) {
                    mTypeTwo.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mTypeTwo.setBackgroundResource(R.drawable.search_text_blue_bg);
                    searchType = search_yuyin;
                    if (yuyinAdapter == null) {
                        yuyinAdapter = new YuYinUsedAdapter(getContext());
                    }
                    mRecyclerView.setAdapter(yuyinAdapter);
                    initData(searchWord);
                }
                break;
            case R.id.search_type_three:
                if (searchType != search_wenzhang) {
                    mTypeThree.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mTypeThree.setBackgroundResource(R.drawable.search_text_blue_bg);
                    searchType = search_wenzhang;
                    if (wenzhangAdapter == null) {
                        wenzhangAdapter = new ArticleAdapter(getContext());
                    }
                    mRecyclerView.setAdapter(wenzhangAdapter);
                    initData(searchWord);
                }
                break;
            case R.id.search_type_four:
                if (searchType != search_caiping) {
                    mTypeFour.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mTypeFour.setBackgroundResource(R.drawable.search_text_blue_bg);
                    searchType = search_caiping;
                    if (caipingAdapter == null) {
                        caipingAdapter = new ProductAdapter(getContext());
                    }
                    mRecyclerView.setAdapter(caipingAdapter);
                    initData(searchWord);
                }
                break;
        }
    }

    private void searchChange(final int mediaType, String word) {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE)
                .queryKnowledge(mediaType, serviceId, page, word), new BaseHttp.HttpObserver<KnowledgeOutput>() {
            @Override
            public void onSuccess(KnowledgeOutput baseOutput) {
                if (baseOutput.isSuccess()) {
                    List<KnowledgeOutput.DataBean.ListBean> list = baseOutput.getData().getList();
                    if (list == null || list.size() == 0) {
                        Toast.makeText(getContext(), "无数据", Toast.LENGTH_SHORT).show();
                        if (mediaType == 1) {
                            changAdapter.clear();
                        } else {
                            yuyinAdapter.clear();
                        }
                        return;
                    }
                    if (mediaType == 1) {
                        changAdapter.clear();
                        changAdapter.addAll(list);
                    } else {
                        yuyinAdapter.clear();
                        yuyinAdapter.addAll(list);
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void searchWenzhang(String word) {
        BaseHttp.subscribe(BaseHttp.getRetrofitService(Constant.BASE_URL_CORE).getArtList(offset, limit, word, ""), new BaseHttp.HttpObserver<ArticleOutput>() {
            @Override
            public void onSuccess(ArticleOutput output) {
                List<ArticleOutput.DataBean> data = output.getData();
                if (data == null || data.size() == 0) {
                    wenzhangAdapter.clear();
                    Toast.makeText(getContext(), "无数据", Toast.LENGTH_SHORT).show();
                } else {
                    wenzhangAdapter.clear();
                    wenzhangAdapter.addAll(data);
                }
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
                List<ProductOutput.DataBean> data = output.getData();
                if (data == null || data.size() == 0) {
                    caipingAdapter.clear();
                    Toast.makeText(getContext(), "无数据", Toast.LENGTH_SHORT).show();
                } else {
                    caipingAdapter.clear();
                    caipingAdapter.addAll(data);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            Window window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setWindowAnimations(R.style.animate_dialog);
            setCancelable(true);
        }
    }
}