package me.inori.mymvvmtest.mvvm.view.main;

import android.databinding.Observable;
import android.os.Bundle;

import me.inori.mymvvmtest.R;
import me.inori.mymvvmtest.base.BaseActivity;
import me.inori.mymvvmtest.mvvm.viewmodel.SplashViewModel;

/**
 * Created by hjx on 2018/1/2.
 */

public class SplashActivity extends BaseActivity {
    private SplashViewModel splashViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashViewModel = new SplashViewModel(mContext);
        setBinding(R.layout.activity_splash).setVariable(me.inori.mymvvmtest.BR.viewModel,splashViewModel);
        //是否需要更新需要加监听
        splashViewModel.isneedupdate.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if(splashViewModel.isneedupdate.get()){
                    dowloadApp();
                }
            }
        } );
    }

    private void dowloadApp(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
