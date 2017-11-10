package com.sen5.lib.api;

import com.sen5.lib.connection.ConnectionParameter;
import com.sen5.lib.entity.constant.Constant;
import com.sen5.lib.entity.device.Device;
import com.sen5.lib.entity.mode.SecurityMode;
import com.sen5.lib.entity.room.Room;
import com.sen5.lib.entity.scene.Scene;
import com.sen5.lib.entity.scene.SceneAction;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/23.
 */

public interface Sender {
    void startConncet(ConnectionParameter parameter);

//    /**
//     * 连接盒子
//     * P2P
//     * @param boxDid
//     */
//    void startConnect(String boxDid);
//
//    /**
//     * Socket
//     * @param host ip地址
//     * @param port 端口号
//     */
//    void startConnect(String host, int port);

    void stopConnect();

    /**
     * 验证当前app用户
     */
    void verify();


    //******************************************************************
    //***************************设备操作***************************************

    /**
     * 获取设备列表
     */
    void deviceList();

    /**
     * 编辑设备
     *
     * @param device
     */
    void editDevice(Device device);

    /**
     * 发送一个命令让盒子搜索并添加设备,会不断推送给app
     */
    void addDevice();

    /**
     * 删除设备
     *
     * @param devId
     */
    void deleteDevice(int devId);

    /**
     * 控制设备操作
     *
     * @param devId        控制动作类型：【0：开关；1：调节，如亮度】
     * @param actionId     {@link Constant}
     * @param actionParams {@link Constant}
     */
    void controlDevice(int devId, int actionId, byte[] actionParams);

    /**
     * 获取单个设备的状态
     *
     * @param devId
     */
    void getDeviceStatus(int devId);

    /**
     * 获取所有设备的状态
     */
    void getAllDeviceStatus();

    //******************************************************************
    //***************************房间操作***************************************

    /**
     * 获取房间列表
     */
    void roomList();


    /**
     * 新建房间
     *
     * @param roomName
     * @param roomDevicesID 如果没有设备就传null
     */
    void createNewRoom(String roomName, List<Integer> roomDevicesID);

    /**
     * 删除单个房间
     *
     * @param roomId
     */
    void deleteRoom(int roomId);

    /**
     * 编辑房间
     *
     * @param room
     */
    void editRoom(Room room);


    //******************************************************************
    //***************************场景操作***************************************

    /**
     * 获取场景列表
     */
    void sceneList();

    /**
     * 新建场景
     *
     * @param sceneName 场景名
     * @param actions   设备及动作
     */
    void createScene(String sceneName, List<SceneAction> actions);

    /**
     * 删除场景
     *
     * @param sceneId
     */
    void deleteScene(int sceneId);

    /**
     * 修改场景
     *
     * @param scene
     */
    void editScene(Scene scene);

    /**
     * 触发场景
     */
    void applyScene(int sceneId);

    //******************************************************************
    //***************************设防模式操作***************************************

    /**
     * 获取设防模式列表
     */
    void securityModeList();

    /**
     * 编辑设防模式
     * @param mode
     */
    void editSecurityMode(SecurityMode mode);

    /**
     * 应用当前设防模式
     * @param modeId {@link SecurityMode}
     */
    void applySecurityMode(int modeId);

    //******************************************************************
    //***************************常用设备操作***************************************

    /**
     * 获取收藏设备列表
     */
    void activeDeviceList();

    /**
     * 编辑常用设备列表
     * @param dev_list
     */
    void editActiveDevice(List<Integer> dev_list);

    //******************************************************************
    //***************************用户操作***************************************

    /**
     * 获取用户列表
     */
    void userList();
}
