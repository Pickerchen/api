package com.rrioo.hsl;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/10 17:00 1.0
 * @time 2017/10/10 17:00
 * @project secQreNew3.0 com.rrioo.hsl
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/10 17:00
 */

public class CameraCallback {

    public void CallBack_SnapShot(long UserID, byte[] buff, int len) {

    }


    public void CallBack_GetParam(long UserID, long nType, String param) {
//		if(settingsListener != null)
//			settingsListener.callBack_getParam(UserID, nType, param);
//        mLogUtil.e(param + "\r\n" + nType);
//		EventBus.getDefault().post();
    }


    public void CallBack_SetParam(long UserID, long nType, int nResult) {
//        mLogUtil.e("收到设置参数回调");
    }

    public void CallBack_RGB(long userID, byte[] datas, int i, int j, int k) {
    }

    public void CallBack_Event(long UserID, long nType) {
    }

    public void VideoData(long UserID, byte[] VideoBuf, int h264Data, int nLen,
                          int Width, int Height, int time) {
        //无法回调
        //mLogUtil.e("收到视频数据回调");
    }

    public void callBackAudioData(long nUserID, byte[] pcm, int size) {
//		if(playListener != null)
//			playListener.callBackAudioData(nUserID, pcm, size);
//		if(recorderListener != null)
//			recorderListener.callBackAudioData(nUserID, pcm, size);
        //播放实时音频回调
    }

    public void CallBack_RecordFileList(long UserID, int filecount, String fname,
                                        String strDate, int size) {
    }

    public void CallBack_P2PMode(long UserID, int nType) {

//        AppLog.i("p2p连接方式:userId =" +UserID+"  nType:"+nType);
//        EventBus.getDefault().post(new TestEvent(nType));

    }

    public void CallBack_RecordPlayPos(long userid, int pos) {
    }

    public void CallBack_VideoData(long nUserID, byte[] data, int type, int size) {
        //mLogUtil.e("收到视频数据回调");
    }

    public void CallBack_AlarmMessage(long UserID, int nType) {
    }


    public void CallBack_RecordPicture(long UserID, byte[] buff, int len) {
    }

    public void CallBack_RecordFileListV2(long UserID, String param) {

    }

    public void CallBack_TfPicture(long UserID, byte[] buff, int len) {
    }
}
