package com.cornucopia.welinktemplateoftab.widget.tab;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cornucopia.welinktemplateoftab.R;
import com.cornucopia.welinktemplateoftab.utils.UiUtils;
import com.cornucopia.welinktemplateoftab.widget.tab.adapter.CacheListAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签容器布局，支持动态添加Tab，指示器定制，滑动模式和平分模式定制，选中颜色和默认颜色的定制，Tab之间的间隔等功能.
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/8
 */
public class TabContainerLayout extends HorizontalScrollView {

  private static final String TAG = "TabContainerLayout";
  //指示器高度
  private int mIndicatorHeight;
  //是否有指示器
  private boolean mHasIndicator;
  //Tab文字大小
  private int mTabTextSize;
  //指示器是否在Tab顶部
  private boolean mIndicatorTop;
  //滑动模式
  public static final int MODE_SCROLLABLE = 0;
  //平分模式
  public static final int MODE_FIXED = 1;
  //Tab的显示模式
  private int mTabMode;
  //Scroll下Tab之间的间隔
  private int mTabSpaceOfScrollMode;
  //Tab未选中颜色
  private int mDefaultNormalColor;
  //Tab选中颜色
  private int mDefaultSelectedColor;
  private static final int NO_POSITION = -1;
  //当前选中的Tab'index
  private int mCurrentSelectedIndex = NO_POSITION;
  //即将选中的Tab'index
  private int mPendingSelectedIndex = NO_POSITION;
  //指示器宽度是否跟随内容宽度
  private boolean mIsIndicatorWidthFollowContent;
  //指示器左内边距(指示器宽度默认为文字宽度，左内边距指相对于文字宽度左延伸的距离)
  private int mIndicatorPaddingLeft;
  //指示器左内边距(指示器宽度默认为文字宽度，左内边距指相对于文字宽度右延伸的距离)
  private int mIndicatorPaddingRight;
  //切换选中Tab的动画
  private Animator mSelectAnimator;
  //记录Viewpager的滑动状态
  private int mViewPagerScrollState = ViewPager.SCROLL_STATE_IDLE;
  //指示器的矩形区域
  private Rect mIndicatorRect = null;
  //指示器的样式
  private Drawable mIndicatorDrawable;
  //布局容器
  private Container mContentLayout;
  //指示器画笔
  private Paint mIndicatorPaint = null;
  //Tab选中状态监听器集合
  private final ArrayList<OnTabSelectedListener> mSelectedListeners = new ArrayList<>();

  private ViewPager mViewPager;
  private PagerAdapter mPagerAdapter;
  private DataSetObserver mPagerAdapterObserver;
  private ViewPager.OnPageChangeListener mOnPageChangeListener;
  private OnTabSelectedListener mViewPagerSelectedListener;
  private AdapterChangeListener mAdapterChangeListener;
  private boolean mIsInSelectTab = false;
  private OnTabClickListener mOnTabClickListener;

  public TabContainerLayout(Context context) {
    this(context, null);
  }

