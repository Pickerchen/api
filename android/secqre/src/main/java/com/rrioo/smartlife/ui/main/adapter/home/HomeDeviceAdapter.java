package com.rrioo.smartlife.ui.main.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.R;
import com.rrioo.smartlife.util.ClickHelper;
import com.rrioo.smartlife.util.DateTimeUtil;
import com.rrioo.smartlife.util.DeviceHelper;
import com.rrioo.smartlife.util.NullUtil;
import com.sen5.lib.entity.constant.Constant;
import com.sen5.lib.entity.device.Device;
import com.sen5.lib.entity.device.DeviceStatus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/30 16:31 1.0
 * @time 2017/9/30 16:31
 * @project secQreNew3.0 com.rrioo.smartlife.ui.main.adapter
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/30 16:31
 */

public class HomeDeviceAdapter extends RecyclerView.Adapter<HomeDeviceAdapter.ViewHolder> {
    private List<Device> mDevices;
    private Context mContext;
    private OnDeviceItemClickListener onItemClickListener;
    private OnDeleteListener onDeleteListener;
    /**
     * 控制设备
     */
    private int VIEW_TYPE_ACTION = 0;
    /**
     * 恒温器
     */
    private int VIEW_TYPE_THERMOSTAT = 1;
    /**
     * 温湿度传感器
     */
    private int VIEW_TYPE_TEMP_HUMIDITY = 2;
    /**
     * 普通传感器
     */
    private int VIEW_TYPE_SENSOR = 3;

    private static final float HALF_TRANSPARENT = 0.6f;
    private boolean mEdit = false;

    private boolean[] lowPowerList;

    public HomeDeviceAdapter(List<Device> mDevices, Context mContext) {
        this.mDevices = mDevices;
        this.mContext = mContext;
        lowPowerList = new boolean[mDevices.size()];
    }

    @Override
    public int getItemViewType(int position) {
        Device device = mDevices.get(position);
        String devType = device.getDev_type();
        if ((Constant.ZLL_ACTION_THERMOSTAT).equals(devType)) {         //恒温器
            return VIEW_TYPE_THERMOSTAT;
        } else if (!(Constant.ZLL_ACTION_THERMOSTAT).equals(devType)
                && DeviceHelper.isActionDevice(devType)) {           //控制设备
            return VIEW_TYPE_ACTION;
        } else if (DeviceHelper.isSensorHumidityAndTemp(devType)) {          //温湿度传感器
            return VIEW_TYPE_TEMP_HUMIDITY;
        } else {                                                       //一般传感器
            return VIEW_TYPE_SENSOR;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_home_device, null);
        if (viewType == VIEW_TYPE_ACTION) {
            return new ActionViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_THERMOSTAT) {
            return new ThermostatViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_TEMP_HUMIDITY) {
            return new TempHumidityViewHolder(itemView);
        }
        return new SensorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        handleCommonHolder(holder, position);
        if (viewType == VIEW_TYPE_ACTION) {
            ActionViewHolder actionViewHolder = (ActionViewHolder) holder;
            bindActionHolder(actionViewHolder, position);
        } else if (viewType == VIEW_TYPE_SENSOR) {
            SensorViewHolder sensorViewHolder = (SensorViewHolder) holder;
            bindSensorHolder(sensorViewHolder, position);
        } else if (viewType == VIEW_TYPE_TEMP_HUMIDITY) {
            TempHumidityViewHolder tempHumidityViewHolder = (TempHumidityViewHolder) holder;
            bindTempHumidityHolder(tempHumidityViewHolder, position);
        } else if (viewType == VIEW_TYPE_THERMOSTAT) {
            ThermostatViewHolder thermostatViewHolder = (ThermostatViewHolder) holder;
            bindThermostatHolder(thermostatViewHolder, position);
        }

        handleEdit(holder, position);
    }

