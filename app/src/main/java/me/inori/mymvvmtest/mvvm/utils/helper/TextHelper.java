package me.inori.mymvvmtest.mvvm.utils.helper;

import me.inori.mymvvmtest.base.BaseConfig;

/**
 * Created by hjx on 2018/1/9.
 */

public class TextHelper {

    public static boolean isEmpty(String str){
        return  str!=null&&!"".equals(str);
    }

    public static void mPrint(String str){
        if (BaseConfig.isDebug){
            System.out.println(str);
        }
    }

    public static boolean checkEmpty(String... str){
        for (String s : str){
            if (isEmpty(s)) {
                return false;
            }
        }
        return true;
    }
}
