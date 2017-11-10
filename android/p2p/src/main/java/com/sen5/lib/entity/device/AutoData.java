package com.sen5.lib.entity.device;

import java.util.Arrays;

/**
 * Created by ZHOUDAO on 2017/8/1.
 */

public class AutoData {
    private int action_dev_id;
    private int action_id;
    private int action_onoff;
    private byte[] action_params;
    private int dev_onoff;
    private String time;

    public AutoData() {
    }

    public int getAction_dev_id() {
        return action_dev_id;
    }

    public void setAction_dev_id(int action_dev_id) {
        this.action_dev_id = action_dev_id;
    }

    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }

    public int getAction_onoff() {
        return action_onoff;
    }

    public void setAction_onoff(int action_onoff) {
        this.action_onoff = action_onoff;
    }

    public byte[] getAction_params() {
        return action_params;
    }

    public void setAction_params(byte[] action_params) {
        this.action_params = action_params;
    }

    public int getDev_onoff() {
        return dev_onoff;
    }

    public void setDev_onoff(int dev_onoff) {
        this.dev_onoff = dev_onoff;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AutoData{" +
                "action_dev_id=" + action_dev_id +
                ", action_id=" + action_id +
                ", action_onoff=" + action_onoff +
                ", action_params=" + Arrays.toString(action_params) +
                ", dev_onoff=" + dev_onoff +
                ", time='" + time + '\'' +
                '}';
    }
}
