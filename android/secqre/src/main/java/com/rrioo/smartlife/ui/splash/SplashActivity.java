package com.rrioo.smartlife.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.rrioo.smartlife.base.BaseActivity;
import com.rrioo.smartlife.ui.main.activity.MainActivity;
import com.rrioo.smartlife.widget.CustomToast;

/**
 * @author zhurongkun
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
