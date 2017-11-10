package com.rrioo.smartlife.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.rrioo.smartlife.util.ClickHelper;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/9 10:05 1.0
 * @time 2017/10/9 10:05
 * @project secQreNew3.0 com.rrioo.smartlife.widget
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/9 10:05
 */

public class BaseImageView extends AppCompatImageView{
    public BaseImageView(Context context) {
        super(context);
    }

    public BaseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        ClickHelper.performClick(this);
        return super.performClick();
    }
}
