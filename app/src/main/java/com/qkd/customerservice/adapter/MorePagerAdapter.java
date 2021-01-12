package com.qkd.customerservice.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qkd.customerservice.bean.MoreAction;
import com.qkd.customerservice.fragment.MoreActionFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/4/20 13:37
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class MorePagerAdapter extends FragmentStateAdapter {

    private List<MoreAction> mActionList;

    public MorePagerAdapter(@NonNull FragmentActivity fragmentActivity, List<MoreAction> mActionList) {
        super(fragmentActivity);
        this.mActionList = mActionList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int startP = position * 8;
        int endP = startP + 8;
        if (endP > mActionList.size()) {
            endP = mActionList.size();
        }
        List<MoreAction> subList = mActionList.subList(startP, endP);
        List<MoreAction> newList = new ArrayList<>(subList);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MoreActionFragment.ACTION_LIST, (Serializable) newList);
        MoreActionFragment fragment = new MoreActionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil(mActionList.size() / 8.0);
    }
}