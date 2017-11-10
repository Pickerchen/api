package com.sen5.lib.entity;

import java.util.Arrays;

/**
 * Created by zhurongkun on 2017/8/18.
 */

public class DataPacket {
    /**
     * 数据包前缀
     * 每个数据包都以##开头，后跟4字节标识数据实际大小（不包括前后缀及这4字节）
     * 最后以！！结尾，每个数据包如果不是这样的格式则说明数据传输有错误，需要进行错误处理
     * 以纠正数据的正确访问
     */
    public static final String PACKAGE_HEAD = "##";

    /**
     * 数据包后缀
     */
    public static final String PACKAGE_TAIL = "!!";

    public static final int HEADER_LENGTH = 2;
    public static final int TAIL_LENGTH = 2;
    public static final int DATA_SIZE_LENGTH = 4;

    private static final int MAX_DATA_SIZE = 100 * 100;

    /**
     * 标识有效数据的大小，不含头尾
     * dataSize和dataSizeBytes同语意，int型和byte[4]数组
     */
    public int dataSize;

    /**
     * 整个包的大小，包含头尾
     */
    public int packetSize;

    public byte[] header;//"##"
    public byte[] dataSizeBytes;//4字节
    public byte[] data;//有效数据
    public byte[] tail;//"!!"

    public byte[] newHeader() {
        return header = clearArray(header, HEADER_LENGTH);
    }

    public byte[] newTail() {
        return tail = clearArray(tail, TAIL_LENGTH);
    }

    public byte[] newDataSizeBytes() {
        return dataSizeBytes = clearArray(dataSizeBytes, DATA_SIZE_LENGTH);
    }


    private byte[] clearArray(byte[] array, int length) {
        if (array == null) {
            array = new byte[length];
        } else {
            Arrays.fill(array, (byte) 0);
        }
        return array;
    }


    public int calcDataSize() {
        byte[] sizeBytes = this.dataSizeBytes;
        int len = 0;
        len |= (sizeBytes[0] & 0xFF) << 24;
        len |= (sizeBytes[1] & 0xFF) << 16;
        len |= (sizeBytes[2] & 0xFF) << 8;
        len |= sizeBytes[3] & 0xFF;
        return this.packetSize = len;
    }

    public byte[] newData(int size) {
        return this.data = new byte[size > MAX_DATA_SIZE ? MAX_DATA_SIZE : size];
    }

    public boolean checkHeaderLegal() {
        if (header == null || header.length < 2)
            return false;
        byte headerByte = '#';
        return header[0] == headerByte && header[1] == headerByte;
    }

    public boolean checkTailLegal() {
        if (tail == null || tail.length < 2)
            return false;
        byte tailByte = '!';
        return tail[0] == tailByte && tail[1] == tailByte;
    }

    /**
     * 释放数据包资源
     */
    public void recycle() {
        header = null;
        dataSizeBytes = null;
        data = null;
        tail = null;
    }

    @Override
    public String toString() {
        return "DataPacket{" +
                "dataSize=" + dataSize +
                ", packetSize=" + packetSize +
                ", header=" + Arrays.toString(header) +
                ", dataSizeBytes=" + Arrays.toString(dataSizeBytes) +
                ", data=" + Arrays.toString(data) +
                ", tail=" + Arrays.toString(tail) +
                '}';
    }
}
