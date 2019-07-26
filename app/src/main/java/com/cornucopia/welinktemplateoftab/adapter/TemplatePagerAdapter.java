package com.cornucopia.welinktemplateoftab.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * 模板页适配器.
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/10
 */
public class TemplatePagerAdapter extends PagerAdapter {

    private List<View> viewList;
    private List<String> titles;

    public TemplatePagerAdapter(List<View> viewList, List<String> titles) {
        this.viewList = viewList;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return viewList == null ? 0 : viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        View view = (View) object;
        int position = (int) view.getTag();
        if (position >= getCount()) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = viewList.get(position);
        view.setTag(position);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(view, params);
        return view;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
