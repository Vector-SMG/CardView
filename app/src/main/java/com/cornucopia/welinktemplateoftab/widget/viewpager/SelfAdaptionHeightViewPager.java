package com.cornucopia.welinktemplateoftab.widget.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 自适应高度ViewPager.
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/12
 */
public class SelfAdaptionHeightViewPager extends ViewPager {

    private int currentIndex = -1;
    private int height=0;

    public SelfAdaptionHeightViewPager(@NonNull Context context) {
        super(context);
    }

    public SelfAdaptionHeightViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (currentIndex >= 0) {
            View child = getChildAt(currentIndex);
            child.measure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));
            height=child.getMeasuredHeight();
            heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 重置高度
     *
     * @param current 当前index
     */
    public void resetHeight(int current) {
        this.currentIndex = current;
        requestLayout();
    }
}
