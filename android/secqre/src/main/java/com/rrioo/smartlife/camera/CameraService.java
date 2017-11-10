package com.rrioo.smartlife.camera;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.rrioo.hsl.CameraCallback;
import com.rrioo.hsl.CameraHslManager;
import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.SecApp;
import com.rrioo.smartlife.camera.hsl.EventHsl;
import com.sen5.libcameralt.CameraLTManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/10 17:08 1.0
 * @time 2017/10/10 17:08
 * @project secQreNew3.0 com.rrioo.smartlife.camera.hsl
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/10 17:08
 */

public class CameraService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CameraHslManager.init(new Callback());
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CameraHslManager.release();
        CameraLTManager.release();
    }

    private class Callback extends CameraCallback {
        @Override
        public void CallBack_Event(long UserID, long nType) {
            super.CallBack_Event(UserID, nType);
            EventBus.getDefault().post(new EventHsl(UserID, nType));
        }
    }
}
