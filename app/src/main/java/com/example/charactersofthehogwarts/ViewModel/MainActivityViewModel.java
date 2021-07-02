package com.example.charactersofthehogwarts.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.Model.CharactersRepository;
import com.example.charactersofthehogwarts.Model.Wand;
import com.example.charactersofthehogwarts.View.OnDeleteCompleted;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    CharactersRepository charactersRepository;
    private Application application;
    private LiveData<List<Character>> characters;
    private LiveData<Wand> wandLiveData;
    private List<Character> characterTemp;
    private final MutableLiveData<Character> selectedCharacterLiveData = new MutableLiveData<>();

    public MainActivityViewModel(Application application) {
        super(application);
        this.application = application;
        charactersRepository = new CharactersRepository(application);
    }


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

    public List<Character> getCharactersDBList(String faculty) {
        characterTemp = charactersRepository.getCharactersDBList(faculty);
        return characterTemp;
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

}
