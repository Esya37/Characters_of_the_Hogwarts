package com.example.charactersofthehogwarts.Model;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.charactersofthehogwarts.services.CharacterService;
import com.example.charactersofthehogwarts.services.RetrofitService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
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
    }


    public MutableLiveData<List<Character>> getCharacters(String faculty) {
        switch (faculty) {
            case ("Griffindor"):
                call = characterService.getGriffindorCharacters();
                call.enqueue(new Callback<List<Character>>() {
                    @Override
                    public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                        if (response.isSuccessful()) {
                            characters.setValue(response.body());
                        }
                    }

                    //TODO Вывести сообщение об отсутствии подключения при отсутствии подключения
                    @Override
                    public void onFailure(Call<List<Character>> call, Throwable t) {
                        t.printStackTrace();
                        Log.d("someTag", "something wrong ((");
                    }
                });
                break;
            case ("Hufflepuff"):
                call = characterService.getHufflepuffCharacters();
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
                break;
            case ("Ravenclaw"):
                call = characterService.getRavenclawCharacters();
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
                break;
            case ("Slytherin"):
                call = characterService.getSlytherinCharacters();
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
                break;
            default:
                break;
        }
        return characters;
    }


}
