package me.inori.mymvvmtest.base;

import android.widget.Toast;

/**
 * Created by hjx on 2018/1/5.
 */

public class Base {

    private BaseApplication mContext;

    private static Base mbase;

    public Base(){

    }
    public Base(BaseApplication mContext){
            this.mContext = mContext;
    }
    public static Base newinstance(BaseApplication mContext){
        if(mbase == null){
            mbase = new Base(mContext);
        }
        return mbase;
    }
    /**
     * 通用方法
     */
    public void makeToast(String str){
        Toast.makeText(mContext.getCurrentActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
