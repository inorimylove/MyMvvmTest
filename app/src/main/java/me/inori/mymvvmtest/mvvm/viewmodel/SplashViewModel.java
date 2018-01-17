package me.inori.mymvvmtest.mvvm.viewmodel;

import android.databinding.ObservableBoolean;

import me.inori.mymvvmtest.base.BaseApp;
import me.inori.mymvvmtest.base.BaseViewModel;

/**
 * Created by hjx on 2018/1/8.
 */

public class SplashViewModel extends BaseViewModel {

    //是否需要更新
    public final ObservableBoolean isneedupdate = new ObservableBoolean(false);
    //是否可进入
    public final ObservableBoolean ishow = new ObservableBoolean(false);

    public SplashViewModel(BaseApp mContext) {
        super(mContext);
        initdata();

    }

    private void initdata(){
//        ishow.set(false);
//        isneedupdate.set(false);
    }

    public void checkupdateApp(){
        if(BaseApp.getBase().islastestVesion()){
            ishow.set(true);
            return;
        }else {
            isneedupdate.set(true);
        }
    }


}
