package com.sen5.lib.throwable;

/**
 * Created by zhurongkun on 2017/8/18.
 */

public class SdkReadPacketException extends Exception {
    public int code;

    public SdkReadPacketException(int code) {
        super("reading data error occurs "+String.valueOf(code));
        this.code = code;
    }
}
