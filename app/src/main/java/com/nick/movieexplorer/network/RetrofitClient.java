package com.nick.movieexplorer.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    RetrofitClient() {
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
    }

    private Retrofit retrofit =
        new Retrofit.Builder()
                .baseUrl("https://app-vpigadas.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient(loggingInterceptor))
                .build();

    public ApiInterface api  = retrofit.create(ApiInterface.class);

    private static RetrofitClient mInstance = new RetrofitClient();

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null)
            mInstance = new RetrofitClient();
        return mInstance;
    }

    private OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .readTimeout(5, java.util.concurrent.TimeUnit.MINUTES)
                .connectTimeout(5, java.util.concurrent.TimeUnit.MINUTES)
                .addInterceptor (loggingInterceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

}
