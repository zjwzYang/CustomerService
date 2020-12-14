package com.qkd.customerservice.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qkd.customerservice.bean.ExpressionManager;
import com.qkd.customerservice.bean.ExpressionType;
import com.qkd.customerservice.fragment.CommonlyUsedFragment;
import com.qkd.customerservice.fragment.NormalExpressionPagerFragment;

import java.util.List;

/**
 * Created on 12/3/20 13:42
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class ExpressionPagerListAdapter extends FragmentStateAdapter {
    private List<ExpressionType> expressionTypeList;

    public ExpressionPagerListAdapter(@NonNull FragmentActivity fragmentActivity, List<ExpressionType> expressionTypeList) {
        super(fragmentActivity);
        this.expressionTypeList = expressionTypeList;
    }


    @Override
    public int getItemCount() {
        if (expressionTypeList == null) {
            return 0;
        } else {
            return expressionTypeList.size();
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return NormalExpressionPagerFragment.newInstance(ExpressionManager.instance.getNormalExpressionList());
        } else if (position == 1) {
            return new CommonlyUsedFragment();
        }
        return new Fragment();
    }
}
