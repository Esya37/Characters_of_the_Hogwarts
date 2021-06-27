package com.example.charactersofthehogwarts.View.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.Model.Wand;
import com.example.charactersofthehogwarts.R;
import com.example.charactersofthehogwarts.ViewModel.MainActivityViewModel;

public class WandFragment extends Fragment {

    private LiveData<Character> selectedCharacter;
    private MainActivityViewModel model;
    private View inflatedView;



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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inflatedView = inflater.inflate(R.layout.fragment_wand, container, false);

        TextView titleTextView = inflatedView.findViewById(R.id.titleWandTextView);
        TextView woodTextView = inflatedView.findViewById(R.id.woodTextView);
        TextView coreTextView = inflatedView.findViewById(R.id.coreTextView);
        TextView lengthTextView = inflatedView.findViewById(R.id.lengthTextView);

        //model = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(MainActivityViewModel.class);
        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        model.getSelectedCharacterLiveData().observe(this.getViewLifecycleOwner(), new Observer<Character>() {
            @Override
            public void onChanged(Character character) {
                titleTextView.setText("Wand of " + character.getName());
                if (character.getWand().getWood().isEmpty()) {
                    woodTextView.setText("unknown");
                } else {
                    woodTextView.setText(character.getWand().getWood());
                }

                if (character.getWand().getCore().isEmpty()) {
                    coreTextView.setText("unknown");
                } else {
                    coreTextView.setText(character.getWand().getCore());
                }

                if (character.getWand().getLength().isEmpty()) {
                    lengthTextView.setText("unknown");
                } else {
                    lengthTextView.setText(String.valueOf(character.getWand().getLength()));
                }
            }
        });
        // Inflate the layout for this fragment
        return inflatedView;
    }
}