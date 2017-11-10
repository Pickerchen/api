package com.sen5.lib.entity.scene;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class Scene {

    /**
     * 以下三个场景属于默认场景不对界面展示
     * 离家模式	1001	away_scene
     * 在家模式	1002	stay_scene
     * 撤防模式	1003	disarm_scene
     */
    public static final int ARM_AWAY_SCENE_ID = 1001;
    public static final int ARM_STAY_SCENE_ID = 1002;
    public static final int ARM_DISARM_SCENE_ID = 1003;

    private int scene_id;
    private String scene_name;
    private long update_time;
    private List<SceneAction> action_list;

    public int getScene_id() {
        return scene_id;
    }

    public void setScene_id(int scene_id) {
        this.scene_id = scene_id;
    }

    public String getScene_name() {
        return scene_name;
    }

    public void setScene_name(String scene_name) {
        this.scene_name = scene_name;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public List<SceneAction> getAction_list() {
        return action_list;
    }

    public void setAction_list(List<SceneAction> action_list) {
        this.action_list = action_list;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "scene_id=" + scene_id +
                ", scene_name='" + scene_name + '\'' +
                ", update_time=" + update_time +
                ", action_list=" + action_list +
                '}';
    }
}
