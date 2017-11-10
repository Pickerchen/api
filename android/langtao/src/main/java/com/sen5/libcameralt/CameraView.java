package com.sen5.libcameralt;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import glnk.media.AViewRenderer;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/10 18:15 1.0
 * @time 2017/10/10 18:15
 * @project secQreNew3.0 com.sen5.libcameralt
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/10 18:15
 */

public class CameraView extends View {
    private AViewRenderer renderer = null;
    private Method onRenderMethod;

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AViewRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(AViewRenderer renderer) {
        this.renderer = renderer;
        try {
            hack(renderer);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void hack(AViewRenderer renderer) throws NoSuchMethodException {
        onRenderMethod = renderer.getClass().getMethod("onRender", Canvas.class);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.renderer != null) {
            try {
                callRenderMethod(renderer, onRenderMethod, canvas);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void callRenderMethod(AViewRenderer renderer, Method onRenderMethod, Canvas canvas) throws InvocationTargetException, IllegalAccessException {
        if (renderer != null && onRenderMethod != null
                && canvas != null) {
            onRenderMethod.invoke(renderer,canvas);
        }
    }
}
