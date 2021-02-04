package com.qkd.customerservice.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.AppUtil;
import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.ImageMsg;
import com.qkd.customerservice.bean.MoreAction;
import com.qkd.customerservice.bean.MsgBean;
import com.qkd.customerservice.bean.TextMsg;
import com.qkd.customerservice.widget.LocalGlideEngine;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

import static com.qkd.customerservice.activity.ChatActivity.IMAGE_REQUEST;

/**
 * Created on 12/4/20 13:47
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class MoreActionAdapter extends RecyclerView.Adapter<MoreActionAdapter.MoreActionViewHolder> {

    private Context context;
    private List<MoreAction> mMoreActions;
    private String qrCode;

    public MoreActionAdapter(Context context, List<MoreAction> moreActions) {
        this.context = context;
        mMoreActions = moreActions;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @NonNull
    @Override
    public MoreActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoreActionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_more_action, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoreActionViewHolder holder, final int position) {
        final MoreAction moreAction = mMoreActions.get(position);
        holder.actionImg.setImageResource(moreAction.getActionReId());
        holder.actionName.setText(moreAction.getActionName());

//        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
//        params.width =
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int actionType = moreAction.getActionType();
                switch (actionType) {
                    case MoreAction.ACTION_TYPE_PHOTO:
                        selectImg();
                        break;
                    case MoreAction.ACTION_TYPE_DINGZHI:
                        String text = "http://qukanbao.qukandian573.com/scheme";
                        TextMsg msgBean = new TextMsg();
                        msgBean.setMsgType(MsgBean.MsgType.TEXT);
                        msgBean.setType(1);
                        msgBean.setContent(text);
                        msgBean.setSendTime(AppUtil.getTimeString(new Date().getTime()));
                        msgBean.setNickName("我");
                        EventBus.getDefault().post(msgBean);
                        break;
                    case MoreAction.ACTION_TYPE_CODE:
                        if (!TextUtils.isEmpty(qrCode)) {
                            ImageMsg imageMsg = new ImageMsg();
                            imageMsg.setNickName("我");
                            imageMsg.setType(1);
                            imageMsg.setMsgType(MsgBean.MsgType.IMAGE);
                            imageMsg.setImgPath(qrCode);
                            imageMsg.setSendTime(AppUtil.getTimeString(new Date().getTime()));
                            EventBus.getDefault().post(imageMsg);
                        } else {
                            Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void selectImg() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Matisse.from((Activity) context)
                                .choose(MimeType.ofImage())
                                .showSingleMediaType(true)
                                .countable(true)
                                .maxSelectable(9)
                                .thumbnailScale(0.8f)
                                .theme(R.style.Matisse_Dracula)
                                .imageEngine(new LocalGlideEngine())
                                .forResult(IMAGE_REQUEST);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(context, "请先获取权限", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

    @Override
    public int getItemCount() {
        return mMoreActions.size();
    }

    static class MoreActionViewHolder extends RecyclerView.ViewHolder {
        private TextView actionName;
        private ImageView actionImg;

        public MoreActionViewHolder(@NonNull View itemView) {
            super(itemView);
            actionImg = itemView.findViewById(R.id.item_action_img);
            actionName = itemView.findViewById(R.id.item_action_name);
        }
    }

}