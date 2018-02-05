package me.inori.mymvvmtest.base;

import android.os.Environment;

/**
 * Created by hjx on 2018/1/2.
 */

public interface BaseConfig {
    boolean isDebug = true;
//    String SERVER_URL_DEBUG = "http://192.168.100.114:8888/";
    String SERVER_URL_DEBUG = "http://10.0.2.2:8888/";
    String SERVER_URL_RELEASE = "http://47.100.58.191:8888/";
    String Project_Name ="myMusic/";
    String SERVER_URL = (isDebug ? SERVER_URL_DEBUG : SERVER_URL_RELEASE)+Project_Name;
    String IMG_DEBUG = "imgtest/";
    String IMG_RELEASE = "img/";

    String IMG_PRE = isDebug ? IMG_DEBUG : IMG_RELEASE;
    String IMGURLDOWN = SERVER_URL + "iml/" + IMG_PRE;
    String download_URL = Environment.getExternalStorageDirectory()+ "/myMusicDownload";
    String apk_URL = download_URL+"/app-debug.apk";
    String dbName="myMusic.db";

}
