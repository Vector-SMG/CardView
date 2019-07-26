package com.cornucopia.welinktemplateoftab.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.cornucopia.welinktemplateoftab.R;
import com.cornucopia.welinktemplateoftab.adapter.NotifyRecyclerViewAdapter;
import com.cornucopia.welinktemplateoftab.adapter.TemplatePagerAdapter;
import com.cornucopia.welinktemplateoftab.domain.NotifyItemBean;
import com.cornucopia.welinktemplateoftab.utils.UiUtils;
import com.cornucopia.welinktemplateoftab.widget.looper.LooperLayoutManger;
import com.cornucopia.welinktemplateoftab.widget.tab.TabContainerLayout;
import com.cornucopia.welinktemplateoftab.widget.viewpager.SelfAdaptionHeightViewPager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 卡片视图-对应Welink需要的export的View.
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/12
 */
public class CardView extends FrameLayout {

    public static final String TAG = "CardView";
    private Context context;
    private RecyclerView rv;
    private ValueAnimator valueAnimator;
    private Handler businessMouldHandler;

    private TabContainerLayout mTabContainerLayout;
    private SelfAdaptionHeightViewPager mViewPager;

    public static final int SCROLL = 0x01;

    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 开始初始化View
     */
    private void initView() {
        View.inflate(context, R.layout.welink_uibundle_business_mould_card_layout, this);
        initNotifyView();
        initTemplatesView();
    }


    /**
     * 初始化通知功能.
     */
    private void initNotifyView() {
        businessMouldHandler = new BusinessMouldHandler(this);
        rv = findViewById(R.id.business_mould_template_notify_rv);
        //初始化无限循环的LayoutManager.
        LooperLayoutManger looper = new LooperLayoutManger();
        looper.setLooperEnable(true);
        looper.setAutoMeasureEnabled(true);
        rv.setLayoutManager(looper);
        NotifyRecyclerViewAdapter notifyRecyclerViewAdapter =
                new NotifyRecyclerViewAdapter(this.context, R.layout.welink_uibundle_business_mould_notify_item_layout);
        rv.setAdapter(notifyRecyclerViewAdapter);
        //阻止手动滚动通知列表
        rv.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        return true;
                }
                return false;
            }
        });
        //模拟通知数据
        List<NotifyItemBean> list = new ArrayList<>();

        NotifyItemBean notifyItemBean2 = new NotifyItemBean();
        notifyItemBean2.setTitle("订货");
        notifyItemBean2.setValue("西欧地区部 本月新增订货207.7M美元");
        list.add(notifyItemBean2);

        NotifyItemBean notifyItemBean = new NotifyItemBean();
        notifyItemBean.setTitle("提示");
        notifyItemBean.setValue("1-5号结账期间收入，回款展示预估数据(非财经定稿),上月核算数据将于5号24:00定稿发布;1-5号结账期间收入，回款展示预估数据(非财经定稿),上月核算数据将于5号24:00定稿发布");
        list.add(notifyItemBean);

        NotifyItemBean notifyItemBean1 = new NotifyItemBean();
        notifyItemBean1.setTitle("收入");
        notifyItemBean1.setValue("1-5号结账期间收入，回款展示预估数据(非财经定稿),上月核算数据将于5号24:00定稿发布;");
        list.add(notifyItemBean1);



        notifyRecyclerViewAdapter.setDataList(list);
        notifyRecyclerViewAdapter.notifyDataSetChanged();

        //延迟3s启动通知列表的滚动
        businessMouldHandler.sendEmptyMessageDelayed(SCROLL, 3000);
    }

    /**
     * 用于让通知延时并且隔一段时间滚动
     */
    public static class BusinessMouldHandler extends Handler {
        WeakReference<CardView> weakReference;

        public BusinessMouldHandler(CardView view) {
            this.weakReference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "执行时间:" + System.currentTimeMillis());
            CardView view = weakReference.get();
            if (view == null || view.getResources() == null) {
                return;
            }
            if (msg.what == SCROLL) {
                view.startScroll();
            }
        }
    }

    /**
     * 启动通知列表滚动
     */
    private void startScroll() {
        int startPoint = 0;
        int endPoint = 0;
        View view = rv.getChildAt(0);
        if (view == null) {
            return;
        }
        int height = view.getHeight();
        int itemHeight = UiUtils.dpToPx(this.context, 24 + 7);
        if (height > itemHeight) {
            endPoint = height / 2;
        } else {
            endPoint = height;
        }
        if (endPoint == 0) {
            return;
        }
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        valueAnimator = ValueAnimator.ofInt(startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public int offsetY = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                try {
                    int nowPoint = (int) valueAnimator.getAnimatedValue();
                    int dy = nowPoint - offsetY;
                    rv.scrollBy(0, dy);
                    offsetY = nowPoint;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();
        businessMouldHandler.sendEmptyMessageDelayed(SCROLL, 3000);
    }

    /**
     * 初始化模板的View.
     */
    private void initTemplatesView() {
        //初始化TabContainer
        mTabContainerLayout = findViewById(R.id.business_mould_template_tcl);
        mViewPager = findViewById(R.id.business_mould_template_shvp);

        List<View> viewList = new ArrayList<>();
        viewList.add(new TemplateOne(this.context));
        viewList.add(new TemplateTwo(this.context));
        viewList.add(new TemplateThree(this.context));

        List<String> titles = new ArrayList<>();
        titles.add("模板一");
        titles.add("模板二");
        titles.add("模板三");
        mViewPager.setAdapter(new TemplatePagerAdapter(viewList, titles));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, position + "");
                mViewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.resetHeight(0);
            }
        },200);

        mTabContainerLayout.setupWithViewPager(mViewPager);
        mTabContainerLayout.setIndicatorWidthAdjustContent(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "显示");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG, "隐藏");
        if (businessMouldHandler != null) {
            businessMouldHandler.removeMessages(SCROLL);
        }
    }
}
