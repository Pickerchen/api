package com.sen5.lib.core;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.sen5.lib.util.LogUtil;


/**
 * Created by zhurongkun on 2017/8/22.
 * api核心服务，根据传递过来的bundle发送消息给服务端，并读取服务端的数据返回给上层业务
 *
 *
 *      接收的bundle结构为：
 *      msg_type: int
 *      msg_body: String
 *
 *      msg_body在connect命令下为盒子DID，
 *      其他情况下，均为对应的请求json字符串
 */

public class CoreService extends Service {
    public static final String SERVICE_NAME = "com.sen5.lib.core.CoreService";

    private CoreWorker mWorker;


    @Override
    public void onCreate() {
        super.onCreate();
        mWorker = new CoreWorker(this);
        mWorker.start();
    }

    @Override
    public void onDestroy() {
        mWorker.stop();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        process(intent, flags, startId);
        return Service.START_STICKY;
    }

    public void sendToLocal(Bundle args){
        Intent intent = getBroadcastIntent();
        intent.putExtras(args);
        sendBroadcast(intent);
    }

    private Intent getBroadcastIntent() {
        Intent intent = new Intent(CoreReceiver.ACTION);
        return intent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void process(Intent intent, int flags, int startId) {
        if (mWorker != null) {
            mWorker.process(intent, flags, startId);
        }
    }

}
