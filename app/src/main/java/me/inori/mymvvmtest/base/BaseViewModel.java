package me.inori.mymvvmtest.base;

import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.messenger.Messenger;

/**
 * Created by hjx on 2018/1/5.
 */

public class BaseViewModel implements ViewModel {

    protected BaseApplication mContext;
    public BaseViewModel(BaseApplication mContext){
        this.mContext = mContext;
    }
    public void onDestroy(){
        Messenger.getDefault().unregister(mContext.getCurrentActivity());
        mContext =null;
    }
}
