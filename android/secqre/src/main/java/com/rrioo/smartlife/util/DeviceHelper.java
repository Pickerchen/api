package com.rrioo.smartlife.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.SecApp;
import com.rrioo.smartlife.camera.hsl.EventHsl;
import com.rrioo.smartlife.data.ApiCache;
import com.sen5.lib.entity.constant.Constant;
import com.sen5.lib.entity.device.Device;
import com.sen5.lib.entity.device.DeviceStatus;
import com.sen5.lib.entity.room.Room;

import java.util.List;

public class DeviceHelper {

    public static boolean isHSLCamera(String did) {
        if (TextUtils.isEmpty(did)) return false;
        return did.startsWith("SLIFE");
    }

    public static boolean isLTCamera(String did) {
        if (TextUtils.isEmpty(did)) return false;
        return did.startsWith("by");
    }

    /**
     * 获取设备类型名称
     *
     * @param devType
     * @return
     */
    public static int getTypeName(String devType) {

        if ((Constant.ZLL_ACTION_LIGHT_1).equals(devType)
                || (Constant.ZLL_ACTION_LIGHT_2).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT1).equals(devType)
//                || (AppConstant.ZHA_ACTION_RELAY).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_TEMPERATURE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_EXTENDED_COLOUR_LIGHT).equals(devType)
                ) {
            return R.string.light_device;

        } else if ((Constant.ZHA_ACTION_RELAY).equals(devType)) {
            return R.string.zha_relay;

        } else if ((Constant.ZHA_ACTION_OUTLET_1).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_2).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_EU).equals(devType)
                || (Constant.ZWAVE_ACTION_OUTLET).equals(devType)
                || (Constant.ZHA_ACTION_LIGHT_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_COLOUR_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_ON_OFF_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZHA_ACTION_DIMMABLE_PLUG_IN_UNIT).equals(devType)
                ) {
            return R.string.outlet_device;
        } else if ((Constant.ZHA_SENSOR_INFRARED).equals(devType)
                || (Constant.ZWAVE_SENSOR_INFRARED).equals(devType)) {
            return R.string.motion_sensor;

        } else if ((Constant.ZHA_SENSOR_DOOR).equals(devType)
                || (Constant.ZWAVE_SENSOR_DOOR).equals(devType)) {
            return R.string.door_sensor;

        } else if ((Constant.ZHA_SENSOR).equals(devType)) {
            return R.string.zha_sensor;

        } else if ((Constant.ZHA_SENSOR_SMOKE).equals(devType)
                || (Constant.ZWAVE_SENSOR_SMOKE).equals(devType)) {
            return R.string.smoke_sensor;

        } else if ((Constant.ZHA_SENSOR_COMBUSTIBLE_GAS).equals(devType)
                || (Constant.ZWAVE_SENSOR_COMBUSTIBLE_GAS).equals(devType)) {
            return R.string.gas_sensor;

        } else if ((Constant.ZHA_SENSOR_CO).equals(devType)
                || (Constant.ZWAVE_SENSOR_CO).equals(devType)) {
            return R.string.co_sensor;

        } else if ((Constant.ZHA_SENSOR_SHOCK).equals(devType)) {
            return R.string.shock_sensor;

        } else if ((Constant.ZHA_SENSOR_WATER).equals(devType)
                || (Constant.ZWAVE_SENSOR_WATER).equals(devType)) {
            return R.string.leak_sensor;

        } else if ((Constant.ZHA_ACTION_SECURE_RC).equals(devType) || (Constant.ZHA_ACTION_SECURE_RC_NEW).equals(devType)) {
            return R.string.security_control_sensor;

        } else if ((Constant.ZHA_ACTION_EMERGENCY_BUTTON).equals(devType)) {
            return R.string.emergency_sensor;

        } else if ((Constant.ZHA_ACTION_ALERTOR).equals(devType)) {
            return R.string.alertor_sensor;

        } else if ((Constant.ZHA_SENSOR_HUMITURE).equals(devType)
                || (Constant.ZWAVE_SENSOR_HUMITURE).equals(devType)) {
            return R.string.humiture_sensor;    //

        } else if ((Constant.ZLL_ACTION_THERMOSTAT).equals(devType)) {
            return R.string.thermostat_device;    //

        } else if ((Constant.DEV_IP_CAMERA).equals(devType)) {
            return R.string.camera;
        }
        return R.string.unknown_device;
    }


    /**
     * 获取设备类型的图标
     *
     * @param devType
     * @return
     */
    public static int getTypeDrawable(String devType) {

        if ((Constant.ZLL_ACTION_LIGHT_1).equals(devType)
                || (Constant.ZLL_ACTION_LIGHT_2).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT1).equals(devType)
//                || (AppConstant.ZHA_ACTION_RELAY).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_TEMPERATURE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_EXTENDED_COLOUR_LIGHT).equals(devType)
                ) {
            return R.drawable.ic_light_device;

        } else if ((Constant.ZHA_ACTION_RELAY).equals(devType)) {
            return R.drawable.ic_relay_device;
        } else if ((Constant.ZHA_ACTION_OUTLET_1).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_2).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_EU).equals(devType)
                || (Constant.ZWAVE_ACTION_OUTLET).equals(devType)
                || (Constant.ZHA_ACTION_LIGHT_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_COLOUR_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_ON_OFF_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZHA_ACTION_DIMMABLE_PLUG_IN_UNIT).equals(devType)
                ) {
            return R.drawable.ic_outlet_device;

        } else if ((Constant.ZHA_SENSOR_INFRARED).equals(devType)
                || (Constant.ZWAVE_SENSOR_INFRARED).equals(devType)) {
            return R.drawable.ic_infrared_device;

        } else if ((Constant.ZHA_SENSOR_DOOR).equals(devType)
                || (Constant.ZWAVE_SENSOR_DOOR).equals(devType)) {
            return R.drawable.ic_door_device;

        } /*else if (devType.equals(AppConstant.ZHA_SENSOR)) {
            return R.drawable.ic_unknown_device;

        } */ else if ((Constant.ZHA_SENSOR_SMOKE).equals(devType)
                || (Constant.ZWAVE_SENSOR_SMOKE).equals(devType)) {
            return R.drawable.ic_smoke_device;

        } else if ((Constant.ZHA_SENSOR_COMBUSTIBLE_GAS).equals(devType)
                || (Constant.ZWAVE_SENSOR_COMBUSTIBLE_GAS).equals(devType)) {
            return R.drawable.ic_combustible_gas_device;

        } else if ((Constant.ZHA_SENSOR_CO).equals(devType)
                || (Constant.ZWAVE_SENSOR_CO).equals(devType)) {
            return R.drawable.ic_co_device;

        } else if ((Constant.ZHA_SENSOR_SHOCK).equals(devType)) {
            return R.drawable.ic_shock_device;

        } else if ((Constant.ZHA_SENSOR_WATER).equals(devType)
                || (Constant.ZWAVE_SENSOR_WATER).equals(devType)) {
            return R.drawable.ic_leak_device;

        } else if ((Constant.ZHA_ACTION_SECURE_RC).equals(devType) || (Constant.ZHA_ACTION_SECURE_RC_NEW).equals(devType)) {
            return R.drawable.ic_security_control_device;

        } else if ((Constant.ZHA_ACTION_EMERGENCY_BUTTON).equals(devType)) {
            return R.drawable.ic_sos_device_setting;

        } else if ((Constant.ZHA_ACTION_ALERTOR).equals(devType)) {
            return R.drawable.ic_alertor_device;

        } else if ((Constant.ZHA_SENSOR_HUMITURE).equals(devType)
                || (Constant.ZWAVE_SENSOR_HUMITURE).equals(devType)) {
            return R.drawable.ic_humiture_device;

        } else if ((Constant.ZLL_ACTION_THERMOSTAT).equals(devType)) {
            return R.drawable.ic_thermostat_device;
        }
        return R.drawable.ic_unknown_device;
    }

    /**
     * 获取房间设备类型的图标
     *
     * @param devType
     * @return
     */
    public static int getTypeDrawableOfRoom(String devType) {

        if ((Constant.ZLL_ACTION_LIGHT_1).equals(devType)
                || (Constant.ZLL_ACTION_LIGHT_2).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT1).equals(devType)
//                || (AppConstant.ZHA_ACTION_RELAY).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_TEMPERATURE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_EXTENDED_COLOUR_LIGHT).equals(devType)
                ) {
            return R.drawable.ic_light_room;

        } else if ((Constant.ZHA_ACTION_RELAY).equals(devType)) {
            return R.drawable.btn_relay_off_devices;

        } else if ((Constant.ZHA_ACTION_OUTLET_1).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_2).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_EU).equals(devType)
                || (Constant.ZWAVE_ACTION_OUTLET).equals(devType)
                || (Constant.ZHA_ACTION_LIGHT_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_COLOUR_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_ON_OFF_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZHA_ACTION_DIMMABLE_PLUG_IN_UNIT).equals(devType)
                ) {
            return R.drawable.ic_outlet_room;

        } else if ((Constant.ZHA_SENSOR_INFRARED).equals(devType)
                || (Constant.ZWAVE_SENSOR_INFRARED).equals(devType)) {
            return R.drawable.ic_infrared_room;

        } else if ((Constant.ZHA_SENSOR_DOOR).equals(devType)
                || (Constant.ZWAVE_SENSOR_DOOR).equals(devType)) {
            return R.drawable.ic_door_room;

        } /*else if (devType.equals(AppConstant.ZHA_SENSOR)) {
            return R.mipmap.ic_launcher;

        } */ else if ((Constant.ZHA_SENSOR_SMOKE).equals(devType)
                || (Constant.ZWAVE_SENSOR_SMOKE).equals(devType)) {
            return R.drawable.ic_smoke_room;

        } else if ((Constant.ZHA_SENSOR_COMBUSTIBLE_GAS).equals(devType)
                || (Constant.ZWAVE_SENSOR_COMBUSTIBLE_GAS).equals(devType)) {
            return R.drawable.ic_combustible_gas_room;

        } else if ((Constant.ZHA_SENSOR_CO).equals(devType)
                || (Constant.ZWAVE_SENSOR_CO).equals(devType)) {
            return R.drawable.ic_co_room;

        } else if ((Constant.ZHA_SENSOR_SHOCK).equals(devType)) {
            return R.drawable.ic_shock_room;

        } else if ((Constant.ZHA_SENSOR_WATER).equals(devType)
                || (Constant.ZWAVE_SENSOR_WATER).equals(devType)) {
            return R.drawable.ic_leak_room;

        } else if ((Constant.ZHA_ACTION_SECURE_RC).equals(devType) || (Constant.ZHA_ACTION_SECURE_RC_NEW).equals(devType)) {
            return R.drawable.ic_security_control_room;

        } else if ((Constant.ZHA_ACTION_EMERGENCY_BUTTON).equals(devType)) {
            return R.drawable.ic_emergency_room;

        } else if ((Constant.ZHA_ACTION_ALERTOR).equals(devType)) {
            return R.drawable.ic_alertor_room;

        } else if ((Constant.ZHA_SENSOR_HUMITURE).equals(devType)
                || (Constant.ZWAVE_SENSOR_HUMITURE).equals(devType)) {
            return R.drawable.ic_humiture_room;

        } else if ((Constant.ZLL_ACTION_THERMOSTAT).equals(devType)) {       //恒温器
            return R.drawable.ic_thermostat_room;
        }
        return R.drawable.ic_unknown_device;
    }

    /**
     * 获取状态的图标
     *
     * @param devType
     * @param status
     * @return
     */
    public static int getStatusDrawable(String devType, int status) {

        if ((Constant.ZLL_ACTION_LIGHT_1).equals(devType)
                || (Constant.ZLL_ACTION_LIGHT_2).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT1).equals(devType)
//                || (AppConstant.ZHA_ACTION_RELAY).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_TEMPERATURE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_EXTENDED_COLOUR_LIGHT).equals(devType)
                ) {
            int drawable = R.drawable.btn_light_off_devices;
            if (Constant.ACTION_OFF == status) {
                drawable = R.drawable.btn_light_off_devices;
            } else if (Constant.ACTION_ON == status) {
                drawable = R.drawable.btn_light_on_devices;
            }

            return drawable;

        } else if ((Constant.ZHA_ACTION_RELAY).equals(devType)) {
            int drawable = R.drawable.btn_relay_off_devices;

            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.btn_relay_off_devices;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.btn_relay_on_devices;
            }
            return drawable;

        } else if ((Constant.ZHA_ACTION_OUTLET_1).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_2).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_EU).equals(devType)
                || (Constant.ZWAVE_ACTION_OUTLET).equals(devType)
                || (Constant.ZHA_ACTION_LIGHT_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_COLOUR_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_ON_OFF_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZHA_ACTION_DIMMABLE_PLUG_IN_UNIT).equals(devType)
                ) {
            int drawable = R.drawable.btn_outlet_off_device;
            if (Constant.ACTION_OFF == status) {
                drawable = R.drawable.btn_outlet_off_device;
            } else if (Constant.ACTION_ON == status) {
                drawable = R.drawable.btn_outlet_on_device;
            }
            return drawable;

        } else if ((Constant.ZHA_SENSOR_INFRARED).equals(devType)) {
            int drawable = R.drawable.ic_motion_device_offline;

            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_motion_device_safety;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_motion_device_danger;
            }
            return drawable;

        } else if ((Constant.ZWAVE_SENSOR_INFRARED).equals(devType)) {
            int drawable = R.drawable.ic_motion_device_offline;
            if (7 == status || 8 == status) {
                drawable = R.drawable.ic_motion_device_danger;
            } else if (3 == status) {
                drawable = R.drawable.ic_motion_device_offline;
            } else if (0 == status) {
                drawable = R.drawable.ic_motion_device_safety;
            }
            return drawable;
        } else if ((Constant.ZHA_SENSOR_DOOR).equals(devType)
                || (Constant.ZWAVE_SENSOR_DOOR).equals(devType)) {
            int drawable = R.drawable.ic_door_device_offline;

            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_door_device_safety;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_door_device_danger;
            }
            return drawable;
        } /*else if (devType.equals(AppConstant.ZHA_SENSOR)) {
            int drawable = R.drawable.ic_door_nowork_device;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_door_safety_device;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_door_danger_device;
            }
            return drawable;

        }*/ else if ((Constant.ZHA_SENSOR_SMOKE).equals(devType)
                || (Constant.ZWAVE_SENSOR_SMOKE).equals(devType)) {
            int drawable = R.drawable.ic_smoke_device_offline;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_smoke_device_safety;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_smoke_device_danger;
            }
            return drawable;

        } else if ((Constant.ZHA_SENSOR_COMBUSTIBLE_GAS).equals(devType)
                || (Constant.ZWAVE_SENSOR_COMBUSTIBLE_GAS).equals(devType)) {
            int drawable = R.drawable.ic_combustible_device_offline;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_combustible_device_safety;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_combustible_device_danger;
            }
            return drawable;

        } else if ((Constant.ZHA_SENSOR_CO).equals(devType)
                || (Constant.ZWAVE_SENSOR_CO).equals(devType)) {
            int drawable = R.drawable.ic_co_device_offline;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_co_device_safety;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_co_device_danger;
            }
            return drawable;

        } else if ((Constant.ZHA_SENSOR_SHOCK).equals(devType)) {
            int drawable = R.drawable.ic_shock_device_offline;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_shock_device_safety;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_shock_device_danger;
            }
            return drawable;

        } else if ((Constant.ZHA_SENSOR_WATER).equals(devType)
                || (Constant.ZWAVE_SENSOR_WATER).equals(devType)) {
            int drawable = R.drawable.ic_leak_device_offline;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_leak_device_safety;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_leak_device_danger;
            }
            return drawable;

        } else if ((Constant.ZHA_ACTION_SECURE_RC).equals(devType)
                || (Constant.ZHA_ACTION_SECURE_RC_NEW).equals(devType)) {
            int drawable = R.drawable.ic_control_device_online;
           /* if (3 == status) {
                // AppLog.i("DeviceStatus.SENSOR_STATUS_SAFETY ====" + DeviceStatus.SENSOR_STATUS_SAFETY);
                drawable = R.drawable.ic_remote_control_safety_device;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status || 2 == status) {
                drawable = R.drawable.ic_remote_control_danger_device;
            }*/
            return drawable;

        } else if ((Constant.ZHA_ACTION_EMERGENCY_BUTTON).equals(devType)) {
            int drawable = R.drawable.ic_sos_device_offline;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_sos_device_safety;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_sos_device_danger;
            }
            return drawable;

        } else if ((Constant.ZHA_ACTION_ALERTOR).equals(devType)) {
            int drawable = R.drawable.ic_alertor_device_offline;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_alertor_device_safety;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_alertor_device_danger;
            }
            return drawable;

        } else if ((Constant.ZHA_SENSOR_HUMITURE).equals(devType)
                || (Constant.ZWAVE_SENSOR_HUMITURE).equals(devType)) {
            int drawable = R.drawable.ic_humiture_device_offline;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_humiture_device_online;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_humiture_device_online;
            }
            return drawable;

        } else if (Constant.ZLL_ACTION_THERMOSTAT.equals(devType)) {
            int drawable = R.drawable.ic_thermostat_device_offline;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                drawable = R.drawable.ic_thermostat_device_online;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                drawable = R.drawable.ic_thermostat_device_online;
            }
            return drawable;
        }

        return R.drawable.btn_unknown_status;
    }


    public static int getStatusAction(String devType, int status, View view) {
        if ((Constant.ZLL_ACTION_LIGHT_1).equals(devType)           //ZLL灯
                || (Constant.ZLL_ACTION_LIGHT_2).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT1).equals(devType)
//                || (AppConstant.ZHA_ACTION_RELAY).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_TEMPERATURE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_EXTENDED_COLOUR_LIGHT).equals(devType)
                ) {
            int devStatus = R.string.unknown_status;
            view.setSelected(true);
            if (Constant.ACTION_OFF == status) {
                devStatus = R.string.turn_off;
                view.setSelected(true);

            } else if (Constant.ACTION_ON == status) {
                devStatus = R.string.turn_on;
                view.setSelected(false);

            }

            return devStatus;

        } else if ((Constant.ZHA_ACTION_RELAY).equals(devType)) {
            int devStatus = R.string.unknown_status;
            view.setSelected(true);

            if (Constant.ACTION_OFF == status) {
                devStatus = R.string.turn_off;
                view.setSelected(true);

            } else if (Constant.ACTION_ON == status) {
                devStatus = R.string.turn_on;
                view.setSelected(false);

            }
            return devStatus;

        } else if ((Constant.ZHA_ACTION_OUTLET_1).equals(devType)   //ZHA开关
                || (Constant.ZHA_ACTION_OUTLET_2).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_EU).equals(devType)
                || (Constant.ZWAVE_ACTION_OUTLET).equals(devType)
                || (Constant.ZHA_ACTION_LIGHT_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_COLOUR_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_ON_OFF_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZHA_ACTION_DIMMABLE_PLUG_IN_UNIT).equals(devType)
                ) {
            int devStatus = R.string.unknown_status;
            view.setSelected(true);

            if (Constant.ACTION_OFF == status) {
                devStatus = R.string.turn_off;
                view.setSelected(true);

            } else if (Constant.ACTION_ON == status) {
                devStatus = R.string.turn_on;
                view.setSelected(false);

            }
            return devStatus;

        }
        return R.string.unknown_status;
    }

    public static int getStatusAction(String devType, int status) {

        if ((Constant.ZLL_ACTION_LIGHT_1).equals(devType)           //ZLL灯
                || (Constant.ZLL_ACTION_LIGHT_2).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT1).equals(devType)
//                || (AppConstant.ZHA_ACTION_RELAY).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_TEMPERATURE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_EXTENDED_COLOUR_LIGHT).equals(devType)
                ) {
            int devStatus = R.string.unknown_status;
            if (Constant.ACTION_OFF == status) {
                devStatus = R.string.close;
            } else if (Constant.ACTION_ON == status) {
                devStatus = R.string.open;
            }

            return devStatus;

        } else if ((Constant.ZHA_ACTION_RELAY).equals(devType)) {
            int devStatus = R.string.unknown_status;
            if (Constant.ACTION_OFF == status) {
                devStatus = R.string.close;
            } else if (Constant.ACTION_ON == status) {
                devStatus = R.string.open;
            }
            return devStatus;

        } else if ((Constant.ZHA_ACTION_OUTLET_1).equals(devType)   //ZHA开关
                || (Constant.ZHA_ACTION_OUTLET_2).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_EU).equals(devType)
                || (Constant.ZWAVE_ACTION_OUTLET).equals(devType)
                || (Constant.ZHA_ACTION_LIGHT_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_COLOUR_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_ON_OFF_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZHA_ACTION_DIMMABLE_PLUG_IN_UNIT).equals(devType)
                ) {
            int devStatus = R.string.unknown_status;
            if (Constant.ACTION_OFF == status) {
                devStatus = R.string.close;
            } else if (Constant.ACTION_ON == status) {
                devStatus = R.string.open;
            }
            return devStatus;

        } else if ((Constant.ZHA_SENSOR_INFRARED).equals(devType)) {    //ZHA红外传感器
            int devStatus = R.string.unknown_status;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                devStatus = R.string.sensor_get_right;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                devStatus = R.string.sensor_trigger_infrared;
            }
            return devStatus;

        } else if ((Constant.ZWAVE_SENSOR_INFRARED).equals(devType)) {  //红外传感器
            int devStatus = R.string.unknown_status;
            if (7 == status || 8 == status) {
                devStatus = R.string.sensor_trigger;
            } else if (3 == status) {
                devStatus = R.string.unknown_status;
            } else if (0 == status) {
                devStatus = R.string.sensor_get_right;
            }
            return devStatus;
        } else if ((Constant.ZHA_SENSOR_DOOR).equals(devType)   //ZHA门磁传感器
                || (Constant.ZWAVE_SENSOR_DOOR).equals(devType)) {
            int devStatus = R.string.unknown_status;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                devStatus = R.string.door_sensor_status_normal;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                devStatus = R.string.door_sensor_status_trigger;
            }
            return devStatus;
        } else if (devType.equals(Constant.ZHA_SENSOR)) {
            int devStatus = R.string.unknown_status;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                devStatus = R.string.sensor_get_right;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                devStatus = R.string.sensor_trigger;
            }
            return devStatus;

        } else if ((Constant.ZHA_SENSOR_SMOKE).equals(devType)    //ZHA烟雾传感器
                || (Constant.ZWAVE_SENSOR_SMOKE).equals(devType)) {
            int devStatus = R.string.unknown_status;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                devStatus = R.string.sensor_get_right;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                devStatus = R.string.sensor_trigger;
            }
            return devStatus;

        } else if ((Constant.ZHA_SENSOR_COMBUSTIBLE_GAS).equals(devType)    //ZHA易燃气体传感器
                || (Constant.ZWAVE_SENSOR_COMBUSTIBLE_GAS).equals(devType)) {
            int devStatus = R.string.unknown_status;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                devStatus = R.string.sensor_get_right;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                devStatus = R.string.sensor_trigger;
            }
            return devStatus;

        } else if ((Constant.ZHA_SENSOR_CO).equals(devType) //ZHA一氧化碳传感器
                || (Constant.ZWAVE_SENSOR_CO).equals(devType)) {
            int devStatus = R.string.unknown_status;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                devStatus = R.string.sensor_get_right;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                devStatus = R.string.sensor_trigger;
            }
            return devStatus;

        } else if ((Constant.ZHA_SENSOR_SHOCK).equals(devType)) {   //ZHA震动传感器
            int devStatus = R.string.unknown_status;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                devStatus = R.string.sensor_get_right;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                devStatus = R.string.sensor_trigger;
            }
            return devStatus;

        } else if ((Constant.ZHA_SENSOR_WATER).equals(devType)  //ZHA水浸传感器
                || (Constant.ZWAVE_SENSOR_WATER).equals(devType)) {
            int devStatus = R.string.unknown_status;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                devStatus = R.string.sensor_get_right;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                devStatus = R.string.sensor_trigger;
            }
            return devStatus;

        } else if ((Constant.ZHA_ACTION_SECURE_RC).equals(devType)  //ZHA安防遥控器
                || (Constant.ZHA_ACTION_SECURE_RC_NEW).equals(devType)) {
            int devStatus = R.string.unknown_status;
            if (3 == status) {
                devStatus = R.string.sensor_get_right;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status || 2 == status) {
                devStatus = R.string.sensor_trigger;
            }
            return devStatus;

        } else if ((Constant.ZHA_ACTION_EMERGENCY_BUTTON).equals(devType)) {  //ZHA紧急按钮
            int devStatus = R.string.unknown_status;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                devStatus = R.string.sensor_get_right;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                devStatus = R.string.sensor_trigger;
            }
            return devStatus;

        } else if ((Constant.ZHA_ACTION_ALERTOR).equals(devType)) { //ZHA报警器
            int devStatus = R.string.unknown_status;
            if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
                devStatus = R.string.sensor_get_right;
            } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
                devStatus = R.string.sensor_trigger;
            }
            return devStatus;

        }
        return R.string.unknown_status;
    }


    /**
     * 传感器的状态
     *
     * @param status
     * @return
     */
    private static int getSensorStatus(int status) {

        int drawable = R.drawable.shape_sensor_status_gray;
        if (DeviceStatus.SENSOR_STATUS_SAFETY == status) {
            drawable = R.drawable.shape_sensor_status_green;
        } else if (DeviceStatus.SENSOR_STATUS_DANGER == status) {
            drawable = R.drawable.shape_sensor_status_red;
        }
        return drawable;
    }


    /**
     * 判断设备是否是灯或开关
     *
     * @param devType
     * @return
     */
    public static boolean isLightOrSwitch(String devType) {

        if ((Constant.ZLL_ACTION_LIGHT_1).equals(devType)
                || (Constant.ZLL_ACTION_LIGHT_2).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT1).equals(devType)
                || (Constant.ZHA_ACTION_RELAY).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_TEMPERATURE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_EXTENDED_COLOUR_LIGHT).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_1).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_2).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_EU).equals(devType)
                || (Constant.ZHA_ACTION_LIGHT_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_COLOUR_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_ON_OFF_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZHA_ACTION_DIMMABLE_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZWAVE_ACTION_OUTLET).equals(devType)) {
            return true;
        }

        return false;
    }

    public static boolean isSwitch(String devType) {
        if ((Constant.ZHA_ACTION_OUTLET_1).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_2).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_EU).equals(devType)
                || (Constant.ZHA_ACTION_RELAY).equals(devType)
                || (Constant.ZWAVE_ACTION_OUTLET).equals(devType)
                || (Constant.ZHA_ACTION_LIGHT_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_COLOUR_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_ON_OFF_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZHA_ACTION_DIMMABLE_PLUG_IN_UNIT).equals(devType)
                ) {
            return true;
        }

        return false;
    }

    public static boolean isLight(String devType) {

        if ((Constant.ZLL_ACTION_LIGHT_1).equals(devType)
                || (Constant.ZLL_ACTION_LIGHT_2).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT1).equals(devType)
//                || (AppConstant.ZHA_ACTION_RELAY).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_TEMPERATURE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_EXTENDED_COLOUR_LIGHT).equals(devType)
                ) {
            return true;
        }

        return false;
    }

    public static boolean isRelayDevice(String devType) {
        if (Constant.ZHA_ACTION_RELAY.equals(devType)) return true;
        return false;
    }

    //是否为安防遥控器
    public static boolean isSecureRc(String devType) {
        if ((Constant.ZHA_ACTION_SECURE_RC).equals(devType)
                || (Constant.ZHA_ACTION_SECURE_RC_NEW).equals(devType)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是红外设备
     *
     * @param devType
     * @return
     */
    public static boolean isInfraredDevice(String devType) {
        if ((Constant.ZHA_SENSOR_INFRARED).equals(devType)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是控制设备
     *
     * @param devType
     * @return
     */
    public static boolean isActionDevice(String devType) {
        if ((Constant.ZLL_ACTION_LIGHT_1).equals(devType)
                || (Constant.ZLL_ACTION_LIGHT_2).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_LIGHT1).equals(devType)
                || (Constant.ZHA_ACTION_RELAY).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_DIMMABLE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_COLOUR_TEMPERATURE_LIGHT).equals(devType)
                || (Constant.ZHA_ZLL_ACTION_EXTENDED_COLOUR_LIGHT).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_1).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_2).equals(devType)
                || (Constant.ZHA_ACTION_OUTLET_EU).equals(devType)
                || (Constant.ZHA_ACTION_LIGHT_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_COLOUR_DIMMER_SWITCH).equals(devType)
                || (Constant.ZHA_ACTION_ON_OFF_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZHA_ACTION_DIMMABLE_PLUG_IN_UNIT).equals(devType)
                || (Constant.ZWAVE_ACTION_OUTLET).equals(devType)
                || (Constant.ZLL_ACTION_THERMOSTAT).equals(devType)
                ) {

            return true;
        }
        return false;
    }


    /**
     * 判断是否是温湿度传感器
     *
     * @param devType
     * @return
     */
    public static boolean isSensorHumidityAndTemp(String devType) {
        if ((Constant.ZHA_SENSOR_HUMITURE).equals(devType)
                || (Constant.ZWAVE_SENSOR_HUMITURE).equals(devType)) {
            return true;
        }
        return false;
    }

    //是否为传感器
    public static boolean isSensorDevice(String devType) {

        if ((Constant.ZHA_SENSOR).equals(devType)
                || (Constant.ZHA_SENSOR_INFRARED).equals(devType)
                || (Constant.ZHA_SENSOR_SMOKE).equals(devType)
                || (Constant.ZHA_SENSOR_COMBUSTIBLE_GAS).equals(devType)
                || (Constant.ZHA_SENSOR_DOOR).equals(devType)
                || (Constant.ZHA_SENSOR_CO).equals(devType)
                || (Constant.ZHA_SENSOR_SHOCK).equals(devType)
                || (Constant.ZHA_SENSOR_WATER).equals(devType)
                || (Constant.ZHA_SENSOR_HUMITURE).equals(devType)
                || (Constant.SENSOR_BOX_ALERTOR).equals(devType)
                || (Constant.ZWAVE_SENSOR_DOOR).equals(devType)
                || (Constant.ZWAVE_SENSOR_CO).equals(devType)
                || (Constant.ZWAVE_SENSOR_WATER).equals(devType)
                || (Constant.ZWAVE_SENSOR_INFRARED).equals(devType)
                || (Constant.ZWAVE_SENSOR_SMOKE).equals(devType)
                || (Constant.ZWAVE_SENSOR_COMBUSTIBLE_GAS).equals(devType)
                || (Constant.ZWAVE_SENSOR_HUMITURE).equals(devType)
                ) {

            return true;
        }
        return false;
    }

    /**
     * 判断设备是否是摄像头
     *
     * @param devType
     * @return
     */
    public static boolean isCamera(String devType) {
        return Constant.DEV_IP_CAMERA.equals(devType);
    }


    /**
     * 判断设备是否是警报器
     *
     * @param devType
     * @return
     */
    public static boolean isAlertor(String devType) {
        if (Constant.SENSOR_BOX_ALERTOR.equals(devType)) {
            return true;
        }
        return false;
    }

    /**
     * 将设备状态转换成字符语言
     *
     * @param statusId
     * @param params
     * @return
     */
    public static String statusToString(int statusId, byte[] params, int mode) {
        Context context = SecApp.get();
        //设备没有状态时，认定是首次添加
        String action = context.getString(R.string.device_added);
        if (params == null || params.length < 1) {
            return action;
        }

        try {
            if (statusId == Constant.STATUS_ID_UNKNOWN) {
                action = context.getString(R.string.device_added);
            } else if (statusId == Constant.STATUS_ID_ON_OFF && mode == Constant.MODE_ACTION) {

                if (params[0] == 0) {//正常状态
                    action = context.getString(R.string.close);
                } else if (params[0] == 1) {//被触发
                    action = context.getString(R.string.open);
                }
            } else if ((statusId == Constant.ZB_STATUS_ID_BASIC && mode == Constant.MODE_SENSOR) || statusId == Constant.STATUS_ID_FEIBIT_SENSOR) {

                if (params[0] == 0) {//正常状态
                    action = context.getString(R.string.sensor_get_right);
                } else if (params[0] == 1) {//被触发
                    action = context.getString(R.string.sensor_trigger);
                }
            } else if (statusId == Constant.ZB_STATUS_ID_TEMPERATURE_VALUE || statusId == Constant.STATUS_ID_FEIBIT_SENSOR_TEMPERATURE) {

            } else if (statusId == Constant.ZB_STATUS_ID_HUMIDITY_VALUE || statusId == Constant.STATUS_ID_FEIBIT_SENSOR_HUMIDITY) {

            } else if (statusId == Constant.STATUS_ID_DEVICE_DID) {

            } else if (statusId == Constant.STATUS_ID_DOOR_SENSOR) {
                if (params[0] == 0) {//正常状态
                    action = context.getString(R.string.door_close);
                } else if (params[0] == 1) {//被触发
                    action = context.getString(R.string.door_open);
                }

            } else if (statusId == Constant.STATUS_ID_LUMINANCE) {

            } else if (statusId == Constant.STATUS_ID_HOME_SECURITY) {

            } else if (statusId == Constant.STATUS_ID_ZWAVE_HUMIDITY) {

            } else if (statusId == Constant.STATUS_ID_WATER_SENSOR) {

            } else if (statusId == Constant.STATUS_ID_CO_SENSOR) {

            } else if (statusId == Constant.STATUS_ID_SMOKE_SENSOR) {

            } else if (statusId == Constant.STATUS_ID_COMBUSTIBLE_GAS_SENSOR) {

            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return action;
    }

    public static String getRoomName(Room room) {
        if (room == null) return "";
        if (room.getRoom_id() == Room.DEFAULT_ROOM_ID)
            return SecApp.get().getString(R.string.default_room);
        return room.getRoom_name();
    }

    public static String getRoomName(int room_id) {
        List<Room> rooms = ApiCache.get().getRooms();
        Room room = null;
        for (Room r : rooms) {
            if (r.getRoom_id() == room_id) {
                room = r;
                break;
            }
        }
        if (room == null) return "";
        return getRoomName(room);
    }

    public static String getDeviceName(Device device) {
        String name = device.getName();
        if (NullUtil.isEmpty(name)
                || "_".equals(name)) {
            return SecApp.get().getString(getTypeName(device.getDev_type()));
        }
        return name;
    }


    /**
     * 是否为门磁
     * @param devType
     * @return
     */
    public static boolean isDoorSensor(String devType) {
        return Constant.ZHA_SENSOR_DOOR.equals(devType)
                || Constant.ZWAVE_SENSOR_DOOR.equals(devType);
    }
}
