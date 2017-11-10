package com.rrioo.smartlife.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.R;
import com.rrioo.smartlife.SecApp;
import com.rrioo.smartlife.base.BaseActivity;
import com.rrioo.smartlife.ui.main.fragment.CameraFragment;
import com.rrioo.smartlife.ui.main.fragment.HomeFragment;
import com.rrioo.smartlife.ui.settings.SettingsActivity;
import com.rrioo.smartlife.util.NullUtil;
import com.rrioo.smartlife.widget.CustomToast;
import com.sen5.lib.api.Api;
import com.sen5.lib.api.event.EventConnect;
import com.sen5.lib.api.event.EventVerify;
import com.sen5.lib.connection.P2pParameter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhurongkun
 */
public class MainActivity extends BaseActivity {

    private static final long EXIT_TIME_INTERVAL = 1000L;
    @BindView(R.id.id_activity_main_vp)
    ViewPager mFragmentVp;

    @BindView(R.id.id_activity_main_text_home)
    TextView mHomeTv;

    @BindView(R.id.id_activity_main_text_home_line)
    View mHomeLine;

    @BindView(R.id.id_activity_main_text_camera)
    TextView mCameraTv;

    @BindView(R.id.id_activity_main_text_camera_line)
    View mCameraLine;

    @BindView(R.id.id_activity_main_setting)
    ImageView mSettingIv;


    private HomeFragment mHomeFragment;
    private CameraFragment mCameraFragment;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        EventBus.getDefault().register(this);
        initData();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.id_activity_main_text_home, R.id.id_activity_main_text_home_line})
    public void clickHome() {
        mFragmentVp.setCurrentItem(0, true);
        onHomeSeleted();
    }

    @OnClick({R.id.id_activity_main_text_camera, R.id.id_activity_main_text_camera_line})
    public void clickCamera() {
        mFragmentVp.setCurrentItem(1, true);
        onCameraSeleted();
    }

    @OnClick(R.id.id_activity_main_setting)
    public void clickSettings() {
        SettingsActivity.startActivity(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventConnect eventConnect) {
        if (eventConnect.getSession() > 0) {
            //connect successfully
            Api.get().getSender().verify();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventVerify eventVerify) {
        //verify successfully
        Api.get().getSender().deviceList();
        Api.get().getSender().securityModeList();
        Api.get().getSender().sceneList();
        Api.get().getSender().roomList();
        Api.get().getSender().getAllDeviceStatus();
        cancelLoading();
    }


    private void onCameraSeleted() {
        mCameraTv.setTextColor(getResources().getColor(R.color.text_blue));
        mCameraLine.setVisibility(View.VISIBLE);
        mHomeTv.setTextColor(getResources().getColor(R.color.text_white));
        mHomeLine.setVisibility(View.GONE);
    }

    private void onHomeSeleted() {
        mHomeTv.setTextColor(getResources().getColor(R.color.text_blue));
        mHomeLine.setVisibility(View.VISIBLE);
        mCameraTv.setTextColor(getResources().getColor(R.color.text_white));
        mCameraLine.setVisibility(View.GONE);
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mCameraFragment = new CameraFragment();
        mFragmentVp.setAdapter(mFragmentAdapter);
        mFragmentVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) onHomeSeleted();
                else if (position == 1) onCameraSeleted();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initData() {
        if (!NullUtil.isEmpty(app.getBoxId())) {
            MyLog.i("init " + app.getBoxId());
            Api.get().getSender().startConncet(new P2pParameter(app.getBoxId()));
            showLoading();
        }
    }

    private FragmentPagerAdapter mFragmentAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return position == 0 ? mHomeFragment
                    : mCameraFragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (mHomeFragment != null) {
            if (mHomeFragment.onBackPressed()) {
                return;
            }
        }
        long timeNow = System.currentTimeMillis();
        if (timeNow - mExitTime < EXIT_TIME_INTERVAL) {
            SecApp.get().exit();
        } else {
            mExitTime = timeNow;
            CustomToast.makeText(this, R.string.press_again_to_exit)
                    .show();
        }
    }
}
