package com.chillingvan.canvasgl.texture;

import android.content.Context;
import android.util.AttributeSet;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glview.texture.BaseGLTextureView;
import com.chillingvan.canvasgl.glview.texture.GLViewRenderer;
import com.chillingvan.canvasgl.glview.texture.gles.GLThread;

/**
 * Created by zhurongkun on 2017/9/1.
 */

public class MyGLTextureView extends BaseGLTextureView {
    private GLViewRenderer renderer;

    public MyGLTextureView(Context context) {
        super(context);
    }

    public MyGLTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGLTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onGLDraw(ICanvasGL canvas) {
        if (this.renderer != null) {
            this.renderer.onDrawFrame();
        }
    }

    @Override
    protected int getRenderMode() {
        return GLThread.RENDERMODE_CONTINUOUSLY;
    }


    @Override
    public void setRenderer(GLViewRenderer renderer) {
        super.setRenderer(renderer);
        this.renderer = renderer;
    }

    public GLViewRenderer getRenderer() {
        return renderer;
    }

}