    private void handleEdit(ViewHolder holder, int position) {
        if (mEdit) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_favorite_device_shake);
            holder.itemView.startAnimation(animation);
            holder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            holder.itemView.clearAnimation();
            holder.ivDelete.setVisibility(View.GONE);
        }
    }

    private void bindThermostatHolder(ThermostatViewHolder holder, int position) {
        Device device = mDevices.get(position);
        String devType = device.getDev_type();
        List<DeviceStatus> statusList = device.getStatus();
        if (NullUtil.isEmpty(statusList)) {
            handleOffline(holder, position);
        } else {
            for (DeviceStatus status : statusList) {
                if (status.getId() == Constant.ZB_STATUS_ID_LOCAL_TEMPERATURE) {
                    int t = getFromByteArray(status.getParams(), 0);
                    holder.tvTemp.setText("" + t);
                }
                if (status.getId() == Constant.ZB_STATUS_ID_SYSTEM_MODE) {
                    int mode = getFromByteArray(status.getParams(), 0);
                    switch (mode) {
                        case 0:
                            holder.tvStatus.setText(R.string.close);
                            break;
                        case 3:
                            holder.tvStatus.setText(R.string.refrigeration);
                            break;
                        case 4:
                            holder.tvStatus.setText(R.string.heating);
                            break;
                        case 5:
                            holder.tvStatus.setText(R.string.open);
                            break;
                        default:
                            holder.tvStatus.setText("");
                            break;
                    }
                }
            }
        }
    }

    private void bindTempHumidityHolder(TempHumidityViewHolder holder, int position) {
        Device device = mDevices.get(position);
        String devType = device.getDev_type();
        List<DeviceStatus> statusList = device.getStatus();
        if (NullUtil.isEmpty(statusList)) {
            handleOffline(holder, position);
        } else {
            holder.tvStatus.setText(R.string.online);
            for (DeviceStatus status : statusList) {
                if (status.getId() == Constant.ZB_STATUS_ID_TEMPERATURE_VALUE) {
                    //temperature
                    int t = getFromByteArray(status.getParams(), 0);
                    holder.tvTemp.setText("" + t);
                }
                if (status.getId() == Constant.ZB_STATUS_ID_HUMIDITY_VALUE) {
                    int humidity = getFromByteArray(status.getParams(), 0);
                    holder.tvHumidity.setText("" + humidity);
                }
                if (status.getId() == Constant.ZB_STATUS_ID_LOW_VOLTAGE
                        || status.getId() == Constant.ZB_STATUS_ID_LOW_VOLTAGE_RCU) {
                    int lowPower = getFromByteArray(status.getParams(), 0);
                    handleLowPower(holder, lowPower);
                }
            }
        }
    }

    private void bindSensorHolder(SensorViewHolder holder, int position) {
        Device device = mDevices.get(position);
        String devType = device.getDev_type();
        List<DeviceStatus> statusList = device.getStatus();

        boolean needTime = DeviceHelper.isInfraredDevice(devType)
                || DeviceHelper.isDoorSensor(devType);


        if (NullUtil.isEmpty(statusList)) {
            handleOffline(holder, position);
        } else {
            boolean hasBasic = false;
            for (DeviceStatus status : statusList) {
                if (status.getId() == Constant.ZB_STATUS_ID_BASIC) {

                    //device on off
                    //查看连续2位，都为0才为关
                    int onoff = getFromByteArray(status.getParams(), 0);
                    if (onoff == 0) {
                        onoff = getFromByteArray(status.getParams(), 1);
                    }
                    String time = getTime(status.getTime() * 1000);
                    if (needTime) {
                        setHolderStatus(holder, devType, onoff, time + " ");
                    } else {
                        setHolderStatus(holder, devType, onoff, null);
                    }

                    //low power
                    int lowPower = getFromByteArray(status.getParams(), 3);
                    if (lowPower == Constant.ACTION_ON) {
                        holder.ivLowPower.setVisibility(View.VISIBLE);
                    } else {
                        holder.ivLowPower.setVisibility(View.GONE);
                    }

                    hasBasic = true;
                }

                if (!hasBasic && status.getId() == Constant.ZB_SENSER_ONLINE_AND_STATUS) {
                    int onoff = getFromByteArray(status.getParams(), 0);
                    if (onoff == 0) {
                        handleOffline(holder, position);
                    } else {
                        onoff = getFromByteArray(status.getParams(), 1);
                        String time = getTime(status.getTime() * 1000);
                        if (needTime) {
                            setHolderStatus(holder, devType, onoff, time + " ");
                        } else {
                            setHolderStatus(holder, devType, onoff, null);
                        }
                    }
                }
                if (status.getId() == Constant.ZB_STATUS_ID_LOW_VOLTAGE) {
                    int lowPower = getFromByteArray(status.getParams(), 0);
                    handleLowPower(holder, lowPower);
                }

//                if (status.getId() == Constant.ZB_STATUS_ID_BATTERY_VALUE) {
//                    int power = getFromByteArray(status.getParams(), 0);
//                    if (position < 50) {
//                        handleLowPower(holder,1);
//                    }else{
//                        handleLowPower(holder,0);
//                    }
//                }
            }
        }
    }

    private void bindActionHolder(ActionViewHolder holder, int position) {
        Device device = mDevices.get(position);
        String devType = device.getDev_type();
        List<DeviceStatus> statusList = device.getStatus();
        if (DeviceHelper.isLight(devType)) {
            holder.ivSetting.setVisibility(View.VISIBLE);
        }

        if (NullUtil.isEmpty(statusList)) {
            //offline
            handleOffline(holder, position);
        } else {
            //online
            boolean hasIdOnOff = false;
            for (DeviceStatus status : statusList) {
                if (status.getId() == Constant.STATUS_ID_ON_OFF) {
                    int onoff = getFromByteArray(status.getParams(), 0);
                    setHolderStatus(holder, devType, onoff, "");
                    hasIdOnOff = true;
                }

                if (!hasIdOnOff && status.getId() == Constant.ZB_SENSER_ONLINE_AND_STATUS) {
                    int onoff = getFromByteArray(status.getParams(), 0);
                    if (onoff == 0) {
                        handleOffline(holder, position);
                    } else {
                        onoff = getFromByteArray(status.getParams(), 1);
                        setHolderStatus(holder, devType, onoff, null);
                    }
                }
                if (status.getId() == Constant.ZB_STATUS_ID_LOW_VOLTAGE) {
                    int lowPower = getFromByteArray(status.getParams(), 0);
                    handleLowPower(holder, lowPower);
                }
            }
        }
    }

    private void handleLowPower(ViewHolder holder, int lowPower) {
        holder.ivLowPower.setVisibility(View.VISIBLE);
        int position = holder.getAdapterPosition();
        if (lowPower == Constant.ACTION_ON) {
            lowPowerList[position] = true;
        } else {
            holder.ivLowPower.setVisibility(View.GONE);
            lowPowerList[position] = false;
        }
    }

    /**
     * 除红外传感器和门磁外，其他设备不显示时间
     *
     * @param holder
     * @param devType
     * @param status
     * @param prefix
     */
    private void setHolderStatus(ViewHolder holder, String devType, int status, String prefix) {

        holder.ivIcon.setImageResource(DeviceHelper.getStatusDrawable(devType, status));
        if (!NullUtil.isEmpty(prefix)) {
            holder.tvStatus.setText(prefix + mContext.getString(DeviceHelper.getStatusAction(devType, status)));
        } else {
            holder.tvStatus.setText(DeviceHelper.getStatusAction(devType, status));
        }
    }

    private String getTime(long timeMilious) {
//        long now = System.currentTimeMillis();
//        if (now / DateTimeUtil.MILLIS_PER_DAY > timeMilious / DateTimeUtil.MILLIS_PER_DAY) {
//            //不是今天，需要显示日期加时间 mm/DD HH:mm
//            return DateTimeUtil.getTimeString(timeMilious, DateTimeUtil.PATTERN_MM_DD_HH_MM);
//        } else {
//            //HH:mm
//            return DateTimeUtil.getTimeString(timeMilious, DateTimeUtil.PATTERN_HH_MM);
//        }

        //只显示时间，不显示日期
        return DateTimeUtil.getTimeString(timeMilious, DateTimeUtil.PATTERN_HH_MM);
    }

    /**
     * 防止越界
     *
     * @param params
     * @param index
     * @return
     */
    private int getFromByteArray(byte[] params, int index) {
        if (NullUtil.isEmpty(params)) return -1;
        if (index >= params.length) return -1;
        return params[index];
    }

    private void handleOffline(ViewHolder actionViewHolder, int position) {
        Device device = mDevices.get(position);
        actionViewHolder.ivIcon.setImageResource(DeviceHelper.getStatusDrawable(device.getDev_type(),
                -1));
        actionViewHolder.tvStatus.setText(DeviceHelper.getStatusAction(device.getDev_type(), -1));
        actionViewHolder.itemView.setAlpha(HALF_TRANSPARENT);
    }

    private void handleCommonHolder(ViewHolder holder, int position) {
        Device device = mDevices.get(position);
        String deviceName = DeviceHelper.getDeviceName(device);
        holder.tvName.setText(deviceName);
        holder.tvRoom.setText(DeviceHelper.getRoomName(device.getRoom_id()));
        holder.ivSetting.setVisibility(View.GONE);
        if (lowPowerList[position]) {
            holder.ivLowPower.setVisibility(View.VISIBLE);
        } else {
            holder.ivLowPower.setVisibility(View.GONE);
        }
        holder.ivDelete.setVisibility(View.GONE);
        holder.itemView.setAlpha(1f);
    }


    @Override
    public int getItemCount() {
        return mDevices.size();
    }

    public List<Device> getDevices() {
        return mDevices;
    }

    public void setOnEditMode(boolean edit) {
        if (this.mEdit != edit) {
            this.mEdit = edit;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_home_device_status_tv)
        TextView tvStatus;

        @BindView(R.id.id_home_device_room_tv)
        TextView tvRoom;

        @BindView(R.id.id_home_device_name_tv)
        TextView tvName;

        @BindView(R.id.id_home_device_setting_iv)
        ImageView ivSetting;

        @BindView(R.id.id_home_device_low_power)
        ImageView ivLowPower;

        @BindView(R.id.id_home_device_delete_iv)
        ImageView ivDelete;

        @BindView(R.id.id_home_device_iv)
        ImageView ivIcon;

        @BindView(R.id.id_home_device_icon_rl)
        RelativeLayout rlIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new ClickHelper.ClickListener() {
                @Override
                public void onClickView(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        Device device = mDevices.get(position);
                        onItemClickListener.onDeviceItemClick(position, device);
                    }
                }
            });
            ivDelete.setOnClickListener(new ClickHelper.ClickListener() {
                @Override
                public void onClickView(View v) {

                    if (onDeleteListener != null) {
                        int position = getAdapterPosition();
                        Device device = mDevices.get(position);
                        onDeleteListener.onDeviceItemDelete(position, device);
                    }
                }
            });
        }
    }

    class ActionViewHolder extends ViewHolder {

        public ActionViewHolder(View itemView) {
            super(itemView);
        }
    }

    class SensorViewHolder extends ViewHolder {

        public SensorViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ThermostatViewHolder extends ViewHolder {
        TextView tvTemp;

        public ThermostatViewHolder(View itemView) {
            super(itemView);
            rlIcon.removeAllViews();
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.common_home_device_temp_include, null);
            rlIcon.addView(view);
            tvTemp = (TextView) view.findViewById(R.id.id_home_device_temp);
        }
    }

    class TempHumidityViewHolder extends ViewHolder {
        TextView tvTemp;
        TextView tvHumidity;

        public TempHumidityViewHolder(View itemView) {
            super(itemView);
            rlIcon.removeAllViews();
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.adapter_home_device_sub_temp_one, null);
            rlIcon.addView(view);
            tvTemp = (TextView) view.findViewById(R.id.id_home_device_temp);
            tvHumidity = (TextView) view.findViewById(R.id.id_home_device_humidity);
        }
    }

    public OnDeviceItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnDeviceItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnDeleteListener getOnDeleteListener() {
        return onDeleteListener;
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface OnDeviceItemClickListener {
        void onDeviceItemClick(int position, Device device);
    }

    public interface OnDeleteListener {
        void onDeviceItemDelete(int position, Device device);
    }
}
