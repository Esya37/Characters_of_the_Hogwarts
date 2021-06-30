package com.example.charactersofthehogwarts.View;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.charactersofthehogwarts.R;
import com.example.charactersofthehogwarts.ViewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new ViewModelProvider(this).get(MainActivityViewModel.class);

    }

}