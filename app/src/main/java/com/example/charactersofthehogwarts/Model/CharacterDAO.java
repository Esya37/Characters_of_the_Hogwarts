package com.example.charactersofthehogwarts.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CharacterDAO {

    @Insert
    void insert(Character character);

    @Delete
    void delete(Character character);

    @Query("Select * from characterTable where house ==:faculty")
    LiveData<List<Character>> getCharactersDB(String faculty);

    @Query("Select * from characterTable")
    LiveData<List<Character>> getAllCharactersDB();

}
