package com.rrioo.hsl;

import hsl.p2pipcam.nativecaller.DeviceSDK;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/10 17:11 1.0
 * @time 2017/10/10 17:11
 * @project secQreNew3.0 com.rrioo.hsl
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/10 17:11
 */

public class CameraHslManager {
    public static void init(CameraCallback cameraCallback){
        DeviceSDK.initialize(CameraConstantHSL.serviceContent);
        DeviceSDK.setCallback(cameraCallback);
        DeviceSDK.networkDetect();
    }

    public static void release(){
        DeviceSDK.unInitialize();
    }

    public static CameraHSLPlayer createPlayer(String did){
        return new CameraHSLPlayer(did);
    }
}
