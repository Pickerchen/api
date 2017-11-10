package com.sen5.lib.entity.device;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/18.
 */

public class Device {

    /**
     * dev_id : 111
     * table_id : 1
     * dev_type : A010402200000
     * mode : 0
     * name : deviceB
     * update_time : 12343234
     * status : {}
     */

    /**
     * 设备ID
     */
    private int dev_id;

    /**
     *
     */
    private int table_id;

    /**
     * 设备类型
     */
    private String dev_type;

    /**
     * //设备类型【0:sensor；1:action；2:full；3:unknown】,即将废弃
     */
    private int mode;

    /**
     * 设备名称
     */
    private String name;


    private int update_time;

    private List<DeviceStatus> status;

    /**
     * 房间ID
     */
    private int room_id;

    private int sec_away;            // 可选
    private int sec_stay;           // 可选
    private int sec_disarm;          // 可选
    private String camera_id ;      // 判断sensor的关联摄像头id

    private List<AutoData> auto_list;

    public int getDev_id() {
        return dev_id;
    }

    public void setDev_id(int dev_id) {
        this.dev_id = dev_id;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getDev_type() {
        return dev_type;
    }

    public void setDev_type(String dev_type) {
        this.dev_type = dev_type;
    }

    public DeviceMode getMode() {
        return DeviceMode.parse(mode);
    }

    public void setMode(DeviceMode mode) {
        this.mode = mode.getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }



    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getSec_away() {
        return sec_away;
    }

    public void setSec_away(int sec_away) {
        this.sec_away = sec_away;
    }

    public int getSec_stay() {
        return sec_stay;
    }

    public void setSec_stay(int sec_stay) {
        this.sec_stay = sec_stay;
    }

    public int getSec_disarm() {
        return sec_disarm;
    }

    public void setSec_disarm(int sec_disarm) {
        this.sec_disarm = sec_disarm;
    }

    public String getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(String camera_id) {
        this.camera_id = camera_id;
    }

    public List<AutoData> getAuto_list() {
        return auto_list;
    }

    public void setAuto_list(List<AutoData> auto_list) {
        this.auto_list = auto_list;
    }

    public List<DeviceStatus> getStatus() {
        return status;
    }

    public void setStatus(List<DeviceStatus> status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        return dev_id == device.dev_id;

    }

    @Override
    public int hashCode() {
        return dev_id;
    }

    @Override
    public String toString() {
        return "Device{" +
                "dev_id=" + dev_id +
                ", table_id=" + table_id +
                ", dev_type='" + dev_type + '\'' +
                ", mode=" + mode +
                ", name='" + name + '\'' +
                ", update_time=" + update_time +
                ", status=" + status +
                ", room_id=" + room_id +
                ", sec_away=" + sec_away +
                ", sec_stay=" + sec_stay +
                ", sec_disarm=" + sec_disarm +
                ", camera_id='" + camera_id + '\'' +
                ", auto_list=" + auto_list +
                '}';
    }
}
