package com.cornucopia.welinktemplateoftab.adapter;

import android.content.Context;
import android.widget.TextView;

import com.cornucopia.welinktemplateoftab.R;
import com.cornucopia.welinktemplateoftab.common.BaseViewHolder;
import com.cornucopia.welinktemplateoftab.common.RecyclerViewBaseAdapter;
import com.cornucopia.welinktemplateoftab.domain.TemplateOneTopItemBean;

/**
 * 模板一-顶部视图-Items适配器
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/11
 */
public class TemplateOneTopItemAdapter extends RecyclerViewBaseAdapter<TemplateOneTopItemBean> {


    public TemplateOneTopItemAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, TemplateOneTopItemBean itemData, int position) {
        TextView mTitleTv = holder.findViewById(R.id.business_mould_template_one_top_title_tv);
        TextView mValueTv = holder.findViewById(R.id.business_mould_template_one_top_value_tv);
        TextView mDescTv = holder.findViewById(R.id.business_mould_template_one_top_desc_tv);


    }


}
