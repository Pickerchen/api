package com.rrioo.smartlife.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.SecApp;
import com.rrioo.smartlife.data.AppConstant;


/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/29 11:53 1.0
 * @time 2017/9/29 11:53
 * @project secQreNew3.0 com.rrioo.smartlife.util
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/29 11:53
 */

public class SpUtil {

    private static final String DEFAULT_FILE_NAME = AppConstant.DEFAULT_XML_NAME;
    private static final int DEFAULT_MODE = Context.MODE_PRIVATE;

    private static final String KEY_BOX_ID = "KEY_BOX_ID";
    private static final String KEY_SELECTED_SCENE = "KEY_SELECTED_SCENE";

    private Context mContext = SecApp.get();
    private String mFileName;
    private int mMode;

    public static SpUtil getDefault() {
        return new SpUtil(DEFAULT_FILE_NAME, DEFAULT_MODE);
    }


    public static SpUtil getCustom(String name) {
        return new SpUtil(name, DEFAULT_MODE);
    }

    private SpUtil(String fileName, int mode) {
        this.mFileName = fileName;
        this.mMode = mode;
    }

    public SharedPreferences getReader() {
        return mContext.getSharedPreferences(mFileName, mMode);
    }

    public SharedPreferences.Editor getWriter() {
        return getReader().edit();
    }


    public void saveBoxId(String did) {
        getWriter().putString(KEY_BOX_ID, did).commit();
    }

    public String getBoxId() {
        return getReader().getString(KEY_BOX_ID, null);
    }

    public int getSelectedScene() {
        return getReader().getInt(KEY_SELECTED_SCENE, -1);
    }

    public void saveSelectedScene(int sceneId){
        getWriter().putInt(KEY_SELECTED_SCENE,sceneId).commit();
    }

    public void changeHome(String newHomeId){
        getWriter().remove(KEY_SELECTED_SCENE);
        saveBoxId(newHomeId);
    }
}
