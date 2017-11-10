package com.sen5.lib.entity.room;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class Room {
    //默认房间id
    public static final int DEFAULT_ROOM_ID = 100;
    /**
     * room_id : 111
     * room_name : RoomA
     * update_time : 12343234，
     * dev_list : {dev_id:111, table_id:1}
     */

    private int room_id;
    private String room_name;
    private String update_time;
    private List<Integer> dev_list;

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<Integer> getDev_list() {
        return dev_list;
    }

    public void setDev_list(List<Integer> dev_list) {
        this.dev_list = dev_list;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return room_id == room.room_id;

    }

    @Override
    public int hashCode() {
        return room_id;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_id=" + room_id +
                ", room_name='" + room_name + '\'' +
                ", update_time='" + update_time + '\'' +
                ", dev_list=" + dev_list +
                '}';
    }
}
