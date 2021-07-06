package com.example.charactersofthehogwarts.View.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.R;
import com.example.charactersofthehogwarts.ViewModel.MainActivityViewModel;

import java.util.List;

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
    MainActivityViewModel model;
    Button facultyButton;
    Button clearDBButton;
    private boolean isNavigated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        postponeEnterTransition();
        inflatedView = inflater.inflate(R.layout.fragment_main, container, false);
        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        clearDBButton = inflatedView.findViewById(R.id.clearDBButton);

        buttonSetOnClickListener(R.id.gryffindorButton, "Gryffindor");
        buttonSetOnClickListener(R.id.hufflepuffButton, "Hufflepuff");
        buttonSetOnClickListener(R.id.ravenclawButton, "Ravenclaw");
        buttonSetOnClickListener(R.id.slytherinButton, "Slytherin");

        clearDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getAllCharactersDB().observe(getViewLifecycleOwner(), new Observer<List<Character>>() {
                    @Override
                    public void onChanged(List<Character> characters) {
                        if (!characters.isEmpty()) {
                            model.deleteAllCharacters();
                        }
                    }
                });
            }
        });

        startPostponedEnterTransition();
        // Inflate the layout for this fragment
        return inflatedView;
    }

    NavController navController;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        isNavigated = false;
    }

    public void buttonSetOnClickListener(int buttonId, String faculty) {

        facultyButton = inflatedView.findViewById(buttonId);
        facultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facultyButton.setClickable(false);
                if (!isNavigated) {
                    if (model.isAllowsNavigate(faculty, getContext())) {
                        Bundle bundle = new Bundle();
                        bundle.putString("faculty", faculty);
                        isNavigated = true;
                        navController.navigate(R.id.action_mainFragment_to_charactersFragment, bundle);
                    } else {
                        Toast.makeText(getContext(), "Please, check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }
                facultyButton.setClickable(true);
            }
        });

    }

}