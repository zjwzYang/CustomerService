package com.qkd.customerservice.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qkd.customerservice.adapter.CalcThreeAdapter;
import com.qkd.customerservice.bean.PlatformThreeDataBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created on 2021/6/23 09:43
 * .
 *
 * @author yj
 * @org 浙江趣看点科技有限公司
 */
public class CalcItemDecoration extends RecyclerView.ItemDecoration {

    private CalcThreeAdapter adapter;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CalcItemDecoration(CalcThreeAdapter adapter, Context context) {
        this.adapter = adapter;
        paint.setTextSize(sp2px(20, context));
        paint.setColor(Color.BLACK);
    }

    @Override
    public void getItemOffsets(@NonNull @NotNull Rect outRect, @NonNull @NotNull View view, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        List<PlatformThreeDataBean.ChildrenDTO.DataDTO> list = adapter.getAll();
        int Offsets = 10;
        if (position == 0) {
            Offsets = 100;
        } else {
            PlatformThreeDataBean.ChildrenDTO.DataDTO lastItem = list.get(position - 1);
            PlatformThreeDataBean.ChildrenDTO.DataDTO item = list.get(position);
            if (!lastItem.getModuleCode().equals(item.getModuleCode())) {
                Offsets = 100;
            }
        }

        outRect.set(0, Offsets, 0, 0);
    }

    @Override
    public void onDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            int position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition() + i;
            View child = parent.getChildAt(i);
            int left = 0;
            int top = child.getTop() - 100;
            int right = child.getRight();
            int bottom = child.getBottom() - child.getMeasuredHeight();
            Rect targetRect = new Rect(left, top, right, bottom);
            paint.setColor(Color.parseColor("#eaeaea"));
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            List<PlatformThreeDataBean.ChildrenDTO.DataDTO> list = adapter.getAll();
            String text = list.get(position).getModuleName();

            if (position == 0) {
                c.drawRect(targetRect, paint);
                drawLetter(text, 0, baseline, c, parent);
            } else {
                PlatformThreeDataBean.ChildrenDTO.DataDTO lastItem = list.get(position - 1);
                PlatformThreeDataBean.ChildrenDTO.DataDTO item = list.get(position);
                if (!lastItem.getModuleCode().equals(item.getModuleCode())) {
                    c.drawRect(targetRect, paint);
                    drawLetter(text, 0, baseline, c, parent);
                }
            }


        }

    }

    private void drawLetter(String text, float width, float height, Canvas c, RecyclerView parent) {
        paint.setColor(Color.BLACK);
        c.drawText(text, 20, height, paint);
    }

    public int sp2px(int sp, Context context) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics()) + 0.5f);
    }
}