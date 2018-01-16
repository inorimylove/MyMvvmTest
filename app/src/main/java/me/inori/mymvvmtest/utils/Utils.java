package me.inori.mymvvmtest.utils;

import me.inori.mymvvmtest.base.BaseConfig;

/**
 * Created by hjx on 2018/1/10.
 */

public class Utils {

    public static void mPrint(String str){
        if (BaseConfig.isDebug){
            System.out.println(str);
        }
    }

    public static boolean checkEmpty(String... str){
        for (String s : str){
            if (TextUtils.isEmpty(s)) {
                return false;
            }
        }
        return true;
    }
}
