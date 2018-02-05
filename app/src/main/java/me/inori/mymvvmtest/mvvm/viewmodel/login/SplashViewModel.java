package me.inori.mymvvmtest.mvvm.viewmodel.login;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableFloat;

import com.kelin.mvvmlight.command.ReplyCommand;
import com.trello.rxlifecycle.ActivityLifecycleProvider;

import java.io.File;
import java.io.IOException;

import me.inori.mymvvmtest.base.BaseApp;
import me.inori.mymvvmtest.base.BaseConfig;
import me.inori.mymvvmtest.base.BaseViewModel;
import me.inori.mymvvmtest.mvvm.service.UpdateService;
import me.inori.mymvvmtest.mvvm.utils.helper.FileHelper;
import me.inori.mymvvmtest.mvvm.utils.helper.TextHelper;
import me.inori.mymvvmtest.mvvm.utils.manager.ConnectionManager;
import me.inori.mymvvmtest.mvvm.view.login.LoginActivity;
import me.inori.mymvvmtest.mvvm.view.main.MainActivity;
import me.inori.mymvvmtest.retrofit.ExceptionHandler;
import me.inori.mymvvmtest.retrofit.RetrofitProvider;
import me.inori.mymvvmtest.retrofit.mydownload.ProgressListener;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static me.inori.mymvvmtest.base.BaseApp.getBase;

/**
 * Created by hjx on 2018/1/8.
 */

public class SplashViewModel extends BaseViewModel {

    //是否需要更新
    public final ObservableBoolean isneedupdate = new ObservableBoolean(false);
    //是否可进入
    public final ObservableBoolean ishow = new ObservableBoolean(false);
    public final ObservableFloat process = new ObservableFloat(0);

    public final ReplyCommand loginClike = new ReplyCommand(() -> {
        BaseApp.getBase().jumpTo(LoginActivity.class);
    });
    public final ReplyCommand guestClike = new ReplyCommand(() -> {
        BaseApp.getBase().jumpTo(MainActivity.class);
    });

    public SplashViewModel(BaseApp mContext) {
        super(mContext);
    }

    public void startService(){
        if(getBase().getConnectStatus()== ConnectionManager.NO_INTENT){
            trytokenlogin();
        }
        else {
            checkupdateApp();
        }

    }

    private void checkupdateApp(){
        getBase().doCheck(result -> {if(result){
            trytokenlogin();
        }else {
            isneedupdate.set(true);
        }
        });

    }


    public void downloadapp(){
        final ProgressListener progressListener = new ProgressListener() {
            boolean firstUpdate = true;

            @Override public void update(long bytesRead, long contentLength, boolean done) {
                if (done) {
                    System.out.println("completed");
                } else {
                    if (firstUpdate) {
                        firstUpdate = false;
                        if (contentLength == -1) {
                            System.out.println("content-length: unknown");
                        } else {
                            System.out.format("content-length: %d\n", contentLength);
                        }
                    }

                    System.out.println(bytesRead);

                    if (contentLength != -1) {
                        System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);
//                        process.set((float) bytesRead / contentLength);
                    }
                }
            }
        };
        RetrofitProvider.getInstance(RetrofitProvider.RetrofitType.default_down_Type,progressListener).create(UpdateService.class)
                .getAPK()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((ActivityLifecycleProvider) mContext.getCurrentActivity()).bindToLifecycle())
                .subscribe(response -> {
                    try {
                        File f = FileHelper.createNewFile(BaseConfig.apk_URL);
                        BufferedSink sink = Okio.buffer(Okio.sink(f));
                        BufferedSource source = response.source();
                        long total = response.contentLength();
                        long curent = 0L;
                        long len;
                        long bufferSize = 1024L*20; //200kb
                        Buffer buffer = sink.buffer();
                        while ((len = source.read(buffer, bufferSize)) != -1) {
                            sink.emit();
                            curent += len;
                            System.out.print((float) curent / total);
                        }
                        source.close();
                        sink.close();
                    } catch (IOException e) {

                    }
                }, ExceptionHandler::handleException);
    }
    //尝试自动登录
    private void trytokenlogin(){
        String username = BaseApp.getBase().shread("username");
        String token = BaseApp.getBase().shread("token");

        if(TextHelper.checkEmpty(username,token)){
            ishow.set(true);
        }
        else {
            BaseApp.getBase().getUserInfo().setUsername(username);
            BaseApp.getBase().getUserInfo().setToken(token);
            BaseApp.getBase().jumpTo(MainActivity.class);
        }
    }


}
