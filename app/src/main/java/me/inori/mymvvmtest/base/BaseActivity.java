package me.inori.mymvvmtest.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.trello.rxlifecycle.components.RxActivity;

/**
 * Created by hjx on 2018/1/2.
 */

public class BaseActivity extends RxActivity {

    private Context mcontext;
    private Base base;
    private ViewDataBinding binding;
    private BaseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.destory();
        viewModel= null;

    }


}
