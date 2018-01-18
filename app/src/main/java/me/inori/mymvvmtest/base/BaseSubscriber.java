package me.inori.mymvvmtest.base;

import me.inori.mymvvmtest.mvvm.utils.manager.ConnectionManager;
import me.inori.mymvvmtest.retrofit.ExceptionHandler;
import rx.Subscriber;

/**
 * Created by hjx on 2018/1/17.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onStart() {
        super.onStart();
        if (BaseApp.getBase().getConnectStatus()== ConnectionManager.NO_INTENT) {
            BaseApp.getBase().makeToast("无网络连接");
            // **一定要主动调用下面这一句**
            onCompleted();
            return;
        }
    }

    @Override
    public void onCompleted() {

    }
    @Override
    public void onError(Throwable e){
        ExceptionHandler.handleException(e);
    }

}
