package com.example.charactersofthehogwarts.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Character.class, Wand.class}, version = 1)
public abstract class CharactersDB extends RoomDatabase {
    private static CharactersDB charactersDB;

    public abstract CharacterDAO getCharacterDAO();

    public abstract WandDAO getWandDAO();

    public static CharactersDB getCharactersDB(Context context) {
        if (charactersDB == null) {
            charactersDB = Room.databaseBuilder(context.getApplicationContext(), CharactersDB.class, "charactersDB").fallbackToDestructiveMigration().build();
        }
        return charactersDB;
    }


}
