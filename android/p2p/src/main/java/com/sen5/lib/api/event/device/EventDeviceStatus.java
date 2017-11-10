package com.sen5.lib.api.event.device;

import com.sen5.lib.entity.device.DeviceStatus;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class EventDeviceStatus extends EventDevice {
    private int dev_id;
    private List<DeviceStatus> status;

    public int getDev_id() {
        return dev_id;
    }

    public void setDev_id(int dev_id) {
        this.dev_id = dev_id;
    }

    public List<DeviceStatus> getStatus() {
        return status;
    }

    public void setStatus(List<DeviceStatus> status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EventDeviceStatus{" +
                "dev_id=" + dev_id +
                ", status=" + status +
                '}';
    }
}
