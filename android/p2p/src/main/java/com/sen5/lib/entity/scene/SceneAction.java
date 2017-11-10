package com.sen5.lib.entity.scene;

import java.util.Arrays;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class SceneAction {
    private int dev_id;
    /**
     * 设备动作
     */
    private int action_id;
    private byte[] action_params;

    public int getDev_id() {
        return dev_id;
    }

    public void setDev_id(int dev_id) {
        this.dev_id = dev_id;
    }

    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }

    public byte[] getAction_params() {
        return action_params;
    }

    public void setAction_params(byte[] action_params) {
        this.action_params = action_params;
    }

    @Override
    public String toString() {
        return "SceneAction{" +
                "dev_id=" + dev_id +
                ", action_id=" + action_id +
                ", action_params=" + Arrays.toString(action_params) +
                '}';
    }
}
