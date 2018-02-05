package me.inori.mymvvmtest.mvvm.viewmodel.login;

import me.inori.mymvvmtest.base.BaseApp;
import me.inori.mymvvmtest.base.BaseViewModel;

/**
 * Created by hjx on 2018/1/8.
 */

public class LoginViewModel extends BaseViewModel {

 /*   //是否需要更新
    public final ObservableBoolean isneedupdate = new ObservableBoolean(false);
    //是否可进入
    public final ObservableBoolean ishow = new ObservableBoolean(false);
    public final ObservableFloat process = new ObservableFloat(0);

    public final ReplyCommand loginClike = new ReplyCommand(() -> {
        BaseApp.getBase().jumpTo(LoginActivity.class);
    });
    public final ReplyCommand guestClike = new ReplyCommand(() -> {
        BaseApp.getBase().jumpTo(MainActivity.class);
    });*/

    public LoginViewModel(BaseApp mContext) {
        super(mContext);
    }

/*
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
        RetrofitProvider.getInstance(RetrofitProvider.RetrofitType.default_down_Type).create(UpdateService.class)
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
                        long curent = 0;
                        long len;
                        int bufferSize = 1024*20; //200kb
                        Buffer buffer = sink.buffer();
                        while ((len = source.read(buffer, bufferSize)) != -1) {
                            sink.emit();
                            curent += len;
                            process.set((float) (curent / total));
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
*/


}
