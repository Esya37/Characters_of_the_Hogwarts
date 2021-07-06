package com.example.charactersofthehogwarts.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Character.class, Wand.class}, version = 2)
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
