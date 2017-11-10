package com.sen5.lib.api.event.device;

import com.sen5.lib.entity.device.Device;

/**
 * Created by zhurongkun on 2017/8/24.
 * 编辑设备回调
 */

public class EventDeviceEdit extends EventDevice{
    private Device device;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
