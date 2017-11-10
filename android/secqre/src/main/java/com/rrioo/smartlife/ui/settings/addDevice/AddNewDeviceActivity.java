package com.rrioo.smartlife.ui.settings.addDevice;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.base.BaseActivity;
import com.rrioo.smartlife.util.DeviceHelper;
import com.rrioo.smartlife.widget.BaseImageView;
import com.rrioo.smartlife.widget.BaseTextView;
import com.sen5.lib.api.Api;
import com.sen5.lib.api.event.device.EventDeviceAdd;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddNewDeviceActivity extends BaseActivity implements OnClickListener,
        Callback {
    //60秒
    private static final int TIME_MAX = 60;
    private static final int TIME_OUT = 0x01;
    @BindView(R.id.id_common_title_back)
    BaseTextView tvTitleName;
    @BindView(R.id.id_activity_add_new_device_wave_iv)
    BaseImageView ivWave;
    @BindView(R.id.id_common_title_done)
    BaseTextView tvTitleDone;
    @BindView(R.id.id_activity_add_new_device_timer_tv)
    BaseTextView tvTimer;
    @BindView(R.id.id_activity_add_new_device_again_btn)
    TextView btnAgain;
    @BindView(R.id.id_activity_add_new_device_hint_tv)
    BaseTextView tvHint;
    @BindView(R.id.id_activity_add_new_device_fail_ll)
    LinearLayout llAddFailed;
    @BindView(R.id.id_activity_add_new_device_scan_ll)
    LinearLayout llScan;

    private Handler mHandler;
    private int mSecond = TIME_MAX;
    private boolean mAddSuccess;
    private AnimationDrawable animationDrawable;

    public static void startActivity(Context from){
        from.startActivity(new Intent(from,AddNewDeviceActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_device);
        ButterKnife.bind(this);
        initData();

        EventBus.getDefault().register(this);
    }

    private void initData() {
        mHandler = new Handler(this);
        mHandler.sendEmptyMessageDelayed(TIME_OUT, 1000);

        tvTitleName.setText(getString(R.string.add_new_device));
        tvTitleDone.setVisibility(View.VISIBLE);
        tvTitleDone.setText(getString(R.string.help));
        tvTimer.setText(String.format("%sS", mSecond));

        animationDrawable = (AnimationDrawable) ivWave.getBackground();
        animationDrawable.start();


        requestNewDevice();
    }

    private void requestNewDevice() {
        Api.get().getSender().addDevice();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case TIME_OUT:
                if (mSecond == 0) {
                    if (mAddSuccess) {
                        finish();
                    } else {
                        llScan.setVisibility(View.GONE);
                    }
                } else {
                    mHandler.sendEmptyMessageDelayed(TIME_OUT, 1000);
                    mSecond--;

                    tvTimer.setText(String.format("%sS", mSecond));
                }

                break;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventDeviceAdd event) {

         //新增设备
            String typeName = getString(DeviceHelper.getTypeName(event.getDevice().getDev_type()));
            if (typeName == getString(R.string.unknown_device)) {
                return;
            }

            mAddSuccess = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        animationDrawable.stop();
        mHandler.removeMessages(TIME_OUT);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @OnClick({R.id.id_common_title_back, R.id.id_common_title_done, R.id.id_activity_add_new_device_again_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_common_title_back:
                finish();
                break;

            case R.id.id_common_title_done:
//                Intent intent = new Intent(this, AddDeviceHelpActivity.class);
//                startActivityAnim(intent);
                break;

            case R.id.id_activity_add_new_device_again_btn:
//                String data = JsonCreator.createAddDeviceJson();
//                mP2PModel.sendData(data);
                requestNewDevice();
                llScan.setVisibility(View.VISIBLE);
                mSecond = TIME_MAX;
                tvTimer.setText(String.format("%sS", mSecond));
                mHandler.sendEmptyMessageDelayed(TIME_OUT, 1000);
                break;

        }
    }
}




