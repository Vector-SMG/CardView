package com.cornucopia.welinktemplateoftab.widget.looper;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 帮助实现RecyclerView无限循环的LooperLayoutManager.
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/11
 */
public class LooperLayoutManger extends RecyclerView.LayoutManager {

    private static final String TAG = "LooperLayoutManger";
    //是否允许可以循环
    private boolean looperEnable;

    public boolean isLooperEnable() {
        return looperEnable;
    }

    public void setLooperEnable(boolean looperEnable) {
        this.looperEnable = looperEnable;
    }

    /**
     * 设置RecyclerView Item的布局参数
     *
     * @return
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) {
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        int autualHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View itemView = recycler.getViewForPosition(i);
            addView(itemView);
            measureChildWithMargins(itemView, 0, 0);
            int width = getDecoratedMeasuredWidth(itemView);
            int height = getDecoratedMeasuredHeight(itemView);
            layoutDecorated(itemView, 0, autualHeight, width, autualHeight + height);
            autualHeight += height;
            if (autualHeight > getHeight()) {
                break;
            }
        }
    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int travl = fill(dy, recycler, state);
        if (travl == 0) {
            return 0;
        }
        offsetChildrenVertical(-travl);
        recyclerHideView(dy, recycler, state);
        return travl;
    }

    /**
     * 上下滑动的填充
     *
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    private int fill(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dy > 0) {
            //上滑动
            View lastView = getChildAt(getChildCount() - 1);
            if (lastView == null) {
                return 0;
            }
            int lastPos = getPosition(lastView);
            //可见的最后一个ItemView完全滑进来，需要补充新的
            if (lastView.getBottom() < getHeight()) {
                View scrap = null;
                if (lastPos == getItemCount() - 1) {
                    if (looperEnable) {
                        scrap = recycler.getViewForPosition(0);
                    } else {
                        dy = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1);
                }
                if (scrap == null) {
                    return dy;
                }
                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, 0, lastView.getBottom(), width, lastView.getBottom()+height);
                return dy;
            }
        } else {
            //下滑动
            View firstView = getChildAt(0);
            if (firstView == null) {
                return 0;
            }
            int firstPos = getPosition(firstView);
            if (firstView.getTop() >= 0) {
                View scrap = null;
                if (firstPos == 0) {
                    if (looperEnable) {
                        scrap = recycler.getViewForPosition(getItemCount() - 1);
                    } else {
                        dy = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1);
                }
                if (scrap == null) {
                    return 0;
                }
                addView(scrap, 0);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, 0, firstView.getTop()-height, width, firstView.getTop());
            }
        }
        return dy;
    }

    /**
     * 回收界面不可见的View
     *
     * @param dy
     * @param recycler
     * @param state
     */
    private void recyclerHideView(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == null) {
                continue;
            }
            if (dy > 0) {
                if (view.getBottom()< 0) {
                    removeAndRecycleView(view, recycler);
                    Log.d(TAG, "循环: 移除 一个view  childCount=" + getChildCount());
                }
            } else {
                if (view.getTop() > getHeight()) {
                    removeAndRecycleView(view, recycler);
                    Log.d(TAG, "循环: 移除 一个view  childCount=" + getChildCount());
                }
            }
        }
    }

}
