package com.example.charactersofthehogwarts.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.Model.CharactersRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    CharactersRepository charactersRepository;
    private LiveData<List<Character>> characters;

    public MainActivityViewModel(Application application){
        super(application);

        charactersRepository = new CharactersRepository(application);
    }

    public LiveData<List<Character>> getCharacters(String faculty)
    {
        characters = charactersRepository.getCharacters(faculty);
        return characters;
    }



}
