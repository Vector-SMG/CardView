package com.cornucopia.welinktemplateoftab.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.cornucopia.welinktemplateoftab.R;
import com.cornucopia.welinktemplateoftab.common.BaseViewHolder;
import com.cornucopia.welinktemplateoftab.common.RecyclerViewBaseAdapter;
import com.cornucopia.welinktemplateoftab.domain.NotifyItemBean;

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

    @Override
    public void bindViewHolder(BaseViewHolder holder, NotifyItemBean itemData, int position) {
        TextView esales_notify_hint_tv = holder.findViewById(R.id.esales_notify_hint_tv);
        TextView esales_notify_value_tv = holder.findViewById(R.id.esales_notify_value_tv);
        String title = "";
        String value = null;
        //设置hint值
        if (itemData != null) {
            if (!TextUtils.isEmpty(itemData.getTitle())) {
                title = itemData.getTitle();
            }
            if (!TextUtils.isEmpty(itemData.getValue())) {
                value = itemData.getValue();
            }
        }
        //设置通知的标题
        esales_notify_hint_tv.setText(title);
        //设置通知的value值
        esales_notify_value_tv.setText(value == null ? "" : value);
    }

}
