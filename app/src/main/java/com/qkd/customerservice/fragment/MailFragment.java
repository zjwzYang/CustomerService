package com.qkd.customerservice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qkd.customerservice.R;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactLayout;

/**
 * Created on 12/2/20 10:22
 * .
 *
 * @author yj
 */
public class MailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mail, container, false);
        ContactLayout contactLayout = view.findViewById(R.id.mail_contact_layout);
        contactLayout.initDefault();
        return view;
    }
}
