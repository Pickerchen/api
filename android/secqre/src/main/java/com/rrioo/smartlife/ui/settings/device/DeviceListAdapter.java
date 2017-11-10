package com.rrioo.smartlife.ui.settings.device;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.helper.SimpleItemDecoration;
import com.rrioo.smartlife.util.DeviceHelper;
import com.rrioo.smartlife.util.SystemUtil;
import com.sen5.lib.api.Api;
import com.sen5.lib.entity.device.Device;
import com.sen5.lib.entity.room.Room;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/13 15:55 1.0
 * @time 2017/10/13 15:55
 * @project secQreNew3.0 com.rrioo.smartlife.ui.settings.device_room
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/13 15:55
 */

public class DeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DeviceRoomBean> mBeans;
    private Context mContext;
    private OnDeviceListListener onDeviceListListener;

    public OnDeviceListListener getOnDeviceListListener() {
        return onDeviceListListener;
    }

    public void setOnDeviceListListener(OnDeviceListListener onDeviceListListener) {
        this.onDeviceListListener = onDeviceListListener;
    }

    public DeviceListAdapter(List<DeviceRoomBean> mBeans, Context mContext) {
        this.mBeans = mBeans;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DeviceRoomBean.ROOM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_setting_room, null);
            return new RoomHolder(view);
        }
        return new DeviceHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_setting_device, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == DeviceRoomBean.ROOM) {
            bindRoom((RoomHolder) holder, position);
        }
        if (viewType == DeviceRoomBean.DEVICE) {
            bindDevice((DeviceHolder) holder, position);
        }
    }

    private void bindDevice(DeviceHolder holder, int position) {
        DeviceRoomBean bean = mBeans.get(position);
        Device device = (Device) bean.getData();
        holder.nameTv.setText(DeviceHelper.getDeviceName(device));
        holder.roomTv.setText(DeviceHelper.getRoomName(device.getRoom_id()));
        holder.iconIv.setImageResource(DeviceHelper.getStatusDrawable(device.getDev_type(), 1));
    }

    private void bindRoom(RoomHolder holder, int position) {
        DeviceRoomBean roomBean = mBeans.get(position);
        Room room = (Room) roomBean.data;
        holder.roomTv.setText(DeviceHelper.getRoomName(room));
    }

    @Override
    public int getItemViewType(int position) {
        DeviceRoomBean deviceRoomBean = mBeans.get(position);
        return deviceRoomBean.getType();
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    private GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            DeviceRoomBean bean = mBeans.get(position);
            if (bean.getType() == DeviceRoomBean.DEVICE) return 1;
            return 3;
        }
    };

    public List<DeviceRoomBean> getData() {
        return mBeans;
    }

    class DeviceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_home_device_iv)
        ImageView iconIv;

        @BindView(R.id.id_home_device_room_tv)
        TextView roomTv;

        @BindView(R.id.id_home_device_name_tv)
        TextView nameTv;

        public DeviceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class RoomHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_adapter_setting_room_tv)
        TextView roomTv;

        public RoomHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void attachToRecycleView(RecyclerView recyclerView) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        manager.setSpanSizeLookup(spanSizeLookup);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(this);
        recyclerView.addItemDecoration(new SimpleItemDecoration(mContext.getResources()
                .getDimensionPixelSize(R.dimen.dp5)));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                DeviceRoomBean bean = mBeans.get(position);
                if (bean.getType() == DeviceRoomBean.DEVICE) {
                    return makeMovementFlags(ItemTouchHelper.UP
                            | ItemTouchHelper.DOWN
                            | ItemTouchHelper.LEFT
                            | ItemTouchHelper.RIGHT, 0);
                }
                return 0;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                if (to == 0) return false;
                if (from < to) {
                    for (int i = from; i < to; i++) {
                        Collections.swap(mBeans, i, i + 1);

                    }
                }
                if (to < from) {
                    for (int i = from; i > to; i--) {
                        Collections.swap(mBeans, i, i - 1);
                    }
                }
                notifyItemMoved(from, to);
                if (onDeviceListListener != null) {
                    DeviceRoomBean bean = mBeans.get(from);
                    if (bean.getType() == DeviceRoomBean.DEVICE) {
                        Object data = bean.getData();
                        if (data instanceof Device) {
                            onDeviceListListener.onDeviceEdit((Device) data);
                        }
                    }
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    SystemUtil.vibrate();
                    if (onDeviceListListener != null) {
                        onDeviceListListener.onStartEdit();
                    }
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    public interface OnDeviceListListener {
        void onStartEdit();

        void onDeviceEdit(Device device);
    }
}
