package com.sen5.lib.api.event;

/**
 * Created by zhurongkun on 2017/8/23.
 * 设备回调的基础类
 */

public class BaseEvent {
    protected int msg_type;

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    @Override
    public String toString() {
        return "BaseEvent{" +
                "msg_type=" + msg_type +
                '}';
    }
}
