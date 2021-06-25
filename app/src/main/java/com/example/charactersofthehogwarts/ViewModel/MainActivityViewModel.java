package com.example.charactersofthehogwarts.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.Model.CharactersRepository;
import com.example.charactersofthehogwarts.Model.Wand;
import com.example.charactersofthehogwarts.View.MainActivity;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    CharactersRepository charactersRepository = new CharactersRepository(MainActivity.getMainApplication());
    private LiveData<List<Character>> characters;
    private MutableLiveData<Character> selectedCharacterLiveData = new MutableLiveData<>();

//    public MainActivityViewModel(Application application){
//        super();
//        charactersRepository = new CharactersRepository(application);
//        selectedCharacterLiveData = new MutableLiveData<>();
//    }

    public MutableLiveData<Character> getSelectedCharacterLiveData() {
        return selectedCharacterLiveData;
    }

    public void setSelectedCharacterLiveData(Character selectedCharacter) {
        this.selectedCharacterLiveData.setValue(selectedCharacter);
    }

    public LiveData<List<Character>> getCharacters(String faculty)
    {
        characters = charactersRepository.getCharacters(faculty);
        return characters;
    }



}
