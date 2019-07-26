package com.cornucopia.welinktemplateoftab.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * ViewHolder通用类
 * @author l84116371
 * @version 1.0
 * @since 2019/6/26
 */
public class BaseViewHolder extends RecyclerView.ViewHolder{
    private Context context;
    private View itemView;
    private SparseArray<View> viewSparseArray;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        this.viewSparseArray = new SparseArray<View>();
    }

    public View getItemView() {
        return itemView;
    }

    public <T extends View> T findViewById(int viewId) {
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }
}
