package me.inori.mymvvmtest.mvvm.view.main;

import android.databinding.Observable;
import android.os.Bundle;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.inori.mymvvmtest.R;
import me.inori.mymvvmtest.base.BaseActivity;
import me.inori.mymvvmtest.base.BaseApp;
import me.inori.mymvvmtest.mvvm.utils.manager.ConnectionManager;
import me.inori.mymvvmtest.mvvm.viewmodel.SplashViewModel;

/**
 * Created by hjx on 2018/1/2.
 */

public class SplashActivity extends BaseActivity {
    private SplashViewModel splashViewModel;
    private SweetAlertDialog progressDialog;
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
        //是否需要更新需要加监听
        splashViewModel.process.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                float process = splashViewModel.process.get();
                if(process!=1) {
                    progressDialog.setContentText("已下载" + process * 100 + "%");
                    progressDialog.getProgressHelper().setProgress(process);
                }
                else {
                    base.makeToast("下载完成");
                }
            }
        } );
        SweetAlertDialog haveUpdateDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.update_hint0))
                .setContentText(downloadwarn)
                .setCancelText(getString(R.string.update_hint2))
                .setConfirmText(getString(R.string.update_hint3))
                .setCancelClickListener(dialog -> dialog.dismiss())
                .setConfirmClickListener(alertDialog -> {
                    alertDialog.dismiss();
                    splashViewModel.downloadapp();
                      progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                            .setTitleText(getString(R.string.update_hint4))
                            .setContentText(getString(R.string.update_hint5));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                });
        haveUpdateDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
