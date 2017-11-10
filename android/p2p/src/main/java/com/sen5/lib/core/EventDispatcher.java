package com.sen5.lib.core;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.sen5.lib.api.event.device.EventDeviceAdd;
import com.sen5.lib.api.event.device.EventDeviceAllStatus;
import com.sen5.lib.api.event.device.EventDeviceControl;
import com.sen5.lib.api.event.scene.EventSceneAdd;
import com.sen5.lib.api.event.mode.EventModeApply;
import com.sen5.lib.api.event.EventConnect;
import com.sen5.lib.api.event.device.EventDeviceDelete;
import com.sen5.lib.api.event.scene.EventSceneDelete;
import com.sen5.lib.api.event.device.EventDeviceList;
import com.sen5.lib.api.event.device.EventDeviceEdit;
import com.sen5.lib.api.event.mode.EventModeEdit;
import com.sen5.lib.api.event.room.EventRoomList;
import com.sen5.lib.api.event.scene.EventSceneList;
import com.sen5.lib.api.event.mode.EventModeList;
import com.sen5.lib.api.event.EventListUser;
import com.sen5.lib.api.event.device.EventDeviceStatus;
import com.sen5.lib.api.event.EventVerify;
import com.sen5.lib.entity.constant.Constant;
import com.sen5.lib.entity.device.Device;
import com.sen5.lib.util.JsonUtil;
import com.sen5.lib.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhurongkun on 2017/8/24.
 * 对服务端返回的数据json解析并分发给对应的event对象
 * <p>
 * bundle 数据格式：
 * msg_type : 消息类型
 * msg_body : 消息json
 */

class EventDispatcher {
    private Context mContext;

    public EventDispatcher(Context mContext) {
        this.mContext = mContext;
    }


    public void dispatch(Bundle extras) {
        int msgType = extras.getInt(Constant.MSG_TYPE);
        switch (msgType) {
            case Constant.MSG_CONNECT:
                onConnect(extras);
                break;
            case Constant.MSG_IDENTITY_LEGAL_RESPOND:
            case Constant.MSG_IDENTITY_NOT_LEGAL_RESPOND:
                onVerify(extras);
                break;
            case Constant.MSG_LIST_DEVICE_RESPOND:
                onDeviceListResult(extras);
                break;
            case Constant.MSG_EDIT_DEVICE_RESPOND:
                onEditDevice(extras);
                break;
            case Constant.MSG_ADD_DEVICE_RESPOND:
                onAddDevice(extras);
                break;
            case Constant.MSG_DELETE_DEVICE_RESPOND:
                onDeleteDevice(extras);
                break;
            case Constant.MSG_CONTROL_DEVICE_RESPOND:
                onControlDevice(extras);
                break;
            case Constant.MSG_REPORT_DEVICE_STATUS_RESPOND:
                onDeviceStatusReport(extras);
                break;
            case Constant.MSG_REQUEST_ALL_DEVICE_STATUS_RESPOND:
                onAllDeviceStatus(extras);
                break;
            case Constant.MSG_LIST_ROOM_RESPOND:
            case Constant.MSG_EDIT_ROOM_RESPOND:
            case Constant.MSG_NEW_ROOM_RESPOND:
            case Constant.MSG_DELETE_ROOM_RESPOND:
                onListRoom(extras);
                break;
            case Constant.MSG_LIST_SCENE_RESPOND:
                onSceneList(extras);
                break;
            case Constant.MSG_NEW_SCENE_RESPOND:
            case Constant.MSG_EDIT_SCENE_RESPOND:
                onAddScene(extras);
                break;
            case Constant.MSG_DELETE_SCENE_RESPOND:
            case Constant.MSG_APPLY_SCENE:
                onDeleteScene(extras);
                break;
            case Constant.MSG_LIST_MODE_RESPOND:
                onListMode(extras);
                break;
            case Constant.MSG_EDIT_MODE_RESPOND:
                onEditMode(extras);
                break;
            case Constant.MSG_APPLY_MODE_RESPOND:
                onApplyMode(extras);
                break;
            case Constant.MSG_LIST_USER_RESPOND:
                onListUser(extras);
                break;
            default:
                break;
        }
    }

    private void onListUser(Bundle extras) {
        String json = getJson(extras);
        EventListUser eventListUser = JsonUtil.fromJson(json, EventListUser.class);
        EventBus.getDefault().post(eventListUser);
    }

    private void onApplyMode(Bundle extras) {
        String json = getJson(extras);
        EventModeApply event = JsonUtil.fromJson(json, EventModeApply.class);
        EventBus.getDefault().post(event);
    }

