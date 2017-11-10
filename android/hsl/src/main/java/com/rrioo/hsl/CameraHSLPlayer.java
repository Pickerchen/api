package com.rrioo.hsl;

import android.opengl.GLSurfaceView;


import com.rrioo.logger.MyLog;

import org.json.JSONException;
import org.json.JSONObject;

import hsl.p2pipcam.nativecaller.DeviceSDK;
import hsl.p2pipcam.nativecaller.NativeCaller;


/**
 * Created by zhurongkun on 2017/8/29.
 */

public class CameraHSLPlayer implements MyRender.RenderListener {
    /**
     * #define AP_ERROR_FAILED			0		// SDK API执行失败
     * #define AP_ERROR_SUCC			1
     * #define AP_ERROR_NOT_INIT		-1		// SDK未初始化
     * #define AP_ERROR_MAX_INSTANCE	-2
     * #define AP_ERROR_INVALID_ID		-3		// userid不可用
     * #define AP_ERROR_LINK_MODE		-4
     * #define AP_ERROR_ALLOC_FAILED	-5
     * #define AP_ERROR_PARAMETERS		-6
     */

    //截图时间间隔
    public static final long SNAPSHOT_INTERVAL = 15 * 1000L;


    private static final int AP_ERROR_SUCC = 1;
    private String did;

    private long playerId;

    //摄像头还未初始化
    public static final int UNINITICAL = 0;
    //正在加载
    public static final int LOADING = 2;
    //正在播放
    public static final int PLAYING = 3;
    //暂停了
    public static final int PAUSED = 4;

    private int playStatus = UNINITICAL;

    public long snapshotTime;

    private GLSurfaceView.Renderer render;


    CameraHSLPlayer(String did) {
        this.did = did;
    }

    /**
     * 第一步：设置render
     *
     * @param render
     */
    public void setRender(GLSurfaceView.Renderer render) {
        this.render = render;
    }

    /**
     * 第二步：初始化,当回调返回100时才能进行播放
     *
     * @return
     */
    public boolean initPlayer() {
        if (render == null) {
            MyLog.e("player didn't set renderer yet!");
            return false;
        }
//        render.setListener(this);
        playerId = DeviceSDK.createDevice("admin", "", "", 0, this.did, 1);
        DeviceSDK.setRender(playerId, render);
        MyLog.i("initPlayer " + playerId);
        int code = DeviceSDK.openDevice(playerId);
        MyLog.i("openDevice "+code);
        return code == AP_ERROR_SUCC;
    }

    /**
     * 第三步：播放
     *
     * @return
     */
    public boolean play() {
        //userid, 10, 2
        DeviceSDK.setRender(playerId, render);
        int code = DeviceSDK.startPlayStream(playerId, 10, 1);
        MyLog.i("startPlayStream " + code);
        if (code != AP_ERROR_SUCC) {
            MyLog.e("play() startPlayStream return err:" + code);
            return false;
        }
        try {


            JSONObject obj = new JSONObject();
            obj.put("param", 13);
            obj.put("value", 1024);
            code = DeviceSDK.setDeviceParam(playerId, 0x2026, obj.toString());

//            if (code <= 0)
//                return false;

            JSONObject obj1 = new JSONObject();
            obj1.put("param", 6);
            obj1.put("value", 15);
            code = DeviceSDK.setDeviceParam(playerId, 0x2026, obj1.toString());

//            if (code <= 0)
//                return false;

            JSONObject obj2 = new JSONObject();
            obj2.put("now", System.currentTimeMillis() / 1000);
            obj2.put("ntp_enable", 1);
            obj2.put("ntp_svr", "time.nist.gov");
            //北京时区偏差值，八个小时
            obj2.put("timezone", -28800);
            //约旦时区偏差：-10800
            String json = obj2.toString();
            code = DeviceSDK.setDeviceParam(playerId, 0x2015, json);
//            if (code <= 0)
//                return false;

        } catch (JSONException e) {
            MyLog.e("play exception " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 暂停，暂停后直接调用play就可继续播放
     *
     * @return
     */
    public boolean pause() {
        return DeviceSDK.stopPlayStream(playerId) == AP_ERROR_SUCC;
    }

    /**
     * 销毁，需要重新initplayer
     *
     * @return
     */
    public boolean release() {
        return DeviceSDK.closeDevice(playerId) == AP_ERROR_SUCC;
    }


    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public int getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(int playStatus) {
        this.playStatus = playStatus;
    }


    public GLSurfaceView.Renderer getRender() {
        return render;
    }


    /**
     * RenderListener
     *
     * @param size
     * @param width
     * @param height
     */
    @Override
    public void initComplete(int size, int width, int height) {

    }

    /**
     * RenderListener
     *
     * @param imageBuffer
     * @param width
     * @param height
     */
    @Override
    public void takePicture(byte[] imageBuffer, int width, int height) {

    }

    public int snapShot(String path) {
        return NativeCaller.CapturePicture(playerId, path);
    }
}
