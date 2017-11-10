package com.sen5.lib.util;

/**
 * Created by zhurongkun on 2017/8/18.
 */

public class PacketUtil {

    public static byte[] makePacket(byte[] realData){

        int dataLen = realData.length;

        int pkgLen = 2 + 4 + realData.length + 2;
        byte[] buffer = new byte[pkgLen];

        buffer[0] = buffer[1] = ('#');
        buffer[2] = (byte) ((dataLen  >>> 24 )&0xff);
        buffer[3] = (byte) ((dataLen  >>> 16 )&0xff);
        buffer[4] = (byte) ((dataLen  >>> 8 )&0xff);
        buffer[5] = (byte) ((dataLen & 0xff));

        System.arraycopy(realData, 0, buffer, 6, dataLen);
        buffer[pkgLen - 1] = buffer[pkgLen - 2] = '!';

        return buffer;
    }


    private PacketUtil(){}
}
