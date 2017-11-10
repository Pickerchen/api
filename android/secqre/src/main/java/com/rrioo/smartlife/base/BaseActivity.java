package com.rrioo.smartlife.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.view.View;

import com.rrioo.smartlife.SecApp;
import com.rrioo.smartlife.util.PermissionUtil;
import com.rrioo.smartlife.widget.LoadingDialog;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/25 10:08 1.0
 * @time 2017/9/25 10:08
 * @project secQreNew3.0 com.rrioo.smartlife
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/25 10:08
 */
public abstract class BaseActivity extends FragmentActivity {
    protected SecApp app = SecApp.get();
    private Map<Integer, PermissionResult> mPermissionResults = PermissionUtil.getPermissionResults();
    private LoadingDialog loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SecApp.get().enqueueActivity(this);
        setStatusBar();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        SecApp.get().enqueueActivity(this);
        setStatusBar();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            View decorView = getWindow().getDecorView();
            int options = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                options |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(options);

            getWindow().setStatusBarColor(Color.TRANSPARENT);

//            ActionBar actionBar = getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.hide();
//            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SecApp.get().dequeueActivity(this);
    }


    protected void requestPermission(String permission, int requestCode, PermissionResult handler) {
        if (PermissionUtil.isPermissionGranted(permission)) {
            handler.setPermission(permission);
            handler.setRequestCode(requestCode);
            handler.onPermissionGranted(permission, requestCode);
        } else {
            handler.setPermission(permission);
            handler.setRequestCode(requestCode);
            mPermissionResults.put(requestCode, handler);
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionResults.containsKey(requestCode)) {
            PermissionResult permissionResult = mPermissionResults.get(requestCode);
            String permission = permissions[0];
            int granted = grantResults[0];
            if (granted == PackageManager.PERMISSION_GRANTED) {
                permissionResult.onPermissionGranted(permission, requestCode);
            } else if (granted == PackageManager.PERMISSION_DENIED) {
                permissionResult.onPermissionDenied(permission, requestCode);
            }
            mPermissionResults.remove(permissionResult);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (!fragment.isDetached() && !fragment.isRemoving()) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    protected void showLoading() {
        if (loading == null)
            loading = new LoadingDialog(this);
        if (loading.isShowing())
            loading.dismiss();
        loading.show();
    }

    protected void cancelLoading() {
        if (loading != null) {
            loading.dismiss();
        }
    }
}
