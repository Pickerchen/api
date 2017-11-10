package com.sen5.lib.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * Created by zhurongkun on 2017/8/18.
 */

public class AppUUID {
    private static final String APP_UUID = "APP_UUID";

    /**
     * 生成UUID
     * UUID不必每次重新生成，已生成的UUID存储在本地方便下次直接使用
     *
     * @param context
     * @return
     */
    public static String generate(Context context) {
        //先从本地记录中找，如果没有则生成并存储到本地
        String id = SharedPreferencesUtil.getString(context, APP_UUID);
        if (id != null && !id.isEmpty()) {
            return id;
        }


        UUID uuid = null;
        //首先获取AndroidId
        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        // Use the Android ID unless it's broken, in which case fallback on deviceId,
        // unless it's not available, then fallback on a random number which we store
        // to a prefs file
        try {
            if (!"9774d56d682e549c".equals(androidId)) {
                //通过字节数组生成UUID
                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
            } else {//如果AndroidId无效,则获取DeviceId，如果无法获取DeviceId，则随机生成UUID
                final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
            }
        } catch (Exception e) {
            uuid = UUID.randomUUID();
        }


        id = uuid.toString();
        SharedPreferencesUtil.save(context, APP_UUID, id);

        return id;
    }

    private AppUUID() {
    }
}
