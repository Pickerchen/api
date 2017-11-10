package com.rrioo.smartlife.ui.settings.device;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/13 16:30 1.0
 * @time 2017/10/13 16:30
 * @project secQreNew3.0 com.rrioo.smartlife.ui.settings.device_room
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/13 16:30
 */

public class DeviceRoomBean {
    public static final int ROOM = 0;
    public static final int DEVICE = 1;

    Object data;
    int position;
    int type;

    public DeviceRoomBean(Object data, int position, int type) {
        this.data = data;
        this.position = position;
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
