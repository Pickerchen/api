package com.rrioo.smartlife.ui.main.adapter.camera;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chillingvan.canvasgl.glview.texture.GLViewRenderer;
import com.chillingvan.canvasgl.texture.MyGLTextureView;
import com.chillingvan.canvasgl.texture.MyTextureRender;
import com.rrioo.hsl.CameraConstantHSL;
import com.rrioo.hsl.CameraHSLPlayer;
import com.rrioo.hsl.CameraHslManager;
import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.R;
import com.rrioo.smartlife.camera.hsl.EventHsl;
import com.rrioo.smartlife.util.ClickHelper;
import com.rrioo.smartlife.util.DeviceHelper;
import com.rrioo.smartlife.util.FileUtils;
import com.rrioo.smartlife.util.SystemUtil;
import com.rrioo.smartlife.widget.CustomToast;
import com.sen5.lib.entity.device.Device;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/10 18:23 1.0
 * @time 2017/10/10 18:23
 * @project secQreNew3.0 com.rrioo.smartlife.ui.main.adapter.camera
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/10 18:23
 */

public class CameraHslAdapter extends RecyclerView.Adapter<CameraHslAdapter.ViewHolder> implements CameraListAdapter.AutoPlay, CameraListAdapter.Snapshot {
    public static final long SNAPSHOT_TIME_INTERVAL = 10 * 1000L;

    private CameraListAdapter mParentAdapter;
    private Context mContext;
    private List<Device> mCameraList;
    private Map<String, CameraHSLPlayer> mPlayers = new ArrayMap<>();

