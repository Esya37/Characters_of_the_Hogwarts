package com.example.charactersofthehogwarts.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit = null;
    private static final String baseUrl = "http://hp-api.herokuapp.com/";
    private static CharacterService characterService;

    public static CharacterService getCharacterService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            characterService = retrofit.create(CharacterService.class);
        }
        return characterService;
    }


}
