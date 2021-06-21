package com.example.charactersofthehogwarts.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Character {
    private String name;
    private String species;
    private String gender;
    private String house;
    private String dateOfBirth;
    private String yearOfBirth;
    private String ancestry;
    private String eyeColour;
    private String hairColour;
    private Wand wand;
    private String patronus;
    private Boolean hogwartsStudent;
    private Boolean hogwartsStaff;
    private String actor;
    private Boolean alive;
    private String image;

    private DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    private Date tempDate;

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

    public Date getDateOfBirth() {

        try {
            tempDate = format.parse(this.dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
            tempDate = new Date(0);
        }
        return tempDate;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = format.format(dateOfBirth);
    }

    public int getYearOfBirth() {
        if (this.yearOfBirth == "") {
            return 0;
        } else {
            return Integer.parseInt(this.yearOfBirth);
        }
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = Integer.toString(yearOfBirth);
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

    public boolean getHogwartsStudent() {
        return this.hogwartsStudent;
    }

    public void setHogwartsStudent(boolean hogwartsStudent) {
        this.hogwartsStudent = hogwartsStudent;
    }

    public boolean getHogwartsStaff() {
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

    public boolean getAlive() {
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
