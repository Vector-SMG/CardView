package com.cornucopia.welinktemplateoftab.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cornucopia.welinktemplateoftab.R;
import com.cornucopia.welinktemplateoftab.adapter.TemplateOneBottomItemAdapter;
import com.cornucopia.welinktemplateoftab.adapter.TemplateOneTopItemAdapter;
import com.cornucopia.welinktemplateoftab.domain.TemplateOneBottomItemBean;
import com.cornucopia.welinktemplateoftab.domain.TemplateOneTopItemBean;
import com.cornucopia.welinktemplateoftab.utils.UiUtils;
import com.cornucopia.welinktemplateoftab.widget.recyclerview.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板一 - 视图.
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/10
 */
public class TemplateOne extends FrameLayout {

    private Context context;
    private RecyclerView mTopRecyclerView;
    private TemplateOneTopItemAdapter templateOneTopItemAdapter;
    private RecyclerView mBottomRecyclerView;
    private TemplateOneBottomItemAdapter templateOneBottomItemAdapter;

    private LinearLayout business_mould_template_link_container_ll;
    private TextView business_mould_template_link_one_tv;
    private LinearLayout business_mould_template_link_two_ll;
    private TextView business_mould_template_link_two_tv;
    private LinearLayout business_mould_template_link_three_ll;
    private TextView business_mould_template_link_three_tv;
    private LinearLayout business_mould_template_link_four_ll;
    private TextView business_mould_template_link_four_tv;


    public TemplateOne(Context context) {
        this(context, null);
    }

    public TemplateOne(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemplateOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View.inflate(this.context, R.layout.welink_uibundle_business_mould_template_one_layout, this);
        initTopView();
        initBottomView();
        updateData();
    }


    /**
     * 初始化顶部视图
     */
    @SuppressLint("ClickableViewAccessibility") private void initTopView() {
        mTopRecyclerView = findViewById(R.id.business_mould_template_one_top_view_rv);
        mTopRecyclerView.setLayoutManager(new GridLayoutManager(this.context, 2));
        mTopRecyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        return true;
                }
                return false;
            }
        });
        templateOneTopItemAdapter =
                new TemplateOneTopItemAdapter(this.context, R.layout.welink_uibundle_business_mould_template_one_top_item_layout);
        mTopRecyclerView.setAdapter(templateOneTopItemAdapter);
    }

    /**
     * 初始化底部视图
     */
    @SuppressLint("ClickableViewAccessibility") private void initBottomView() {
        mBottomRecyclerView = findViewById(R.id.business_mould_template_one_bottom_view_rv);
        mBottomRecyclerView.setLayoutManager(new GridLayoutManager(this.context, 2));
        mBottomRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, UiUtils.dpToPx(this.context, 4), false));
        mBottomRecyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        return true;
                }
                return false;
            }
        });
        templateOneBottomItemAdapter =
                new TemplateOneBottomItemAdapter(this.context, R.layout.welink_uibundle_business_mould_template_one_bottom_item_layout);
        mBottomRecyclerView.setAdapter(templateOneBottomItemAdapter);
    }

    /**
     * 更新数据.
     */
    public void updateData() {
        updateDataOfTopView();
        updateDataOfBottomView();
    }

    /**
     * 更新顶部视图.
     */
    private void updateDataOfTopView() {
        List<TemplateOneTopItemBean> templateOneTopItemBeanList = new ArrayList<>();
        templateOneTopItemBeanList.add(new TemplateOneTopItemBean());
        templateOneTopItemBeanList.add(new TemplateOneTopItemBean());
        templateOneTopItemAdapter.setDataList(templateOneTopItemBeanList);
        templateOneTopItemAdapter.notifyDataSetChanged();
    }

    /**
     * 更新底部视图.
     */
    private void updateDataOfBottomView() {
        List<TemplateOneBottomItemBean> templateOneBottomItemBeanList = new ArrayList<>();
        templateOneBottomItemBeanList.add(new TemplateOneBottomItemBean());
        templateOneBottomItemBeanList.add(new TemplateOneBottomItemBean());
        templateOneBottomItemBeanList.add(new TemplateOneBottomItemBean());
        templateOneBottomItemBeanList.add(new TemplateOneBottomItemBean());
        templateOneBottomItemAdapter.setDataList(templateOneBottomItemBeanList);
        templateOneBottomItemAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化链接视图
     */
    private void initLinksView() {
        business_mould_template_link_container_ll = findViewById(R.id.business_mould_template_link_container_ll);
        business_mould_template_link_one_tv = findViewById(R.id.business_mould_template_link_one_tv);
        business_mould_template_link_two_ll = findViewById(R.id.business_mould_template_link_two_ll);
        business_mould_template_link_two_tv = findViewById(R.id.business_mould_template_link_two_tv);
        business_mould_template_link_three_ll = findViewById(R.id.business_mould_template_link_three_ll);
        business_mould_template_link_three_tv = findViewById(R.id.business_mould_template_link_three_tv);
        business_mould_template_link_four_ll = findViewById(R.id.business_mould_template_link_four_ll);
        business_mould_template_link_four_tv = findViewById(R.id.business_mould_template_link_four_tv);


    }


}
