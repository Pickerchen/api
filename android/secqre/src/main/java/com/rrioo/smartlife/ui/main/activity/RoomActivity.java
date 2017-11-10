package com.rrioo.smartlife.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.R;
import com.rrioo.smartlife.base.BaseActivity;
import com.rrioo.smartlife.data.ApiCache;
import com.rrioo.smartlife.helper.SimpleItemDecoration;
import com.rrioo.smartlife.qclCopy.BlurBehind;
import com.rrioo.smartlife.ui.main.adapter.home.HomeDeviceAdapter;
import com.rrioo.smartlife.util.DeviceHelper;
import com.rrioo.smartlife.util.NullUtil;
import com.sen5.lib.api.Api;
import com.sen5.lib.api.event.device.EventDevice;
import com.sen5.lib.entity.constant.Constant;
import com.sen5.lib.entity.device.Device;
import com.sen5.lib.entity.device.DeviceStatus;
import com.sen5.lib.entity.room.Room;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/10 14:05 1.0
 * @time 2017/10/10 14:05
 * @project secQreNew3.0 com.rrioo.smartlife.ui.main.activity
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/10 14:05
 */
public class RoomActivity extends BaseActivity implements HomeDeviceAdapter.OnDeviceItemClickListener {
    @BindView(R.id.id_room_name_tv)
    TextView mNameTv;

    @BindView(R.id.id_room_device_rv)
    RecyclerView mDevicesRv;


    private Room mRoom;
    private List<Device> mDevices;
    private Point mPoint;

    public static void startActivity(Activity from, int roomId, Point pos) {
        Intent intent = new Intent(from, RoomActivity.class);
        intent.putExtra("roomId", roomId);
        intent.putExtra("point",pos);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        from.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        EventBus.getDefault().register(this);
        BlurBehind.getInstance().withAlpha(70)
                .setBackground(this);
        ButterKnife.bind(this);
        initData();
        initView();

        initAnimation();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initAnimation() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventDevice eventDevice){
        RecyclerView.Adapter adapter = mDevicesRv.getAdapter();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
        MyLog.object(eventDevice);
    }

    private void initView() {
        mNameTv.setText(DeviceHelper.getRoomName(mRoom.getRoom_id()));

        mDevicesRv.setLayoutManager(new GridLayoutManager(this,3));
        mDevicesRv.addItemDecoration(new SimpleItemDecoration(getResources().getDimensionPixelSize(R.dimen.dp3)));

        HomeDeviceAdapter adapter = new HomeDeviceAdapter(mDevices, this);
        mDevicesRv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void initData() {
        int roomID = getIntent().getIntExtra("roomId", -1);
        Room room = ApiCache.get().findRoomById(roomID);
        if (room == null) {
            throw new IllegalArgumentException("no room find with id " + roomID);
        }
        mPoint = getIntent().getParcelableExtra("point");
        MyLog.object(mPoint);

        mRoom = room;
        mDevices = ApiCache.get().getRoomDevices(roomID);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }

    @Override
    public void onDeviceItemClick(int position, Device device) {
        if (DeviceHelper.isActionDevice(device.getDev_type())
                && !device.getDev_type().equals(Constant.ZLL_ACTION_THERMOSTAT)) {
            List<DeviceStatus> statusList = device.getStatus();
            if (NullUtil.isEmpty(statusList)) return;
            DeviceStatus onOffStatus = findOnOffStatus(statusList);
            if (onOffStatus == null) {
                Api.get().getSender().controlDevice(device.getDev_id(),
                        Constant.STATUS_ID_ON_OFF, new byte[]{Constant.ACTION_ON});
            } else {
                int curStatus = onOffStatus.getParams()[0];
                if (curStatus == Constant.ACTION_ON)
                    curStatus = Constant.ACTION_OFF;
                else if (curStatus == Constant.ACTION_OFF)
                    curStatus = Constant.ACTION_ON;

                Api.get().getSender().controlDevice(device.getDev_id(),
                        Constant.STATUS_ID_ON_OFF, new byte[]{(byte) curStatus});
            }
        }
    }

    private DeviceStatus findOnOffStatus(List<DeviceStatus> statusList) {
        for (DeviceStatus status : statusList) {
            if (status.getId() == Constant.STATUS_ID_ON_OFF)
                return status;
        }
        return null;
    }

    @OnClick(R.id.id_room_layout)
    public void onClickLayout(){
        finish();
    }
}
