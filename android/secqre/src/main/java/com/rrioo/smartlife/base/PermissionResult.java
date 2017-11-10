package com.rrioo.smartlife.base;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/30 9:04 1.0
 * @time 2017/9/30 9:04
 * @project secQreNew3.0 com.rrioo.smartlife.base
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/30 9:04
 */

public abstract class PermissionResult {
    String permission;
    int requestCode;

    public final String getPermission() {
        return permission;
    }

    public final void setPermission(String permission) {
        this.permission = permission;
    }

    public final int getRequestCode() {
        return requestCode;
    }

    public final void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public abstract void onPermissionGranted(String permisssion, int requestCode);

    public abstract void onPermissionDenied(String permission, int requestCode);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionResult that = (PermissionResult) o;

        if (requestCode != that.requestCode) return false;
        return permission != null ? permission.equals(that.permission) : that.permission == null;

    }

    @Override
    public int hashCode() {
        int result = permission != null ? permission.hashCode() : 0;
        result = 31 * result + requestCode;
        return result;
    }
}
