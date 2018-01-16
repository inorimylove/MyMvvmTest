package me.inori.mymvvmtest.mvvm.view.main;

import android.databinding.Observable;
import android.os.Bundle;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.inori.mymvvmtest.R;
import me.inori.mymvvmtest.base.BaseActivity;
import me.inori.mymvvmtest.base.BaseApplication;
import me.inori.mymvvmtest.mvvm.viewmodel.SplashViewModel;

import static me.inori.mymvvmtest.utils.ConnectionDetector.IS_MOBILE;
import static me.inori.mymvvmtest.utils.ConnectionDetector.IS_WIFI;

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

/*        SweetAlertDialog haveUpdateDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.update_hint0))
                .setCancelText(getString(R.string.update_hint2))
                .setConfirmText(getString(R.string.update_hint3))
                .setCancelClickListener(dialog ->{
                    dialog.dismiss();
                })
                .setConfirmClickListener(alertDialog -> {
                            alertDialog.dismiss();
                        });
        haveUpdateDialog.show();*/


        //是否需要更新需要加监听
        splashViewModel.isneedupdate.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                System.out.print(splashViewModel.isneedupdate.get());

                if(splashViewModel.isneedupdate.get()){
//
//                    dowloadApp();
                }
            }
        } );
    }


    private void dowloadApp()  {
        System.out.print("你仿佛再逗我");
        BaseApplication.getBase().makeToast("开始下载");
        String downloadwarn="" ;
        switch (BaseApplication.getBase().getConnectStatus()){
            case IS_WIFI:
                downloadwarn =getString(R.string.update_hint1_1);
                break;
            case IS_MOBILE:
                downloadwarn = getString(R.string.update_hint1_2);
                break;
        }

        SweetAlertDialog haveUpdateDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.update_hint0))
                .setContentText(downloadwarn)
                .setCancelText(getString(R.string.update_hint2))
                .setConfirmText(getString(R.string.update_hint3))
                .setCancelClickListener(dialog ->{
                    dialog.dismiss();
                })
                .setConfirmClickListener(alertDialog -> {
                    alertDialog.dismiss();
                   /* SweetAlertDialog  progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
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
                                    File f = FileHelper.createFile(BaseConfig.download_URL + "/app-debug.apk");
                                    BufferedSink sink = Okio.buffer(Okio.sink(f));
                                    BufferedSource source = response.source();
                                    long total = response.contentLength();
                                    long curent = 0;
                                    long len;
                                    int bufferSize = 200 * 1024; //200kb
                                    Buffer buffer = sink.buffer();

                                    while ((len = source.read(buffer, bufferSize)) != -1) {
                                        sink.emit();
                                        curent += len;
                                        progressDialog.getProgressHelper().setInstantProgress((float)curent / total);
                                    }
                                    source.close();
                                    sink.close();
                                    BaseApplication.getBase().makeToast("下载完成");

                                }
                                catch (IOException e){

                                }
                            }, ExceptionHandler::handleException);*/
                });
        haveUpdateDialog.show();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
