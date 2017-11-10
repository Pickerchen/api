package com.sen5.lib.api.event.scene;

import com.sen5.lib.entity.scene.Scene;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class EventSceneList extends EventScene{
    private List<Scene> scenes;

    public List<Scene> getScenes() {
        return scenes;
    }

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }
}
