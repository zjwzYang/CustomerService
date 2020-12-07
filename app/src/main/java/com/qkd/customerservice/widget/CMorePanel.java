package com.qkd.customerservice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.qkd.customerservice.MyApp;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.MorePagerAdapter;
import com.qkd.customerservice.bean.MoreAction;
import com.qkd.customerservice.key_library.IPanel;
import com.qkd.customerservice.key_library.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/3/20 14:25
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class CMorePanel extends FrameLayout implements IPanel {

    private ViewPager2 mViewPager2;
    private LinearLayout mPageIndex;

    public CMorePanel(@NonNull Context context) {
        this(context, null);
    }

    public CMorePanel(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CMorePanel(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_more_panel, this, true);
        mViewPager2 = findViewById(R.id.more_view_pager);
        mPageIndex = findViewById(R.id.more_index);
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
        List<MoreAction> mActionList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            MoreAction action = new MoreAction();
            action.setActionReId(R.mipmap.ic_launcher);
            action.setActionName("第" + (i + 1) + "条");
            mActionList.add(action);
        }
        MorePagerAdapter adapter = new MorePagerAdapter((FragmentActivity) getContext(), mActionList);
        mViewPager2.setAdapter(adapter);
//        mViewPager2.setUserInputEnabled(true);
        for (int i = 0; i < adapter.getItemCount(); i++) {
            ImageView view = new ImageView(getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    DensityUtil.dp2px(getContext(), 20f), DensityUtil.dp2px(getContext(), 20f));
            view.setLayoutParams(params);
            if (i == 0) {
                view.setImageResource(R.drawable.ic_page_index_select);
            } else {
                view.setImageResource(R.drawable.ic_page_index);
            }
            mPageIndex.addView(view);
        }
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for (int i = 0; i < mPageIndex.getChildCount(); i++) {
                    ((ImageView) mPageIndex.getChildAt(i)).setImageResource(R.drawable.ic_page_index);
                }
                ((ImageView) mPageIndex.getChildAt(position)).setImageResource(R.drawable.ic_page_index_select);
            }
        });
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
        return MyApp.keyboardHeight - DensityUtil.dp2px(getContext(), 56.0f);
    }
}
