package com.sen5.lib.connection;

import android.util.Log;

import com.sen5.lib.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by zhurongkun on 2017/8/28.
 */

public class SocketConnection extends AbstractConnection {
    private static final int TIMEOUT = 10 * 1000;
    private volatile String mHost;
    private volatile int mPort;
    private volatile Socket mSocket;
    private volatile InputStream mIn;
    private volatile OutputStream mOut;


    @Override
    public void setParams(ConnectionParameter args) {
        if (args instanceof SocketParameter) {
            SocketParameter parameter = (SocketParameter) args;
            mHost = parameter.getHost();
            mPort = parameter.getPort();
        } else {
            throw new ClassCastException("need SocketParameter.class but " + args.getClass());
        }
    }

    @Override
    public int connect() {
        LogUtil.i("connectSocket");
        try {
            mSocket = new Socket();
            mSocket.setKeepAlive(true);
            mSocket.connect(new InetSocketAddress(mHost, mPort), TIMEOUT);
            mIn = new BufferedInputStream(mSocket.getInputStream());
            mOut = new BufferedOutputStream(mSocket.getOutputStream());
        } catch (IOException e) {
            LogUtil.i("connectSocket " + e);
            e.printStackTrace();
            return ERROR;
        }
        return 1;
    }

    @Override
    public boolean interrupted() {
        return mSocket.isClosed();
    }

    @Override
    public int disConnect() {
        try {
            if (mSocket != null) {
                mSocket.shutdownInput();
                mSocket.shutdownOutput();
            }
            if (mIn != null) {
                mIn.close();
            }
            if (mOut != null) {
                mOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }
        return 1;

    }

    @Override
    public int read(byte[] buffer, int len) {
        if (mSocket != null && mIn != null && !mSocket.isInputShutdown()
                && mSocket.isConnected()) {
            try {
                if (mIn.available() > 0) {
                    int readCount = mIn.read(buffer, 0, len);
                    LogUtil.i("socket read " + readCount + " bytes");
                    return readCount;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogUtil.i("socket exception , shutdown or disconnect or input stream closed!");
        }
        return 0;
    }

    @Override
    public int write(byte[] buffer, int len) {
        try {
            if (mOut != null) {
                mOut.write(buffer, 0, len);
                mOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }
        return 1;
    }

    @Override
    public boolean isConnectionSucc(int session) {
        return session >= 0;
    }

}
