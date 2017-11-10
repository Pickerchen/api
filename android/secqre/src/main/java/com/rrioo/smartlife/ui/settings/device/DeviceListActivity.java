package com.rrioo.smartlife.ui.settings.device;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.R;
import com.rrioo.smartlife.base.BaseActivity;
import com.rrioo.smartlife.data.ApiCache;
import com.rrioo.smartlife.ui.settings.addDevice.AddDeviceActivity;
import com.rrioo.smartlife.util.NullUtil;
import com.rrioo.smartlife.widget.DeviceAndRoomDialog;
import com.sen5.lib.api.Api;
import com.sen5.lib.api.event.device.EventDevice;
import com.sen5.lib.api.event.device.EventDeviceList;
import com.sen5.lib.api.event.room.EventRoom;
import com.sen5.lib.entity.device.Device;
import com.sen5.lib.entity.room.Room;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/13 15:24 1.0
 * @time 2017/10/13 15:24
 * @project secQreNew3.0 com.rrioo.smartlife.ui.settings.device_room
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/13 15:24
 */

public class DeviceListActivity extends BaseActivity {

    @BindView(R.id.id_common_title_back)
    TextView mTitleTv;

    @BindView(R.id.id_activity_device_room_rv)
    RecyclerView mDeviceRoomRv;

    @BindView(R.id.id_common_title_add)
    ImageView mAddTv;

    @BindView(R.id.id_common_title_done)
    TextView mDoneTv;

    private List<Device> mDevices;
    private List<Room> mRooms;
    private List<DeviceRoomBean> mBeans;
    private List<Device> mEditDevices;
    private CountDownLatch mLoadingCountDownLatch;

    public static void startActivity(Context from) {
        from.startActivity(new Intent(from, DeviceListActivity.class));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_room);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
        mTitleTv.setText(R.string.device_and_room);
        mAddTv.setVisibility(View.VISIBLE);
        DeviceListAdapter adapter = new DeviceListAdapter(mBeans, this);
        adapter.attachToRecycleView(mDeviceRoomRv);
        adapter.setOnDeviceListListener(new DeviceListAdapter.OnDeviceListListener() {
            @Override
            public void onStartEdit() {
                mDoneTv.setVisibility(View.VISIBLE);
                mAddTv.setVisibility(View.GONE);
                mEditDevices = new ArrayList<Device>();
            }

            @Override
            public void onDeviceEdit(Device device) {
                mEditDevices.add(device);
            }
        });
    }

    private void initData() {
        mDevices = ApiCache.get().getSmartDevices();
        mRooms = ApiCache.get().getRooms();
        createBeans();
    }

    private void createBeans() {
        mBeans = new ArrayList<>();
        int position = 0;
        for (Room room : mRooms) {
            DeviceRoomBean bean = new DeviceRoomBean
                    (room, position++, DeviceRoomBean.ROOM);
            mBeans.add(bean);
            for (Device device : mDevices) {
                if (device.getRoom_id() == room.getRoom_id()) {
                    mBeans.add(new DeviceRoomBean(device, position++, DeviceRoomBean.DEVICE));
                }
            }
        }
    }

    @OnClick(R.id.id_common_title_back)
    public void clickBack() {
        finish();
    }

    @OnClick(R.id.id_common_title_add)
    public void clickAdd() {
        new DeviceAndRoomDialog(this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DeviceAndRoomDialog.CLICK_ITEM_ADD_DEVICE:
                        AddDeviceActivity.startActivity(DeviceListActivity.this);
                        break;
                    case DeviceAndRoomDialog.CLICK_ITEM_ADD_ROOM:

                        break;
                    case DeviceAndRoomDialog.CLICK_ITEM_EDIT_ROOM:

                        break;
                }
            }
        }).show();
    }

    @OnClick(R.id.id_common_title_done)
    public void clickDone() {
        if (NullUtil.isEmpty(mEditDevices)) {
            mDoneTv.setVisibility(View.GONE);
            mAddTv.setVisibility(View.VISIBLE);
            return;
        }

        RecyclerView.Adapter adapter = mDeviceRoomRv.getAdapter();
        if (adapter instanceof DeviceListAdapter) {
            DeviceListAdapter deviceListAdapter = (DeviceListAdapter) adapter;
            List<DeviceRoomBean> data = deviceListAdapter.getData();
            Iterator<DeviceRoomBean> iterator = data.iterator();
            List<Room> rooms = new ArrayList<>();
            while (iterator.hasNext()) {
                DeviceRoomBean bean = iterator.next();
                if (bean.getType() == DeviceRoomBean.ROOM) {
                    Room room = (Room) bean.getData();
                    room.setDev_list(new ArrayList<Integer>());
                    rooms.add(room);
                } else {
                    if (bean.getType() == DeviceRoomBean.DEVICE) {
                        Device device = (Device) bean.getData();
                        if (rooms.isEmpty())
                            throw new IllegalStateException("device room state error,device has no room");
                        Room room = rooms.get(rooms.size() - 1);
                        device.setRoom_id(room.getRoom_id());
                        List<Integer> dev_list = room.getDev_list();
                        dev_list.add(device.getDev_id());
                        room.setDev_list(dev_list);
                    }
                }
            }
            showLoading();

            for (Room room : rooms) {
                Api.get().getSender().editRoom(room);
            }

            Api.get().getSender().deviceList();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventRoom eventRoom) {
        MyLog.object(eventRoom);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventDeviceList eventDeviceList){
        cancelLoading();
        mDeviceRoomRv.getAdapter().notifyDataSetChanged();
    }
}
