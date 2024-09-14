package com.ambitious.parampara.Other;


import com.ambitious.parampara.Connection.LoadInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by user1 on 11/16/2017.
 */

public class AppConfig {
    private static Retrofit retrofit = null;
    private static LoadInterface loadInterface = null;
    private static String BASEURL = "http://vedicparampara.com/panel/index.php/api/";
    private static String BASEIMGURL = "http://localhost/parampara/index.php/api/";

    private static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(10, TimeUnit.MINUTES).build();
            retrofit = new Retrofit.Builder().baseUrl(BASEURL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

    public static LoadInterface loadInterface() {
        if (loadInterface == null) {
            loadInterface = AppConfig.getClient().create(LoadInterface.class);
        }
        return loadInterface;
    }


}
