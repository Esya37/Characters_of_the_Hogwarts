package com.example.charactersofthehogwarts.Model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.charactersofthehogwarts.View.OnDeleteCompleted;
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

    public void insertCharacters(List<Character> characters) {
        new InsertCharactersAsyncTask(characterDAO, wandDAO).execute(characters);
    }

    public static String faculty;
    public static boolean allCharactersAdded;

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
                    if ((faculty.equals(characters.get(0).getHouse())) && (allCharactersAdded) && (charactersDB.isEmpty() == false) && (characters.size() == charactersDB.size())) {
                        for (int i = 0; i < characters.size(); i++) {
                            characters.get(i).getWand().setId(charactersDB.get(i).getId());
                            characters.get(i).getWand().setCharacterIdFK(charactersDB.get(i).getId());
                            new InsertWandAsyncTask(wandDAO).execute(characters.get(i).getWand());
                        }
                    }
                }
            });
        }

        @Override
        protected List<Character> doInBackground(List<Character>... lists) {
            faculty = lists[0].get(0).getHouse();
            allCharactersAdded = false;
            for (int i = 0; i < lists[0].size(); i++) {
                characterDAO.insert(lists[0].get(i));
            }
            allCharactersAdded = true;
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

    public void deleteAllCharacters(OnDeleteCompleted listener) {
        new DeleteAllCharactersAsyncTask(characterDAO, listener).execute();
    }

    private static class DeleteAllCharactersAsyncTask extends AsyncTask<Void, Void, Void> {

        private CharacterDAO characterDAO;
        private OnDeleteCompleted listener;

        public DeleteAllCharactersAsyncTask(CharacterDAO characterDAO, OnDeleteCompleted listener) {
            this.characterDAO = characterDAO;
            this.listener = listener;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            listener.onDeleteCompleted();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            characterDAO.deleteAll();
            return null;
        }
    }


    public void deleteCharacters(List<Character> characters, OnDeleteCompleted listener) {
        new DeleteCharactersAsyncTask(characterDAO, listener).execute(characters);
    }

    private static class DeleteCharactersAsyncTask extends AsyncTask<List<Character>, Void, Void> {

        private CharacterDAO characterDAO;
        private OnDeleteCompleted listener;

        public DeleteCharactersAsyncTask(CharacterDAO characterDAO, OnDeleteCompleted listener) {
            this.characterDAO = characterDAO;
            this.listener = listener;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            listener.onDeleteCompleted();
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
