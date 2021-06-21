package com.example.charactersofthehogwarts.services;

import com.example.charactersofthehogwarts.Model.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CharacterService {

    @GET("house/gryffindor/")
    Call<List<Character>> getGriffindorCharacters();

}
