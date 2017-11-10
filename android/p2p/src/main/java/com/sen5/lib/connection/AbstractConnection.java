package com.sen5.lib.connection;

import com.sen5.lib.entity.constant.Constant;

/**
 * Created by zhurongkun on 2017/8/28.
 */

public abstract class AbstractConnection {
    public static final byte CHANNEL_WRITE = Constant.CHANNEL_WRITE;
    public static final byte CHANNEL_READ = Constant.CHANNEL_READ;
    public static final int ERROR = -1;

    public abstract void setParams(ConnectionParameter args);
    public abstract int connect();
    public abstract boolean interrupted();
    public abstract int disConnect();
    public abstract int read(byte[] buffer,int len);
    public abstract int write(byte[] buffer,int len);
    public abstract boolean isConnectionSucc(int session);
}
