package me.inori.mymvvmtest.mvvm.view.main;

import android.databinding.Observable;
import android.os.Bundle;

import com.trello.rxlifecycle.ActivityLifecycleProvider;

import java.io.File;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.inori.mymvvmtest.R;
import me.inori.mymvvmtest.base.BaseActivity;
import me.inori.mymvvmtest.base.BaseApp;
import me.inori.mymvvmtest.base.BaseConfig;
import me.inori.mymvvmtest.mvvm.service.UpdateService;
import me.inori.mymvvmtest.mvvm.utils.manager.ConnectionManager;
import me.inori.mymvvmtest.mvvm.utils.helper.FileHelper;
import me.inori.mymvvmtest.mvvm.viewmodel.SplashViewModel;
import me.inori.mymvvmtest.retrofit.ExceptionHandler;
import me.inori.mymvvmtest.retrofit.RetrofitProvider;
import me.inori.mymvvmtest.retrofit.RetrofitProvider.RetrofitType;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hjx on 2018/1/2.
 */

public class SplashActivity extends BaseActivity {
    private SplashViewModel splashViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashViewModel = new SplashViewModel(mContext);
        setBinding(R.layout.activity_splash).setVariable(me.inori.mymvvmtest.BR.viewModel,splashViewModel);

        //是否需要更新需要加监听
        splashViewModel.isneedupdate.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if(splashViewModel.isneedupdate.get()){
                    dowloadApp();
                }
            }
        } );

        splashViewModel.checkupdateApp();
    }


    private void dowloadApp()  {
        String downloadwarn="" ;
        switch (BaseApp.getBase().getConnectStatus()){
            case ConnectionManager.IS_WIFI:
                downloadwarn =getString(R.string.update_hint1_1);
                break;
            case ConnectionManager.IS_MOBILE:
                downloadwarn = getString(R.string.update_hint1_2);
                break;
        }

        SweetAlertDialog haveUpdateDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.update_hint0))
                .setContentText(downloadwarn)
                .setCancelText(getString(R.string.update_hint2))
                .setConfirmText(getString(R.string.update_hint3))
                .setCancelClickListener(dialog -> dialog.dismiss())
                .setConfirmClickListener(alertDialog -> {
                    alertDialog.dismiss();
                    SweetAlertDialog  progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                            .setTitleText(getString(R.string.update_hint4))
                            .setContentText(getString(R.string.update_hint5));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    RetrofitProvider.getInstance(RetrofitType.default_down_Type).create(UpdateService.class)
                            .getAPK()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(((ActivityLifecycleProvider) this).bindToLifecycle())
                            .subscribe(response -> {
                                try {
                                    File f = FileHelper.createFile(BaseConfig.apk_URL);
                                    BufferedSink sink = Okio.buffer(Okio.sink(f));
                                    BufferedSource source = response.source();
                                    long total = response.contentLength();
                                    long curent = 0;
                                    long len;
                                    int bufferSize =  1024; //200kb
                                    Buffer buffer = sink.buffer();
                                    progressDialog.getProgressHelper().setInstantProgress((float)curent / total);
                                    progressDialog.setContentText("已下载"+((float)curent / total)*100+"%");
                                    while ((len = source.read(buffer, bufferSize)) != -1) {
                                        sink.emit();
                                        curent += len;
                                        progressDialog.getProgressHelper().setInstantProgress((float)curent / total);
                                        progressDialog.setContentText("已下载"+((float)curent / total)*100+"%");
                                    }
                                    source.close();
                                    sink.close();
                                    base.makeToast("下载完成");
                                    base.installapk();
                                }
                                catch (IOException e){

                                }
                            }, ExceptionHandler::handleException);
                });
        haveUpdateDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
