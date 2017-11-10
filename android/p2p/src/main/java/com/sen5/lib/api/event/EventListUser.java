package com.sen5.lib.api.event;

import com.sen5.lib.entity.member.Member;

import java.util.List;

/**
 * Created by zhurongkun on 2017/8/24.
 */

public class EventListUser extends BaseEvent{
    private List<Member> users;

    public List<Member> getUsers() {
        return users;
    }

    public void setUsers(List<Member> users) {
        this.users = users;
    }
}
