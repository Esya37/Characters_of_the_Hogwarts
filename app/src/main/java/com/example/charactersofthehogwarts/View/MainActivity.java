package com.example.charactersofthehogwarts.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.charactersofthehogwarts.R;

public class MainActivity extends AppCompatActivity {
//TODO Проверить, нужно ли разрешение на использование интернета на устройствах с API < 23
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}