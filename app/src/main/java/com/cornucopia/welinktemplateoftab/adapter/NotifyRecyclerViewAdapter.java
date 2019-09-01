package com.cornucopia.welinktemplateoftab.adapter;

import android.content.Context;
import android.graphics.Color;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.cornucopia.welinktemplateoftab.R;
import com.cornucopia.welinktemplateoftab.common.BaseViewHolder;
import com.cornucopia.welinktemplateoftab.common.RecyclerViewBaseAdapter;
import com.cornucopia.welinktemplateoftab.domain.NotifyItemBean;
import java.util.List;

/**
 * 通知功能-数据适配器.
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/11
 */
public class NotifyRecyclerViewAdapter extends RecyclerViewBaseAdapter<NotifyItemBean> {


    public NotifyRecyclerViewAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public NotifyRecyclerViewAdapter(Context context, int layoutId,
        List<NotifyItemBean> dataList) {
        super(context, layoutId, dataList);
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, NotifyItemBean itemData, int position) {
        Log.e("CardView","第"+position+"项开始:"+System.currentTimeMillis());
        TextView esales_notify_hint_tv = holder.findViewById(R.id.esales_notify_hint_tv);
        TextView esales_notify_value_tv = holder.findViewById(R.id.esales_notify_value_tv);

        esales_notify_hint_tv.setText(itemData.getTitle());
        esales_notify_value_tv.setText(itemData.getValue());

        try {
            esales_notify_hint_tv.setTextColor(Color.parseColor(itemData.getTitle()));
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            esales_notify_value_tv.setTextColor(Color.parseColor(itemData.getValue()));
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e("CardView","第"+position+"项结束:"+System.currentTimeMillis());
    }

}
