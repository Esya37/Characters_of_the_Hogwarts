package com.example.charactersofthehogwarts.Model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.charactersofthehogwarts.View.OnDeleteCompleted;
import com.example.charactersofthehogwarts.services.CharacterService;
import com.example.charactersofthehogwarts.services.RetrofitService;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersRepository {

    private CharacterService characterService;
    private Call<List<Character>> call;
    ExecutorService executorService;

    private CharacterDAO characterDAO;
    private WandDAO wandDAO;

    private MutableLiveData<List<Character>> characters;

    public CharactersRepository(Application application) {
        CharactersDB charactersDB = CharactersDB.getCharactersDB(application);
        characterDAO = charactersDB.getCharacterDAO();
        wandDAO = charactersDB.getWandDAO();
        characterService = RetrofitService.getRetrofitService();
        characters = new MutableLiveData<>();

    }

    public LiveData<List<Character>> getCharactersDB(String faculty) {
        return characterDAO.getCharactersDB(faculty);
    }

    public List<Character> getCharactersDBList(String faculty) {
        return characterDAO.getCharactersDBList(faculty);
    }

    public LiveData<List<Character>> getAllCharactersDB() {
        return characterDAO.getAllCharactersDB();
    }

    public LiveData<Wand> getWandDB(int characterIdFK) {
        return wandDAO.getWand(characterIdFK);
    }

    public void insertCharacters(List<Character> characters) {

        executorService = Executors.newSingleThreadExecutor();
        Callable<Boolean> insertCharacters = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                for (int i = 0; i < characters.size(); i++) {
                    characterDAO.insert(characters.get(i));
                }
                return null;
            }
        };
        Callable<Boolean> insertWand = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                List<Character> charactersDB = characterDAO.getCharactersDBList(characters.get(0).getHouse());
                for (int i = 0; i < characters.size(); i++) {
                    characters.get(i).getWand().setId(charactersDB.get(i).getId());
                    characters.get(i).getWand().setCharacterIdFK(charactersDB.get(i).getId());
                    wandDAO.insert(characters.get(i).getWand());
                }
                return null;
            }

        };

        try {
            executorService.submit(insertCharacters).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            executorService.submit(insertWand).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

    }

    public void insertWand(Wand wand) {
        executorService = Executors.newSingleThreadExecutor();
        Callable<Boolean> insertWand = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                wandDAO.insert(wand);
                return null;
            }

        };
        try {
            executorService.submit(insertWand).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public void deleteAllCharacters(OnDeleteCompleted listener) {
        executorService = Executors.newSingleThreadExecutor();
        Callable<Boolean> deleteAllCharacters = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                characterDAO.deleteAll();
                return null;
            }

        };
        try {
            executorService.submit(deleteAllCharacters).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        listener.onDeleteCompleted();
        executorService.shutdown();

    }

    public MutableLiveData<List<Character>> getCharacters(String faculty) {

        call = characterService.getCharacters(faculty);
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
