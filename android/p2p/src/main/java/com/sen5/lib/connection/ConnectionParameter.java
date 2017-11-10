package com.sen5.lib.connection;

import java.io.Serializable;

/**
 * Created by zhurongkun on 2017/8/28.
 */

public abstract class ConnectionParameter implements Serializable{
    public abstract ConnectionType type();
}
