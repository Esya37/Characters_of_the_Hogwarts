package com.example.charactersofthehogwarts.View.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.R;
import com.example.charactersofthehogwarts.View.RecyclerViewAdapter;
import com.example.charactersofthehogwarts.ViewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharactersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharactersFragment extends Fragment {

    private static final String ARG_FACULTY = "faculty";

    private String faculty;
    private List<Character> characterList;

    RecyclerView charactersRecyclerView;
    RecyclerViewAdapter adapter;

    private boolean dataIsLoad;

    public CharactersFragment() {
        // Required empty public constructor
    }

    public static CharactersFragment newInstance(String faculty) {
        CharactersFragment fragment = new CharactersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FACULTY, faculty);
        fragment.setArguments(args);
        return fragment;
    }

    View inflatedView;
    MainActivityViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            faculty = getArguments().getString(ARG_FACULTY);
        }

        dataIsLoad = false;
        characterList = new ArrayList<Character>();

        //model = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(MainActivityViewModel.class);
        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        model.getCharacters(faculty).observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                characterList.clear();
                characterList.addAll(characters);
                adapter.notifyDataSetChanged();
                dataIsLoad=true;
                //startPostponedEnterTransition();

            }
        });


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        postponeEnterTransition();
        inflatedView = inflater.inflate(R.layout.fragment_characters, container, false);

        charactersRecyclerView = (RecyclerView) inflatedView.findViewById(R.id.charactersRecyclerView);

        charactersRecyclerView.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));
        adapter = new RecyclerViewAdapter(inflatedView.getContext(), characterList);
        adapter.setClickListener(this::onItemClick);
        charactersRecyclerView.setAdapter(adapter);

        ViewTreeObserver viewTreeObserver = charactersRecyclerView.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if(dataIsLoad==true) {
                    startPostponedEnterTransition();
                    charactersRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return false;
            }
        });

        // Inflate the layout for this fragment
        return inflatedView;
    }

    private void onItemClick(View view, int i) {

        model.setSelectedCharacterLiveData(characterList.get(i));
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = WandFragment.newInstance();
        fragment.setEnterTransition(new Slide(Gravity.RIGHT));
        fragment.setExitTransition(new Slide(Gravity.LEFT));
        fm.beginTransaction().setReorderingAllowed(true).add(R.id.fragmentContainerView, fragment).hide(this).addToBackStack(null).commit();

    }


}