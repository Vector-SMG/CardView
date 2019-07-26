package com.cornucopia.welinktemplateoftab.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.cornucopia.welinktemplateoftab.R;
import com.cornucopia.welinktemplateoftab.common.BaseViewHolder;
import com.cornucopia.welinktemplateoftab.common.RecyclerViewBaseAdapter;
import com.cornucopia.welinktemplateoftab.domain.TemplateThreeItemBean;

/**
 * 模板三-Items适配器
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/11
 */
public class TemplateThreeItemAdapter extends RecyclerViewBaseAdapter<TemplateThreeItemBean> {


    public TemplateThreeItemAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, TemplateThreeItemBean itemData, int position) {
        ImageView imageView = holder.findViewById(R.id.business_mould_template_three_item_icon);
        TextView tv = holder.findViewById(R.id.business_mould_template_three_item_tv);
        tv.setText(itemData.getTitle() + "");
    }

}
