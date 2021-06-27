package com.example.charactersofthehogwarts.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WandDAO {

    @Insert
    void insert(Wand wand);

    @Delete
    void delete(Wand wand);

    @Query("Select * from wandTable where characterIdFK==:id")
    LiveData<Wand> getWand(int id);

}
