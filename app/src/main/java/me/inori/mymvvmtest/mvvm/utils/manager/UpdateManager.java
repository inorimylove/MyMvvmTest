package me.inori.mymvvmtest.mvvm.utils.manager;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.trello.rxlifecycle.ActivityLifecycleProvider;

import me.inori.mymvvmtest.base.BaseApp;
import me.inori.mymvvmtest.base.BaseSubscriber;
import me.inori.mymvvmtest.mvvm.entity.VersionJson;
import me.inori.mymvvmtest.mvvm.service.UpdateService;
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
    private String remoteVersion;
    private String currentVersion;
    private boolean islastestVersion;

    public UpdateManager(BaseApp mContext){
        this.mContext = mContext;
        islastestVersion =false;
    }

    public boolean islastestVesion(){
        updateCurrentVision();
        updateRemoteVision();
        return currentVersion!=null&&currentVersion.equals(islastestVersion);
    }



    private void updateRemoteVision(){
        RetrofitProvider.getInstance().create(UpdateService.class)
                .getRemoteVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((ActivityLifecycleProvider) mContext.getCurrentActivity()).bindToLifecycle())
                .subscribe(new BaseSubscriber<VersionJson>(){
                    @Override
                    public void onCompleted(){
                    }
                    @Override
                    public void onNext(VersionJson versionJson) {
                        remoteVersion = versionJson.getVerName();
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
            currentVersion = "1.0.0";
            e.printStackTrace();
        }

    }

}
