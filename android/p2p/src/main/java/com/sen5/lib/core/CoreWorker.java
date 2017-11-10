package com.sen5.lib.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import com.sen5.lib.connection.AbstractConnection;
import com.sen5.lib.connection.ConnectionParameter;
import com.sen5.lib.connection.ConnectionType;
import com.sen5.lib.connection.P2pConnection;
import com.sen5.lib.connection.SocketConnection;
import com.sen5.lib.entity.DataPacket;
import com.sen5.lib.entity.constant.Constant;
import com.sen5.lib.throwable.SdkReadPacketException;
import com.sen5.lib.util.JsonUtil;
import com.sen5.lib.util.LogUtil;
import com.sen5.lib.util.PacketUtil;
import com.sen5.lib.util.TimeUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by zhurongkun on 2017/8/22.
 * {@link CoreService}
 * 处理实际任务的线程
 * 发送命令，读取服务端返回的数据，处理数据错误
 */

class CoreWorker {
    private static final byte CHANNEL_WRITE = Constant.CHANNEL_WRITE;
    private static final byte CHANNEL_READ = Constant.CHANNEL_READ;

    //线程睡眠时间
    private static final long SLEEP_TIMEOUT = 300L;
    //心跳间隔
    private static final long HEARTBEAT_DURATION = 20 * 1000L;
    private static final long CONNECT_AGAIN_INTERVAL = 2000L;

    private CoreService mService;
    private ExecutorService mThreadPool;
    private volatile AtomicBoolean mAlive;
    private volatile ArrayBlockingQueue<Task> mTaskQueue;

    private volatile String mDid;
    private volatile int mSession;
    private AbstractConnection mConnection;
    private Handler handler;

    public CoreWorker(CoreService coreService) {
        mThreadPool = Executors.newCachedThreadPool();
        mAlive = new AtomicBoolean(false);
        mTaskQueue = new ArrayBlockingQueue<Task>(50);
        this.mService = coreService;
        HandlerThread ht = new HandlerThread(getClass().getSimpleName());
        ht.start();
        handler = new Handler(ht.getLooper());
    }

    public void start() {
        if (mAlive.get()) {
            throw new IllegalStateException(getClass().getName() + " attempt to start a thread already started!");
        }
        if (mThreadPool != null) {
            mAlive.set(true);
            mThreadPool.execute(new ReaderWorker());
            mThreadPool.execute(new TaskWorker());
        }
    }

    public void stop() {
        mAlive.set(false);
        if (mThreadPool != null) {
            try {
                mThreadPool.awaitTermination(10 * 1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


//    @Override
//    public void run() {
//        LogUtil.i(getClass().getName() + " running ");
//        while (mAlive.get()) {
//            LogUtil.v("background worker running");
//            Task task = mTaskQueue.poll();
//            if (task != null) {
//                handleTask(task);
//                task.recycle();
//            }
//            if (mConnection != null && !mConnection.interrupted())
//                excuteReading();
//        }
//    }

    private void excuteReading() throws SdkReadPacketException {
        DataPacket packet = null;
        packet = readRemoteResponse();
        if (packet == null)
            return;
        String json = new String(packet.data);
        int msgType = JsonUtil.getElementInt(json, "msg_type");
        LogUtil.i("receive json:\n");
        LogUtil.json(json);
        mService.sendToLocal(makeBundle(msgType, json));
        packet.recycle();
    }

    private Bundle makeBundle(int msgType, String json) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.MSG_TYPE, msgType);
        bundle.putString(Constant.MSG_BODY, json);
        return bundle;
    }

    private void handleTask(Task task) {
        Intent intent = task.intent;
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                int msgType = extras.getInt(Constant.MSG_TYPE, 0);
                switch (msgType) {
                    case Constant.MSG_CONNECT:
                        connect(extras);
                        break;
                    case Constant.MSG_DISCONNECT:
                        disConncet();
                        break;
                    default:
                        write(extras);
                        break;
                }
            }
        }
    }

