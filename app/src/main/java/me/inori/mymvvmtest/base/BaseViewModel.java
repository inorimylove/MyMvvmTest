package me.inori.mymvvmtest.base;

import com.kelin.mvvmlight.base.ViewModel;

/**
 * Created by hjx on 2018/1/5.
 */

public class BaseViewModel implements ViewModel {

    private BaseActivity mContext;


    public void destory(){
        mContext =null;
    }
}