  public TabContainerLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TabContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    //取消横向滚动条
    setHorizontalScrollBarEnabled(false);
    //取消对子View的裁剪
    setClipToPadding(false);
    //初始化xml配置，并初始化视图
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    TypedArray array =
        context.obtainStyledAttributes(attrs, R.styleable.WeLinkUibundleTabContainerLayout,
            defStyleAttr, 0);
    mDefaultNormalColor = array.getColor(
        R.styleable.WeLinkUibundleTabContainerLayout_welink_uibundle_tcl_tab_default_color,
        ContextCompat.getColor(context, R.color.welink_uibundle_tab_container_layout_normal_color));
    mDefaultSelectedColor = array.getColor(
        R.styleable.WeLinkUibundleTabContainerLayout_welink_uibundle_tcl_tab_selected_color,
        ContextCompat.getColor(context,
            R.color.welink_uibundle_tab_container_layout_selected_color));
    mIndicatorHeight = array.getDimensionPixelSize(
        R.styleable.WeLinkUibundleTabContainerLayout_welink_uibundle_tcl_tab_indicator_height,
        getResources().getDimensionPixelSize(
            R.dimen.welink_uibundle_tab_container_layout_indicator_height));
    mHasIndicator = array.getBoolean(
        R.styleable.WeLinkUibundleTabContainerLayout_welink_uibundle_tcl_tab_has_indicator, true);
    mTabTextSize = array.getDimensionPixelSize(
        R.styleable.WeLinkUibundleTabContainerLayout_welink_uibundle_tcl_tab_text_size,
        getResources().getDimensionPixelSize(
            R.dimen.welink_uibundle_tab_container_layout_text_size));
    mIndicatorTop = array.getBoolean(
        R.styleable.WeLinkUibundleTabContainerLayout_welink_uibundle_tcl_tab_indicator_top, false);
    mIndicatorPaddingLeft = array.getDimensionPixelSize(
        R.styleable.WeLinkUibundleTabContainerLayout_welink_uibundle_tcl_tab_padding_left,
        getResources().getDimensionPixelSize(
            R.dimen.welink_uibundle_tab_container_layout_indicator_padding_left));
    mIndicatorPaddingRight = array.getDimensionPixelSize(
        R.styleable.WeLinkUibundleTabContainerLayout_welink_uibundle_tcl_tab_padding_right,
        getResources().getDimensionPixelSize(
            R.dimen.welink_uibundle_tab_container_layout_indicator_padding_right));
    mTabMode =
        array.getInt(R.styleable.WeLinkUibundleTabContainerLayout_welink_uibundle_tcl_tab_mode,
            MODE_FIXED);
    mTabSpaceOfScrollMode = array.getDimensionPixelSize(
        R.styleable.WeLinkUibundleTabContainerLayout_welink_uibundle_tcl_tab_space,
        getResources().getDimensionPixelSize(
            R.dimen.welink_uibundle_tab_container_item_space_of_scrollable));
    array.recycle();
    mContentLayout = new Container(getContext());
    addView(mContentLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.MATCH_PARENT));
  }

  public static class Tab {
    public static final int DEFAULT_VALUE = Integer.MIN_VALUE;
    private int textSize = DEFAULT_VALUE;
    private int normalColor = DEFAULT_VALUE;
    private int selectedColor = DEFAULT_VALUE;
    private int contentWidth = 0;
    private int contentLeft = 0;
    private int gravity = Gravity.CENTER;
    private CharSequence text;
    private List<View> mCustomViews;
    private float rightSpaceWeight = 0f;
    private float leftSpaceWeight = 0f;
    private int leftAddonMargin = 0;
    private int rightAddonMargin = 0;

    public Tab(CharSequence text) {
      this.text = text;
    }

    public int getTextSize() {
      return textSize;
    }

    public void setTextSize(int textSize) {
      this.textSize = textSize;
    }

    public int getNormalColor() {
      return normalColor;
    }

    public void setNormalColor(int normalColor) {
      this.normalColor = normalColor;
    }

    public int getSelectedColor() {
      return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
      this.selectedColor = selectedColor;
    }

    public int getContentWidth() {
      return contentWidth;
    }

    public void setContentWidth(int contentWidth) {
      this.contentWidth = contentWidth;
    }

    public int getContentLeft() {
      return contentLeft;
    }

    public void setContentLeft(int contentLeft) {
      this.contentLeft = contentLeft;
    }

    public int getGravity() {
      return gravity;
    }

    public void setGravity(int gravity) {
      this.gravity = gravity;
    }

    public CharSequence getText() {
      return text;
    }

    public void setText(CharSequence text) {
      this.text = text;
    }

    public List<View> getmCustomViews() {
      return mCustomViews;
    }

    public void setmCustomViews(List<View> mCustomViews) {
      this.mCustomViews = mCustomViews;
    }

    public float getRightSpaceWeight() {
      return rightSpaceWeight;
    }

    public void setRightSpaceWeight(float rightSpaceWeight) {
      this.rightSpaceWeight = rightSpaceWeight;
    }

    public float getLeftSpaceWeight() {
      return leftSpaceWeight;
    }

    public void setLeftSpaceWeight(float leftSpaceWeight) {
      this.leftSpaceWeight = leftSpaceWeight;
    }

    public int getLeftAddonMargin() {
      return leftAddonMargin;
    }

    public void setLeftAddonMargin(int leftAddonMargin) {
      this.leftAddonMargin = leftAddonMargin;
    }

    public int getRightAddoMargin() {
      return rightAddonMargin;
    }

    public void setRightAddoMargin(int rightAddoMargin) {
      this.rightAddonMargin = rightAddoMargin;
    }

    public void addCustomView(View view) {
      if (mCustomViews == null) {
        mCustomViews = new ArrayList<>();
      }
      if (view != null && view.getLayoutParams() == null) {
        view.setLayoutParams(getDefaultCustomLayoutParam());
      }
      mCustomViews.add(view);
    }

    private ViewGroup.LayoutParams getDefaultCustomLayoutParam() {
      return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.WRAP_CONTENT);
    }
  }

  public class TabItemView extends RelativeLayout {

    private final AppCompatTextView mTextView;

    public TabItemView(Context context) {
      super(context);
      mTextView = new AppCompatTextView(getContext());
      mTextView.setSingleLine(true);
      mTextView.setGravity(Gravity.CENTER);
      mTextView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
      mTextView.setId(R.id.welink_uibundle_tcl_tab_item_id);
      RelativeLayout.LayoutParams tvLp =
          new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
              ViewGroup.LayoutParams.WRAP_CONTENT);
      tvLp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
      addView(mTextView, tvLp);
    }

    public TextView getTextView() {
      return mTextView;
    }

    public void setColorInTransition(Tab tab, int color) {
      mTextView.setTextColor(color);
    }

    public void updateDecoration(Tab tab, boolean isSelected) {
      int color = isSelected ? getTabSelectedColor(tab) : getTabNormalColor(tab);
      mTextView.setTextColor(color);
    }
  }

  private int getTabSelectedColor(Tab item) {
    int color = item.getSelectedColor();
    if (color == Tab.DEFAULT_VALUE) {
      color = mDefaultSelectedColor;
    }
    return color;
  }

  private int getTabNormalColor(Tab item) {
    int color = item.getNormalColor();
    if (color == Tab.DEFAULT_VALUE) {
      color = mDefaultNormalColor;
    }
    return color;
  }

  public class TabAdapter extends CacheListAdapter<Tab, TabItemView> {

    public TabAdapter(ViewGroup parentView) {
      super(parentView);
    }

    @Override
    protected TabItemView createView(ViewGroup parentView) {
      return new TabItemView(getContext());
    }

    @Override
    protected void bind(Tab item, TabItemView view, int position) {
      TextView tv = view.getTextView();
      List<View> mCustomViews = item.getmCustomViews();
      if (mCustomViews != null && mCustomViews.size() > 0) {
        view.setTag(R.id.welink_uibundle_tcl_can_not_cache_tag, true);
        for (View v : mCustomViews) {
          if (v.getParent() == null) {
            view.addView(v);
          }
        }
      }

      if (mTabMode == MODE_FIXED) {
        int gravity = item.getGravity();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
            (gravity & Gravity.LEFT) == Gravity.LEFT ? RelativeLayout.TRUE : 0);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL,
            (gravity & Gravity.CENTER) == Gravity.CENTER ? RelativeLayout.TRUE : 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
            (gravity & Gravity.RIGHT) == Gravity.RIGHT ? RelativeLayout.TRUE : 0);
        tv.setLayoutParams(lp);
      }

      tv.setText(item.getText());
      tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTabTextSize(item));
      view.updateDecoration(item, mCurrentSelectedIndex == position);
      view.setTag(position);
      view.setOnClickListener(mTabOnClickListener);
    }
  }

  /**
   * 获取tab中文字的大小
   */
  private int getTabTextSize(Tab item) {
    int textSize = item.getTextSize();
    if (textSize == Tab.DEFAULT_VALUE) {
      textSize = mTabTextSize;
    }
    return textSize;
  }

  /**
   * 设置tab中文字大小
   */
  public void setTabTextSize(int tabTextSize) {
    mTabTextSize = tabTextSize;
  }

  /**
   * 设置指示器高度
   *
   * @param height 指示器高度
   */
  public void setIndicatorHeight(int height) {
    this.mIndicatorHeight = height;
  }

  /**
   * 设置 indicator 为自定义的 Drawable(默认跟随 Tab 的 selectedColor)
   *
   * @param indicatorDrawable 自定义drawable
   */
  public void setIndicatorDrawable(Drawable indicatorDrawable) {
    mIndicatorDrawable = indicatorDrawable;
    if (indicatorDrawable != null) {
      mIndicatorHeight = indicatorDrawable.getIntrinsicHeight();
    }
    mContentLayout.invalidate();
  }

  /**
   * 设置indicator的宽度是否随内容宽度变化
   */
  public void setIndicatorWidthAdjustContent(boolean indicatorWidthFollowContent) {
    if (mIsIndicatorWidthFollowContent != indicatorWidthFollowContent) {
      mIsIndicatorWidthFollowContent = indicatorWidthFollowContent;
      mContentLayout.requestLayout();
    }
  }

  /**
   * 设置是否需要显示 indicator
   *
   * @param hasIndicator 是否需要显示 indicator
   */
  public void setHasIndicator(boolean hasIndicator) {
    if (mHasIndicator != hasIndicator) {
      mHasIndicator = hasIndicator;
      invalidate();
    }
  }

  /**
   * 设置指示器的左右内边距
   *
   * @param mIndicatorPaddingLeft 指示器左边距
   * @param mIndicatorPaddingRight 指示器右边距
   */
  public void setmIndicatorPaddingLeftAndRight(int mIndicatorPaddingLeft,
      int mIndicatorPaddingRight) {
    this.mIndicatorPaddingLeft = mIndicatorPaddingLeft;
    this.mIndicatorPaddingRight = mIndicatorPaddingRight;
  }

  /**
   * 设置默认颜色
   */
  public void setmDefaultNormalColor(int mDefaultNormalColor) {
    this.mDefaultNormalColor = mDefaultNormalColor;
  }

  /**
   * 设置选中颜色
   */
  public void setmDefaultSelectedColor(int mDefaultSelectedColor) {
    this.mDefaultSelectedColor = mDefaultSelectedColor;
  }

  /**
   * 设置项和项之间的间隔
   *
   * @param itemSpaceInScrollMode Tab之间的间隔
   */
  public void setItemSpaceInScrollMode(int itemSpaceInScrollMode) {
    mTabSpaceOfScrollMode = itemSpaceInScrollMode;
  }

  /**
   * 设置指示器是否在顶部
   *
   * @param isTop true在顶部;否则，在底部。
   */
  public void setIndicatorTop(boolean isTop) {
    this.mIndicatorTop = isTop;
  }

  @IntDef(value = { MODE_SCROLLABLE, MODE_FIXED })
  @Retention(RetentionPolicy.SOURCE)
  public @interface Mode {
  }

  /**
   * 设置显示模式
   */
  public void setMode(@Mode int mode) {
    if (mTabMode != mode) {
      mTabMode = mode;
      mContentLayout.invalidate();
    }
  }

  /**
   * 自定义tab点击监听
   */
  public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
    mOnTabClickListener = onTabClickListener;
  }

  //创建HorizontalScrollView唯一的直接子ViewGroup,用作容器
  private final class Container extends ViewGroup {
    private TabAdapter mTabAdapter;

    public Container(Context context) {
      super(context);
      mTabAdapter = new TabAdapter(this);
    }

    public TabAdapter getTabAdapter() {
      return mTabAdapter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

      int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
      int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
      List<TabItemView> childViews = mTabAdapter.getViews();
      int size = childViews.size();
      int i;

      int visibleChild = 0;
      for (i = 0; i < size; i++) {
        View child = childViews.get(i);
        if (child.getVisibility() == VISIBLE) {
          visibleChild++;
        }
      }
      if (size == 0 || visibleChild == 0) {
        setMeasuredDimension(widthSpecSize, heightSpecSize);
        return;
      }

      int childHeight = heightSpecSize - getPaddingTop() - getPaddingBottom();
      int childWidthMeasureSpec, childHeightMeasureSpec, resultWidthSize = 0;
      if (mTabMode == MODE_FIXED) {
        resultWidthSize = widthSpecSize;
        int modeFixItemWidth = widthSpecSize / visibleChild;
        for (i = 0; i < size; i++) {
          final View child = childViews.get(i);
          if (child.getVisibility() != VISIBLE) {
            continue;
          }
          childWidthMeasureSpec =
              MeasureSpec.makeMeasureSpec(modeFixItemWidth, MeasureSpec.EXACTLY);
          childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
          child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

          // reset
          Tab tab = mTabAdapter.getItem(i);
          tab.leftAddonMargin = 0;
          tab.rightAddonMargin = 0;
        }
      } else {
        float totalWeight = 0;
        for (i = 0; i < size; i++) {
          final View child = childViews.get(i);
          if (child.getVisibility() != VISIBLE) {
            continue;
          }
          childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSpecSize, MeasureSpec.AT_MOST);
          childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
          child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
          resultWidthSize += child.getMeasuredWidth() + mTabSpaceOfScrollMode;
          Tab tab = mTabAdapter.getItem(i);
          totalWeight += tab.leftSpaceWeight + tab.rightSpaceWeight;

          // reset first
          tab.leftAddonMargin = 0;
          tab.rightAddonMargin = 0;
        }
        resultWidthSize -= mTabSpaceOfScrollMode;
        if (totalWeight > 0 && resultWidthSize < widthSpecSize) {
          int remain = widthSpecSize - resultWidthSize;
          resultWidthSize = widthSpecSize;
          for (i = 0; i < size; i++) {
            final View child = childViews.get(i);
            if (child.getVisibility() != VISIBLE) {
              continue;
            }
            Tab tab = mTabAdapter.getItem(i);
            tab.leftAddonMargin = (int) (remain * tab.leftSpaceWeight / totalWeight);
            tab.rightAddonMargin = (int) (remain * tab.rightSpaceWeight / totalWeight);
          }
        }
      }

      setMeasuredDimension(resultWidthSize, heightSpecSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
      List<TabItemView> childViews = mTabAdapter.getViews();
      int size = childViews.size();
      int i;
      int visibleChild = 0;
      for (i = 0; i < size; i++) {
        View child = childViews.get(i);
        if (child.getVisibility() == VISIBLE) {
          visibleChild++;
        }
      }

      if (size == 0 || visibleChild == 0) {
        return;
      }

      int usedLeft = getPaddingLeft();
      for (i = 0; i < size; i++) {
        TabItemView childView = childViews.get(i);
        if (childView.getVisibility() != VISIBLE) {
          continue;
        }

        Tab model = mTabAdapter.getItem(i);
        final int childMeasureWidth = childView.getMeasuredWidth();
        childView.layout(
            usedLeft + model.leftAddonMargin,
            getPaddingTop(),
            usedLeft + model.leftAddonMargin + childMeasureWidth + model.rightAddonMargin,
            b - t - getPaddingBottom());

        int contentWidth = childView.getTextView().getWidth();
        Log.e(TAG, childView.getTextView().getWidth() + " " + childMeasureWidth);
        if (mTabMode == MODE_FIXED) {
          if (childMeasureWidth < contentWidth + mIndicatorPaddingLeft * 2) {
            RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) childView.getTextView().getLayoutParams();
            layoutParams.width = childMeasureWidth - mIndicatorPaddingLeft * 2;
            childView.getTextView().setLayoutParams(layoutParams);
          }
        } else if (mTabMode == MODE_SCROLLABLE) {
          childView.getTextView().setPadding(mIndicatorPaddingLeft,0,mIndicatorPaddingRight,0);
        }

        int oldLeft, oldWidth, newLeft, newWidth;
        oldLeft = model.getContentLeft();
        oldWidth = model.getContentWidth();
        if (mTabMode == MODE_FIXED && mIsIndicatorWidthFollowContent) {
          TextView contentView = childView.getTextView();
          Log.e(TAG, contentView.getWidth() + "");
          newLeft = usedLeft + contentView.getLeft();
          newWidth = contentView.getWidth();
        } else {
          newLeft = usedLeft + model.leftAddonMargin;
          newWidth = childMeasureWidth;
        }
        if (oldLeft != newLeft || oldWidth != newWidth) {
          model.setContentLeft(newLeft);
          model.setContentWidth(newWidth);
        }
        usedLeft = usedLeft + childMeasureWidth
            + model.leftAddonMargin + model.rightAddonMargin
            + (mTabMode == MODE_SCROLLABLE ? mTabSpaceOfScrollMode : 0);
      }

      if (mCurrentSelectedIndex != NO_POSITION && mSelectAnimator == null
          && mViewPagerScrollState == ViewPager.SCROLL_STATE_IDLE) {
        layoutIndicator(mTabAdapter.getItem(mCurrentSelectedIndex), false);
      }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
      super.dispatchDraw(canvas);
      if (mHasIndicator && mIndicatorRect != null) {
        if (mIndicatorTop) {
          mIndicatorRect.top = getPaddingTop();
          mIndicatorRect.bottom = mIndicatorRect.top + mIndicatorHeight;
        } else {
          mIndicatorRect.bottom = getHeight() - getPaddingBottom();
          mIndicatorRect.top = mIndicatorRect.bottom - mIndicatorHeight;
        }
        if (mIndicatorDrawable != null) {
          mIndicatorDrawable.setBounds(mIndicatorRect);
          mIndicatorDrawable.draw(canvas);
        } else {
          canvas.drawRect(mIndicatorRect, mIndicatorPaint);
        }
      }
    }
  }

  private void layoutIndicator(Tab item, boolean invalidate) {
    if (item == null) {
      return;
    }
    if (mIndicatorRect == null) {
      mIndicatorRect = new Rect(item.contentLeft, 0, item.contentLeft + item.contentWidth, 0);
    } else {
      if(mTabMode == MODE_FIXED) {
        mIndicatorRect.left = item.contentLeft - mIndicatorPaddingLeft;
        mIndicatorRect.right = item.contentLeft + item.contentWidth + mIndicatorPaddingRight;
      }else{
        mIndicatorRect.left = item.contentLeft;
        mIndicatorRect.right = item.contentLeft + item.contentWidth;
      }
    }
    if (mIndicatorPaint == null) {
      mIndicatorPaint = new Paint();
      mIndicatorPaint.setStyle(Paint.Style.FILL);
    }
    mIndicatorPaint.setColor(getTabSelectedColor(item));
    if (invalidate) {
      mContentLayout.invalidate();
    }
  }

  /**
   * 根据ViewPager装载Tab
   *
   * @param viewPager 关联ViewPager
   */
  public void setupWithViewPager(@Nullable ViewPager viewPager) {
    setupWithViewPager(viewPager, true);
  }

  /**
   * 根据ViewPager装载Tab
   *
   * @param viewPager 关联ViewPager
   * @param useAdapterTitle PagerAdapter.getTitle取值
   */
  public void setupWithViewPager(ViewPager viewPager, boolean useAdapterTitle) {
    setupWithViewPager(viewPager, useAdapterTitle, true);
  }

  /**
   * 根据ViewPager装载Tab
   *
   * @param viewPager 关联ViewPager
   * @param useAdapterTitle PagerAdapter.getTitle取值
   * @param autoRefresh 是否自动刷新
   */
  public void setupWithViewPager(ViewPager viewPager, boolean useAdapterTitle,
      boolean autoRefresh) {
    //解除和ViewPager相关的的监听器
    if (mViewPager != null) {
      if (mOnPageChangeListener != null) {
        mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
      }
      if (mAdapterChangeListener != null) {
        mViewPager.removeOnAdapterChangeListener(mAdapterChangeListener);
      }
    }
    //解除Tab选中状态监听器
    if (mViewPagerSelectedListener != null) {
      removeOnTabSelectedListener(mViewPagerSelectedListener);
      mViewPagerSelectedListener = null;
    }

    if (viewPager != null) {
      mViewPager = viewPager;
      //设置ViewPager页面监听器
      if (mOnPageChangeListener == null) {
        mOnPageChangeListener = new TabLayoutOnPageChangeListener(this);
      }
      viewPager.addOnPageChangeListener(mOnPageChangeListener);
      //设置Tab选中状态变化监听器
      mViewPagerSelectedListener = new ViewPagerOnTabSelectedListener(viewPager);
      addOnTabSelectedListener(mViewPagerSelectedListener);

      final PagerAdapter adapter = viewPager.getAdapter();
      if (adapter != null) {
        setPagerAdapter(adapter, useAdapterTitle, autoRefresh);
      }

      //设置ViewPager适配器变化的监听器
      if (mAdapterChangeListener == null) {
        mAdapterChangeListener = new AdapterChangeListener(useAdapterTitle);
      }
      mAdapterChangeListener.setAutoRefresh(autoRefresh);
      viewPager.addOnAdapterChangeListener(mAdapterChangeListener);
    } else {
      mViewPager = null;
      setPagerAdapter(null, false, false);
    }
  }

  /**
   * 注册Tab的点击事件
   */
  protected OnClickListener mTabOnClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      if (mSelectAnimator != null || mViewPagerScrollState != ViewPager.SCROLL_STATE_IDLE) {
        return;
      }
      int index = (int) v.getTag();
      Tab model = getAdapter().getItem(index);
      if (model != null) {
        selectTab(index, false, true);
      }
      if (mOnTabClickListener != null) {
        mOnTabClickListener.onTabClick(index);
      }
    }
  };

  public interface OnTabClickListener {
    /**
     * 当某个 Tab 被点击时会触发
     *
     * @param index 被点击的 Tab 下标
     */
    void onTabClick(int index);
  }

  private class AdapterChangeListener implements ViewPager.OnAdapterChangeListener {
    private boolean mAutoRefresh;
    private final boolean mUseAdapterTitle;

    AdapterChangeListener(boolean useAdapterTitle) {
      mUseAdapterTitle = useAdapterTitle;
    }

    @Override
    public void onAdapterChanged(@NonNull ViewPager viewPager,
        @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
      if (mViewPager == viewPager) {
        setPagerAdapter(newAdapter, mUseAdapterTitle, mAutoRefresh);
      }
    }

    void setAutoRefresh(boolean autoRefresh) {
      mAutoRefresh = autoRefresh;
    }
  }

  /**
   * 给Adapter注册观察者
   *
   * @param adapter 适配器
   * @param useAdapterTitle PageAdapter中的title
   * @param addObserver 是否注册观察者
   */
  private void setPagerAdapter(PagerAdapter adapter, boolean useAdapterTitle, boolean addObserver) {
    if (mPagerAdapter != null && mPagerAdapterObserver != null) {
      //如果已经存在页面适配器变化的观察者，则先反注册它
      mPagerAdapter.unregisterDataSetObserver(mPagerAdapterObserver);
    }
    mPagerAdapter = adapter;
    //注册新的页面适配器变化的观察者
    if (addObserver && adapter != null) {
      if (mPagerAdapterObserver == null) {
        mPagerAdapterObserver = new PagerAdapterObserver(useAdapterTitle);
      }
      adapter.registerDataSetObserver(mPagerAdapterObserver);
    }
    populateFromPagerAdapter(useAdapterTitle);
  }

  /**
   * Tab选中状态监听器
   */
  public interface OnTabSelectedListener {
    //tab被选中
    void onTabSelected(int index);

    //tab被取消选中
    void onTabUnselected(int index);

    //tab被再次选中
    void onTabReselected(int index);
  }

  /**
   * 解除`Tab`选中状态监听
   */
  private void removeOnTabSelectedListener(OnTabSelectedListener listener) {
    mSelectedListeners.remove(listener);
  }

  /**
   * 添加Tab选中状态监听器
   */
  public void addOnTabSelectedListener(@NonNull OnTabSelectedListener listener) {
    if (!mSelectedListeners.contains(listener)) {
      mSelectedListeners.add(listener);
    }
  }

  /**
   * 设置ViewPager页面变化监听器
   */
  public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
    private final WeakReference<TabContainerLayout> mTabSegmentRef;

    public TabLayoutOnPageChangeListener(TabContainerLayout tabSegment) {
      mTabSegmentRef = new WeakReference<>(tabSegment);
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
      final TabContainerLayout tabContainerLayout = mTabSegmentRef.get();
      if (tabContainerLayout != null) {
        tabContainerLayout.setViewPagerScrollState(state);
      }
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset,
        final int positionOffsetPixels) {
      final TabContainerLayout tabContainerLayout = mTabSegmentRef.get();
      if (tabContainerLayout != null) {
        tabContainerLayout.updateIndicatorPosition(position, positionOffset);
      }
    }

    @Override
    public void onPageSelected(final int position) {
      final TabContainerLayout tabContainerLayout = mTabSegmentRef.get();
      if (tabContainerLayout != null && tabContainerLayout.mPendingSelectedIndex != NO_POSITION) {
        tabContainerLayout.mPendingSelectedIndex = position;
        return;
      }
      if (tabContainerLayout != null && tabContainerLayout.getSelectedIndex() != position
          && position < tabContainerLayout.getTabCount()) {
        tabContainerLayout.selectTab(position, true, false);
      }
    }
  }

  /**
   * 更新指示器位置
   */
  private void updateIndicatorPosition(final int index, float offsetPercent) {
    if (mSelectAnimator != null || mIsInSelectTab || offsetPercent == 0) {
      return;
    }

    int targetIndex;
    if (offsetPercent < 0) {
      targetIndex = index - 1;
      offsetPercent = -offsetPercent;
    } else {
      targetIndex = index + 1;
    }

    TabAdapter tabAdapter = getAdapter();
    final List<TabItemView> listViews = tabAdapter.getViews();
    if (listViews.size() <= index || listViews.size() <= targetIndex) {
      return;
    }
    Tab preModel = tabAdapter.getItem(index);
    Tab targetModel = tabAdapter.getItem(targetIndex);
    TabItemView preView = listViews.get(index);
    TabItemView targetView = listViews.get(targetIndex);
    int preColor = UiUtils.computeColor(getTabSelectedColor(preModel), getTabNormalColor(preModel),
        offsetPercent);
    int targetColor =
        UiUtils.computeColor(getTabNormalColor(targetModel), getTabSelectedColor(targetModel),
            offsetPercent);
    preView.setColorInTransition(preModel, preColor);
    targetView.setColorInTransition(targetModel, targetColor);
    layoutIndicatorInTransition(preModel, targetModel, offsetPercent);
  }

  /**
   * 设置ViewPager滚动状态
   */
  private void setViewPagerScrollState(int state) {
    mViewPagerScrollState = state;
    if (mViewPagerScrollState == ViewPager.SCROLL_STATE_IDLE) {
      if (mPendingSelectedIndex != NO_POSITION && mSelectAnimator == null) {
        selectTab(mPendingSelectedIndex, true, false);
        mPendingSelectedIndex = NO_POSITION;
      }
    }
  }

  /**
   * 获取选中index
   */
  private int getSelectedIndex() {
    return mCurrentSelectedIndex;
  }

  /**
   * 获取tab总数量
   */
  private int getTabCount() {
    return getAdapter().getSize();
  }

  /**
   * 根据Tab选中变化，改变ViewPager页面。
   */
  private static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
    private final ViewPager mViewPager;

    public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
      mViewPager = viewPager;
    }

    @Override
    public void onTabSelected(int index) {
      mViewPager.setCurrentItem(index, false);
    }

    @Override
    public void onTabUnselected(int index) {
    }

    @Override
    public void onTabReselected(int index) {
    }
  }

  /**
   * ViewPager适配器变化的观察者
   */
  private class PagerAdapterObserver extends DataSetObserver {
    private final boolean mUseAdapterTitle;

    PagerAdapterObserver(boolean useAdapterTitle) {
      mUseAdapterTitle = useAdapterTitle;
    }

    @Override
    public void onChanged() {
      populateFromPagerAdapter(mUseAdapterTitle);
    }

    @Override
    public void onInvalidated() {
      populateFromPagerAdapter(mUseAdapterTitle);
    }
  }

  /**
   * 从ViewPager的适配器中取得数据并生成Tab
   *
   * @param useAdapterTitle 是否使用PageAdapter中的getTitle()
   */
  void populateFromPagerAdapter(boolean useAdapterTitle) {
    if (mPagerAdapter == null) {
      if (useAdapterTitle) {
        reset();
      }
      return;
    }
    final int adapterCount = mPagerAdapter.getCount();
    if (useAdapterTitle) {
      reset();
      for (int i = 0; i < adapterCount; i++) {
        addTab(new Tab(mPagerAdapter.getPageTitle(i)));
      }
      notifyDataChanged();
    }

    if (mViewPager != null && adapterCount > 0) {
      final int curItem = mViewPager.getCurrentItem();
      selectTab(curItem, true, false);
    }
  }

  /**
   * 重置存在的Tab
   */
  private void reset() {
    mContentLayout.getTabAdapter().clear();
    mCurrentSelectedIndex = NO_POSITION;
    if (mSelectAnimator != null) {
      mSelectAnimator.cancel();
      mSelectAnimator = null;
    }
  }

  /**
   * 添加Tab.
   *
   * @param tab 新的tab
   */
  public TabContainerLayout addTab(Tab tab) {
    mContentLayout.getTabAdapter().addItem(tab);
    return this;
  }

  /**
   * 通知数据变化
   */
  private void notifyDataChanged() {
    getAdapter().setup();
    populateFromPagerAdapter(false);
  }

  private TabAdapter getAdapter() {
    return mContentLayout.getTabAdapter();
  }

  /**
   * 选中tab
   */
  private void selectTab(final int index, boolean noAnimation, boolean fromTabClick) {
    if (mIsInSelectTab) {
      return;
    }
    mIsInSelectTab = true;
    TabAdapter tabAdapter = getAdapter();
    List<TabItemView> listViews = tabAdapter.getViews();

    if (listViews.size() != tabAdapter.getSize()) {
      tabAdapter.setup();
      listViews = tabAdapter.getViews();
    }

    if (listViews.size() == 0 || listViews.size() <= index) {
      mIsInSelectTab = false;
      return;
    }

    if (mSelectAnimator != null || mViewPagerScrollState != ViewPager.SCROLL_STATE_IDLE) {
      mPendingSelectedIndex = index;
      mIsInSelectTab = false;
      return;
    }

    if (mCurrentSelectedIndex == index) {
      if (fromTabClick) {
        // dispatch re select only when click tab
        dispatchTabReselected(index);
      }
      mIsInSelectTab = false;
      //刷新页面
      mContentLayout.invalidate();
      return;
    }

    if (mCurrentSelectedIndex > listViews.size()) {
      Log.i(TAG, "selectTab: current selected index is bigger than views size.");
      mCurrentSelectedIndex = NO_POSITION;
    }

    if (mCurrentSelectedIndex == NO_POSITION) {
      Tab model = tabAdapter.getItem(index);
      layoutIndicator(model, true);
      listViews.get(index).updateDecoration(model, true);
      dispatchTabSelected(index);
      mCurrentSelectedIndex = index;
      mIsInSelectTab = false;
      return;
    }

    final int prev = mCurrentSelectedIndex;
    final Tab prevModel = tabAdapter.getItem(prev);
    final TabItemView prevView = listViews.get(prev);
    final Tab nowModel = tabAdapter.getItem(index);
    final TabItemView nowView = listViews.get(index);

    if (noAnimation) {
      dispatchTabUnselected(prev);
      dispatchTabSelected(index);
      prevView.updateDecoration(prevModel, false);
      nowView.updateDecoration(nowModel, true);
      if (getScrollX() > nowView.getLeft()) {
        smoothScrollTo(nowView.getLeft(), 0);
      } else {
        int realWidth = getWidth() - getPaddingRight() - getPaddingLeft();
        if (getScrollX() + realWidth < nowView.getRight()) {
          smoothScrollBy(nowView.getRight() - realWidth - getScrollX(), 0);
        }
      }
      mCurrentSelectedIndex = index;
      mIsInSelectTab = false;
      layoutIndicator(nowModel, true);
      return;
    }

    final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
    animator.setInterpolator(new LinearInterpolator());
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float animValue = (float) animation.getAnimatedValue();
        int preColor =
            UiUtils.computeColor(getTabSelectedColor(prevModel), getTabNormalColor(prevModel),
                animValue);
        int nowColor =
            UiUtils.computeColor(getTabNormalColor(nowModel), getTabSelectedColor(nowModel),
                animValue);
        prevView.setColorInTransition(prevModel, preColor);
        nowView.setColorInTransition(nowModel, nowColor);
        layoutIndicatorInTransition(prevModel, nowModel, animValue);
      }
    });
    animator.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {
        mSelectAnimator = animation;
      }

      @Override
      public void onAnimationEnd(Animator animation) {
        mSelectAnimator = null;
        prevView.updateDecoration(prevModel, false);
        nowView.updateDecoration(nowModel, true);
        dispatchTabSelected(index);
        dispatchTabUnselected(prev);
        mCurrentSelectedIndex = index;
        mIsInSelectTab = false;
        if (mPendingSelectedIndex != NO_POSITION
            && mViewPagerScrollState == ViewPager.SCROLL_STATE_IDLE) {
          selectTab(mPendingSelectedIndex, true, false);
          mPendingSelectedIndex = NO_POSITION;
        }
        requestLayout();
      }

      @Override
      public void onAnimationCancel(Animator animation) {
        mSelectAnimator = null;
        prevView.updateDecoration(prevModel, true);
        nowView.updateDecoration(nowModel, false);
        layoutIndicator(prevModel, true);
        mIsInSelectTab = false;
      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });
    animator.setDuration(200);
    animator.start();
  }

  private void dispatchTabUnselected(int index) {
    for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
      mSelectedListeners.get(i).onTabUnselected(index);
    }
  }

  private void dispatchTabReselected(int index) {
    for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
      mSelectedListeners.get(i).onTabReselected(index);
    }
  }

  private void dispatchTabSelected(int index) {
    for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
      mSelectedListeners.get(i).onTabSelected(index);
    }
  }

  /**
   * 使指示器过渡
   */
  private void layoutIndicatorInTransition(Tab preModel, Tab targetModel, float offsetPercent) {
    final int leftDistance = targetModel.getContentLeft() - preModel.getContentLeft();
    final int widthDistance = targetModel.getContentWidth() - preModel.getContentWidth();
    final int targetLeft = (int) (preModel.getContentLeft() + leftDistance * offsetPercent);
    final int targetWidth = (int) (preModel.getContentWidth() + widthDistance * offsetPercent);
    if (mIndicatorRect == null) {
      mIndicatorRect = new Rect(targetLeft, 0, targetLeft + targetWidth, 0);
    } else {
      if(mTabMode==MODE_FIXED) {
        mIndicatorRect.left = targetLeft - mIndicatorPaddingLeft;
        mIndicatorRect.right = targetLeft + targetWidth + mIndicatorPaddingRight;
      }else{
        mIndicatorRect.left = targetLeft;
        mIndicatorRect.right = targetLeft + targetWidth;
      }
    }

    if (mIndicatorPaint == null) {
      mIndicatorPaint = new Paint();
      mIndicatorPaint.setStyle(Paint.Style.FILL);
    }
    int indicatorColor = UiUtils.computeColor(
        getTabSelectedColor(preModel), getTabSelectedColor(targetModel), offsetPercent);
    mIndicatorPaint.setColor(indicatorColor);
    mContentLayout.invalidate();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    final int widthMode = MeasureSpec.getMode(widthMeasureSpec);

    if (getChildCount() > 0) {
      final View child = getChildAt(0);
      int paddingHor = getPaddingLeft() + getPaddingRight();
      child.measure(MeasureSpec.makeMeasureSpec(widthSize - paddingHor, MeasureSpec.EXACTLY),
          heightMeasureSpec);
      if (widthMode == MeasureSpec.AT_MOST) {
        setMeasuredDimension(Math.min(widthSize, child.getMeasuredWidth() + paddingHor),
            heightMeasureSpec);
        return;
      }
    }
    setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    if (mCurrentSelectedIndex != NO_POSITION && mTabMode == MODE_SCROLLABLE) {
      TabAdapter tabAdapter = getAdapter();
      final TabItemView view = tabAdapter.getViews().get(mCurrentSelectedIndex);
      if (getScrollX() > view.getLeft()) {
        scrollTo(view.getLeft(), 0);
      } else {
        int realWidth = getWidth() - getPaddingRight() - getPaddingLeft();
        if (getScrollX() + realWidth < view.getRight()) {
          scrollBy(view.getRight() - realWidth - getScrollX(), 0);
        }
      }
    }
  }
}
