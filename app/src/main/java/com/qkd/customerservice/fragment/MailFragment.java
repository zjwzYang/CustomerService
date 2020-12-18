package com.qkd.customerservice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.qkd.customerservice.R;
import com.qkd.customerservice.adapter.MailFAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/2/20 10:22
 * .
 *
 * @author yj
 */
public class MailFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail, container, false);
        mTabLayout = view.findViewById(R.id.mail_tab);
        mViewPager = view.findViewById(R.id.mail_page);
        initView();
        return view;
    }

    private void initView() {
        List<Fragment> fragments = new ArrayList<>();
        MailIndexFragment fragment1 = new MailIndexFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("userStatus", 1);
        fragment1.setArguments(bundle1);

        MailIndexFragment fragment2 = new MailIndexFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("userStatus", 2);
        fragment2.setArguments(bundle2);

        MailIndexFragment fragment3 = new MailIndexFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("userStatus", 3);
        fragment3.setArguments(bundle3);

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        MailFAdapter adapter = new MailFAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData() {

    }
}
