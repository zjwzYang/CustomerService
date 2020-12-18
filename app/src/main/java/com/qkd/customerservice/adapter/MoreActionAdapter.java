package com.qkd.customerservice.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.R;
import com.qkd.customerservice.bean.MoreAction;
import com.qkd.customerservice.widget.LocalGlideEngine;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

import static com.qkd.customerservice.activity.ChatActivity.IMAGE_REQUEST;

/**
 * Created on 12/4/20 13:47
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class MoreActionAdapter extends RecyclerView.Adapter<MoreActionAdapter.MoreActionViewHolder> {

    private Context context;
    private List<MoreAction> mMoreActions;

    public MoreActionAdapter(Context context, List<MoreAction> moreActions) {
        this.context = context;
        mMoreActions = moreActions;
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
                if (position == 0) {
                    selectImg();
                } else if (position == 2) {
                    //context.startActivity(new Intent(context, CustomizedActivity.class));
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