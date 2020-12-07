package com.qkd.customerservice.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created on 12/3/20 13:59
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class TopPaddingDecoration extends RecyclerView.ItemDecoration {

    private int padding;

    public TopPaddingDecoration(int padding) {
        this.padding = padding;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gl = (GridLayoutManager) layoutManager;
            int spanCount = gl.getSpanCount();
            int itemCount = gl.getItemCount();
            int startPos = 0;
            int endPos;
            if (itemCount >= spanCount) {
                endPos = spanCount;
            } else {
                endPos = itemCount;
            }
            for (int i = startPos; i < endPos; i++) {
                outRect.set(0, padding, 0, 0);
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (position == 0) {
                outRect.set(0, padding, 0, 0);
            }
        }
    }
}