    private void disConncet() {
        if (mConnection != null) {
            mConnection.disConnect();
        }
    }

    private void write(Bundle extras) {
        String json = extras.getString(Constant.MSG_BODY, null);
        LogUtil.i("send json :\n");
        LogUtil.json(json);
        byte[] packet = PacketUtil.makePacket(json.getBytes());
        int code = mConnection.write(packet, packet.length);

        //old p2p write
//        String json = extras.getString(Constant.MSG_BODY, null);
//        LogUtil.i("handle task json= " + json);
//        byte[] packet = PacketUtil.makePacket(json.getBytes());
//        int code = PPCS_APIs.PPCS_Write(mSession, CHANNEL_WRITE, packet, packet.length);
//        LogUtil.i("PPCS_Write session=" + mSession + " result=" + code);
    }

    private void connect(Bundle bundle) {
        mConnection = getConnectionObject(bundle);

        int session = mConnection.connect();
        LogUtil.i("connect return " + session);

        Bundle args = new Bundle();
        args.putInt(Constant.MSG_TYPE, Constant.MSG_CONNECT);
        args.putInt("session", session);
        mService.sendToLocal(args);

        if (!mConnection.isConnectionSucc(session)) {
            connectAgain(bundle);
        }

        //old p2p connect way
//        String msgBody = bundle.getString(Constant.MSG_BODY, null);
//        if (msgBody == null || TextUtils.isEmpty(msgBody)) {
//            LogUtil.e("msg body is null");
//            return;
//        }
//        mDid = DID.getDID(msgBody);
//        LogUtil.i("did " + mDid);
//        int code = -1;
//
//        if (Constant.DID_SLIFE.equalsIgnoreCase(mDid)) {
//            code = PPCS_APIs.PPCS_Initialize(Constant.P2P_PARAM_SLIFE.getBytes());
//        } else {
//            code = PPCS_APIs.PPCS_Initialize(Constant.P2P_PARAM_DEFAULT.getBytes());
//        }
//        LogUtil.i("PPCS_Initialize " + code);
//
//        mSession = PPCS_APIs.PPCS_Connect(mDid, (byte) 1, 0);
//        LogUtil.i("PPCS_Connect session=" + mSession);
//
//        Bundle args = new Bundle();
//        args.putInt(Constant.MSG_TYPE, Constant.MSG_CONNECT);
//        args.putInt("session", mSession);
//        mService.sendToLocal(args);
    }

