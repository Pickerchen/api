package com.sen5.lib.api.event.mode;

import com.sen5.lib.api.event.BaseEvent;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class EventModeApply extends EventMode {
    private int cur_sec_mode;

    public int getCur_sec_mode() {
        return cur_sec_mode;
    }

    public void setCur_sec_mode(int cur_sec_mode) {
        this.cur_sec_mode = cur_sec_mode;
    }
}
