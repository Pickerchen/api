package com.rrioo.smartlife.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rrioo.smartlife.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhurongkun on 2017/9/21.
 */

public class DeviceAndRoomDialog extends Dialog {
    public static final int CLICK_ITEM_ADD_DEVICE = 0;
    public static final int CLICK_ITEM_ADD_ROOM = 1;
    public static final int CLICK_ITEM_EDIT_ROOM = 2;


    public DeviceAndRoomDialog(@NonNull Context context) {
        super(context, R.style.DialogBaseStyle);
        View root = LayoutInflater.from(context).inflate(R.layout.device_room_dialog, null);
        setContentView(root);
        ButterKnife.bind(this);

        Window window = getWindow();
        // 设置window出现动画及位置
        window.setWindowAnimations(R.style.dialog_fromBottom_anim);
        window.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = window.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = context.getResources().getDisplayMetrics().widthPixels; // 宽度
        window.setAttributes(lp);
    }

    public DeviceAndRoomDialog(@NonNull Context context, OnClickListener onClickListener) {
        this(context);
        this.onClickListener = onClickListener;
    }

    @OnClick(R.id.device_room_dialog_tv_add_device)
    void clickAddDevice() {
        if (onClickListener != null) {
            onClickListener.onClick(this, CLICK_ITEM_ADD_DEVICE);
        }
    }

    @OnClick(R.id.device_room_dialog_tv_add_room)
    void clickAddRoom() {
        if (onClickListener != null) {
            onClickListener.onClick(this, CLICK_ITEM_ADD_ROOM);
        }
    }

    @OnClick(R.id.device_room_dialog_tv_edit_room)
    void clickEditRoom() {
        if (onClickListener != null) {
            onClickListener.onClick(this, CLICK_ITEM_EDIT_ROOM);
        }
    }

    @OnClick(R.id.device_room_dialog_tv_cancel)
    void clickCancel() {
        cancel();
    }

    OnClickListener onClickListener = null;

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
