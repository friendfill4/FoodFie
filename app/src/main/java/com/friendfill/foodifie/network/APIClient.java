package com.friendfill.foodifie.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FriendFill on 30-Jan-18.
 */
public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.61/foodfie/api/")
                // .baseUrl("https://foodfie.000webhostapp.com//api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        return retrofit;
    }

}
