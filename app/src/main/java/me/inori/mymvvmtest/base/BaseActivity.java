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

import me.inori.mymvvmtest.customView.LoadingDialog;

/**
 * Created by hjx on 2018/1/2.
 */

public class BaseActivity extends RxAppCompatActivity {


    protected BaseApplication mContext;

    private ViewDataBinding binding;
    private List<BaseViewModel> viewModels;

    private long time = 0;
    public LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (BaseApplication) getApplication();
        mContext.addActivity(this);
        loadingDialog = new LoadingDialog(this);

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
            BaseApplication.getBase().makeToast("再按一次退出");
        }else {
            mContext.destoryAllActivity();
        }
    }

    public View findBViewById(@IdRes int id){
        return binding.getRoot().findViewById(id);
    }

    public BaseActivity setBinding(ViewDataBinding dataBinding){
        binding = dataBinding;
        return this;
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



}