    public CameraHslAdapter(Context mContext, List<Device> mCameraList, CameraListAdapter cameraListAdapter) {
        this.mContext = mContext;
        this.mCameraList = mCameraList;
        this.mParentAdapter = cameraListAdapter;


        for (Device device : mCameraList) {
            if (DeviceHelper.isHSLCamera(device.getCamera_id())) {
                CameraHSLPlayer player = CameraHslManager.createPlayer(device.getCamera_id());
                mPlayers.put(device.getCamera_id(), player);
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        EventBus.getDefault().register(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_camera, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Device device = mCameraList.get(position);
        CameraHSLPlayer player = mPlayers.get(device.getCamera_id());

        if (player.getRender() == null) {
            GLViewRenderer renderer = holder.mSurface.getRenderer();
            if (renderer instanceof MyTextureRender) {
                player.setRender((MyTextureRender) renderer);
            }
        }

        bindByPlayStatus(player.getPlayStatus(), holder, player);
    }

    private void bindByPlayStatus(int playStatus, ViewHolder holder, CameraHSLPlayer player) {
        switch (player.getPlayStatus()) {
            case CameraHSLPlayer.UNINITICAL:
                showLoading(holder, player, false);
                showPlayButton(holder, player, true);
                showSnapshot(holder, player, true);
                break;
            case CameraHSLPlayer.LOADING:
                showLoading(holder, player, true);
                showPlayButton(holder, player, false);
                showSnapshot(holder, player, true);
                break;
            case CameraHSLPlayer.PLAYING:
                showLoading(holder, player, false);
                showPlayButton(holder, player, false);
                showSnapshot(holder, player, false);
                break;
            case CameraHSLPlayer.PAUSED:
                showLoading(holder, player, false);
                showPlayButton(holder, player, true);
                break;
        }
    }


    private void showPlayButton(ViewHolder holder, CameraHSLPlayer player, boolean show) {
        if (show) {
            holder.mPlayBtn.setVisibility(View.VISIBLE);
            holder.mPlayBtn.setAlpha(1f);
        } else {
            holder.mPlayBtn.setVisibility(View.VISIBLE);
            holder.mPlayBtn.setAlpha(0f);
        }
    }


    private void showSnapshot(ViewHolder holder, CameraHSLPlayer player, boolean show) {
        if (show) {
            holder.mSnapshotIv.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .setDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(
                            DiskCacheStrategy.NONE
                    ))
                    .load(FileUtils.getSnapshotFilePath(player.getDid()))
                    .into(holder.mSnapshotIv);
        } else {
            holder.mSnapshotIv.setVisibility(View.GONE);
        }
    }

    private void showLoading(ViewHolder holder, CameraHSLPlayer player, boolean show) {
        if (show) {
            holder.mLoadingPb.setVisibility(View.VISIBLE);
        } else {
            holder.mLoadingPb.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return mCameraList.size();
    }

    @Override
    public void autoPlay(String did) {
        CameraHSLPlayer player = mPlayers.get(did);
        if (player != null) {
            if (player.getPlayStatus() == CameraHSLPlayer.UNINITICAL) {
                initPlayer(player);
                itemChanged(did);
            }
        }
    }

    @Override
    public void snapshot(String did, String path) {
        CameraHSLPlayer player = mPlayers.get(did);
        if (player != null) {
            player.snapShot(path);
        }
    }


    private void play(final CameraHSLPlayer player) {
        player.play();
        player.setPlayStatus(CameraHSLPlayer.PLAYING);
        takeSnapShot(player);
    }

    private synchronized void takeSnapShot(CameraHSLPlayer player) {
        long snapshotTime = player.snapshotTime;
        if (System.currentTimeMillis() - snapshotTime < SNAPSHOT_TIME_INTERVAL) {
            //时间间隔内截图过了，不执行
            return;
        }
        snapshot(player.getDid(), FileUtils.getSnapshotFilePath(player.getDid()));
        player.snapshotTime = System.currentTimeMillis();
    }

    private void itemChanged(String did) {
        if (mParentAdapter != null) {
            mParentAdapter.itemChanged(did);
        }
    }

    private void initPlayer(final CameraHSLPlayer player) {
        player.initPlayer();
        player.setPlayStatus(CameraHSLPlayer.LOADING);
    }


    private void pause(CameraHSLPlayer player) {
        player.pause();
        player.setPlayStatus(CameraHSLPlayer.PAUSED);
    }

    private void closePlayer(CameraHSLPlayer player) {
        player.release();
        player.setPlayStatus(CameraHSLPlayer.UNINITICAL);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventHsl eventHsl) {
        MyLog.object(eventHsl);
        long playId = eventHsl.getPlayId();
        CameraHSLPlayer player = findPlayerByPlayId(playId);
        switch ((int) eventHsl.getStatus()) {
            case CameraConstantHSL.RESULT_CONNECT_SUCC://连接成功 100
                if (player != null) {
                    play(player);
                    itemChanged(player.getDid());
                }
                break;
            case CameraConstantHSL.RESULT_CONNECTING://连接中 0
                break;
            case CameraConstantHSL.RESULT_CAMERA_OFFLINE://离线
                CustomToast.makeText(mContext, R.string.camera_offline).show();
                if (player != null) {
                    closePlayer(player);
                    itemChanged(player.getDid());
                }
                break;
            case CameraConstantHSL.RESULT_CONNECT_ERR://连接错误
            case CameraConstantHSL.RESULT_CONNECT_INTERRUPT://中断
            case CameraConstantHSL.RESULT_CONNECT_TIMEOUT://超时
            case CameraConstantHSL.RESULT_INVALID_ID_ERR://id错误
            case CameraConstantHSL.RESULT_USER_PWD_ERR://密码错误
                CustomToast.makeText(mContext, R.string.connection_timeout).show();
                if (player != null) {
                    closePlayer(player);
                    itemChanged(player.getDid());
                }
                break;
        }
    }


    private CameraHSLPlayer findPlayerByPlayId(long playId) {
        Collection<CameraHSLPlayer> values = mPlayers.values();
        for (CameraHSLPlayer player : values) {
            if (player.getPlayerId() == playId) {
                return player;
            }
        }
        return null;
    }


    private void setClickListeners(final ViewHolder viewHolder) {

        viewHolder.mPlayBtn.setOnClickListener(new ClickHelper.ClickListener() {
            @Override
            public void onClickView(View v) {
                int position = viewHolder.getAdapterPosition();
                Device device = mCameraList.get(position);
                final CameraHSLPlayer player = mPlayers.get(device.getCamera_id());
                if (device == null || player == null)
                    return;
                int playStatus = player.getPlayStatus();
                switch (playStatus) {
                    case CameraHSLPlayer.PLAYING:
                        pause(player);
                        bindByPlayStatus(player.getPlayStatus(), viewHolder, player);
                        break;
                    case CameraHSLPlayer.UNINITICAL:
                        SystemUtil.checkMobileNetThen(mContext, new Runnable() {
                            @Override
                            public void run() {
                                initPlayer(player);
                                bindByPlayStatus(player.getPlayStatus(), viewHolder, player);
                            }
                        });

                        break;
                    case CameraHSLPlayer.LOADING:
                        break;
                    case CameraHSLPlayer.PAUSED:
                        SystemUtil.checkMobileNetThen(mContext, new Runnable() {
                            @Override
                            public void run() {
                                play(player);
                                bindByPlayStatus(player.getPlayStatus(), viewHolder, player);
                            }
                        });
                        break;
                }

            }
        });

        viewHolder.mFullScrenIv.setOnClickListener(new ClickHelper.ClickListener() {
            @Override
            public void onClickView(View v) {

            }
        });
    }


    class ViewHolder extends CameraViewHolder {
        MyGLTextureView mSurface;

        public ViewHolder(View itemView) {
            super(itemView);
            mContainerFl.removeAllViews();
            mSurface = new MyGLTextureView(itemView.getContext());
            ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            mSurface.setLayoutParams(params);
            MyTextureRender render = new MyTextureRender();
            mSurface.setRenderer(render);
            mContainerFl.addView(mSurface);
            setClickListeners(this);
        }
    }
}
