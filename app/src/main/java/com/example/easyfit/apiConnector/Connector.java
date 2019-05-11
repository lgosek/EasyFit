package com.example.easyfit.apiConnector;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connector {
    private static JsonPlaceHolderApi instance;

    private Connector() {
    }

    public static synchronized JsonPlaceHolderApi getInstance() {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.73:3000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance = retrofit.create(JsonPlaceHolderApi.class);
        }

        return instance;
    }

}
