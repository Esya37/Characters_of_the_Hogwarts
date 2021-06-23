package com.example.charactersofthehogwarts.View.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.charactersofthehogwarts.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View inflatedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inflatedView = inflater.inflate(R.layout.fragment_main, container, false);

        Button griffindorButton = (Button) inflatedView.findViewById(R.id.griffindorButton);
        Button hufflepuffButton = (Button) inflatedView.findViewById(R.id.hufflepuffButton);
        Button ravenclawButton = (Button) inflatedView.findViewById(R.id.ravenclawButton);
        Button slytherinButton = (Button) inflatedView.findViewById(R.id.slytherinButton);

        buttonSetOnClickListener(griffindorButton, "Griffindor");
        buttonSetOnClickListener(hufflepuffButton, "Hufflepuff");
        buttonSetOnClickListener(ravenclawButton, "Ravenclaw");
        buttonSetOnClickListener(slytherinButton, "Slytherin");

        // Inflate the layout for this fragment
        return inflatedView;
    }

    public void buttonSetOnClickListener(Button button, String faculty){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment = CharactersFragment.newInstance(faculty);
                fm.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();

            }
        });

    }

}