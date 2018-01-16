package me.inori.mymvvmtest.mvvm.service;

import me.inori.mymvvmtest.mvvm.entity.VersionJson;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by hjx on 2018/1/16.
 */

public interface UpdateService {
    @GET ("app/version.json")
    Observable<VersionJson> getRemoteVersion();

    @GET("app/app-debug.apk")
    Observable<ResponseBody> getAPK();

}
