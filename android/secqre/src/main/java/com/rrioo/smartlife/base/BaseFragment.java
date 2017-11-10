package com.rrioo.smartlife.base;

import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;

import com.rrioo.smartlife.util.PermissionUtil;
import com.rrioo.smartlife.widget.LoadingDialog;

import java.util.Map;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/28 17:49 1.0
 * @time 2017/9/28 17:49
 * @project secQreNew3.0 com.rrioo.smartlife.base
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/28 17:49
 */

public abstract class BaseFragment extends Fragment {
    private LoadingDialog loading;
    private Map<Integer, PermissionResult> mPermissionResults = PermissionUtil.getPermissionResults();

    /**
     * 依附的activity必须是baseactivity，否则不会回调
     *
     * @param permission
     * @param requestCode
     * @param handler
     */
    protected void requestPermission(String permission, int requestCode, PermissionResult handler) {
        if (PermissionUtil.isPermissionGranted(permission)) {
            handler.setPermission(permission);
            handler.setRequestCode(requestCode);
            handler.onPermissionGranted(permission, requestCode);
        } else {
            handler.setPermission(permission);
            handler.setRequestCode(requestCode);
            mPermissionResults.put(requestCode, handler);
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        }
    }

    protected void showLoading() {
        if (loading == null)
            loading = new LoadingDialog(getContext());
        if (loading.isShowing())
            loading.dismiss();
        loading.show();
    }

    protected void cancelLoading() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    /**
     * 需要配合activity的onBackPressed使用
     */
    public boolean onBackPressed() {
        return false;
    }
}
