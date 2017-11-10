package com.sen5.lib.api.event.mode;

import com.sen5.lib.api.event.BaseEvent;
import com.sen5.lib.entity.mode.SecurityMode;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class EventModeList extends EventMode {
    private int cur_sec_mode;
    private List<SecurityMode> modes;

    public int getCur_sec_mode() {
        return cur_sec_mode;
    }

    public void setCur_sec_mode(int cur_sec_mode) {
        this.cur_sec_mode = cur_sec_mode;
    }

    public List<SecurityMode> getModes() {
        return modes;
    }

    public void setModes(List<SecurityMode> modes) {
        this.modes = modes;
    }
}
