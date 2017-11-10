package com.sen5.lib.api.event.device;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class EventDeviceAllStatus extends EventDevice{
    private List<EventDeviceStatus> status_list;

    public List<EventDeviceStatus> getStatus_list() {
        return status_list;
    }

    public void setStatus_list(List<EventDeviceStatus> status_list) {
        this.status_list = status_list;
    }

    @Override
    public String toString() {
        return "EventDeviceAllStatus{" +
                "status_list=" + status_list +
                '}';
    }
}