    private void onEditMode(Bundle extras) {
        String json = getJson(extras);
        EventModeEdit eventModeEdit = JsonUtil.fromJson(json, EventModeEdit.class);
        EventBus.getDefault().post(eventModeEdit);
    }

    private void onListMode(Bundle extras) {
        LogUtil.i("onListMode");
        String json = getJson(extras);
        EventModeList event = JsonUtil.fromJson(json, EventModeList.class);
        EventBus.getDefault().post(event);
    }

    private void onDeleteScene(Bundle extras) {
        String json = getJson(extras);
        EventSceneDelete event = JsonUtil.fromJson(json, EventSceneDelete.class);
        EventBus.getDefault().post(event);
    }

    private void onAddScene(Bundle extras) {
        String json = getJson(extras);
        EventSceneAdd eventSceneAdd = JsonUtil.fromJson(json, EventSceneAdd.class);
        EventBus.getDefault().post(eventSceneAdd);
    }

    private void onSceneList(Bundle extras) {
        String json = getJson(extras);
        EventSceneList eventSceneList = JsonUtil.fromJson(json, EventSceneList.class);
        EventBus.getDefault().post(eventSceneList);
    }

    private void onListRoom(Bundle extras) {
        String json = getJson(extras);
        EventRoomList eventRoomList = JsonUtil.fromJson(json, EventRoomList.class);
        EventBus.getDefault().post(eventRoomList);
    }

    private void onAllDeviceStatus(Bundle extras) {
        String json = getJson(extras);
        EventDeviceAllStatus event = JsonUtil.fromJson(json, EventDeviceAllStatus.class);
        EventBus.getDefault().post(event);
    }

    private void onDeviceStatusReport(Bundle extras) {
        String json = getJson(extras);
        EventDeviceStatus event = JsonUtil.fromJson(json, EventDeviceStatus.class);
        EventBus.getDefault().post(event);
    }

    private void onControlDevice(Bundle extras) {
        String json = getJson(extras);
        EventDeviceControl event = JsonUtil.fromJson(json, EventDeviceControl.class);
        event.setMsg_type(Constant.MSG_CONTROL_DEVICE_RESPOND);
        EventBus.getDefault().post(event);
    }

    private void onEditDevice(Bundle extras) {
        String json = getJson(extras);
        Device device = JsonUtil.fromJson(json, Device.class);
        EventDeviceEdit event = new EventDeviceEdit();
        event.setMsg_type(Constant.MSG_EDIT_DEVICE_RESPOND);
        event.setDevice(device);
        EventBus.getDefault().post(event);
    }

    private void onDeleteDevice(Bundle extras) {
        String json = getJson(extras);
        if (TextUtils.isEmpty(json))
            return;
        EventDeviceDelete event = JsonUtil.fromJson(json, EventDeviceDelete.class);
        if (event.getDev_id() > 0) {
            EventBus.getDefault().post(event);
        }
    }

    private void onAddDevice(Bundle extras) {
        String json = getJson(extras);
        if (TextUtils.isEmpty(json))
            return;
        LogUtil.i(getClass().getName() + " onAddDevice json=" + json);
        Device device = JsonUtil.fromJson(json, Device.class);
        if (device == null || device.getDev_id() == 0 || TextUtils.isEmpty(device.getDev_type())) {
            LogUtil.i(getClass().getName() + " onAddDevice regular response ,no device");
            return;
        }
        EventDeviceAdd event = new EventDeviceAdd();
        event.setMsg_type(Constant.MSG_ADD_DEVICE_RESPOND);
        event.setDevice(device);
        EventBus.getDefault().post(event);
    }

    private void onDeviceListResult(Bundle extras) {
        String json = getJson(extras);
        if (TextUtils.isEmpty(json))
            return;
        EventDeviceList eventDeviceList = JsonUtil.fromJson(json, EventDeviceList.class);
        EventBus.getDefault().post(eventDeviceList);
    }

    private String getJson(Bundle extras) {
        if (extras == null)
            return null;
        String json = extras.getString(Constant.MSG_BODY, null);
        return json;
    }

    private void onVerify(Bundle extras) {
        String json = extras.getString(Constant.MSG_BODY);
        if (json != null && !TextUtils.isEmpty(json)) {
            EventVerify event = JsonUtil.fromJson(json, EventVerify.class);
            EventBus.getDefault().post(event);
        }
    }

    private void onConnect(Bundle bundle) {
        EventConnect event = new EventConnect();
        event.setMsg_type(Constant.MSG_CONNECT);
        event.setSession(bundle.getInt("session", -1));
        EventBus.getDefault().post(event);
    }
}
