package me.inori.mymvvmtest.mvvm.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableFloat;

import com.trello.rxlifecycle.ActivityLifecycleProvider;

import java.io.File;
import java.io.IOException;

import me.inori.mymvvmtest.base.BaseApp;
import me.inori.mymvvmtest.base.BaseConfig;
import me.inori.mymvvmtest.base.BaseSubscriber;
import me.inori.mymvvmtest.base.BaseViewModel;
import me.inori.mymvvmtest.mvvm.service.UpdateService;
import me.inori.mymvvmtest.mvvm.utils.helper.FileHelper;
import me.inori.mymvvmtest.retrofit.RetrofitProvider;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hjx on 2018/1/8.
 */

public class SplashViewModel extends BaseViewModel {

    //是否需要更新
    public final ObservableBoolean isneedupdate = new ObservableBoolean(false);
    //是否可进入
    public final ObservableBoolean ishow = new ObservableBoolean(false);
    public final ObservableFloat process = new ObservableFloat(0);


    public SplashViewModel(BaseApp mContext) {
        super(mContext);
        initdata();

    }

    private void initdata(){

    }

    public void checkupdateApp(){
        if(BaseApp.getBase().islastestVesion()){
            ishow.set(true);
            return;
        }else {
            isneedupdate.set(true);
        }
    }

    public void downloadapp(){
        RetrofitProvider.getInstance(RetrofitProvider.RetrofitType.default_down_Type).create(UpdateService.class)
                .getAPK()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((ActivityLifecycleProvider) mContext.getCurrentActivity()).bindToLifecycle())
                .subscribe(new BaseSubscriber<ResponseBody>(){
                    @Override
                    public void onNext(ResponseBody response) {
                        try {
                            File f = FileHelper.createFile(BaseConfig.apk_URL);
                            BufferedSink sink = Okio.buffer(Okio.sink(f));
                            BufferedSource source = response.source();
                            long total = response.contentLength();
                            long curent = 0;
                            long len;
                            int bufferSize =  1024; //200kb
                            Buffer buffer = sink.buffer();
                            while ((len = source.read(buffer, bufferSize)) != -1) {
                                sink.emit();
                                curent += len;
                                process.set((float)curent / total);
                            }
                            source.close();
                            sink.close();
                        }
                        catch (IOException e){

                        }
                    }
                }

        );
    }


}
