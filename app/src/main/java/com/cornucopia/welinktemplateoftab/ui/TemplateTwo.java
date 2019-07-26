package com.cornucopia.welinktemplateoftab.ui;

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
import com.cornucopia.welinktemplateoftab.adapter.TemplateTwoBottomItemAdapter;
import com.cornucopia.welinktemplateoftab.adapter.TemplateTwoTopItemAdapter;
import com.cornucopia.welinktemplateoftab.domain.TemplateOneBottomItemBean;
import com.cornucopia.welinktemplateoftab.domain.TemplateOneTopItemBean;
import com.cornucopia.welinktemplateoftab.domain.TemplateTwoTopItemBean;
import com.cornucopia.welinktemplateoftab.utils.UiUtils;
import com.cornucopia.welinktemplateoftab.widget.recyclerview.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板三-视图
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/10
 */
public class TemplateTwo extends FrameLayout {

    private Context context;
    private RecyclerView mTopRecyclerView;
    private TemplateTwoTopItemAdapter templateTwoTopItemAdapter;
    private RecyclerView mBottomRecyclerView;
    private TemplateTwoBottomItemAdapter templateTwoBottomItemAdapter;

    private LinearLayout business_mould_template_link_container_ll;
    private TextView business_mould_template_link_one_tv;
    private LinearLayout business_mould_template_link_two_ll;
    private TextView business_mould_template_link_two_tv;
    private LinearLayout business_mould_template_link_three_ll;
    private TextView business_mould_template_link_three_tv;
    private LinearLayout business_mould_template_link_four_ll;
    private TextView business_mould_template_link_four_tv;


    public TemplateTwo(Context context) {
        this(context, null);
    }

    public TemplateTwo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemplateTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        updateData();
    }

    private void init() {
        View.inflate(this.context, R.layout.welink_uibundle_business_mould_template_two_layout, this);
        initTopView();
        initBottomView();
    }

    /**
     * 初始化顶部视图.
     */
    private void initTopView() {
        mTopRecyclerView = findViewById(R.id.business_mould_template_two_top_view_rv);
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
        mTopRecyclerView.setLayoutManager(new GridLayoutManager(this.context, 3));
        templateTwoTopItemAdapter =
                new TemplateTwoTopItemAdapter(this.context, R.layout.welink_uibundle_business_mould_template_two_top_item_layout);
        mTopRecyclerView.setAdapter(templateTwoTopItemAdapter);
    }

    /**
     * 初始化底部视图.
     */
    private void initBottomView() {
        mBottomRecyclerView = findViewById(R.id.business_mould_template_two_bottom_view_rv);
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
        mBottomRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, UiUtils.dpToPx(this.context, 4), false));
        mBottomRecyclerView.setLayoutManager(new GridLayoutManager(this.context, 3));
        templateTwoBottomItemAdapter =
                new TemplateTwoBottomItemAdapter(this.context, R.layout.welink_uibundle_business_mould_template_two_bottom_item_layout);
        mBottomRecyclerView.setAdapter(templateTwoBottomItemAdapter);
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
        List<TemplateTwoTopItemBean> templateTwoTopItemBeanList = new ArrayList<>();
        templateTwoTopItemBeanList.add(new TemplateTwoTopItemBean());
        templateTwoTopItemBeanList.add(new TemplateTwoTopItemBean());
        templateTwoTopItemBeanList.add(new TemplateTwoTopItemBean());
        templateTwoTopItemAdapter.setDataList(templateTwoTopItemBeanList);
        templateTwoBottomItemAdapter.notifyDataSetChanged();
    }

    /**
     * 更新底部视图.
     */
    private void updateDataOfBottomView() {
        List<String> list = new ArrayList<>();
        list.add("文本信息");
        list.add("文本信息");
        list.add("文本信息");
        list.add("文本信息");
        list.add("文本信息");
        list.add("文本信息");
        list.add("文本信息");

        templateTwoBottomItemAdapter.setDataList(list);
        templateTwoBottomItemAdapter.notifyDataSetChanged();
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
