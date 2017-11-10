package com.rrioo.smartlife.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.base.BaseFragment;
import com.rrioo.smartlife.data.ApiCache;
import com.rrioo.smartlife.helper.SimpleItemDecoration;
import com.rrioo.smartlife.ui.main.adapter.camera.CameraHslAdapter;
import com.rrioo.smartlife.ui.main.adapter.camera.CameraListAdapter;
import com.rrioo.smartlife.util.DisplayUtil;
import com.rrioo.smartlife.util.NullUtil;
import com.sen5.lib.api.event.device.EventDevice;
import com.sen5.lib.api.event.device.EventDeviceAdd;
import com.sen5.lib.api.event.device.EventDeviceDelete;
import com.sen5.lib.api.event.device.EventDeviceList;
import com.sen5.lib.entity.constant.Constant;
import com.sen5.lib.entity.device.Device;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/10 16:25 1.0
 * @time 2017/10/10 16:25
 * @project secQreNew3.0 com.rrioo.smartlife.ui.camera
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/10 16:25
 */

public class CameraFragment extends BaseFragment {

    @BindView(R.id.id_camera_tv)
    TextView mCameraTv;

    @BindView(R.id.id_camera_rv)
    RecyclerView mCameraRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camera, null);
        ButterKnife.bind(this, root);
        showEmpty(true);
        mCameraRv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mCameraRv.addItemDecoration(new SimpleItemDecoration(0, getContext()
                .getResources().getDimensionPixelSize(R.dimen.dp8)));
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventDevice eventDevice) {
        if (eventDevice instanceof EventDeviceAdd
                || eventDevice instanceof EventDeviceDelete
                || eventDevice instanceof EventDeviceList) {
            List<Device> cameras = ApiCache.get().getCameras();
            if (NullUtil.isEmpty(cameras)) {
                showEmpty(true);
            } else {
                showEmpty(false);
                initAdapter(cameras);
            }
        }
    }

    private void initAdapter(List<Device> cameras) {
        mCameraRv.setAdapter(new CameraListAdapter(getContext(),cameras));
    }

    private void showEmpty(boolean show) {
        if (show) {
            mCameraRv.setVisibility(View.GONE);
            mCameraTv.setVisibility(View.VISIBLE);
        } else {
            mCameraRv.setVisibility(View.VISIBLE);
            mCameraTv.setVisibility(View.GONE);
        }
    }
}
