package com.sen5.lib.entity.device;

/**
 * Created by zhurongkun on 2017/8/21.
 */

public enum DeviceMode {
    /**
     * 传感器
     */
    SENSOR(0),
    /**
     * 控制类设备
     */
    ACTION(1),

    /**
     * 兼具所有类型
     */
    FULL(2),

    /**
     * 未知
     */
    UNKNOWN(3);
    private int val;

    DeviceMode(int val) {
        this.val = val;
    }

    public int getValue() {
        return val;
    }

    public static DeviceMode parse(int val) {
        if (val == 0)
            return SENSOR;
        if (val == 1)
            return ACTION;
        if (val == 2)
            return FULL;
        if (val == 3)
            return UNKNOWN;
        return null;
    }

}
