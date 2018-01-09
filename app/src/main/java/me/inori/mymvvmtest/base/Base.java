package me.inori.mymvvmtest.base;

import android.widget.Toast;

import me.inori.mymvvmtest.utils.ConnectionDetector;
import me.inori.mymvvmtest.utils.SharedHelper;
import me.inori.mymvvmtest.utils.UpdateManager;

/**
 * Created by hjx on 2018/1/5.
 */

public class Base {

    private BaseApplication mContext;

    private static Base mbase;
    //写入写出
    private SharedHelper sHelper;
    //网络连接判断
    private ConnectionDetector cDetector;
    //app更新判断
    private UpdateManager uManager;

    public Base(BaseApplication mContext){
        this.mContext = mContext;
        sHelper = new SharedHelper(mContext);
        cDetector = new ConnectionDetector(mContext);
        uManager = new UpdateManager(mContext);
//            loadingDialog.setOnCancelListener((dialogInterface -> canceled = true));
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
    //判断版本是否最新
    public boolean islastestVesion(){
        return uManager.islastestVesion();
    }
    /**
     * 获取联网状态
     * NO_INTENT = -1
     * IS_WIFI = 1;
     * IS_MOBILE = 2;
     */
    public int getConnectStatus(){
        return cDetector.getConnectStatus();
    }
    //SharedPreferences 存读
    public void shsave(String key,String value){
        sHelper.save(key,value);
    }

    public String shread(String key){
        return sHelper.read(key);
    }

    public void onDestroy(){

    }
}
