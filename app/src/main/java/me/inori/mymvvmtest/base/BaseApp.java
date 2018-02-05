package me.inori.mymvvmtest.base;

import android.app.Application;

import java.util.Stack;

/**
 * Created by hjx on 2017/12/19.
 */

public class BaseApp extends Application {
    //退出时全部关闭
    private Stack<BaseActivity> activityStack;
    private static Base base;
    //判断网络

    @Override
    public void onCreate() {
        super.onCreate();
        activityStack = new Stack<>();
        base = Base.newinstance(this);
    }

    public void addActivity(BaseActivity bActivity){
        activityStack.push(bActivity);
    }
    public BaseActivity getCurrentActivity(){
        return activityStack.peek();
    }
    public void finishCurrentActivity(){
        activityStack.pop();
    }
    public void finishCurrentActivity(BaseActivity baseActivity){
        activityStack.remove(baseActivity);
    }
    public void destoryAllActivity(){
//        activityStack.gforEach(a->a.finish());
        for(BaseActivity base:activityStack){
            base.finish();
        }
        activityStack.clear();
        base.onDestroy();
        base = null;
        System.gc();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public static Base getBase() {
        return base;
    }
}
