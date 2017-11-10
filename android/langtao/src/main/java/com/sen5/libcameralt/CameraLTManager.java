package com.sen5.libcameralt;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import glnk.client.GlnkClient;
import glnk.io.OnDeviceStatusChangedListener;
import glnk.media.AView;
import glnk.rt.MyRuntime;
import glnk.utils.DateUtil;

/**
 * Created by zhurongkun on 2017/8/29.
 */

public class CameraLTManager {
    public static final String TAG = "LTCAMERA";

    /**
     * 1.初始化库
     *
     * @param application
     * @return
     */
    public static void init(Application application) {
        boolean supported = GlnkClient.supported();
        if (!supported) {
            Log.e("CameraLtManager err", "!GlnkClient.supported()");
            return;
        }
        GlnkClient client = GlnkClient.getInstance();
        String clientName = "sen5";
        String startTime = DateUtil.getDateOfString();
        String clientUUID = "b30770968c5f";
        client.init(application, clientName, startTime, clientUUID, 1, 1);
        client.setStatusAutoUpdate(true);
        client.setAppKey(LTKey.CAMERA_KEY);
//        client.setOnDeviceStatusChangedListener(new OnDeviceStatusChangedListener() {
//            @Override
//            public void onChanged(String s, int i) {
//                Log.i(TAG, "onChanged: " + s + " " + i);
//            }
//
//            @Override
//            public void onPushSvrInfo(String s, String s1, int i) {
//                Log.i(TAG, "onPushSvrInfo: " + s + " " + s1 + " " + i);
//            }
//
//            @Override
//            public void onDevFunInfo(String s, String s1) {
//                Log.i(TAG, "onDevFunInfo: " + s + " " + s1);
//            }
//
//            @Override
//            public void onStAuthResult(String s) {
//                Log.i(TAG, "onStAuthResult: " + s);
//            }
//
//            @Override
//            public void onStDevList(String s, String s1) {
//                Log.i(TAG, "onStDevList: " + s + " " + s1);
//            }
//        });
        client.start();
    }

    /**
     * 注销库
     */
    public static void release() {
        GlnkClient.getInstance().release();
    }

    /**
     * 预加载摄像头数组
     *
     * @param gids
     * @return
     */
    public static void preloadCamera(List<String> gids) {
        if (gids == null || gids.isEmpty())
            return;
        for (String gid : gids) {
            GlnkClient.getInstance().addGID(gid);
        }
        return;
    }

    /**
     * 预加载单个摄像头
     *
     * @param gid
     * @return
     */
    public static void preloadCamera(String gid) {
        if (!TextUtils.isEmpty(gid))
            GlnkClient.getInstance().addGID(gid);
        return;
    }


    public static CameraLTPlayer newCameraPlayer(AView view, String did) {
        return CameraLTPlayer.newPlayer(view, did);
    }

}
