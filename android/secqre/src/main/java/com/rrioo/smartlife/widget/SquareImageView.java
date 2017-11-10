package com.rrioo.smartlife.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/16 15:22 1.0
 * @time 2017/10/16 15:22
 * @project secQreNew3.0 com.rrioo.smartlife.widget
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/16 15:22
 */

public class SquareImageView extends BaseImageView{
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
