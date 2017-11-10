package com.rrioo.smartlife;

import android.app.Activity;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.camera.CameraService;
import com.rrioo.smartlife.data.ApiCache;
import com.rrioo.smartlife.util.SpUtil;
import com.sen5.lib.api.Api;
import com.sen5.libcameralt.CameraLTManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/25 10:21 1.0
 * @time 2017/9/25 10:21
 * @project secQreNew3.0 com.rrioo.smartlife
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/25 10:21
 */
public class SecApp extends MultiDexApplication {
    private static SecApp INSTANCE;
    private List<Activity> mActivitys = new CopyOnWriteArrayList<>();
    private String mBoxId;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        CameraLTManager.init(SecApp.get());
        /**
         * 获取缓存的盒子ID
         */
        mBoxId = SpUtil.getDefault().getBoxId();

        /**
         * log初始化
         */
        MyLog.init();

        /**
         * p2p sdk初始化
         */
        Api.get().init(this);

        /**
         * 注册摄像头服务
         */
        startService(new Intent(this, CameraService.class));

        ApiCache.get().onCreate();
    }

    public static SecApp get() {
        return INSTANCE;
    }

    public void enqueueActivity(Activity activity) {
        this.mActivitys.add(activity);
    }

    public void dequeueActivity(Activity activity) {
        this.mActivitys.remove(activity);
    }

    public void exit() {
        for (Activity activity : mActivitys) {
            activity.finish();
        }
        ApiCache.get().onDestroy();
    }

    public String getBoxId() {
        return mBoxId;
    }

    public void setBoxId(String mBoxId) {
        this.mBoxId = mBoxId;
    }
}
