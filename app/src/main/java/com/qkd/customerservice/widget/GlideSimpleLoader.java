package com.qkd.customerservice.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.ielse.imagewatcher.ImageWatcher;
import com.qkd.customerservice.R;

/**
 * Created on 2019/4/12 09:38
 * 图片加载类.
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class GlideSimpleLoader implements ImageWatcher.Loader {
    @Override
    public void load(Context context, Uri uri, final ImageWatcher.LoadCallback lc) {
        Glide.with(context).load(uri).error(R.drawable.bg_defaule_img).into(new CustomTarget<Drawable>() {

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                lc.onLoadStarted(placeholder);
            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                lc.onResourceReady(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                lc.onLoadFailed(placeholder);
            }
        });
    }
}
