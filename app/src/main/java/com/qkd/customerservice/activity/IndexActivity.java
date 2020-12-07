package com.qkd.customerservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.qkd.customerservice.R;
import com.qkd.customerservice.fragment.MailFragment;
import com.qkd.customerservice.fragment.MineFragment;
import com.qkd.customerservice.fragment.MsgFragment;

/**
 * Created on 12/2/20 09:22
 * .
 *
 * @author yj
 */
public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mMsgImg;
    private TextView mMsgText;
    private ImageView mMailImg;
    private TextView mMailText;
    private ImageView mMineImg;
    private TextView mMineText;

    private MsgFragment mMsgFragment;
    private MailFragment mMailFragment;
    private MineFragment mMineFragment;
    private Fragment currFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mMsgImg = findViewById(R.id.index_msg_img);
        mMsgText = findViewById(R.id.index_msg_text);
        mMailImg = findViewById(R.id.index_mail_img);
        mMailText = findViewById(R.id.index_mail_text);
        mMineImg = findViewById(R.id.index_mine_img);
        mMineText = findViewById(R.id.index_mine_text);

        findViewById(R.id.msg_linear).setOnClickListener(this);
        findViewById(R.id.mail_linear).setOnClickListener(this);
        findViewById(R.id.mine_linear).setOnClickListener(this);

        selectImg(0);
    }

    private void selectImg(int index) {
        mMsgText.setTextColor(ContextCompat.getColor(this, R.color.text_gray));
        mMsgImg.setBackgroundResource(R.mipmap.index_msg);
        mMailText.setTextColor(ContextCompat.getColor(this, R.color.text_gray));
        mMailImg.setBackgroundResource(R.mipmap.index_mail);
        mMineText.setTextColor(ContextCompat.getColor(this, R.color.text_gray));
        mMineImg.setImageResource(R.mipmap.index_mine);
        switch (index) {
            case 0:
                mMsgImg.setBackgroundResource(R.mipmap.index_msg_selected);
                mMsgText.setTextColor(ContextCompat.getColor(this, R.color.text_black));
                break;
            case 1:
                mMailImg.setBackgroundResource(R.mipmap.index_mail_selected);
                mMailText.setTextColor(ContextCompat.getColor(this, R.color.text_black));
                break;
            case 2:
                mMineImg.setBackgroundResource(R.mipmap.index_mine_selected);
                mMineText.setTextColor(ContextCompat.getColor(this, R.color.text_black));
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (currFragment != null) {
            ft.hide(currFragment);
        }
        switch (index) {
            case 0:
                if (mMsgFragment == null) {
                    mMsgFragment = new MsgFragment();
                    ft.add(R.id.index_frame, mMsgFragment);
                } else {
                    ft.show(mMsgFragment);
                }
                currFragment = mMsgFragment;
                break;
            case 1:
                if (mMailFragment == null) {
                    mMailFragment = new MailFragment();
                    ft.add(R.id.index_frame, mMailFragment);
                } else {
                    ft.show(mMailFragment);
                }
                currFragment = mMailFragment;
                break;
            case 2:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    ft.add(R.id.index_frame, mMineFragment);
                } else {
                    ft.show(mMineFragment);
                }
                currFragment = mMineFragment;
                break;
        }
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.msg_linear:
                selectImg(0);
                break;
            case R.id.mail_linear:
                selectImg(1);
                break;
            case R.id.mine_linear:
                selectImg(2);
                break;
        }
    }
}
