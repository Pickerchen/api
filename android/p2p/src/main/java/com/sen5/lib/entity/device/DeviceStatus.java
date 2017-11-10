package com.sen5.lib.entity.device;

import java.util.Arrays;

/**
 * Created by zhurongkun on 2017/8/21.
 */

public class DeviceStatus {
    //设备状态参数
    public static final int SENSOR_STATUS_NO_WORK = 0;
    public static final int SENSOR_STATUS_SAFETY = 0;
    public static final int SENSOR_STATUS_DANGER = 1;

    private long time;
    private int id;        //设备状态ID
    private byte[] params; //状态参数 base64

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getParams() {
        return params;
    }

    public void setParams(byte[] params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceStatus status = (DeviceStatus) o;

        return id == status.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "DeviceStatus{" +
                "time=" + time +
                ", id=" + id +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
