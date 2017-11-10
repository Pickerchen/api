package com.rrioo.smartlife.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;

import com.rrioo.smartlife.SecApp;
import com.rrioo.smartlife.base.PermissionResult;

import java.util.Map;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/29 18:28 1.0
 * @time 2017/9/29 18:28
 * @project secQreNew3.0 com.rrioo.smartlife.util
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/29 18:28
 */

public class PermissionUtil {
    private static Map<Integer, PermissionResult> permissionResults = new ArrayMap<>(10);

    public static Map<Integer, PermissionResult> getPermissionResults() {
        return permissionResults;
    }

    public static boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(SecApp.get(), permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

}
