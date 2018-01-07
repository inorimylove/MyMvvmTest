package me.inori.mymvvmtest.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import com.trello.rxlifecycle.components.RxActivity;

/**
 * Created by hjx on 2018/1/2.
 */

public class BaseActivity extends RxActivity {

    private static Base base;
    private BaseApplication bApplication;
    private ViewDataBinding binding;
//    private BaseViewModel viewModel;
    private long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bApplication = (BaseApplication) getApplication();
        bApplication.addActivity(this);
        base = Base.newinstance(bApplication);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bApplication.finishCurrentActivity();
//        viewModel.destory();
//        viewModel = null;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            backBtnAction();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //退出
    private void backBtnAction(){

        if(System.currentTimeMillis()-time>2000){
            time = System.currentTimeMillis();

            base.makeToast("再按一次退出");
        }else {
            bApplication.destoryAllActivity();
        }
    }
}