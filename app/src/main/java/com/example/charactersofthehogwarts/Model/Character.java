package com.example.charactersofthehogwarts.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "characterTable")
public class Character {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String species;
    private String gender;
    private String house;
    private String dateOfBirth;
    private String yearOfBirth;
    private String ancestry;
    private String eyeColour;
    private String hairColour;
    @Ignore
    private Wand wand;
    private String patronus;
    private boolean hogwartsStudent;
    private boolean hogwartsStaff;
    private String actor;
    private boolean alive;
    private String image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return this.species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHouse() {
        return this.house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getAncestry() {
        return this.ancestry;
    }

    public void setAncestry(String ancestry) {
        this.ancestry = ancestry;
    }

    public String getEyeColour() {
        return this.eyeColour;
    }

    public void setEyeColour(String eyeColour) {
        this.eyeColour = eyeColour;
    }

    public String getHairColour() {
        return this.hairColour;
    }

    public void setHairColour(String hairColour) {
        this.hairColour = hairColour;
    }

    public Wand getWand() {
        return this.wand;
    }

    public void setWand(Wand wand) {
        this.wand = wand;
    }

    public String getPatronus() {
        return this.patronus;
    }

    public void setPatronus(String patronus) {
        this.patronus = patronus;
    }

    public boolean isHogwartsStudent() {
        return this.hogwartsStudent;
    }

    public void setHogwartsStudent(boolean hogwartsStudent) {
        this.hogwartsStudent = hogwartsStudent;
    }

    public boolean isHogwartsStaff() {
        return this.hogwartsStaff;
    }

    public void setHogwartsStaff(boolean hogwartsStaff) {
        this.hogwartsStaff = hogwartsStaff;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
