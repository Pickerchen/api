package com.sen5.lib.api.event.device;

import com.sen5.lib.entity.device.Device;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 * 获取设备列表回调
 */

public class EventDeviceList extends EventDevice{
    private List<Device> devices;

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "EventDeviceList{" +
                "msg_type=" + msg_type +
                ", devices=" + devices +
                '}';
    }
}
