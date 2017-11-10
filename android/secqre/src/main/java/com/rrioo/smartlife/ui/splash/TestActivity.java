package com.rrioo.smartlife.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.R;
import com.sen5.libcameralt.CameraLTManager;
import com.sen5.libcameralt.CameraLTPlayer;
import com.sen5.libcameralt.SimpleGlnkDataSourceListener;

import glnk.media.AView;

public class TestActivity extends AppCompatActivity {

    private CameraLTPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        FrameLayout view = (FrameLayout) findViewById(R.id.id_splash_fl);
        view.removeAllViews();
        final AView aView = new AView(this);
        view.addView(aView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        String did = "by0177e9c1";
//        String did = "by0178cde4";
//        String did = "by0178be7e";
//        String did = "by0178b78b";
        String did = "by0178b013";

        CameraLTManager.preloadCamera(did);
        player = CameraLTManager.newCameraPlayer(aView, did);
        player.getDataSource()
                .setGlnkDataSourceListener(new SimpleGlnkDataSourceListener() {
                    @Override
                    public void onConnecting() {
                        MyLog.i("onConnecting");
                    }

                    @Override
                    public void onConnected(int i, String s, int i1) {
                        MyLog.i("onConnected " + i + " " + s + " " + i1);
                    }

                    @Override
                    public void onDisconnected(int i) {
                        MyLog.i("onDisconnected " + i);
                    }

                    @Override
                    public void onAuthorized(int i) {
                        super.onAuthorized(i);
                        MyLog.i("onAuthorized " + i);
                    }

                    @Override
                    public void onIOCtrl(int i, byte[] bytes) {
                        super.onIOCtrl(i, bytes);
                        MyLog.i("onIOCtrl " + i);
                    }

                    @Override
                    public void onOpenVideoProcess(int i) {
                        super.onOpenVideoProcess(i);
                        MyLog.i("onOpenVideoProcess " + i);
                    }

                    @Override
                    public void onPermision(int i) {
                        super.onPermision(i);
                        MyLog.i("onPermision " + i);
                    }

                });
        player.start();

    }

    public void onClick(View v) {
        if (v.getId() == R.id.id_splash_btn1) {
            player.start();
        } else if (v.getId() == R.id.id_splash_btn2) {
            player.stop();
        }
    }

}
