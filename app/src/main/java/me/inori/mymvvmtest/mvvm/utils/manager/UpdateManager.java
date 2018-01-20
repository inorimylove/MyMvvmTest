package me.inori.mymvvmtest.mvvm.utils.manager;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.trello.rxlifecycle.ActivityLifecycleProvider;

import me.inori.mymvvmtest.base.BaseApp;
import me.inori.mymvvmtest.mvvm.service.UpdateService;
import me.inori.mymvvmtest.retrofit.ExceptionHandler;
import me.inori.mymvvmtest.retrofit.RetrofitProvider;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hjx on 2018/1/8.
 */

public class UpdateManager {

    private BaseApp mContext;
    private String appname;

    //需要获取远程版本
    private String remoteVersion="";
    private String currentVersion;
    private boolean islastestVersion;

    private CheckCallBack checkCallBack;

    public UpdateManager(BaseApp mContext){
        this.mContext = mContext;
        islastestVersion =false;
    }


    public void doCheck(CheckCallBack checkCallBack){
        this.checkCallBack = checkCallBack;
        updateCurrentVision();
        updateRemoteVision();

    }


    private void updateRemoteVision(){
        RetrofitProvider.getInstance().create(UpdateService.class)
                .getRemoteVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((ActivityLifecycleProvider) mContext.getCurrentActivity()).bindToLifecycle())
                .subscribe(Version ->{
                    remoteVersion = Version.getVerName();
                    islastestVersion=currentVersion!=null&&currentVersion.equals(remoteVersion);
                    if(checkCallBack!=null) {
                        checkCallBack.call(islastestVersion);
                    }
                }, throwable->{
                    ExceptionHandler.handleException(throwable);
                    remoteVersion = currentVersion;
                    islastestVersion=currentVersion!=null&&currentVersion.equals(remoteVersion);
                    if(checkCallBack!=null) {
                        checkCallBack.call(islastestVersion);
                    }
                });

    }
    private void updateCurrentVision(){
        try {
            PackageInfo pInfo=mContext.getPackageManager()
                    .getPackageInfo(mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            appname = pInfo.packageName;
            currentVersion = pInfo.versionName;

        }catch (PackageManager.NameNotFoundException e){
            currentVersion = "1.0.1";
            e.printStackTrace();
        }

    }

    public interface  CheckCallBack{
        void call(boolean result);
    }
}
