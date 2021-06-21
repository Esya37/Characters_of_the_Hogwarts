package com.example.charactersofthehogwarts.Model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.charactersofthehogwarts.services.CharacterService;
import com.example.charactersofthehogwarts.services.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersRepository {

    private CharacterService characterService;
    private Call<List<Character>> call;

    private MutableLiveData<List<Character>> characters;

    public CharactersRepository(Application application) {
        characterService = RetrofitService.getRetrofitService();
        characters = new MutableLiveData<>();
        //call = characterService.getGriffindorCharacters();
    }

    public MutableLiveData<List<Character>> getGriffindorCharacters() {
        call = characterService.getGriffindorCharacters();
        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                if (response.isSuccessful()) {
                    characters.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                t.printStackTrace();
                Log.d("someTag", "something wrong ((");
            }
        });
        return characters;
    }

}
