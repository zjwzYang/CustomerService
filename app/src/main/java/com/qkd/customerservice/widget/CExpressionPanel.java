package com.qkd.customerservice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.qkd.customerservice.MyApp;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.ExpressionPagerListAdapter;
import com.qkd.customerservice.adapter.ExpressionTypeListAdapter;
import com.qkd.customerservice.bean.ExpressionType;
import com.qkd.customerservice.key_library.IPanel;
import com.qkd.customerservice.key_library.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/3/20 13:12
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CExpressionPanel extends LinearLayout implements IPanel {

    private List<ExpressionType> expressionTypeList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ViewPager2 mViewPager2;

    public CExpressionPanel(Context context) {
        this(context, null);
    }

    public CExpressionPanel(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CExpressionPanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_expression_panel, this, true);
        mRecyclerView = findViewById(R.id.recycler_view);
        mViewPager2 = findViewById(R.id.view_pager);
        init();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getPanelHeight();
        setLayoutParams(layoutParams);
    }

    private void init() {
        setOrientation(VERTICAL);
        initData();
        initRecyclerView();
        initViewPager();
    }

    private void initData() {
        expressionTypeList.add(new ExpressionType(R.drawable.ic_expression_panel_tab_normal));
        expressionTypeList.add(new ExpressionType(R.drawable.ic_chang_text));
        expressionTypeList.add(new ExpressionType(R.drawable.ic_wenzhang));
        expressionTypeList.add(new ExpressionType(R.drawable.ic_product));
    }

    private void initRecyclerView() {
        ExpressionTypeListAdapter adapter = new ExpressionTypeListAdapter(getContext(), expressionTypeList);
        adapter.setOnClickExpressionItemListener(new ExpressionTypeListAdapter.OnClickExpressionItemListener() {
            @Override
            public void onClickItem(int position) {
                mViewPager2.setCurrentItem(position);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    private void initViewPager() {
        ExpressionPagerListAdapter adapter = new ExpressionPagerListAdapter((FragmentActivity) getContext(), expressionTypeList);
        mViewPager2.setAdapter(adapter);
        mViewPager2.setUserInputEnabled(true);
    }

    @Override
    public void reset() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(GONE);
            }
        }, 0);
    }

    @Override
    public int getPanelHeight() {
        int height;
        if (MyApp.keyboardHeight == 0) {
            height = DensityUtil.getScreenHeight(getContext()) / 5 * 2;
        } else {
            height = MyApp.keyboardHeight;
        }
        return height + DensityUtil.dp2px(getContext(), 36.0f);
    }
}
