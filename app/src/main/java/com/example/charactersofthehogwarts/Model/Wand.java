package com.example.charactersofthehogwarts.Model;

public class Wand {
    private String wood;
    private String core;
    private String length;

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

    public float getLength() {
        if (length.isEmpty()) {
            return 0;
        } else {
            return Float.parseFloat(this.length);
        }
    }

    public void setLength(float length) {
        this.length = Float.toString(length);
    }
}
