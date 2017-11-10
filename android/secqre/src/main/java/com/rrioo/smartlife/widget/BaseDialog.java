package com.rrioo.smartlife.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.util.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/12 17:47 1.0
 * @time 2017/10/12 17:47
 * @project secQreNew3.0 com.rrioo.smartlife.widget
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/12 17:47
 */

public class BaseDialog extends BaseFloatDialog {
    private int mAnimIn;
    private int mAnimOut;

    @BindView(R.id.id_common_dialog_top)
    FrameLayout mTop;

    @BindView(R.id.id_common_dialog_bottom)
    FrameLayout mBottom;

    public BaseDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_common);
        ButterKnife.bind(this);
    }

    public BaseDialog setTopLayout(@LayoutRes int layoutRes) {
        mTop.removeAllViews();
        mTop.addView(LayoutInflater.from(getContext()).inflate(layoutRes, null));
        return this;
    }

    public BaseDialog setBottomLayout(@LayoutRes int layoutRes) {
        mBottom.removeAllViews();
        mBottom.addView(LayoutInflater.from(getContext()).inflate(layoutRes, null));
        return this;
    }

    public BaseDialog setGravity(int gravity) {
        getWindow().setGravity(gravity);
        return this;
    }

    public BaseDialog setAnimationStyle(@StyleRes int animationStyle) {
        getWindow().setWindowAnimations(animationStyle);
        return this;
    }

    public BaseDialog setMatchWidth(int margin) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        attributes.width = screenWidth - margin * 2;
        getWindow().setAttributes(attributes);
        return this;
    }

    public BaseDialog setMatchHeight(int margin) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        int screenHeight = ScreenUtil.getScreenHeight(getContext());
        attributes.height = screenHeight - margin * 2;
        getWindow().setAttributes(attributes);
        return this;
    }
}
