package com.sen5.lib.api.event;

/**
 * Created by zhurongkun on 2017/8/23.
 * 连接设备回调
 */

public class EventConnect extends BaseEvent {
    private int session;

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }
}
