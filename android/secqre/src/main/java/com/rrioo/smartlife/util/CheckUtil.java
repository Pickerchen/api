package com.rrioo.smartlife.util;

import com.sen5.lib.api.encrypt.DID;
import com.sen5.lib.entity.constant.Constant;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/29 18:15 1.0
 * @time 2017/9/29 18:15
 * @project secQreNew3.0 com.rrioo.smartlife.util
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/29 18:15
 */

public class CheckUtil {

    public static boolean isBoxIdLegal(String result) {
        if (result.contains(Constant.HOME_TYPE_SLIFE)
                || result.contains(Constant.HOME_TYPE_DDD))
            return true;
        result = DID.getDID(result);
        return result.contains(Constant.HOME_TYPE_SLIFE)
                || result.contains(Constant.HOME_TYPE_DDD);
    }
}
