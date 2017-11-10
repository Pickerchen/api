package com.sen5.lib.connection;

/**
 * Created by zhurongkun on 2017/8/28.
 */

public class P2pParameter extends ConnectionParameter {
    private String boxId;

    public P2pParameter(String boxId) {
        this.boxId = boxId;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    @Override
    public ConnectionType type() {
        return ConnectionType.P2P;
    }
}
