package com.rrioo.smartlife.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.util.DisplayUtil;
import com.rrioo.smartlife.util.ReflectUtil;
import com.rrioo.smartlife.util.ScreenUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomToast extends Toast {

    private static final int TOAST = 0x00;
    private Context mContext = null;
    private String strToast = "";
    private TextView tvContent;

    private CustomToast(Context context, String strToast) {
        super(context);
        mContext = context;

        this.strToast = strToast;

        inflate();
    }

    private void inflate() {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.widget_custom_toast, null);
        tvContent = (TextView) layout.findViewById(R.id.tv_toast_content);
//			int nViewWidth = mContext.getResources().getDisplayMetrics().widthPixels / 4;
//			int nViewHeight = mContext.getResources().getDisplayMetrics().heightPixels;
//			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(nViewWidth, nViewHeight);
//			tvContent.setLayoutParams(params);
        tvContent.setText(strToast);

        Toast toast = this;
        int bottom = mContext.getResources().getDisplayMetrics().heightPixels / 8;
        toast.setGravity(Gravity.BOTTOM, 0, bottom);
        toast.setMargin(0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

//        updateToast(mContext, toast);
    }

    /**
     * 采用反射统一 Toast 动画和高宽
     * 使 Toast 动画不再因不同系统而产生差异
     */
    @SuppressLint("InlinedApi")
    protected static void updateToast(Context context, Toast toast) {
        if (toast != null) {
            // Toast 动画
            WindowManager.LayoutParams params = getParams(context, toast);
            if (params != null) {
                if (toast instanceof CustomToast) {
                    CustomToast customToast = (CustomToast) toast;
                    setParamsSize(params, customToast);
                } else {
                    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    params.height = (int) context.getResources().getDimension(DisplayUtil.dip2px(context, 50));
                }
                params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            }
        }
    }

    private static WindowManager.LayoutParams getParams(Context context, Toast toast) {
        try {
            Method windowParamsMethod = toast.getClass().getMethod("getWindowParams");
            if (windowParamsMethod != null) {
                Object inv = windowParamsMethod.invoke(toast);
                if (inv != null) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) inv;
                    return params;
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        Object mTN = null;
        try {
            mTN = ReflectUtil.getField(toast, "mTN");
            if (mTN != null) {
                Object mParams = ReflectUtil.getField(mTN, "mParams");
                if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    return params;
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void setParamsSize(WindowManager.LayoutParams params, CustomToast customToast) {
        TextView tvContent = customToast.tvContent;
        if (tvContent != null) {
            TextPaint paint = tvContent.getPaint();
            float measureTextWidth = paint.measureText(customToast.strToast.toString());
            float textMaxLineWidth = getTextMaxLineWidth(tvContent);
            params.width = (int) Math.min(measureTextWidth, textMaxLineWidth);

            int nLine = (int) (measureTextWidth / textMaxLineWidth + 1);
            float textLineHeight = getTextMaxHeight(tvContent, nLine);

            params.height = (int) textLineHeight;
        }
    }

    private static float getTextMaxHeight(TextView tvContent, int nLine) {
        Paint.FontMetrics fontMetrics = tvContent.getPaint().getFontMetrics();
        float lineHeight = fontMetrics.bottom - fontMetrics.top;
        lineHeight = lineHeight * nLine;

        ViewGroup.LayoutParams layoutParams = tvContent.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mp = (ViewGroup.MarginLayoutParams) layoutParams;
            lineHeight += mp.topMargin;
            lineHeight += mp.bottomMargin;
        }

        lineHeight += tvContent.getPaddingTop();
        lineHeight += tvContent.getPaddingBottom();
        return lineHeight;
    }

    private static float getTextMaxLineWidth(TextView tvContent) {
        ViewGroup.LayoutParams layoutParams = tvContent.getLayoutParams();
        float width = ScreenUtil.getScreenWidth(tvContent.getContext());
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mp = (ViewGroup.MarginLayoutParams) layoutParams;
            width -= (mp.leftMargin + mp.rightMargin);
        }
        width -= tvContent.getPaddingLeft();
        width -= tvContent.getPaddingRight();
        return width;
    }

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param context  The context to use.  Usually your {@link android.app.Application}
     *                 or {@link android.app.Activity} object.
     * @param text     The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     *                 {@link #LENGTH_LONG}
     */
    public static Toast makeText(Context context, CharSequence text, int duration) {
        return new CustomToast(context, text.toString());
    }

    public static Toast makeText(Context context, int resId, int duration)
            throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }

    public static Toast makeText(Context context, CharSequence text) {
        return makeText(context, text, Toast.LENGTH_SHORT);
    }

    public static Toast makeText(Context context, int resId) {
        return makeText(context, resId, Toast.LENGTH_SHORT);
    }
}