    private void connectAgain(final Bundle bundle) {
        LogUtil.i("failed ! connect again ");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                connect(bundle);
            }
        }, CONNECT_AGAIN_INTERVAL);
    }

    private AbstractConnection getConnectionObject(Bundle bundle) {
        ConnectionParameter parameter = (ConnectionParameter) bundle.getSerializable("parameter");
        if (parameter.type() == ConnectionType.P2P) {
            mConnection = new P2pConnection();
        } else if (parameter.type() == ConnectionType.SOCKET) {
            mConnection = new SocketConnection();
        }
        mConnection.setParams(parameter);
        return mConnection;
    }

    public void process(Intent intent, int flags, int startId) {
        if (!mAlive.get()) {
            start();
        }
        Task task = new Task(intent, flags, startId);
        mTaskQueue.offer(task);
        checkWorkerLife();
    }

    private void checkWorkerLife() {
    }

    /**
     * 清除脏数据
     * 一直读数据，直到遇到"!!"
     */
    private void handleResponseException() {
        LogUtil.d("reading error, meet dirty data");
        final byte STOP_FLAG = '!';

        byte[] cur = new byte[1];
        byte last = 0;

        final int RETRY_COUNT = 3;

        int retry = RETRY_COUNT;

        while (mAlive.get()) {
            cur[0] = 0;
            int i = read(cur, cur.length);

            //当返回值正确的时候，读取数据，
            // 如果碰到“!!”，则该段脏数据读取完毕。
            if (i > 0) {
                if (last == STOP_FLAG && cur[0] == STOP_FLAG) {
                    return;
                }

                //继续循环，保存上次的值
                last = cur[0];

                continue;
            } else {
                //错误，重试
                if (retry > 0) {
                    retry--;
                    continue;
                }


//                //重试次数用完
//                //这里简单的处理为清空缓冲区，一次性读取缓冲区最大值
//                int len = Byte.MAX_VALUE;
//                cur = new byte[len];
//                read(cur, len);
                return;
            }
        }

    }

    private synchronized DataPacket readRemoteResponse() throws SdkReadPacketException {
        DataPacket packet = new DataPacket();

        //read header
        int code = read(packet.newHeader(), DataPacket.HEADER_LENGTH);
        if (code < 0) {
            return null;
        }
        boolean check = packet.checkHeaderLegal();
        if (!check) {
            LogUtil.v("checkHeaderLegal error " + code);
            throw new SdkReadPacketException(-1);
//            return null;
        }

        //read size
        code = read(packet.newDataSizeBytes(), DataPacket.DATA_SIZE_LENGTH);
        if (code < 0) {
            LogUtil.v("read size error " + code);
            throw new SdkReadPacketException(code);
//            return null;
        }
        int size = packet.calcDataSize();
        if (size < 0) {
            LogUtil.v("data size error " + size);
            throw new SdkReadPacketException(code);
//            return null;
        }

        //read data
        packet.newData(size);
        code = read(packet.data, size);
        if (code < 0) {
            LogUtil.v("read data error " + code);
            throw new SdkReadPacketException(code);
//            return null;
        }

        //read tail
        code = read(packet.newTail(), DataPacket.TAIL_LENGTH);
        if (code < 0) {
            LogUtil.v("read tail error " + code);
            throw new SdkReadPacketException(code);
//            return null;
        }
        check = packet.checkTailLegal();
        if (!check) {
            LogUtil.v("check tail error " + check);
            throw new SdkReadPacketException(-1);
//            return null;
        }

        return packet;
    }


    private int read(byte[] bytes, int length) {
        int[] len = new int[1];
        len[0] = length;
        return mConnection.read(bytes, length);
//        return PPCS_APIs.PPCS_Read(mSession, Constant.CHANNEL_READ, bytes, len, Constant.TIME_OUT);
    }


    private static class Task {
        Intent intent;
        int flags;
        int startId;

        public Task(Intent intent, int flags, int startId) {
            this.intent = intent;
            this.flags = flags;
            this.startId = startId;
        }

        public void recycle() {
            intent = null;
        }
    }


    private class ReaderWorker implements Runnable {
        private long lastHb = System.currentTimeMillis();

        @Override
        public void run() {
            while (mAlive.get()) {
                lastHb = heartBeat("READ", lastHb);
                if (mConnection != null && !mConnection.interrupted()) {
                    try {
                        excuteReading();
                    } catch (SdkReadPacketException e) {
                        e.printStackTrace();
                        handleResponseException();
                        sleep(20L);
                    }
                } else {
                    if (mConnection == null) {
                        LogUtil.d("connection is null");
                    } else if (mConnection.interrupted()) {
                        LogUtil.d("connection interrupted");
                    }
                    sleep(SLEEP_TIMEOUT);
                }
            }
        }
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class TaskWorker implements Runnable {
        private long lastHeartBeat = System.currentTimeMillis();

        @Override
        public void run() {
            while (mAlive.get()) {
                lastHeartBeat = heartBeat("WRITE", lastHeartBeat);

                Task task = mTaskQueue.poll();
                if (task != null) {
                    handleTask(task);
                    task.recycle();
                } else {
                    sleep(20);
                }
            }
        }
    }

    private long heartBeat(String tag, long lastHeartBeat) {
        long hbNow = System.currentTimeMillis();
        if (hbNow - lastHeartBeat >= HEARTBEAT_DURATION) {
            LogUtil.d("THREAD ALIVE TESTING " + " HEART BEAT " + tag + " " + TimeUtil.formatTime(this.mService.getApplicationContext(), hbNow));
            return hbNow;
        }
        return lastHeartBeat;
    }


}
