package com.cornucopia.welinktemplateoftab.adapter;

import android.content.Context;
import android.widget.TextView;

import com.cornucopia.welinktemplateoftab.R;
import com.cornucopia.welinktemplateoftab.common.BaseViewHolder;
import com.cornucopia.welinktemplateoftab.common.RecyclerViewBaseAdapter;
import com.cornucopia.welinktemplateoftab.domain.TemplateOneBottomItemBean;
import com.cornucopia.welinktemplateoftab.domain.TemplateOneTopItemBean;

/**
 * 模板一-顶部视图-Items适配器
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/11
 */
public class TemplateOneBottomItemAdapter extends RecyclerViewBaseAdapter<TemplateOneBottomItemBean> {


    public TemplateOneBottomItemAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, TemplateOneBottomItemBean itemData, int position) {
        TextView mValueTv = holder.findViewById(R.id.business_mould_template_one_bottom_item_value_tv);
        TextView mDescTv = holder.findViewById(R.id.business_mould_template_one_bottom_item_desc_tv);

    }


}
