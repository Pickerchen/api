package com.sen5.lib.api;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.sen5.lib.core.CoreReceiver;
import com.sen5.lib.core.CoreService;
import com.sen5.lib.util.AppUUID;

/**
 * Created by zhurongkun on 2017/8/22.
 */

public class Api {
    private Context mAppContext;
    private CoreReceiver mReceiver;
    private Sender mSender;

    public static Api get() {
        return _Api.INSTANCE;
    }

    public void init(Context context) {
        this.mAppContext = context.getApplicationContext();
        startCoreService();
        startCoreReciever();
    }


    public void unInit() {
        stopCoreService();
        stopReceiver();
        mSender = null;
    }

    public Sender getSender() {
        return mSender;
    }


    void sendMessageToRemote(Bundle args){
        Intent intent = getServiceIntent();
        intent.putExtras(args);
        mAppContext.startService(intent);
    }

    String getUserId() {
        return AppUUID.generate(mAppContext);
    }


    private Intent getServiceIntent() {
        Intent intent = new Intent();
        intent.setClassName(mAppContext,CoreService.SERVICE_NAME);
        return intent;
    }

    private void startCoreService() {
        Intent intent = getServiceIntent();
        mAppContext.startService(intent);
    }

    private void stopCoreService() {
        Intent intent = getServiceIntent();
        mAppContext.stopService(intent);
    }

    private void startCoreReciever() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CoreReceiver.ACTION);
        mReceiver = new CoreReceiver();
        mAppContext.registerReceiver(mReceiver, filter);
    }


    private void stopReceiver() {
        if (mReceiver != null) {
            mAppContext.unregisterReceiver(mReceiver);
        }
    }

    private Api() {
        mSender = new SenderImpl();
    }


    private static class _Api {
        static Api INSTANCE = new Api();
    }
}
