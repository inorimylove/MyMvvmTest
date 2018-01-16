package me.inori.mymvvmtest.utils;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.inori.mymvvmtest.base.BaseApplication;
import me.inori.mymvvmtest.base.BaseConfig;

/**
 * Created by lazyeraser on 2017/9/18.
 */


public class DBHelper extends SQLiteOpenHelper  {
    private BaseApplication mContext;
    private static final int DB_VERSION = 1;
    public DBHelper (BaseApplication mContext){
        super(mContext, BaseConfig.dbName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    //数据库升级，感觉不会调用它，先不管了
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
