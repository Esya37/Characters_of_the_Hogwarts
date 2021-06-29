package com.example.charactersofthehogwarts.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "wandTable", foreignKeys = @ForeignKey(entity = Character.class, parentColumns = "id", childColumns = "characterIdFK", onDelete = ForeignKey.CASCADE))
public class Wand {
    @PrimaryKey(autoGenerate = false)
    private int id;
    private int characterIdFK;
    private String wood;
    private String core;
    private String length;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCharacterIdFK() {
        return characterIdFK;
    }

    public void setCharacterIdFK(int characterIdFK) {
        this.characterIdFK = characterIdFK;
    }

    public String getLength() {
        return this.length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWood() {
        return this.wood;
    }

    public void setWood(String wood) {
        this.wood = wood;
    }

    public String getCore() {
        return this.core;
    }

    public void setCore(String core) {
        this.core = core;
    }

}
