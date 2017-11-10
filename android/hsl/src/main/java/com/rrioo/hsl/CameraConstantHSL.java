package com.rrioo.hsl;

/**
 * Created by zhurongkun on 2017/8/30.
 * <p>
 * 海视联摄像头sdk一些常量
 */

public class CameraConstantHSL {
    static String serviceContent = "EBGJEBBBKAJIGEJLEPGCFAEPHCMCHMNFGJEABDCGBOJGLJLACAAKCBOMHBLNJHKCAKMHLEDAODMNANCBJHNMICAI" +
            "PBCJLLMMKBEIJCLDHGPJMLEMCDMGMMNOEIIGLHENDLEDCIHNBOMKKFFMCBBH";

    //连接中
    public static final int RESULT_CONNECTING = 0;

    //用户名或密码错误
    public static final int RESULT_USER_PWD_ERR = 1;

    //无效ID
    public static final int RESULT_INVALID_ID_ERR = 5;

    //设备不在线
    public static final int RESULT_CAMERA_OFFLINE = 9;

    //连接超时
    public static final int RESULT_CONNECT_TIMEOUT = 10;

    //设备断开连接,网络不好时经常断开，在有网的情况下，断开会自动重连,不保证连上
    public static final int RESULT_CONNECT_INTERRUPT = 11;

    //连接错误
    public static final int RESULT_CONNECT_ERR = 101;

    //连接成功
    public static final int RESULT_CONNECT_SUCC = 100;


}
