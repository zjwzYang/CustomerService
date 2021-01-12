package com.qkd.customerservice.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created on 12/16/20 16:30
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class MailFAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private String[] titles = {"关注", "未关注", "方案定制"};

    public MailFAdapter(@NonNull FragmentManager fm, List<Fragment> mFragments) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mFragments = mFragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}