package com.example.charactersofthehogwarts.services;

import com.example.charactersofthehogwarts.Model.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CharacterService {

    @GET("api/characters/house/{faculty}/")
    Call<List<Character>> getCharacters(@Path("faculty") String faculty);

}
