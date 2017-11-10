package com.sen5.lib.api.event.device;

/**
 * Created by zhurongkun on 2017/8/24.
 * 删除设备回调
 */

public class EventDeviceDelete extends EventDevice{
    private int dev_id;

    public int getDev_id() {
        return dev_id;
    }

    public void setDev_id(int dev_id) {
        this.dev_id = dev_id;
    }

    @Override
    public String toString() {
        return "EventDeviceDelete{" +
                "dev_id=" + dev_id +
                '}';
    }
}
