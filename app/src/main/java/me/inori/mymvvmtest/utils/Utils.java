package me.inori.mymvvmtest.utils;

import me.inori.mymvvmtest.base.Config;

/**
 * Created by hjx on 2018/1/10.
 */

public class Utils {

    public static void mPrint(String str){
        if (Config.isDebug){
            System.out.println(str);
        }
    }
}
