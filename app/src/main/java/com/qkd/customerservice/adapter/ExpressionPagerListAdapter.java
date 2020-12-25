package com.qkd.customerservice.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qkd.customerservice.bean.ExpressionManager;
import com.qkd.customerservice.bean.ExpressionType;
import com.qkd.customerservice.fragment.ArticleFragment;
import com.qkd.customerservice.fragment.CommonlyUsedFragment;
import com.qkd.customerservice.fragment.NormalExpressionPagerFragment;
import com.qkd.customerservice.fragment.ProductFragment;

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
        ExpressionType expressionType = expressionTypeList.get(position);
        String type = expressionType.getType();
        switch (type) {
            case ExpressionType.EXPRESSION_EMOJI:
                return NormalExpressionPagerFragment.newInstance(ExpressionManager.instance.getNormalExpressionList());
            case ExpressionType.EXPRESSION_KNOWLEDGE_TEXT:
                CommonlyUsedFragment textFragment = new CommonlyUsedFragment();
                Bundle textB = new Bundle();
                textB.putString("type", ExpressionType.EXPRESSION_KNOWLEDGE_TEXT);
                textFragment.setArguments(textB);
                return textFragment;
            case ExpressionType.EXPRESSION_KNOWLEDGE_YUYING:
                CommonlyUsedFragment yuyinFragment = new CommonlyUsedFragment();
                Bundle yuyinB = new Bundle();
                yuyinB.putString("type", ExpressionType.EXPRESSION_KNOWLEDGE_YUYING);
                yuyinFragment.setArguments(yuyinB);
                return yuyinFragment;
            case ExpressionType.EXPRESSION_PRODUCT_ONE:
                return new ArticleFragment();
            case ExpressionType.EXPRESSION_PRODUCT_TWO:
                return new ProductFragment();
            default:
                return new Fragment();
        }
    }
}
