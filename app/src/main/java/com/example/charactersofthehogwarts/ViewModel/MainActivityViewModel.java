package com.example.charactersofthehogwarts.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.Model.CharactersRepository;
import com.example.charactersofthehogwarts.Model.Wand;
import com.example.charactersofthehogwarts.View.MainActivity;
import com.example.charactersofthehogwarts.View.OnDeleteCompleted;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    CharactersRepository charactersRepository = new CharactersRepository(MainActivity.getMainApplication());
    private LiveData<List<Character>> characters;
    private LiveData<Wand> wandLiveData;
    private MutableLiveData<Character> selectedCharacterLiveData = new MutableLiveData<>();


    public MutableLiveData<Character> getSelectedCharacterLiveData() {
        return selectedCharacterLiveData;
    }

    public void setSelectedCharacterLiveData(Character selectedCharacter) {
        this.selectedCharacterLiveData.setValue(selectedCharacter);
    }

    public LiveData<List<Character>> getCharacters(String faculty) {
        characters = charactersRepository.getCharacters(faculty);
        return characters;
    }

    public LiveData<List<Character>> getCharactersDB(String faculty) {
        characters = charactersRepository.getCharactersDB(faculty);
        return characters;
    }

    public LiveData<List<Character>> getAllCharactersDB() {
        characters = charactersRepository.getAllCharactersDB();
        return characters;
    }


    public LiveData<Wand> getWandDB(int characterIdFK) {
        wandLiveData = charactersRepository.getWandDB(characterIdFK);
        return wandLiveData;
    }

    public void addCharacters(List<Character> characterList) {
        charactersRepository.insertCharacters(characterList);
    }

    public void deleteAllCharacters(OnDeleteCompleted listener) {
        charactersRepository.deleteAllCharacters(listener);
    }

    public void deleteCharacters(List<Character> characterList, OnDeleteCompleted listener) {
        charactersRepository.deleteCharacters(characterList, listener);
    }


    public void addWand(Wand wand) {
        charactersRepository.insertWand(wand);
    }

    public void deleteWand(Wand wand) {
        charactersRepository.deleteWand(wand);
    }

}
