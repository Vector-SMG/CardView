package com.cornucopia.welinktemplateoftab.widget.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cornucopia.welinktemplateoftab.utils.UiUtils;

/**
 * @author l84116371
 * @version 1.0
 * @since 2019/7/25
 */
public class BorderTextView extends TextView {

    private int stroke_width;

    public BorderTextView(Context context) {
        this(context,null);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        stroke_width= UiUtils.dpToPx(context,1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint _debugInfoPaint = new Paint(); //设置无锯齿 也可以使用setAntiAlias(true)
        _debugInfoPaint.setColor(Color.parseColor("#00C696"));//设置画笔颜色
        _debugInfoPaint.setStrokeWidth(stroke_width);//设置线宽
        _debugInfoPaint.setStyle(Paint.Style.STROKE);//设置样式：FILL表示颜色填充整个；STROKE表示空心
        canvas.drawRoundRect(new RectF(stroke_width/2, stroke_width/2, getWidth()-stroke_width/2, getHeight()-stroke_width/2), 10, 10, _debugInfoPaint);
    }
}
