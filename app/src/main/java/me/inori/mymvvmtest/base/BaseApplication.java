package me.inori.mymvvmtest.base;

import android.app.Application;

import java.util.Stack;

import me.inori.mymvvmtest.utils.ConnectionDetector;

/**
 * Created by hjx on 2017/12/19.
 */

public class BaseApplication extends Application {
    //退出时全部关闭
    private Stack<BaseActivity> activityStack;
    //判断网络

    @Override
    public void onCreate() {
        super.onCreate();
        activityStack = new Stack<>();
        ConnectionDetector.newinstance(this);
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

    public void destoryAllActivity(){
//        activityStack.gforEach(a->a.finish());
        for(BaseActivity base:activityStack){
            base.finish();
        }
        activityStack.clear();
        System.gc();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ConnectionDetector.destory();
    }

}
