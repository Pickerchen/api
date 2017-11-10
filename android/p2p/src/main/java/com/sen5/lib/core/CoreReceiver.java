package com.sen5.lib.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sen5.lib.util.LogUtil;

/**
 * Created by zhurongkun on 2017/8/23.
 * 负责接收 {@link CoreService}返回的数据，处理后分发给上层业务
 */

public class CoreReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.sen5.lib.CORE";
    private EventDispatcher mDispatcher;

    @Override
    public void onReceive(Context context, Intent intent) {
        mDispatcher = createDispatcher(context);
        Bundle extras = intent.getExtras();
        dispatchBundles(extras);

    }

    private EventDispatcher createDispatcher(Context context) {
        return mDispatcher == null ? new EventDispatcher(context) : mDispatcher;
    }

    private void dispatchBundles(Bundle extras) {
        if (mDispatcher == null) {
            throw new IllegalStateException(getClass().getName() + " dispatcher is null on method dispatchBundles");
        }
        mDispatcher.dispatch(extras);
    }


}
