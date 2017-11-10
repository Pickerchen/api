package com.rrioo.smartlife.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.rrioo.smartlife.R;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/12 18:08 1.0
 * @time 2017/10/12 18:08
 * @project secQreNew3.0 com.rrioo.smartlife.widget
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/12 18:08
 */

public class SimpleDialog extends BaseDialog {

//    @BindView(R.id.id_dialog_simple_title_tv)
    TextView mTitleTv;

//    @BindView(R.id.id_dialog_simple_positive_tv)
    TextView mPositiveTv;

//    @BindView(R.id.id_dialog_simple_negative_tv)
    TextView mNegativeTv;

    private OnKeyListener onKeyListener;

    public OnKeyListener getOnKeyListener() {
        return onKeyListener;
    }

    @Override
    public void setOnKeyListener(OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
    }

    public SimpleDialog(@NonNull Context context) {
        super(context);
        setTopLayout(R.layout.dialog_simple_top);
        setBottomLayout(R.layout.dialog_simple_bottom);
        setGravity(Gravity.CENTER);
        findViews();
        mPositiveTv.setBackgroundResource(R.drawable.button_ripple);
        mNegativeTv.setBackgroundResource(R.drawable.button_ripple);
    }

    private void findViews() {
        mTitleTv = (TextView) findViewById(R.id.id_dialog_simple_title_tv);
        mPositiveTv = (TextView) findViewById(R.id.id_dialog_simple_positive_tv);
        mNegativeTv = (TextView) findViewById(R.id.id_dialog_simple_negative_tv);

        mPositiveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPositive();
            }
        });

        mNegativeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNegative();
            }
        });
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        mTitleTv.setText(title);
    }

    @Override
    public void setTitle(@StringRes int titleId) {
        setTitle(getContext().getString(titleId));
    }

    public void setPositiveText(CharSequence text){
        mPositiveTv.setText(text);
    }

    public void setPositiveText(@StringRes int textId){
        setPositiveText(getContext().getString(textId));
    }

//    @OnClick(R.id.id_dialog_simple_positive_tv)
    public void onClickPositive() {
        if (onKeyListener != null) {
            onKeyListener.onKey(this, Dialog.BUTTON_POSITIVE, null);
        }
    }

//    @OnClick(R.id.id_dialog_simple_negative_tv)
    public void onClickNegative() {
        if (onKeyListener != null) {
            onKeyListener.onKey(this, Dialog.BUTTON_NEGATIVE, null);
        }
    }




}
