package com.rrioo.smartlife.util;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.view.KeyEvent;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.SecApp;
import com.rrioo.smartlife.widget.SimpleDialog;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/12 10:25 1.0
 * @time 2017/10/12 10:25
 * @project secQreNew3.0 com.rrioo.smartlife.util
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/12 10:25
 */

public class SystemUtil {
    public static boolean sPlayInMobileNet = false;

    public static void checkMobileNetThen(Context context,final Runnable todo) {
        if (!sPlayInMobileNet && NetUtils.isMobileConnected(context)) {
            SimpleDialog simpleDialog = new SimpleDialog(context);
            simpleDialog.setTitle(R.string.camera_dialog);
            simpleDialog.setPositiveText(R.string.play);
            simpleDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == DialogInterface.BUTTON_POSITIVE) {
                        sPlayInMobileNet = true;
                        todo.run();
                    }
                    dialog.dismiss();
                    return false;
                }
            });
            simpleDialog.show();
        } else {
            todo.run();
        }
    }


    public static void vibrate() {
        vibrate(20);
    }

    private static void vibrate(long time) {
        Vibrator vibrator = (Vibrator) SecApp.get().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }

    public static void vibrateLong() {
        vibrate(100);
    }

    private SystemUtil() {
    }

    public static boolean canPlayVideo() {
        return !NetUtils.isMobileConnected(SecApp.get())
                || sPlayInMobileNet;
    }
}
