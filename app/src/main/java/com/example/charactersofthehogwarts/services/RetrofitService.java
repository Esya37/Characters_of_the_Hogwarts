package com.example.charactersofthehogwarts.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit = null;
    private static final String baseUrl = "http://hp-api.herokuapp.com/";

    public static CharacterService getRetrofitService()
    {
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(CharacterService.class);
    }

}
