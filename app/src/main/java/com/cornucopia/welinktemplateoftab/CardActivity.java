package com.cornucopia.welinktemplateoftab;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cornucopia.welinktemplateoftab.ui.CardView;

/**
 * 主入口类.
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/12
 */
public class CardActivity extends AppCompatActivity {

    FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welink_uibundle_business_mould_activity_card_layout);
        frameLayout = findViewById(R.id.business_mould_card_fl);
        frameLayout.addView(new CardView(this));
    }
}
