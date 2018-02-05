package me.inori.mymvvmtest.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import me.inori.mymvvmtest.base.BaseConfig;
import me.inori.mymvvmtest.retrofit.mydownload.ProgressListener;
import me.inori.mymvvmtest.retrofit.mydownload.ProgressResponseBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.inori.mymvvmtest.retrofit.RetrofitProvider.RetrofitType.default_Type;
import static me.inori.mymvvmtest.retrofit.RetrofitProvider.RetrofitType.default_down_Type;

public class RetrofitProvider {

    private static Retrofit retrofit;



    private RetrofitProvider() {
    }

    public static Retrofit getInstance(RetrofitType type,ProgressListener progressListener) {


            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                    .registerTypeAdapterFactory(new ApiTypeAdapterFactory())
                    .create();
        OkHttpClient client=new OkHttpClient.Builder().build();
        if(type==default_down_Type) {
         client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response originalResponse = chain.proceed(chain.request());
                            return originalResponse.newBuilder()
                                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                    .build();
                        }
                    })
                    .build();
        }
            switch (type){
                case default_down_Type:
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BaseConfig.SERVER_URL)
                            .client(client)
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
    public static Retrofit getInstance() {
        return getInstance(default_Type,null);
    }
    //default 项目相关的，约定返回值解析方式和baseurl
    //default_down_Type,约定baseurl但是没有返回值解析方式
    //resume含断点续传
    public enum RetrofitType{
        default_Type ,default_down_Type,resume_down_Type
    }
}
