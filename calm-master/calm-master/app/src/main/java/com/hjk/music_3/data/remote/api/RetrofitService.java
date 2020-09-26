package com.hjk.music_3.data.remote.api;

import com.hjk.music_3.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public static Retrofit getRetro(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1,TimeUnit.MINUTES)
                .writeTimeout(1,TimeUnit.MINUTES)
                .build();
        return new Retrofit.Builder()
                .baseUrl(Constants.Retrofit_Base_Url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static Retrofit word_getRetro(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1,TimeUnit.MINUTES)
                .writeTimeout(1,TimeUnit.MINUTES)
                .build();
        return new Retrofit.Builder()
                .baseUrl(Constants.Retrofit_Word_Url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
