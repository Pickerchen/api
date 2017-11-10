package com.rrioo.smartlife.data;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.util.DeviceHelper;
import com.rrioo.smartlife.util.NullUtil;
import com.sen5.lib.api.Api;
import com.sen5.lib.api.event.device.EventDeviceAdd;
import com.sen5.lib.api.event.device.EventDeviceAllStatus;
import com.sen5.lib.api.event.device.EventDeviceControl;
import com.sen5.lib.api.event.device.EventDeviceDelete;
import com.sen5.lib.api.event.device.EventDeviceList;
import com.sen5.lib.api.event.device.EventDeviceStatus;
import com.sen5.lib.api.event.mode.EventModeList;
import com.sen5.lib.api.event.room.EventRoomList;
import com.sen5.lib.api.event.scene.EventSceneList;
import com.sen5.lib.entity.device.Device;
import com.sen5.lib.entity.device.DeviceStatus;
import com.sen5.lib.entity.mode.SecurityMode;
import com.sen5.lib.entity.room.Room;
import com.sen5.lib.entity.scene.Scene;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/30 11:03 1.0
 * @time 2017/9/30 11:03
 * @project secQreNew3.0 com.rrioo.smartlife.data
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/30 11:03
 */

public class ApiCache {
    private static final int PRIORITY = 100;

    /********** devices ********/
    private List<Device> mCameras = new CopyOnWriteArrayList<>();
    private List<Device> mSmartDevices = new CopyOnWriteArrayList<>();

    /********** securiry modes **********/
    private List<SecurityMode> mSecModes = new CopyOnWriteArrayList<>();

    /************ rooms ******************/
    private List<Room> mRooms = new CopyOnWriteArrayList<>();

    /************* scenes ****************/
    private List<Scene> mScenes = new CopyOnWriteArrayList<>();

    /***************** 在设防模式之下默认触发的场景 ***********************/
    private Scene mDefaultSceneAway;
    private Scene mDefaultSceneStay;
    private Scene mDefaultSceneDisarm;


