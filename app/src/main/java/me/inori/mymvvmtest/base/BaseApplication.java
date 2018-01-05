package me.inori.mymvvmtest.base;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hjx on 2017/12/19.
 */

public class BaseApplication extends Application {
    //退出时全部关闭
    private Map<String,BaseActivity> activityMap;

    @Override
    public void onCreate() {
        super.onCreate();
        activityMap = new HashMap<>();
    }

    public void addActivity(BaseActivity bActivity){
        activityMap.put(bActivity.getClass().getName(),bActivity);
    }

    public void destoryAllActivity(){
        activityMap.forEach((name,activity)->activity.finish());
        activityMap.clear();
        System.gc();
    }


}
