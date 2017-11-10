package com.rrioo.smartlife.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.widget.BaseButton;

import java.lang.ref.WeakReference;
import java.util.Map;

import butterknife.OnClick;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/9 9:12 1.0
 * @time 2017/10/9 9:12
 * @project secQreNew3.0 com.rrioo.smartlife.util
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/9 9:12
 */

public class ClickHelper {
    private static Map<Integer, ValueAnimator> animatorCache = new ArrayMap<>();

    public static void performClick(View view, final Runnable endAction) {
        ValueAnimator animation = getAnimation(view, view.hashCode());
        if (animation.isRunning()) {
            return;
        }
        animation.removeAllListeners();
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (endAction != null) endAction.run();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animation.start();
    }

    private static ValueAnimator getAnimation(final View view, int hash) {
        if (animatorCache.containsKey(hash)) return animatorCache.get(hash);
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 1.1f, 1.0f)
                .setDuration(100);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setScaleX(value);
                view.setScaleY(value);
            }
        });
        animatorCache.put(hash, animator);
        return animator;
    }

    public static void performClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.scale_fvdevice_icon_set));
    }

    public static abstract class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {
            performClick(v, new Runnable() {
                @Override
                public void run() {
                    onClickView(v);
                }
            });
        }

        public abstract void onClickView(View v);
    }

    private ClickHelper(){}
}
