package com.sen5.lib.entity.mode;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 * 设防模式bean
 */

public class SecurityMode {
    public static final int MODE_AWAY = 1;
    public static final int MODE_HOME = 2;
    public static final int MODE_DISARM = 3;


    private int sec_mode;//设防模式 1：离家 2：在家 3：撤防模式
    private int no_motion; //是否开启包括所有sensor，但motion sensor除外。【0：否 1：是】
    private List<Integer> dev_list; //当前模式起作用的Sensor

    public int getSec_mode() {
        return sec_mode;
    }

    public void setSec_mode(int sec_mode) {
        this.sec_mode = sec_mode;
    }

    public int getNo_motion() {
        return no_motion;
    }

    public void setNo_motion(int no_motion) {
        this.no_motion = no_motion;
    }

    public List<Integer> getDev_list() {
        return dev_list;
    }

    public void setDev_list(List<Integer> dev_list) {
        this.dev_list = dev_list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecurityMode that = (SecurityMode) o;

        return sec_mode == that.sec_mode;

    }

    @Override
    public int hashCode() {
        return sec_mode;
    }

    @Override
    public String toString() {
        return "SecurityMode{" +
                "sec_mode=" + sec_mode +
                ", no_motion=" + no_motion +
                ", dev_list=" + dev_list +
                '}';
    }
}
