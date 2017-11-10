package com.rrioo.smartlife.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * TextView的基类
 */
public class BaseTextView extends AppCompatTextView {

    protected Context mContext = null;

    public BaseTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        mContext = context;
        init();
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        init();
    }

    public BaseTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
        init();
    }

    private void init() {

//		// 字体
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        setTypeface(typeface);
    }
}
