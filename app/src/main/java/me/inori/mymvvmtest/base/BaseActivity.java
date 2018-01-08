package me.inori.mymvvmtest.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.KeyEvent;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.inori.mymvvmtest.customView.LoadingDialog;
import me.inori.mymvvmtest.utils.SharedHelper;

/**
 * Created by hjx on 2018/1/2.
 */

public class BaseActivity extends RxAppCompatActivity {

    private static Base base;
    protected BaseApplication mContext;

    private ViewDataBinding binding;
    private List<BaseViewModel> viewModels;

    private long time = 0;
    private SharedHelper sh;
    public LoadingDialog loadingDialog;
    public SweetAlertDialog updateProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (BaseApplication) getApplication();
        mContext.addActivity(this);
        loadingDialog = new LoadingDialog(this);
//        loadingDialog.setOnCancelListener((dialogInterface -> canceled = true));
        base = Base.newinstance(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext.finishCurrentActivity();
        viewModels.stream().filter(v->v!=null).forEach(v->v.onDestroy());
        viewModels.clear();

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
            mContext.destoryAllActivity();
        }
    }

    public <T extends View> T findBViewById(@IdRes int id){
        return (T)binding.getRoot().findViewById(id);
    }

    public BaseActivity setBinding(@LayoutRes int layoutId){
        binding = DataBindingUtil.setContentView(this, layoutId);
        return this;
    }

    public BaseActivity setVariable(int id, Object v){
        binding.setVariable(id, v);
        if (v instanceof BaseViewModel){
            if (viewModels == null){
                viewModels = new ArrayList<>();
            }
            viewModels.add((BaseViewModel)v);
        }
        return this;
    }


    //set,get

    public static Base getBase() {
        return base;
    }

}