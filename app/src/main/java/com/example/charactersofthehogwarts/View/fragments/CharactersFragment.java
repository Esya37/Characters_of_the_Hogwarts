package com.example.charactersofthehogwarts.View.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.Model.Wand;
import com.example.charactersofthehogwarts.R;
import com.example.charactersofthehogwarts.View.RecyclerViewAdapter;
import com.example.charactersofthehogwarts.ViewModel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CharactersFragment extends Fragment {

    private static final String ARG_FACULTY = "faculty";

    private String faculty;
    private List<Character> characterList;

    RecyclerView charactersRecyclerView;
    RecyclerViewAdapter adapter;

    private boolean isDataLoaded;
    private ExecutorService executorService;

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

    private View inflatedView;
    private MainActivityViewModel model;
    private ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            faculty = getArguments().getString(ARG_FACULTY);
        }

        pd = new ProgressDialog(getParentFragment().getContext());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();
        isDataLoaded = false;
        characterList = new ArrayList<Character>();

        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        model.getCharacters(faculty).observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                if (!characters.isEmpty()) {
                    if (characters.get(0).getId() != 0) {
                        characterList.clear();
                        characterList.addAll(characters);
                        adapter.notifyDataSetChanged();
                        isDataLoaded = true;
                    }
                }
            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        postponeEnterTransition();
        inflatedView = inflater.inflate(R.layout.fragment_characters, container, false);

        charactersRecyclerView = inflatedView.findViewById(R.id.charactersRecyclerView);

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
    public void onResume() {
        super.onResume();
        pd.dismiss();
        executorService = Executors.newSingleThreadExecutor();
        Runnable wait = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);  //Время анимации перехода между фрагментами
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                adapter.isClickable = true;
            }
        };
        executorService.execute(wait);
        executorService.shutdown();
    }

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