package me.inori.mymvvmtest.base;

/**
 * Created by hjx on 2018/1/2.
 */

public interface Config {
    boolean isDebug = true;
    String SERVER_URL_DEBUG = "http://192.168.100.113:8080/";
    String SERVER_URL_RELEASE = "http://47.100.58.191:8888/";
    String SERVER_URL = isDebug ? SERVER_URL_DEBUG : SERVER_URL_RELEASE;

    String IMG_DEBUG = "imgtest/";
    String IMG_RELEASE = "img/";

    String IMG_PRE = isDebug ? IMG_DEBUG : IMG_RELEASE;
    String IMGURLDOWN = SERVER_URL + "gmo/" + IMG_PRE;
}
