package com.qkd.customerservice.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.qkd.customerservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/8/20 14:43
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class MainImgAdapter extends RecyclerView.Adapter<MainImgAdapter.MainViewHolder> {

    private Context mContext;
    private List<String> dataList;

    public MainImgAdapter(Context context) {
        mContext = context;
        dataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main_img, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Glide.with(mContext)
                .load(dataList.get(position))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.i("12345678", "onLoadFailed: " + e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void add(String url) {
        dataList.add(url);
        Log.i("12345678", "add: 长度" + dataList.size());
        notifyDataSetChanged();
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_main_img);
        }
    }
}