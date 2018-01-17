package me.inori.mymvvmtest.mvvm.utils.manager;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

import me.inori.mymvvmtest.base.BaseApp;
import me.inori.mymvvmtest.base.BaseConfig;

/**
 * Created by hjx on 2018/1/8.
 */

public class ApkManager {
    private BaseApp mContext;

    public ApkManager(BaseApp mContext){
        this.mContext = mContext;
    }

    public void installAppk(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(BaseConfig.apk_URL)), "application/vnd.android.package-archive");
        mContext.getCurrentActivity().startActivity(intent);
        mContext.destoryAllActivity();
    }


}
