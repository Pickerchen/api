package com.rrioo.smartlife.ui.settings.addDevice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrioo.smartlife.R;
import com.rrioo.smartlife.base.BaseActivity;
import com.rrioo.smartlife.widget.BaseTextView;
import com.sen5.lib.entity.constant.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class AddDeviceGuideActivity extends BaseActivity {

    @BindView(R.id.id_activity_add_device_guide_gif)
    GifImageView ivGifGuide;
    @BindView(R.id.id_activity_add_device_guide_step_one_tv)
    TextView tvStepOne;
    @BindView(R.id.id_activity_add_device_guide_step_two_tv)
    TextView tvStepTwo;
    @BindView(R.id.id_activity_add_device_guide_step_three_tv)
    TextView tvStepThree;
    @BindView(R.id.id_activity_add_device_guide_next_btn)
    Button btnNext;
    @BindView(R.id.id_common_title_back)
    BaseTextView tvTitleName;


    public static void startActivity(Context from, int devStrId) {
        Intent intent = new Intent(from, AddDeviceGuideActivity.class);
        intent.putExtra("devStrId", devStrId);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device_guide);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        int devStrId = getIntent().getIntExtra("devStrId",0);
        if (devStrId == R.string.motion_sensor) {
            loadGif(R.drawable.anim_add_montion, ivGifGuide);
            tvTitleName.setText(getString(R.string.motion_sensor));
            setStep();
        } else if (devStrId == R.string.door_sensor) {
            loadGif(R.drawable.anim_add_door, ivGifGuide);
            tvTitleName.setText(getString(R.string.door_sensor));
            setStep();
        } else if (devStrId == R.string.humiture_sensor) {
            loadGif(R.drawable.anim_add_tem, ivGifGuide);
            tvTitleName.setText(getString(R.string.humiture_sensor));
            setStep();
        } else if (devStrId == R.string.leak_sensor) {
            loadGif(R.drawable.anim_add_leak, ivGifGuide);
            tvTitleName.setText(getString(R.string.leak_sensor));
            setStep();
        } else if (devStrId == R.string.gas_sensor) {
            loadGif(R.drawable.anim_add_gas, ivGifGuide);
            tvTitleName.setText(getString(R.string.gas_sensor));
            tvStepOne.setText(getString(R.string.add_step_one_combustible));
            tvStepTwo.setText(getString(R.string.add_step_two_combustible));
            tvStepThree.setVisibility(View.GONE);
        } else if (devStrId == R.string.smoke_sensor) {
            loadGif(R.drawable.anim_add_smoke, ivGifGuide);
            tvTitleName.setText(getString(R.string.smoke_sensor));
            tvStepOne.setText(getString(R.string.add_step_one_smoke));
            tvStepTwo.setText(getString(R.string.add_step_two_motion_sensor));
            tvStepThree.setText(getString(R.string.add_step_three_motion_sensor));
        } else if (devStrId == R.string.rcu) {
            loadGif(R.drawable.anim_add_rcu, ivGifGuide);
            tvTitleName.setText(getString(R.string.rcu));
            setStep();
        } else if (devStrId == R.string.socket) {
            loadGif(R.drawable.anim_add_socket, ivGifGuide);
            tvTitleName.setText(getString(R.string.socket));
            tvStepOne.setText(getString(R.string.add_step_one_socket));
            tvStepTwo.setText(getString(R.string.add_step_two_socket));
            tvStepThree.setText(getString(R.string.add_step_three_socket));
        }
    }

    private void loadGif(int resId, ImageView gifImageView) {
        GifDrawable drawable = GifDrawable.createFromResource(getResources(), resId);
        if (drawable == null) return;
        drawable.setLoopCount(0);
        gifImageView.setImageDrawable(drawable);
    }

    private void setStep() {
        tvStepOne.setText(getString(R.string.add_step_one_motion_sensor));
        tvStepTwo.setText(getString(R.string.add_step_two_motion_sensor));
        tvStepThree.setText(getString(R.string.add_step_three_motion_sensor));
    }

    @OnClick(R.id.id_activity_add_device_guide_next_btn)
    public void onClick(View view) {
        finish();
//        Intent intent = new Intent(this, AddNewDeviceActivity.class);
//        startActivity(intent);
        AddNewDeviceActivity.startActivity(this);
    }

    @OnClick(R.id.id_common_title_back)
    public void onClick() {
        finish();
    }
}