    private ApiCache() {
    }

    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = PRIORITY)
    public void onMessageEvent(EventDeviceList eventDeviceList) {
        List<Device> devices = eventDeviceList.getDevices();
//        mCameras.clear();
//        mSmartDevices.clear();
        List<Device> cameras = new CopyOnWriteArrayList<>();
        List<Device> smartDevs = new CopyOnWriteArrayList<>();
        for (Device device : devices) {
            Device oldDevice = findDeviceById(device.getDev_id());
            if (oldDevice != null) {
                List<DeviceStatus> status = oldDevice.getStatus();
                device.setStatus(status);
            }
            if (DeviceHelper.isCamera(device.getDev_type())) {
//                insertOrUpdate(mCameras, device);
                cameras.add(device);
            } else {
                smartDevs.add(device);
//                insertOrUpdate(mSmartDevices, device);
            }
        }
        eventDeviceList.getDevices().clear();
        mCameras.clear();
        mSmartDevices.clear();

        mCameras = cameras;
        mSmartDevices = smartDevs;

        MyLog.object(mCameras);
        MyLog.object(mSmartDevices);
        Device ltCamera = new Device();
        ltCamera.setCamera_id("by0178b013");
        mCameras.add(ltCamera);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = PRIORITY)
    public void onMessageEvent(EventDeviceAdd eventDeviceAdd) {
        Device device = eventDeviceAdd.getDevice();
        if (DeviceHelper.isCamera(device.getDev_type())) {
            insertOrUpdate(mCameras, device);
        } else {
            insertOrUpdate(mSmartDevices, device);
        }
        Api.get().getSender().getDeviceStatus(device.getDev_id());
        MyLog.object(eventDeviceAdd);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = PRIORITY)
    public void onMessageEvent(EventDeviceAllStatus event) {
        List<EventDeviceStatus> status_list = event.getStatus_list();
        for (EventDeviceStatus status : status_list) {
            int dev_id = status.getDev_id();
            Device device = findDeviceById(dev_id);
            if (device != null) {
                device.setStatus(status.getStatus());
            }
        }
        MyLog.object(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = PRIORITY)
    public void onMessageEvent(EventDeviceStatus event) {
        int devId = event.getDev_id();
        Device device = findDeviceById(devId);
        if (device != null) {
            device.setStatus(event.getStatus());
        }
        MyLog.object(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = PRIORITY)
    public void onMessageEvent(EventDeviceControl event) {
        int devId = event.getDev_id();
        Device device = findDeviceById(devId);
        if (device != null) {
            List<DeviceStatus> statusList = device.getStatus();
            if (NullUtil.isEmpty(statusList)) {
                statusList = new ArrayList<>();
                DeviceStatus status = new DeviceStatus();
                status.setId(event.getAction_id());
                status.setParams(event.getAction_params());
                statusList.add(status);
            } else {
                boolean has = false;
                for (DeviceStatus status : statusList) {
                    if (status.getId() == event.getAction_id()) {
                        status.setParams(event.getAction_params());
                        has = true;
                        break;
                    }
                }
                if (!has) {
                    DeviceStatus status = new DeviceStatus();
                    status.setId(event.getAction_id());
                    status.setParams(event.getAction_params());
                    statusList.add(status);
                }
            }
        }
        MyLog.object(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = PRIORITY)
    public void onMessageEvent(EventDeviceDelete eventDeviceDelete) {
        int dev_id = eventDeviceDelete.getDev_id();
        Device device = findDeviceById(dev_id);
        mSmartDevices.remove(device);
        mCameras.remove(device);
        MyLog.object(eventDeviceDelete);
    }

    public Device findDeviceById(int devId) {
        for (Device device : mSmartDevices) {
            if (device.getDev_id() == devId) return device;
        }
        for (Device device : mCameras) {
            if (device.getDev_id() == devId) return device;
        }
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = PRIORITY)
    public void onMessageEvent(EventModeList eventModeList) {
        List<SecurityMode> modes = eventModeList.getModes();
        mSecModes.clear();
        insertOrUpdate(mSecModes, modes);
        eventModeList.getModes().clear();
        MyLog.object(mSecModes);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = PRIORITY)
    public void onMessageEvent(EventRoomList eventRoomList) {
        List<Room> rooms = eventRoomList.getRooms();
        mRooms.clear();
        insertOrUpdate(mRooms, rooms);
        eventRoomList.getRooms().clear();
        MyLog.object(mRooms);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = PRIORITY)
    public void onMessageEvent(EventSceneList eventSceneList) {
        List<Scene> scenes = eventSceneList.getScenes();
        Iterator<Scene> iterator = scenes.iterator();
        while (iterator.hasNext()) {
            Scene next = iterator.next();
            if (next.getScene_id() == Scene.ARM_AWAY_SCENE_ID) {
                mDefaultSceneAway = next;
                iterator.remove();
            }
            if (next.getScene_id() == Scene.ARM_STAY_SCENE_ID) {
                mDefaultSceneStay = next;
                iterator.remove();
            }
            if (next.getScene_id() == Scene.ARM_DISARM_SCENE_ID) {
                mDefaultSceneDisarm = next;
                iterator.remove();
            }
        }

        mScenes.clear();
        insertOrUpdate(mScenes, scenes);
        eventSceneList.getScenes().clear();
    }


    private void insertOrUpdate(List collection, List target) {
        for (int i = 0; i < target.size(); i++) {
            Object o = target.get(i);
            insertOrUpdate(collection, o);
        }
    }


    private void insertOrUpdate(List collection, Object target) {
        if (collection == null) return;
        if (collection.contains(target)) {
            int i = collection.indexOf(target);
            collection.add(i, target);
        } else {
            collection.add(target);
        }
    }

    public Room findRoomById(int roomID) {
        if (NullUtil.isEmpty(mRooms)) return null;
        for (Room room : mRooms) {
            if (room.getRoom_id() == roomID)
                return room;
        }
        return null;
    }

    public List<Device> getCameras() {
        return mCameras;
    }


    public List<Device> getSmartDevices() {
        return mSmartDevices;
    }


    public List<SecurityMode> getSecModes() {
        return mSecModes;
    }


    public List<Room> getRooms() {
        return mRooms;
    }


    public List<Scene> getScenes() {
        return mScenes;
    }


    public static ApiCache get() {
        return BUILDER.INSTANCE;
    }

    /**
     * 获取属于该房间的智能设备，不包括摄像头
     *
     * @param roomID
     * @return
     */
    public List<Device> getRoomDevices(int roomID) {
        List<Device> result = new CopyOnWriteArrayList<>();
        for (Device device : mSmartDevices) {
            if (device.getRoom_id() == roomID)
                result.add(device);
        }
        return result;
    }

    private static class BUILDER {
        static ApiCache INSTANCE = new ApiCache();
    }
}
