package com.sen5.libcameralt;

import android.graphics.Matrix;
import android.view.ViewTreeObserver;

import glnk.client.GlnkClient;
import glnk.media.AView;
import glnk.media.AViewRenderer;
import glnk.media.GlnkDataSource;
import glnk.media.GlnkDataSourceListener;
import glnk.media.GlnkPlayer;
import glnk.media.VideoRenderer;

/**
 * Created by zhurongkun on 2017/8/29.
 * <p>
 * 使用方法：
 * 1.CameraLTManager.newCameraPlayer（）方法创建player
 * 2.start()
 * 3.stop（）
 */

public class CameraLTPlayer {
    //摄像头还未初始化
    public static final int UNINITICAL = 0;
    //正在加载
    public static final int LOADING = 2;
    //正在播放
    public static final int PLAYING = 3;
    //暂停了
    public static final int PAUSED = 4;

    private int playStatus = UNINITICAL;
    private AViewRenderer renderer;
    private GlnkDataSource dataSource;
    private GlnkPlayer player;

    private AView view;
    private String did;
    private boolean isInited;
    public long snapshotTime;
    private GlnkDataSourceListener dataSourceListener;


    public int getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(int playStatus) {
        this.playStatus = playStatus;
    }


    public GlnkPlayer getPlayer() {
        return player;
    }

    public void setPlayer(GlnkPlayer player) {
        this.player = player;
    }

    public CameraLTPlayer start() {
        if (player == null || !isInited) {
            init();
        }
        player.start();
        renderer.start();
        return this;
    }


    public CameraLTPlayer stop() {
        if (player != null) {
            player.stop();
            player = null;
            renderer = null;
            dataSource = null;
            isInited = false;
        }
        return this;
    }


    public CameraLTPlayer reSize(final int newWidth, final int newHeight) {
        if (renderer == null)
            return this;
        renderer.setOnVideoSizeChangedListener(new VideoRenderer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(VideoRenderer videoRenderer, int w, int h) {
                int videoWidth = w;
                int videoHeight = h;
                float scaleX = (float) newWidth / (float) videoWidth;
                float scaleY = (float) newHeight / (float) videoHeight;
                Matrix matrix = renderer.getMatrix();
                matrix.reset();
                matrix.setScale(scaleX, scaleY);
                renderer.setMatrix(matrix);
            }
        });
        return this;
    }

    static CameraLTPlayer newPlayer(AView view, String did) {
        CameraLTPlayer holder = new CameraLTPlayer();
        holder.setView(view);
        holder.setDid(did);
        holder.init();
        return holder;
    }

    private void init() {
        CameraLTManager.preloadCamera(did);

        this.renderer = new AViewRenderer(view.getContext(), view);
        this.dataSource = new GlnkDataSource(GlnkClient.getInstance());
        this.dataSource.setTalkVolue(1);
        this.dataSource.setMetaData(did,
                "admin",
                "admin",
                0,
                1,
                2
        );

        this.player = new GlnkPlayer();
        this.player.prepare();
        this.player.setDisplay(renderer);
        this.player.setDataSource(dataSource);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                int w = view.getWidth();
                int h = view.getHeight();
                reSize(w, h);
                return true;
            }
        });

        if (this.dataSourceListener != null) {
            this.dataSource.setGlnkDataSourceListener(this.dataSourceListener);
        }

        isInited = true;
    }


    public boolean isInited() {
        return isInited;
    }

    public void setInited(boolean inited) {
        isInited = inited;
    }

    public AViewRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(AViewRenderer renderer) {
        this.renderer = renderer;
    }

    public GlnkDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(GlnkDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public AView getView() {
        return view;
    }

    public void setView(AView view) {
        this.view = view;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    private CameraLTPlayer() {
    }

    public void setDataSourceListener(GlnkDataSourceListener dataSourceListener) {
        this.dataSourceListener = dataSourceListener;
        if (this.dataSource != null) {
            this.dataSource.setGlnkDataSourceListener(dataSourceListener);
        }
    }

    public GlnkDataSourceListener getDataSourceListener() {
        return dataSourceListener;
    }
}
