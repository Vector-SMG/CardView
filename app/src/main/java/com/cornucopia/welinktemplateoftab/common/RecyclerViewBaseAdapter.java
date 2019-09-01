package com.cornucopia.welinktemplateoftab.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * RecylcerView通用的适配器，提取通用逻辑，简化调用。
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/6/26
 */
public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private final int layoutId;
    private Context context;
    private List<T> dataList;

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public Context getContext() {
        return context;
    }

    public RecyclerViewBaseAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    public RecyclerViewBaseAdapter(Context context, int layoutId,List<T> dataList) {
        this.context = context;
        this.layoutId = layoutId;
        this.dataList=dataList;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        bindViewHolder(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public abstract void bindViewHolder(BaseViewHolder holder, T itemData, int position);

}
