package com.sen5.lib.api.event.room;

import com.sen5.lib.api.event.BaseEvent;
import com.sen5.lib.entity.room.Room;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class EventRoomList extends EventRoom {
    private List<Room> rooms;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "EventRoomList{" +
                "rooms=" + rooms +
                '}';
    }
}
