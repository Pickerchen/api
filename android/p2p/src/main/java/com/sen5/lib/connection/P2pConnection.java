package com.sen5.lib.connection;

import com.p2p.pppp_api.PPCS_APIs;
import com.sen5.lib.api.encrypt.DID;
import com.sen5.lib.entity.constant.Constant;
import com.sen5.lib.util.LogUtil;

/**
 * Created by zhurongkun on 2017/8/28.
 */

public class P2pConnection extends AbstractConnection {


    private String mDid;
    private int mSession;

    @Override
    public void setParams(ConnectionParameter args) {
        if (args instanceof P2pParameter) {
            P2pParameter p2pParameter = (P2pParameter) args;
            mDid = DID.getDID(p2pParameter.getBoxId());
            LogUtil.i("did:" + mDid);
        } else {
            throw new ClassCastException("need P2pParameter.class but " + args.getClass());
        }
    }

    @Override
    public int connect() {
        int code = -1;

        if (mDid.contains(Constant.DID_SLIFE)) {
            code = PPCS_APIs.PPCS_Initialize(Constant.P2P_PARAM_SLIFE.getBytes());
        } else {
            code = PPCS_APIs.PPCS_Initialize(Constant.P2P_PARAM_DEFAULT.getBytes());
        }
        LogUtil.i("PPCS_Initialize " + code);

        mSession = PPCS_APIs.PPCS_Connect(mDid, (byte) 1, 0);
        LogUtil.i("PPCS_Connect session=" + mSession);
        return mSession;
    }

    @Override
    public boolean interrupted() {
        return false;
    }

    @Override
    public int disConnect() {
        PPCS_APIs.PPCS_Connect_Break();
        PPCS_APIs.PPCS_DeInitialize();
        return 0;
    }

    @Override
    public int read(byte[] buffer, int length) {
        int[] len = new int[1];
        len[0] = length;
        return PPCS_APIs.PPCS_Read(mSession, Constant.CHANNEL_READ, buffer, len, Constant.TIME_OUT);

    }

    @Override
    public int write(byte[] buffer, int len) {
        return PPCS_APIs.PPCS_Write(mSession, CHANNEL_WRITE, buffer, len);
    }

    @Override
    public boolean isConnectionSucc(int session) {
        return session >= 0;
    }

}
