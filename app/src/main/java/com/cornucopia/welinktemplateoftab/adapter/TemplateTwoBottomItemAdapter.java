package com.cornucopia.welinktemplateoftab.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.cornucopia.welinktemplateoftab.R;
import com.cornucopia.welinktemplateoftab.common.BaseViewHolder;
import com.cornucopia.welinktemplateoftab.common.RecyclerViewBaseAdapter;
import com.cornucopia.welinktemplateoftab.domain.NotifyItemBean;

/**
 * 模板二-底部视图-Items适配器
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/11
 */
public class TemplateTwoBottomItemAdapter extends RecyclerViewBaseAdapter<String> {


    public TemplateTwoBottomItemAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, String itemData, int position) {
        TextView mValueTv = holder.findViewById(R.id.business_mould_template_two_bottom_value_tv);
        mValueTv.setText(TextUtils.isEmpty(itemData) ? "" : itemData);
    }

}
