package me.inori.mymvvmtest.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.inori.mymvvmtest.base.BaseConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.inori.mymvvmtest.retrofit.RetrofitProvider.RetrofitType.default_Type;

public class RetrofitProvider {

    private static Retrofit retrofit;



    private RetrofitProvider() {
    }

    public static Retrofit getInstance(RetrofitType type) {



            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                    .registerTypeAdapterFactory(new ApiTypeAdapterFactory())
                    .create();


            switch (type){
                case default_down_Type:
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BaseConfig.SERVER_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                    break;
                default:
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BaseConfig.SERVER_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                    break;
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
