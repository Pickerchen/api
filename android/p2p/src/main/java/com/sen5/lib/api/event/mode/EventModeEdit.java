package com.sen5.lib.api.event.mode;

import com.sen5.lib.api.event.BaseEvent;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class EventModeEdit extends EventMode {
    private int sec_mode;
    private List<Integer> dev_list;

    public int getSec_mode() {
        return sec_mode;
    }

    public void setSec_mode(int sec_mode) {
        this.sec_mode = sec_mode;
    }

    public List<Integer> getDev_list() {
        return dev_list;
    }

    public void setDev_list(List<Integer> dev_list) {
        this.dev_list = dev_list;
    }
}
