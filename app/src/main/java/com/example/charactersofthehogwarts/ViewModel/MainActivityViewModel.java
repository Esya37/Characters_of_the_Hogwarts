package com.example.charactersofthehogwarts.ViewModel;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.Model.CharactersRepository;
import com.example.charactersofthehogwarts.Model.Wand;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends AndroidViewModel {

    private final CharactersRepository charactersRepository;
    private LiveData<List<Character>> characters;
    private LiveData<Wand> wandLiveData;
    private List<Character> characterTemp;
    private final MutableLiveData<Character> selectedCharacterLiveData = new MutableLiveData<>();
    private boolean isFromApi;
    private boolean isDataLoadedDB;
    private boolean isAllowsNavigate;
    private ExecutorService executorService;


    public MainActivityViewModel(Application application) {
        super(application);
        charactersRepository = new CharactersRepository(application);
        isFromApi = true;
        isDataLoadedDB = false;
        characterTemp = new ArrayList<>();
    }

    public boolean isFromApi() {
        return isFromApi;
    }

    public void setFromApi(boolean fromApi) {
        isFromApi = fromApi;
    }

    public MutableLiveData<Character> getSelectedCharacterLiveData() {
        return selectedCharacterLiveData;
    }

    public void setSelectedCharacterLiveData(Character selectedCharacter) {
        this.selectedCharacterLiveData.setValue(selectedCharacter);
    }

    public LiveData<List<Character>> getCharacters(String faculty) {
        if (isFromApi) {
            characters = charactersRepository.getCharacters(faculty);
            if (!isDataLoadedDB) {
                addCharactersInDB();
            }
            characters = getCharactersDB(faculty);
            isFromApi = false;
        } else {
            characters = charactersRepository.getCharactersDB(faculty);
        }
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

    public void deleteAllCharacters() {
        charactersRepository.deleteAllCharacters();
    }

    public void addCharactersInDB() {

        characters.observeForever(new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> charactersList) {
                if (!charactersList.isEmpty()) {
                    addCharacters(charactersList);
                    isDataLoadedDB = true;
                    //characters = getCharactersDB(faculty);
                    characters.removeObserver(this);
                }
            }
        });

    }

    public boolean isAllowsNavigate(String faculty, Context context) {
        isAllowsNavigate=false;
        executorService = Executors.newSingleThreadExecutor();
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() {
                if (!getCharactersDBList(faculty).isEmpty()) {
                    isAllowsNavigate = true;
                    setFromApi(false);
                } else {
                    if (hasConnection(context)) {
                        setFromApi(true);
                        isAllowsNavigate = true;
                    } else {
                        isAllowsNavigate = false;
                    }
                }
                return isAllowsNavigate;
            }
        };
        try {
            executorService.submit(callable).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        return isAllowsNavigate;
    }

    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        return wifiInfo != null && wifiInfo.isConnected();
    }

}
