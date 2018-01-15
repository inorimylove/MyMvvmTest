package me.inori.mymvvmtest.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.inori.mymvvmtest.base.Config;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.inori.mymvvmtest.retrofit.RetrofitProvider.RetrofitType.default_Type;

public class RetrofitProvider {

    private static Retrofit retrofit;



    private RetrofitProvider() {
    }

    public static Retrofit getInstance(RetrofitType type) {

        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                    .registerTypeAdapterFactory(new ApiTypeAdapterFactory())
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.SERVER_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }


        return retrofit;

    }
    public static Retrofit getInstance(){
        return getInstance(default_Type);
    }
    //default 项目相关的，约定返回值解析方式和baseurl
    //default_down_Type,约定baseurl但是没有返回值解析方式
    //resume含断点续传
    public enum RetrofitType{
        default_Type ,default_down_Type,resume_down_Type
    }
}
