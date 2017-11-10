package com.rrioo.smartlife.ui.main.adapter.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.R;
import com.rrioo.smartlife.util.ClickHelper;
import com.rrioo.smartlife.util.DeviceHelper;
import com.rrioo.smartlife.util.FileUtils;
import com.rrioo.smartlife.util.SystemUtil;
import com.sen5.lib.entity.device.Device;
import com.sen5.libcameralt.CameraLTManager;
import com.sen5.libcameralt.CameraLTPlayer;
import com.sen5.libcameralt.SimpleGlnkDataSourceListener;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import glnk.media.AView;
import glnk.media.GlnkPlayer;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/11 15:24 1.0
 * @time 2017/10/11 15:24
 * @project secQreNew3.0 com.rrioo.smartlife.ui.main.adapter.camera
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/11 15:24
 */

public class CameraLtAdapter extends RecyclerView.Adapter<CameraLtAdapter.ViewHolder> implements CameraListAdapter.AutoPlay, CameraListAdapter.Snapshot {

    private Context mContext;
    private List<Device> mCameraList;
    private CameraListAdapter mParentAdapter;
    private Map<String, CameraLTPlayer> mPlayers;

    public CameraLtAdapter(Context mContext, List<Device> mCameraList, CameraListAdapter mParentAdapter) {
        this.mContext = mContext;
        this.mCameraList = mCameraList;
        this.mParentAdapter = mParentAdapter;
        mPlayers = new ArrayMap<>();
        for (Device device : mCameraList) {
            String did = device.getCamera_id();
            if (DeviceHelper.isLTCamera(did)) {
                CameraLTManager.preloadCamera(did);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_camera, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Device device = mCameraList.get(position);
        createPlayerIfNeed(holder, device.getCamera_id());
        final CameraLTPlayer player = mPlayers.get(device.getCamera_id());
        bindByPlayStatus(player.getPlayStatus(), holder, player);
        player.setDataSourceListener(new SimpleGlnkDataSourceListener() {
            @Override
            public void onConnecting() {
            }

            @Override
            public void onConnected(int i, String s, int i1) {
                player.setPlayStatus(CameraLTPlayer.PLAYING);
                bindByPlayStatus(player.getPlayStatus(), holder, player);
                String did = player.getDid();
                new SnapshotThread(FileUtils.getSnapshotFilePath(did)
                        , did, 2000).start();
            }

            @Override
            public void onDisconnected(int i) {
                MyLog.i("onDisconnected " + i);
            }
        });
    }

    private void bindByPlayStatus(int playStatus, ViewHolder holder, CameraLTPlayer player) {
        switch (player.getPlayStatus()) {
            case CameraLTPlayer.UNINITICAL:
                showLoading(holder, player, false);
                showPlayButton(holder, player, true);
                showSnapshot(holder, player, true);
                break;
            case CameraLTPlayer.LOADING:
                showLoading(holder, player, true);
                showPlayButton(holder, player, false);
                showSnapshot(holder, player, true);
                break;
            case CameraLTPlayer.PLAYING:
                showLoading(holder, player, false);
                showPlayButton(holder, player, false);
                showSnapshot(holder, player, false);
                break;
            case CameraLTPlayer.PAUSED:
                showLoading(holder, player, false);
                showPlayButton(holder, player, true);

                //因为浪涛sdk暂停后会黑屏，放缩略图
                showSnapshot(holder, player, true);
                break;
        }
    }


    private void showPlayButton(ViewHolder holder, CameraLTPlayer player, boolean show) {
        if (show) {
            holder.mPlayBtn.setVisibility(View.VISIBLE);
            holder.mPlayBtn.setAlpha(1f);
        } else {
            holder.mPlayBtn.setVisibility(View.VISIBLE);
            holder.mPlayBtn.setAlpha(0f);
        }
    }


    private void showSnapshot(ViewHolder holder, CameraLTPlayer player, boolean show) {
        if (show) {
            holder.mSnapshotIv.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .setDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(
                            DiskCacheStrategy.NONE
                    ).skipMemoryCache(true))
                    .load(FileUtils.getSnapshotFilePath(player.getDid()))
                    .into(holder.mSnapshotIv);
        } else {
            holder.mSnapshotIv.setVisibility(View.GONE);
        }
    }

    private void showLoading(ViewHolder holder, CameraLTPlayer player, boolean show) {
        if (show) {
            holder.mLoadingPb.setVisibility(View.VISIBLE);
        } else {
            holder.mLoadingPb.setVisibility(View.GONE);
        }
    }


    private void createPlayerIfNeed(final ViewHolder holder, String cameraId) {
        if (mPlayers.containsKey(cameraId)) {
            //this player has been created , return
            return;
        }
        final CameraLTPlayer player = CameraLTManager.newCameraPlayer(holder.mAView, cameraId);
        mPlayers.put(cameraId, player);
    }

    @Override
    public int getItemCount() {
        return mCameraList.size();
    }

    @Override
    public void autoPlay(String did) {
        CameraLTPlayer player = mPlayers.get(did);
        if (player != null) {
            play(player);
            itemChanged(did);
        }
    }

    private void itemChanged(String did) {
        if (mParentAdapter != null) {
            mParentAdapter.itemChanged(did);
        }
    }


    @Override
    public void snapshot(String did, String path) {
        CameraLTPlayer player = mPlayers.get(did);
        if (player == null) return;
        GlnkPlayer glnkPlayer = player.getPlayer();
        if (glnkPlayer == null) return;
        Bitmap frame = glnkPlayer.getFrame();
        if (frame != null) {
            try {
                FileUtils.saveSnapshot(frame, path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    class ViewHolder extends CameraViewHolder {
        private AView mAView;

        public ViewHolder(View itemView) {
            super(itemView);
            mContainerFl.removeAllViews();
            AView aView = new AView(itemView.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            aView.setLayoutParams(params);
            mContainerFl.addView(aView);
            mAView = aView;

            setClickListeners(this);
        }
    }

    private void setClickListeners(final ViewHolder holder) {
        holder.mPlayBtn.setOnClickListener(new ClickHelper.ClickListener() {
            @Override
            public void onClickView(View v) {
                int position = holder.getAdapterPosition();
                Device device = mCameraList.get(position);
                final CameraLTPlayer player = mPlayers.get(device.getCamera_id());
                switch (player.getPlayStatus()) {
                    case CameraLTPlayer.UNINITICAL:
                    case CameraLTPlayer.PAUSED:
                        SystemUtil.checkMobileNetThen(mContext, new Runnable() {
                            @Override
                            public void run() {
                                play(player);
                                bindByPlayStatus(player.getPlayStatus(), holder, player);
                            }
                        });

                        break;
                    case CameraLTPlayer.LOADING:
                        break;
                    case CameraLTPlayer.PLAYING:
                        showSnapshot(holder, player, true);
                        pause(player);
                        bindByPlayStatus(player.getPlayStatus(), holder, player);
                        break;
                }

            }
        });

        holder.mFullScrenIv.setOnClickListener(new ClickHelper.ClickListener() {
            @Override
            public void onClickView(View v) {

            }
        });
    }

    private void pause(CameraLTPlayer player) {
        player.stop();
        player.setPlayStatus(CameraLTPlayer.PAUSED);
    }


    private void play(final CameraLTPlayer player) {
        player.start();
        player.setPlayStatus(CameraLTPlayer.LOADING);
    }

    private class SnapshotThread implements Runnable {

        private final long delay;
        private final String did;
        private ExecutorService threadPool;

        String path;
        private Handler snapshotHandler;

        public SnapshotThread(String path, String did, long delay) {
            this.path = path;
            this.did = did;
            this.delay = delay;
            threadPool = Executors.newSingleThreadExecutor();
            HandlerThread ht = new HandlerThread(SnapshotThread.class.getName());
            ht.start();
            snapshotHandler = new Handler(ht.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    handle(msg);
                }
            };
        }

        private void handle(Message msg) {
            threadPool.execute(this);
        }

        public void start() {
            snapshotHandler.sendEmptyMessageDelayed(0, delay);
        }

        @Override
        public void run() {
            takeSnapShot(did, path);
        }

        private void takeSnapShot(String did, String path) {
            CameraLTPlayer player = mPlayers.get(did);
            if (player == null) return;
            synchronized (player) {
                long time = player.snapshotTime;
                if (System.currentTimeMillis() - time < CameraHslAdapter.SNAPSHOT_TIME_INTERVAL) {
                    return;
                }
                snapshot(did, path);
                player.snapshotTime = System.currentTimeMillis();
            }
        }
    }


}
