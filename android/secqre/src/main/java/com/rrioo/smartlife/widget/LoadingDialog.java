package com.rrioo.smartlife.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.rrioo.smartlife.R;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/10 9:16 1.0
 * @time 2017/10/10 9:16
 * @project secQreNew3.0 com.rrioo.smartlife.widget
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/10 9:16
 */

public class LoadingDialog extends Dialog {
    private static final long DELAY_TIMEOUT = 10 * 1000L;
    private ImageView mIvLoading;
    private AnimationDrawable mAnimationDrawable;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.DialogBaseStyle);
        setContentView(R.layout.dialog_loading);
        this.mIvLoading = (ImageView) findViewById(R.id.id_dialog_loading_iv);
        Drawable drawable = mIvLoading.getDrawable();
        if (drawable != null && drawable instanceof AnimationDrawable) {
            this.mAnimationDrawable = (AnimationDrawable) drawable;
        }
    }

    @Override
    public void show() {
        super.show();
        start();

    }

    private void start() {
        startAnimation();
        handler.sendEmptyMessageDelayed(1, DELAY_TIMEOUT);
    }

    private void startAnimation() {
        if (mAnimationDrawable != null) mAnimationDrawable.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        stop();
    }

    private void stop() {
        stopAnimation();
        handler.removeMessages(1);
    }

    private void stopAnimation() {
        if (mAnimationDrawable != null) mAnimationDrawable.stop();
    }

    @Override
    public void cancel() {
        super.cancel();
        stop();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CustomToast.makeText(getContext(),R.string.connection_timeout).show();
            LoadingDialog.this.dismiss();
        }
    };
}
