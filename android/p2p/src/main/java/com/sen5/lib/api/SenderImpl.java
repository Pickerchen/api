package com.sen5.lib.api;

import android.os.Bundle;

import com.sen5.lib.connection.ConnectionParameter;
import com.sen5.lib.entity.constant.Constant;
import com.sen5.lib.entity.device.Device;
import com.sen5.lib.entity.mode.SecurityMode;
import com.sen5.lib.entity.room.Room;
import com.sen5.lib.entity.scene.Scene;
import com.sen5.lib.entity.scene.SceneAction;
import com.sen5.lib.util.Base64Util;
import com.sen5.lib.util.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhurongkun on 2017/8/23.
 */

class SenderImpl implements Sender {

    @Override
    public void startConncet(ConnectionParameter parameter) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.MSG_TYPE,Constant.MSG_CONNECT);
        bundle.putSerializable("parameter",parameter);
        Api.get().sendMessageToRemote(bundle);
    }

//    @Override
//    public void startConnect(String boxDid) {
//        Bundle bundle = makeBundle(Constant.MSG_CONNECT,"");
//        bundle.putInt("type", ConnectionType.P2P.getValue());
//        bundle.putString("boxId",boxDid);
//        Api.get().sendMessageToRemote(bundle);
//    }
//
//    @Override
//    public void startConnect(String host, int port) {
//        Bundle bundle = makeBundle(Constant.MSG_CONNECT,"");
//        bundle.putInt("type", ConnectionType.SOCKET.getValue());
//        bundle.putString("host",host);
//        bundle.putInt("port",port);
//        Api.get().sendMessageToRemote(bundle);
//    }

    @Override
    public void stopConnect() {
        Bundle bundle = makeBundle(Constant.MSG_DISCONNECT, " ");
        Api.get().sendMessageToRemote(bundle);
    }

    @Override
    public void verify() {
        String userId = Api.get().getUserId();
        Map map = createBasicJsonMap(Constant.MSG_IDENTITY_VERIFY);
        map.put(Constant.IDENTITY_ID, userId);
        String json = JsonUtil.toJson(map);
        Bundle bundle = makeBundle(Constant.MSG_IDENTITY_VERIFY, json);
        Api.get().sendMessageToRemote(bundle);
    }

    /**
     * {
     * msg_type:101,
     * }
     */
    @Override
    public void deviceList() {
        Map map = createBasicJsonMap(Constant.MSG_LIST_DEVICE);
        String json = JsonUtil.toJson(map);
        Api.get().sendMessageToRemote(makeBundle(Constant.MSG_LIST_DEVICE, json));
    }

    /**
     * {
     * msg_type:102,
     * dev_id:111,
     * table_id:1,
     * name:"asdf",          // 可选
     * room_id:2             // 可选
     * sec_away:0            // 可选
     * sec_stay:0            // 可选
     * sec_disarm:0          // 可选
     * camera_id = 101       // 判断sensor的关联摄像头id
     * auto_list = [
     * {
     * dev_onoff = 1             // 传感设备状态（报警/正常）
     * action_dev_id = 3         //  触控设备id
     * action_onoff = 1          //  单个automation是否开启
     * action_id = 1             //  动作ID
     * action_params = "AQ=="    //  动作参数
     * time = 123                // 时间触发
     * }
     * ]                             // 联动，传感设备触发控制设备
     * }
     */
    @Override
    public void editDevice(Device device) {
        Map map = createBasicJsonMap(Constant.MSG_EDIT_DEVICE);
        map.put("dev_id", device.getDev_id());
        map.put("table_id", device.getTable_id());
        map.put("name", device.getName());
        map.put("room_id", device.getRoom_id());
        map.put("camera_id", device.getCamera_id());
        map.put("sec_away", device.getSec_away());
        map.put("sec_stay", device.getSec_stay());
        map.put("sec_disarm", device.getSec_disarm());
        map.put("auto_list", device.getAuto_list());
        Api.get().sendMessageToRemote(makeBundle(Constant.MSG_EDIT_DEVICE,
                JsonUtil.toJson(map)));
    }

    @Override
    public void addDevice() {
        int msgType = Constant.MSG_ADD_DEVICE;
        Map map = createBasicJsonMap(msgType);
        Api.get().sendMessageToRemote(makeBundle(msgType, JsonUtil.toJson(map)));
    }


    /**
     * {
     * msg_type:104,
     * dev_id:111,
     * table_id:1
     * }
     *
     * @param devId
     */
    @Override
    public void deleteDevice(int devId) {
        int msgType = Constant.MSG_DELETE_DEVICE;
        Map map = createBasicJsonMap(msgType);
        map.put("dev_id", devId);
        Api.get().sendMessageToRemote(makeBundle(msgType, JsonUtil.toJson(map)));
    }

    /**
     * {
     * msg_type:105,
     * dev_id:111,       //设备ID
     * table_id:1,       //表号，1表示zigbee
     * action_id:1,
     * action_params:"MQ=="  //byte数组转为base64字符串
     * }
     *
     * @param devId
     * @param actionId
     * @param actionParams
     */
    @Override
    public void controlDevice(int devId, int actionId, byte[] actionParams) {
        int msgtype = Constant.MSG_CONTROL_DEVICE;
        Map map = createBasicJsonMap(msgtype);
        map.put("dev_id", devId);
        map.put("action_id", actionId);
        String aparams = Base64Util.encodeToString(actionParams);
        map.put("action_params", aparams);
        Api.get().sendMessageToRemote(makeBundle(msgtype, JsonUtil.toJson(map)));
    }

    @Override
    public void getDeviceStatus(int devId) {
        int msgType = Constant.MSG_REQUEST_SINGLE_DEVICE_STATUS;
        Map map = createBasicJsonMap(msgType);
        map.put("dev_id", msgType);
        Api.get().sendMessageToRemote(makeBundle(msgType, JsonUtil.toJson(map)));
    }

    @Override
    public void getAllDeviceStatus() {
        int msgType = Constant.MSG_REQUEST_ALL_DEVICE_STATUS;
        Map map = createBasicJsonMap(msgType);
        Api.get().sendMessageToRemote(makeBundle(msgType, JsonUtil.toJson(map)));
    }

    @Override
    public void roomList() {
        int msg = Constant.MSG_LIST_ROOM;
        Map map = createBasicJsonMap(msg);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void createNewRoom(String roomName, List<Integer> roomDevicesID) {
        int msg = Constant.MSG_NEW_ROOM;
        Map map = createBasicJsonMap(msg);
        if (roomDevicesID == null)
            roomDevicesID = new ArrayList<>();
        map.put("room_name", roomName);
        map.put("dev_list", roomDevicesID);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void deleteRoom(int roomId) {
        int msg = Constant.MSG_DELETE_ROOM;
        Map map = createBasicJsonMap(msg);
        map.put("room_id", roomId);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void editRoom(Room room) {
        int msg = Constant.MSG_EDIT_ROOM;
        Map map = createBasicJsonMap(msg);
        map.put("room_id", room.getRoom_id());
        map.put("room_name", room.getRoom_name());
        map.put("dev_list", room.getDev_list());
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void sceneList() {
        int msg = Constant.MSG_LIST_SCENE;
        Map map = createBasicJsonMap(msg);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void createScene(String sceneName, List<SceneAction> actions) {
        int msg = Constant.MSG_NEW_SCENE;
        Map map = createBasicJsonMap(msg);
        map.put("scene_name", sceneName);
        map.put("select_mode", 0);
        map.put("action_list", actions == null ? new ArrayList<>() : actions);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void deleteScene(int sceneId) {
        int msg = Constant.MSG_DELETE_SCENE;
        Map map = createBasicJsonMap(msg);
        map.put("scene_id", sceneId);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void editScene(Scene scene) {
        int msg = Constant.MSG_EDIT_SCENE;
        Map map = createBasicJsonMap(msg);
        map.put("scene_id", scene.getScene_id());
        map.put("scene_name", scene.getScene_name());
        map.put("select_mode", 0);
        map.put("action_list", scene.getAction_list());
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void applyScene(int sceneId) {
        int msg = Constant.MSG_APPLY_SCENE;
        Map map = createBasicJsonMap(msg);
        map.put("scene_id", sceneId);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void securityModeList() {
        int msg = Constant.MSG_LIST_MODE;
        Map map = createBasicJsonMap(msg);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void editSecurityMode(SecurityMode mode) {
        int msg = Constant.MSG_EDIT_MODE;
        Map map = createBasicJsonMap(msg);
        map.put("sec_mode", mode.getSec_mode());
        map.put("no_motion", mode.getNo_motion());
        map.put("dev_list", mode.getDev_list());
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void applySecurityMode(int modeId) {
        int msg = Constant.MSG_APPLY_MODE;
        Map map = createBasicJsonMap(msg);
        map.put("cur_sec_mode", modeId);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void activeDeviceList() {
        int msg = Constant.MSG_LIST_FAVORITE;
        Map map = createBasicJsonMap(msg);
        map.put("user_id", Api.get().getUserId());
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void editActiveDevice(List<Integer> dev_list) {
        int msg = Constant.MSG_EDIT_FAVORITE;
        Map map = createBasicJsonMap(msg);
        map.put("user_id", Api.get().getUserId());
        map.put("dev_list", dev_list);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    @Override
    public void userList() {
        int msg = Constant.MSG_LIST_USER;
        Map map = createBasicJsonMap(msg);
        Api.get().sendMessageToRemote(makeBundle(msg, JsonUtil.toJson(map)));
    }

    private Map createBasicJsonMap(int msgType) {
        Map map = new HashMap();
        map.put(Constant.MSG_TYPE, msgType);
        return map;
    }

    private Bundle makeBundle(int msgType, String msgBody) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.MSG_TYPE, msgType);
        bundle.putString(Constant.MSG_BODY, msgBody);
        return bundle;
    }

}
