package me.inori.mymvvmtest.mvvm.utils.helper;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import me.inori.mymvvmtest.R;

/**
 * Created by hjx on 2018/1/10.
 */

public class UIHelper {

    //隐藏所有键盘
    public static void hideAllInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    //设置snackBar的文字颜色
    public static void setSnackbarMsgTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }

    //设置snackBar的Action文字颜色
    public static void setSnackbarActTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_action)).setTextColor(color);
    }
}
