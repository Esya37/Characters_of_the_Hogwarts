package com.example.charactersofthehogwarts.View.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.charactersofthehogwarts.R;

public class WandFragment extends Fragment {

    public WandFragment() {
        // Required empty public constructor
    }


    public static WandFragment newInstance() {
        WandFragment fragment = new WandFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wand, container, false);
    }
}