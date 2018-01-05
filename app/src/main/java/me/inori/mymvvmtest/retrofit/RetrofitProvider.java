package me.inori.mymvvmtest.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.inori.mymvvmtest.base.Config;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static Retrofit retrofit;

    private RetrofitProvider() {
    }

    public static Retrofit getInstance() {

        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapterFactory(new ApiTypeAdapterFactory())
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.SERVER_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;

    }
}
