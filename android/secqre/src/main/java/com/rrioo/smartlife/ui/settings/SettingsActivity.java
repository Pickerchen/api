package com.rrioo.smartlife.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rrioo.logger.MyLog;
import com.rrioo.smartlife.R;
import com.rrioo.smartlife.base.BaseActivity;
import com.rrioo.smartlife.helper.SimpleOnItemClickListener;
import com.rrioo.smartlife.ui.settings.device.DeviceListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create By zhurongkun
 *
 * @author zhurongkun
 * @version 2017/10/13 13:43 1.0
 * @time 2017/10/13 13:43
 * @project secQreNew3.0 com.rrioo.smartlife.ui.settings
 * @description
 * @updateVersion 1.0
 * @updateTime 2017/10/13 13:43
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.id_common_title_back)
    TextView mBackTv;

    @BindView(R.id.id_activity_settings_rv)
    RecyclerView mSettingRv;
    private List<SettingsAdapter.SettingsBean> mSettings;

    @OnClick(R.id.id_common_title_back)
    public void clickBack(){
        finish();
    }

    public static void startActivity(Context from){
        from.startActivity(new Intent(from,SettingsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initData();
        initView();

    }

    private void initView() {
        mBackTv.setBackgroundResource(R.drawable.button_ripple);
        mBackTv.setClickable(true);
        mBackTv.setText(R.string.navigation_settings);

        mSettingRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        SettingsAdapter adapter =new SettingsAdapter(this, mSettings);
        mSettingRv.setAdapter(adapter);
        mSettingRv.addItemDecoration(adapter.decoration);
        adapter.setOnItemClickListener(new SimpleOnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View itemView, int position, Object data) {
                SettingsAdapter.SettingsBean settingsBean = (SettingsAdapter.SettingsBean) data;
                switch (settingsBean.getSettingType()){
                    case SettingsAdapter.SettingsBean.TYPE_NOTIFICATION:
                        break;
                    case SettingsAdapter.SettingsBean.TYPE_SECURITY:
                        break;
                    case SettingsAdapter.SettingsBean.TYPE_SCENE:
                        break;
                    case SettingsAdapter.SettingsBean.TYPE_DEVICES_ROOM:
                        MyLog.i("TYPE_DEVICES_ROOM");
                        DeviceListActivity.startActivity(SettingsActivity.this);
                        break;
                    case SettingsAdapter.SettingsBean.TYPE_PAIR_HOME:
                        break;
                    case SettingsAdapter.SettingsBean.TYPE_MEMBERS:
                        break;
                    case SettingsAdapter.SettingsBean.TYPE_SUPPORT:
                        break;

                }
            }
        });
    }

    private void initData() {
        mSettings = new ArrayList<>();
        int gap = getResources().getDimensionPixelSize(R.dimen.dp21);
        mSettings.add(new SettingsAdapter.SettingsBean(R.drawable.ic_notification_setting,
                R.string.notification,
                false,
                gap,
                SettingsAdapter.SettingsBean.TYPE_NOTIFICATION
        ));

        mSettings.add(new SettingsAdapter.SettingsBean(R.drawable.ic_mode_setting,
                R.string.security_mode
                , false, gap,
                SettingsAdapter.SettingsBean.TYPE_SECURITY));

        mSettings.add(new SettingsAdapter.SettingsBean(R.drawable.ic_scene_setting,
                R.string.scene, true, 0,
                SettingsAdapter.SettingsBean.TYPE_SCENE));

        mSettings.add(new SettingsAdapter.SettingsBean(R.drawable.ic_devices_rooms_setting,
                R.string.device_and_room, false, gap,
                SettingsAdapter.SettingsBean.TYPE_DEVICES_ROOM));

        mSettings.add(new SettingsAdapter.SettingsBean(R.drawable.ic_home_setting,
                R.string.pair_home,true,0,
                SettingsAdapter.SettingsBean.TYPE_PAIR_HOME));

        mSettings.add(new SettingsAdapter.SettingsBean(R.drawable.ic_member_setting,
                R.string.members,false,gap,
                SettingsAdapter.SettingsBean.TYPE_MEMBERS));

        mSettings.add(new SettingsAdapter.SettingsBean(R.drawable.ic_about_setting,
                R.string.support,false,gap,
                SettingsAdapter.SettingsBean.TYPE_SUPPORT));
    }
}
