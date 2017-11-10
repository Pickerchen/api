package com.sen5.lib.connection;

/**
 * Created by zhurongkun on 2017/8/28.
 */

public class SocketParameter extends ConnectionParameter {
    private String host;
    private int port;

    public SocketParameter(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public ConnectionType type() {
        return ConnectionType.SOCKET;
    }
}
