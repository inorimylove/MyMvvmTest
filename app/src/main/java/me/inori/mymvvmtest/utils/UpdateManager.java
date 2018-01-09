package me.inori.mymvvmtest.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.inori.mymvvmtest.base.Config;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hjx on 2018/1/8.
 */

public class UpdateManager {

    private Context mContext;
    private String appname;

    //需要获取远程版本
    private String remoteVersion;
    private String currentVersion;
    private boolean islastestVersion;

    public UpdateManager(Context mContext){
        this.mContext = mContext;
        islastestVersion =false;
    }

    public boolean islastestVesion(){
        updateCurrentVision();
        updateRemoteVision();
        return currentVersion!=null&&currentVersion.equals(islastestVersion);
    }



    private void updateRemoteVision(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(Config.APP_CheckUpdateURL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String verjson = response.body().string();
                    if(!TextUtils.isEmpty(verjson)){
                        try {
                            JSONObject obj = new JSONObject(verjson);
                            remoteVersion = obj.getString("verName");
                        }catch (JSONException e){
                            remoteVersion = currentVersion;
                        }
                    }
                }
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
