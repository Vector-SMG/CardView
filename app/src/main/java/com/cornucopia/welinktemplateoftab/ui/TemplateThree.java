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
import com.cornucopia.welinktemplateoftab.adapter.TemplateThreeItemAdapter;
import com.cornucopia.welinktemplateoftab.domain.TemplateThreeItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板二-视图
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/10
 */
public class TemplateThree extends FrameLayout {

    private Context context;
    private RecyclerView business_mould_template_three_rv;
    private TemplateThreeItemAdapter myRecyclerViewAdapter;

    private LinearLayout business_mould_template_link_container_ll;
    private TextView business_mould_template_link_one_tv;
    private LinearLayout business_mould_template_link_two_ll;
    private TextView business_mould_template_link_two_tv;
    private LinearLayout business_mould_template_link_three_ll;
    private TextView business_mould_template_link_three_tv;
    private LinearLayout business_mould_template_link_four_ll;
    private TextView business_mould_template_link_four_tv;


    public TemplateThree(Context context) {
        this(context, null);
    }

    public TemplateThree(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemplateThree(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View.inflate(this.context, R.layout.welink_uibundle_business_mould_template_three_layout, this);
        initTemplateThreeRv();
        updateDataOfTemplateThreeRv();

        initLinksView();
    }


    /**
     * 初始化模板三中的RecyclerView
     *
     * @return
     */
    private void initTemplateThreeRv() {
        business_mould_template_three_rv = findViewById(R.id.business_mould_template_three_rv);
        business_mould_template_three_rv.setLayoutManager(new GridLayoutManager(this.context, 4));
        business_mould_template_three_rv.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        return true;
                }
                return false;
            }
        });
        myRecyclerViewAdapter =
                new TemplateThreeItemAdapter(this.context, R.layout.welink_uibundle_business_mould_template_three_item_layout);
        business_mould_template_three_rv.setAdapter(myRecyclerViewAdapter);
    }

    /**
     * 更新模板三中的RecyclerView的数据
     */
    private void updateDataOfTemplateThreeRv() {
        List<TemplateThreeItemBean> list = new ArrayList<>();
        list.add(new TemplateThreeItemBean(R.drawable.ic_launcher_background, "文本信息"));
        list.add(new TemplateThreeItemBean(R.drawable.ic_launcher_background, "文本信息"));
        list.add(new TemplateThreeItemBean(R.drawable.ic_launcher_background, "文本信息"));
        list.add(new TemplateThreeItemBean(R.drawable.ic_launcher_background, "文本信息"));
        list.add(new TemplateThreeItemBean(R.drawable.ic_launcher_background, "文本信息"));
        list.add(new TemplateThreeItemBean(R.drawable.ic_launcher_background, "文本信息"));
        list.add(new TemplateThreeItemBean(R.drawable.ic_launcher_background, "文本信息"));
        list.add(new TemplateThreeItemBean(R.drawable.ic_launcher_background, "文本信息"));

        myRecyclerViewAdapter.setDataList(list);
        myRecyclerViewAdapter.notifyDataSetChanged();
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
