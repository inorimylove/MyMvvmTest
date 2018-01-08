package me.inori.mymvvmtest.mvvm.view.main;

import android.os.Bundle;

import me.inori.mymvvmtest.R;
import me.inori.mymvvmtest.base.BaseActivity;

/**
 * Created by hjx on 2018/1/2.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
