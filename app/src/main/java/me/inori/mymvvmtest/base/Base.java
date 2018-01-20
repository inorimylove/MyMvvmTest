package me.inori.mymvvmtest.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import me.inori.mymvvmtest.customView.LoadingDialog;
import me.inori.mymvvmtest.mvvm.utils.helper.UIHelper;
import me.inori.mymvvmtest.mvvm.utils.manager.ApkManager;
import me.inori.mymvvmtest.mvvm.utils.manager.ConnectionManager;
import me.inori.mymvvmtest.mvvm.utils.manager.SharedManager;
import me.inori.mymvvmtest.mvvm.utils.manager.UpdateManager;


/**
 * Created by hjx on 2018/1/5.
 */

public class Base {

    private BaseApp mContext;

    private static Base mbase;
    //用户状态
    private UserInfo userInfo;
    //写入写出
    private SharedManager sHelper;
    //网络连接判断
    private ConnectionManager cManager;
    //app更新判断
    private UpdateManager uManager;

    private LoadingDialog loadingDialog;

    private ApkManager apkManager;



    public Base(BaseApp mContext){
        this.mContext = mContext;
        sHelper = new SharedManager(mContext);
        cManager = new ConnectionManager(mContext);
        uManager = new UpdateManager(mContext);
        apkManager = new ApkManager(mContext);
//            loadingDialog.setOnCancelListener((dialogInterface -> canceled = true));
    }
    public static Base newinstance(BaseApp mContext){
        if(mbase == null){
            mbase = new Base(mContext);
        }
        return mbase;
    }

    //get
    public UserInfo getUserInfo() {
        return userInfo;
    }



    /**
     * 通用方法
     */
    public void makeToast(String str){
        Toast.makeText(mContext.getCurrentActivity(), str, Toast.LENGTH_SHORT).show();
    }
    public void makeToast(@StringRes int id) {
        makeToast(mContext.getString(id));
    }



    //弹SnackBar
    public void makeSBar(String str, View v) {
        UIHelper.hideAllInput(mContext.getCurrentActivity());
        Snackbar snackbar = Snackbar.make(v, str, Snackbar.LENGTH_LONG);
        UIHelper.setSnackbarMsgTextColor(snackbar, Color.WHITE);
        snackbar.show();
    }

    //弹SnackBar
    public void makeSBar(String str) {
        View v = mContext.getCurrentActivity().getCurrentFocus();
        UIHelper.hideAllInput(mContext.getCurrentActivity());
        Snackbar snackbar = Snackbar.make(v, str, Snackbar.LENGTH_LONG);
        UIHelper.setSnackbarMsgTextColor(snackbar, Color.WHITE);
        snackbar.show();
    }
    //弹Snack Bar
    public void makeSBar(@StringRes int id, View v) {
        makeSBar(mContext.getCurrentActivity().getString(id), v);
    }

    public void jumpToAndFinish(Class dst) {
        BaseActivity baseActivity = mContext.getCurrentActivity();
        Intent intent = new Intent();
        intent.setClass(baseActivity, dst);
        baseActivity.startActivity(intent);
        baseActivity.finish();
    }

    public void jumpToAndFinish(Class dst, Bundle data) {
        BaseActivity baseActivity = mContext.getCurrentActivity();
        Intent intent = new Intent();
        intent.putExtras(data);
        intent.setClass(baseActivity, dst);
        baseActivity.startActivity(intent);
        baseActivity.finish();
    }

    public void jumpTo(Class dst) {
        BaseActivity baseActivity = mContext.getCurrentActivity();
        Intent intent = new Intent();
        intent.setClass(baseActivity, dst);
        baseActivity.startActivity(intent);
    }

    public void jumpTo(Class dst, Bundle data) {
        BaseActivity baseActivity = mContext.getCurrentActivity();
        Intent intent = new Intent();
        intent.putExtras(data);
        intent.setClass(baseActivity, dst);
        baseActivity.startActivity(intent);
    }

    public void jumpForRE(Class dst, int requestCode) {
        BaseActivity baseActivity = mContext.getCurrentActivity();
        Intent intent = new Intent();
        intent.setClass(baseActivity, dst);
        baseActivity.startActivityForResult(intent, requestCode);
    }

    public void jumpForRE(Context context, Class dst, int requestCode) {
        BaseActivity baseActivity = mContext.getCurrentActivity();
        Intent intent = new Intent();
        intent.setClass(context, dst);
        baseActivity.startActivityForResult(intent, requestCode);
    }

    public void jumpForRE(Class dst, Bundle data, int requestCode) {
        BaseActivity baseActivity = mContext.getCurrentActivity();
        Intent intent = new Intent();
        intent.putExtras(data);
        intent.setClass(baseActivity, dst);
        baseActivity.startActivityForResult(intent, requestCode);
    }

    //判断版本是否最新
   public void doCheck(UpdateManager.CheckCallBack checkCallBack) {
       uManager.doCheck(checkCallBack);
   }

    /**
     * 获取联网状态
     * NO_INTENT = -1
     * IS_WIFI = 1;
     * IS_MOBILE = 2;
     */
    public int getConnectStatus(){
        return cManager.getConnectStatus();
    }
    //SharedPreferences 存读
    public void shsave(String key,String value){
        sHelper.save(key,value);
    }

    public String shread(String key){
        return sHelper.read(key);
    }

    public void showloading(){
        loadingDialog = new LoadingDialog(mContext.getCurrentActivity());
        loadingDialog.show();
    }
    public void dismissLoading() {
        if(loadingDialog!=null&&loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;

    }
    public void installapk(){
        apkManager.installAppk();
    }

    public void onDestroy(){

    }

}
