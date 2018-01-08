package me.inori.mymvvmtest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hjx on 2018/1/8.
 */

public class ConnectionDetector {
    public static final int NO_INTENT = -1;
    public static final int IS_WIFI = 1;
    public static final int IS_MOBILE = 2;
    private static int status = NO_INTENT;
    private static Context mcontext;

    private static  ConnectionDetector instance;

    public static ConnectionDetector newinstance(Context mcontext){
        if(instance ==null){
            instance = new ConnectionDetector(mcontext);
        }
        return  instance;
    }

    public ConnectionDetector (Context mcontext){
        this.mcontext= mcontext;
    }

    public static void destory(){
        instance = null;
        mcontext = null;
    }

    public int getConnectStatus(){
        if(instance!=null){
        ConnectivityManager cm=(ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);;
        // 获取代表联网状态的NetWorkInfo对象
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
           switch (networkInfo.getType()){
               case ConnectivityManager.TYPE_VPN:
               case ConnectivityManager.TYPE_WIFI:
                   status =IS_WIFI;
               case ConnectivityManager.TYPE_MOBILE:
               case ConnectivityManager.TYPE_MOBILE_DUN:
                   status =IS_MOBILE;
           }
           if(!networkInfo.isConnected()){
               status = NO_INTENT;
           }

        }
        return status;
    }

}
