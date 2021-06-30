package com.example.charactersofthehogwarts.View.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.Model.Wand;
import com.example.charactersofthehogwarts.R;
import com.example.charactersofthehogwarts.View.RecyclerViewAdapter;
import com.example.charactersofthehogwarts.ViewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class CharactersFragment extends Fragment {

    private static final String ARG_FACULTY = "faculty";
    private static final String ARG_SOURCE = "source";

    private String faculty;
    private String source;
    private List<Character> characterList;

    RecyclerView charactersRecyclerView;
    RecyclerViewAdapter adapter;

    private boolean isDataLoaded;
    private boolean isDataLoadedDB;
    Fragment thisFragment;

    public CharactersFragment() {
        // Required empty public constructor
    }

    public static CharactersFragment newInstance(String faculty, String source) {
        CharactersFragment fragment = new CharactersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FACULTY, faculty);
        args.putString(ARG_SOURCE, source);
        fragment.setArguments(args);
        return fragment;
    }

    View inflatedView;
    MainActivityViewModel model;
    FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            faculty = getArguments().getString(ARG_FACULTY);
            source = getArguments().getString(ARG_SOURCE);
        }
        thisFragment = this;
        isDataLoaded = false;
        isDataLoadedDB = false;
        characterList = new ArrayList<Character>();

        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        if (source.equals("internet")) {

            model.getCharacters(faculty).observe(this, new Observer<List<Character>>() {
                @Override
                public void onChanged(List<Character> characters) {
                    if (!characters.isEmpty() && (characters.get(0).getHouse().equals(faculty))) {
                        characterList.clear();
                        characterList.addAll(characters);
                        adapter.notifyDataSetChanged();
                        isDataLoaded = true;
                        addCharactersInDB();
                    }

                }
            });
        }
        if (source.equals("DB")) {
            model.getCharactersDB(faculty).observe(this, new Observer<List<Character>>() {
                @Override
                public void onChanged(List<Character> characters) {
                    characterList.clear();
                    characterList.addAll(characters);
                    adapter.notifyDataSetChanged();
                    isDataLoaded = true;

                }
            });
        }


    }


    public void addCharactersInDB() {

        model.getCharactersDB(faculty).observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                if ((characters.isEmpty()) && (!isDataLoadedDB)) {
                    if (!characterList.isEmpty()) {
                        isDataLoadedDB = true;
                        model.addCharacters(characterList);
                    }
                }
                if (characters.size() == characterList.size()) {
                    for (int i = 0; i < characters.size(); i++) {
                        characterList.get(i).setId(characters.get(i).getId());
                    }
                }
            }
        });

    }

    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
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
                if (isDataLoaded) {
                    startPostponedEnterTransition();
                    charactersRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return false;
            }
        });

        // Inflate the layout for this fragment
        return inflatedView;
    }

    NavController navController;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

    }

    private void onItemClick(View view, int i) {
        model.getWandDB(characterList.get(i).getId()).observe(getViewLifecycleOwner(), new Observer<Wand>() {
            @Override
            public void onChanged(Wand wand) {
                if (wand != null) {
                    characterList.get(i).setWand(wand);
                    model.setSelectedCharacterLiveData(characterList.get(i));
                    navController.navigate(R.id.action_charactersFragment_to_wandFragment);

                } else {
                    Toast.makeText(getContext(), "Please, check your internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}