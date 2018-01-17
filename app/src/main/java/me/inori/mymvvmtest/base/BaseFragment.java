package me.inori.mymvvmtest.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjx on 2018/1/2.
 */

public class BaseFragment extends RxFragment {
    protected BaseApp mContext;
    protected BaseActivity bActivity;
    protected Base base;

    private ViewDataBinding binding;
    private List<BaseViewModel> viewModels;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bActivity = (BaseActivity) getActivity();
        mContext = (BaseApp) bActivity.getApplication();
        base = BaseApp.getBase();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public BaseFragment setBinding(@LayoutRes int layoutId, LayoutInflater inflater, @Nullable ViewGroup container){
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        return this;
    }

    public BaseFragment setVariable(int id, Object v){
        binding.setVariable(id, v);
        if (v instanceof BaseViewModel){
            if (viewModels == null){
                viewModels = new ArrayList<>();
            }
            viewModels.add((BaseViewModel)v);
        }
        return this;
    }


    public <T extends View> T getBView(@IdRes int id){
        return (T)binding.getRoot().findViewById(id);
    }
    protected void initView(){
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewModels != null){
            viewModels.stream().filter(v->v!=null).forEach(v->v.onDestroy());
        }
    }

}
