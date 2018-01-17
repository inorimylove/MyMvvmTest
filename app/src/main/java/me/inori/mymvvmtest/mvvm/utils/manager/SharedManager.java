package me.inori.mymvvmtest.mvvm.utils.manager;

import android.content.Context;
import android.content.SharedPreferences;

import me.inori.mymvvmtest.base.BaseApp;

public class SharedManager {

    private BaseApp mContext;

    public final static String KEY_RemoteVersion = "remoteVersion";
    public final static String KEY_DownloadVesion = "downLoadVersion";
    public final static String KEY_LastUser = "lastUser";
    public final static String KEY_LastPWD = "lastPWD";

    public SharedManager(BaseApp mContext) {
        this.mContext =  mContext;
    }

    //保存
    public void save(String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences("SharedHelperSp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //读取
    public String read(String key) {
        SharedPreferences sp = mContext.getSharedPreferences("SharedHelperSp", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
}
