package com.rrioo.smartlife.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.rrioo.smartlife.util.ClickHelper;

/**
 * Button的基类
 */
public class BaseButton extends AppCompatButton {
	
	protected Context mContext = null;
	
	public BaseButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
		init();
	}

	public BaseButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		init();
	}

	public BaseButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		init();
	}

	@Override
	public boolean performClick() {
		ClickHelper.performClick(this);
		return super.performClick();
	}

	private void init() {
		
//		// 字体
		Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
		setTypeface(typeface);
	}
}
