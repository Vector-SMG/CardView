package com.cornucopia.welinktemplateoftab;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cornucopia.welinktemplateoftab.utils.UiUtils;

/**
 * @author l84116371
 * @version 1.0
 * @since 2019/7/24
 */
public class TextViewActivity extends AppCompatActivity {

    private static final String TAG = "TextViewActivity";
    private TextView tv1, tv2,tv3;
    private Rect rect = new Rect();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_layout);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

        Log.e(TAG,"2dp "+ UiUtils.dpToPx(this,2));

        tv3.postDelayed(new Runnable() {
            @Override
            public void run() {
                int tv1Height=tv1.getMeasuredHeight();
                int tv2Height=tv2.getMeasuredHeight();
                int tv3Height=tv3.getMeasuredHeight();

                Log.e(TAG,"tv1的高度:"+tv1Height);
//                Log.e(TAG,"tv2的高度:"+tv2Height);
//                Log.e(TAG,"tv3的高度:"+tv3Height);


//                int tv1TextHeight=getHeight(tv1);
//                int tv2TextHeight=getHeight(tv2);
//                int tv3TextHeight=getHeight(tv3);
//                Log.e(TAG,"tv1的文字高度:"+tv1TextHeight);
//                Log.e(TAG,"tv2的文字高度:"+tv2TextHeight);
//                Log.e(TAG,"tv3的文字高度:"+tv3TextHeight);
            }
        },2000);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private int getHeight(TextView tv) {
        String text = tv.getText().toString();
        tv.getPaint().getTextBounds(text, 0, text.length(), rect);
        return rect.bottom - rect.top;
    }
}
