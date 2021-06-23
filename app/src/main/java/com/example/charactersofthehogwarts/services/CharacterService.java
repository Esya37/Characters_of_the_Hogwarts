package com.example.charactersofthehogwarts.services;

import com.example.charactersofthehogwarts.Model.Character;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CharacterService {

    @GET("api/characters/house/gryffindor/")
    Call<List<Character>> getGriffindorCharacters();

    @GET("api/characters/house/hufflepuff/")
    Call<List<Character>> getHufflepuffCharacters();

    @GET("api/characters/house/ravenclaw/")
    Call<List<Character>> getRavenclawCharacters();

    @GET("api/characters/house/slytherin/")
    Call<List<Character>> getSlytherinCharacters();


}
