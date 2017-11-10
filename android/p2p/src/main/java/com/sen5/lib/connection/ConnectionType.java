package com.sen5.lib.connection;

/**
 * Created by zhurongkun on 2017/8/28.
 */

public enum ConnectionType {
    SOCKET(0),
    P2P(1);

    int value;

    ConnectionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ConnectionType parse(int value) {
        if (value == 0)
            return SOCKET;
        if (value == 1)
            return P2P;
        return null;
    }
}
