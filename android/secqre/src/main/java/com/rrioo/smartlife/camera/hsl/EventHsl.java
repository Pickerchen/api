package com.rrioo.smartlife.camera.hsl;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/10 17:09 1.0
 * @time 2017/10/10 17:09
 * @project secQreNew3.0 com.rrioo.smartlife.camera.hsl
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/10 17:09
 */

public class EventHsl {
    private long playId;
    private long status;

    public EventHsl(long playId, long status) {
        this.playId = playId;
        this.status = status;
    }

    public long getPlayId() {
        return playId;
    }

    public void setPlayId(long playId) {
        this.playId = playId;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EventHsl{" +
                "playId=" + playId +
                ", status=" + status +
                '}';
    }
}
