package com.sen5.lib.api.event.scene;

import com.sen5.lib.entity.scene.Scene;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class EventSceneAdd extends EventScene {
    private Scene scene_info;

    public Scene getScene_info() {
        return scene_info;
    }

    public void setScene_info(Scene scene_info) {
        this.scene_info = scene_info;
    }
}
