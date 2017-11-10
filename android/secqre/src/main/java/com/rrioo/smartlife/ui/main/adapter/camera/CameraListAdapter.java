package com.rrioo.smartlife.ui.main.adapter.camera;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.rrioo.smartlife.util.DeviceHelper;
import com.rrioo.smartlife.util.SystemUtil;
import com.sen5.lib.entity.device.Device;

import java.util.List;
import java.util.Map;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/11 11:58 1.0
 * @time 2017/10/11 11:58
 * @project secQreNew3.0 com.rrioo.smartlife.ui.main.adapter.camera
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/11 11:58
 */

public class CameraListAdapter extends RecyclerView.Adapter<CameraViewHolder> {
    private static final long AUTO_PLAY_INTERVAL = 1500L;
    private Context mContext;
    private List<Device> mCameraList;
    private CameraHslAdapter mHslAdapter;
    private CameraLtAdapter mLtAdapter;


    private Map<Integer, CameraViewHolder> mHolderCache;


    public CameraListAdapter(Context mContext, List<Device> mCameraList) {
        this.mContext = mContext;
        this.mCameraList = mCameraList;
        mHslAdapter = new CameraHslAdapter(mContext, mCameraList, this);
        mLtAdapter = new CameraLtAdapter(mContext, mCameraList, this);
        mHolderCache = new ArrayMap<>(mCameraList.size());
    }

    public void itemChanged(String did) {
        int position = -1;
        for (int i = 0; i < mCameraList.size(); i++) {
            Device device = mCameraList.get(i);
            if (device.getCamera_id().equals(did)) {
                position = i;
                break;
            }
        }

        if (position >= 0 && position < getItemCount()) {
            notifyItemChanged(position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (mHslAdapter!=null){
            mHslAdapter.onAttachedToRecyclerView(recyclerView);
        }
        if (mLtAdapter!=null){
            mLtAdapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mHslAdapter!=null){
            mHslAdapter.onDetachedFromRecyclerView(recyclerView);
        }
        if (mLtAdapter!=null){
            mLtAdapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    @Override
    public CameraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int position = viewType;
        if (mHolderCache.containsKey(position))
            return mHolderCache.get(position);
        final Device device = mCameraList.get(position);
        CameraViewHolder holder = null;
        if (DeviceHelper.isHSLCamera(device.getCamera_id())) {
            holder = mHslAdapter.onCreateViewHolder(parent, viewType);
        } else if (DeviceHelper.isLTCamera(device.getCamera_id())) {
            holder = mLtAdapter.onCreateViewHolder(parent, viewType);
        }

        autoPlay(parent,device);

        mHolderCache.put(position, holder);

        return holder;
    }

    private void autoPlay(View view, final Device device) {
        if (SystemUtil.canPlayVideo()) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (DeviceHelper.isHSLCamera(device.getCamera_id())) {
                        mHslAdapter.autoPlay(device.getCamera_id());
                    }
                    if (DeviceHelper.isLTCamera(device.getCamera_id())){
                        mLtAdapter.autoPlay(device.getCamera_id());
                    }
                }
            }, AUTO_PLAY_INTERVAL);
        }
    }

    @Override
    public void onBindViewHolder(CameraViewHolder holder, int position) {
        Device device = mCameraList.get(position);
        if (DeviceHelper.isHSLCamera(device.getCamera_id())) {
            mHslAdapter.onBindViewHolder((CameraHslAdapter.ViewHolder) holder, position);
        } else if (DeviceHelper.isLTCamera(device.getCamera_id())) {
            mLtAdapter.onBindViewHolder((CameraLtAdapter.ViewHolder) holder, position);
        }
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mCameraList.size();
    }

    public interface AutoPlay {
        void autoPlay(String did);
    }

    public interface Snapshot {
        void snapshot(String did, String path);
    }
}
