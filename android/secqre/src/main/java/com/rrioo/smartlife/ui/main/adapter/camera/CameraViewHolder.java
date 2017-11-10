package com.rrioo.smartlife.ui.main.adapter.camera;

import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rrioo.smartlife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/10 18:32 1.0
 * @time 2017/10/10 18:32
 * @project secQreNew3.0 com.rrioo.smartlife.ui.main.adapter.camera
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/10 18:32
 */

public class CameraViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.id_camera_container_fl)
    FrameLayout mContainerFl;

    @BindView(R.id.id_camera_snapshot_iv)
    ImageView mSnapshotIv;

    @BindView(R.id.id_camera_play_btn)
    View mPlayBtn;

    @BindView(R.id.id_camera_loading_pb)
    View mLoadingPb;

    @BindView(R.id.id_camera_full_iv)
    View mFullScrenIv;

    public CameraViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            itemView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    Rect rect = new Rect(0,0,view.getWidth(),view.getHeight());
                    int round = view.getContext().getResources().getDimensionPixelSize(R.dimen.dp8);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        outline.setRoundRect(rect,round);
                    }
                }
            });
            itemView.setClipToOutline(true);
        }
    }

}
