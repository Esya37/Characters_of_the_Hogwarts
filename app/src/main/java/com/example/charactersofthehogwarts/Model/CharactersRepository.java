package com.example.charactersofthehogwarts.Model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.charactersofthehogwarts.services.CharacterService;
import com.example.charactersofthehogwarts.services.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersRepository {

    private CharacterService characterService;
    private Call<List<Character>> call;

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

    public LiveData<List<Character>> getAllCharactersDB() {
        return characterDAO.getAllCharactersDB();
    }


    public LiveData<Wand> getWandDB(int characterIdFK) {
        return wandDAO.getWand(characterIdFK);
    }

    public AsyncTask insertCharacters(List<Character> characters) {
        AsyncTask task = new InsertCharactersAsyncTask(characterDAO, wandDAO).execute(characters);
        return task;
    }

    public static int countOfInsertedCharacters;
    public static int countOfCharacters;
    public static String faculty;

    private static class InsertCharactersAsyncTask extends AsyncTask<List<Character>, Void, List<Character>> {

        private CharacterDAO characterDAO;
        private WandDAO wandDAO;

        public InsertCharactersAsyncTask(CharacterDAO characterDAO, WandDAO wandDAO) {
            this.characterDAO = characterDAO;
            this.wandDAO = wandDAO;
        }

        @Override
        protected void onPostExecute(List<Character> characters) {
            super.onPostExecute(characters);
            characterDAO.getCharactersDB(characters.get(0).getHouse()).observeForever(new Observer<List<Character>>() {
                @Override
                public void onChanged(List<Character> charactersDB) {

                    for (int i = 0; i < characters.size(); i++) {
                        if ((faculty.equals(characters.get(0).getHouse())) && (countOfInsertedCharacters < countOfCharacters)) {
                            characters.get(i).getWand().setCharacterIdFK(charactersDB.get(i).getId());
                            new InsertWandAsyncTask(wandDAO).execute(characters.get(i).getWand());
                            countOfInsertedCharacters++;
                        }
                    }
                }
            });
        }

        @Override
        protected List<Character> doInBackground(List<Character>... lists) {
            countOfCharacters = lists[0].size();
            countOfInsertedCharacters = 0;
            faculty = lists[0].get(0).getHouse();
            for (int i = 0; i < lists[0].size(); i++) {
                characterDAO.insert(lists[0].get(i));
            }

            return lists[0];
        }
    }


    public void insertWand(Wand wand) {
        new InsertWandAsyncTask(wandDAO).execute(wand);
    }

    private static class InsertWandAsyncTask extends AsyncTask<Wand, Void, Void> {

        private WandDAO wandDAO;

        public InsertWandAsyncTask(WandDAO wandDAO) {
            this.wandDAO = wandDAO;
        }

        @Override
        protected Void doInBackground(Wand... wands) {
            wandDAO.insert(wands[0]);
            return null;
        }
    }

    public void deleteCharacters(List<Character> characters) {
        new DeleteCharactersAsyncTask(characterDAO).execute(characters);
    }

    private static class DeleteCharactersAsyncTask extends AsyncTask<List<Character>, Void, Void> {

        private CharacterDAO characterDAO;

        public DeleteCharactersAsyncTask(CharacterDAO characterDAO) {
            this.characterDAO = characterDAO;
        }

        @Override
        protected Void doInBackground(List<Character>... lists) {
            for (int i = 0; i < lists[0].size(); i++) {
                characterDAO.delete(lists[0].get(i));
            }
            return null;
        }
    }

    public void deleteWand(Wand wand) {
        new DeleteWandAsyncTask(wandDAO).execute(wand);
    }

    private static class DeleteWandAsyncTask extends AsyncTask<Wand, Void, Void> {

        private WandDAO wandDAO;

        public DeleteWandAsyncTask(WandDAO wandDAO) {
            this.wandDAO = wandDAO;
        }

        @Override
        protected Void doInBackground(Wand... wands) {
            wandDAO.delete(wands[0]);
            return null;
        }
    }

    public MutableLiveData<List<Character>> getCharacters(String faculty) {
        switch (faculty) {
            case ("Gryffindor"):
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
