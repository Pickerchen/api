package com.rrioo.smartlife.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/9/30 15:11 1.0
 * @time 2017/9/30 15:11
 * @project secQreNew3.0 com.rrioo.smartlife.helper
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/9/30 15:11
 */

public interface SimpleOnItemClickListener {
    void onItemClick(RecyclerView.Adapter adapter,View itemView, int position, Object data);
}
