package me.inori.mymvvmtest.mvvm.viewmodel;

import android.databinding.ObservableBoolean;

import me.inori.mymvvmtest.base.BaseApplication;
import me.inori.mymvvmtest.base.BaseViewModel;

/**
 * Created by hjx on 2018/1/8.
 */

public class SplashViewModel extends BaseViewModel {

    //是否需要更新
    public final ObservableBoolean isneedupdate = new ObservableBoolean();
    //是否可进入
    public final ObservableBoolean ishow = new ObservableBoolean();

    public SplashViewModel(BaseApplication mContext) {
        super(mContext);
        initdata();
        updateApp();
    }

    private void initdata(){
        ishow.set(false);
        isneedupdate.set(false);
    }

    private void updateApp(){
        if(isLastestVesrion()){
            ishow.set(true);
            return;
        }else {
            isneedupdate.set(true);
        }
    }

    private boolean isLastestVesrion(){
        return BaseApplication.getBase().islastestVesion();
    }

}
