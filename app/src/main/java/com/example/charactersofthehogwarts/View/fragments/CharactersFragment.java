package com.example.charactersofthehogwarts.View.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.Model.Wand;
import com.example.charactersofthehogwarts.R;
import com.example.charactersofthehogwarts.View.OnBackPressedListener;
import com.example.charactersofthehogwarts.View.RecyclerViewAdapter;
import com.example.charactersofthehogwarts.ViewModel.MainActivityViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CharactersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CharactersFragment extends Fragment {      //public class CharactersFragment extends Fragment implements OnBackPressedListener {

    private static final String ARG_FACULTY = "faculty";
    private static final String ARG_SOURCE = "source";

    private String faculty;
    private String source;
    private List<Character> characterList;

    RecyclerView charactersRecyclerView;
    RecyclerViewAdapter adapter;

    private boolean dataIsLoad;
    private boolean dataIsLoadDB;
    private AsyncTask task;

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


//    @Override
//    public void onBackPressed() {
//
//        if (task != null) {
//            if (task.getStatus() == AsyncTask.Status.FINISHED) {
//                fm = getParentFragmentManager();
//                fm.popBackStack();
//            }
//        } else {
//            fm = getParentFragmentManager();
//            fm.popBackStack();
//        }
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            faculty = getArguments().getString(ARG_FACULTY);
            source = getArguments().getString(ARG_SOURCE);
        }

        dataIsLoad = false;
        dataIsLoadDB = false;
        characterList = new ArrayList<Character>();

        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        if (source == "internet") {

            model.getCharacters(faculty).observe(this, new Observer<List<Character>>() {
                @Override
                public void onChanged(List<Character> characters) {
                    if (!characters.isEmpty() && (characters.get(0).getHouse().equals(faculty))) {        //&& ((faculty == model.getFaculty()) || (model.getFaculty() == null))
                        characterList.clear();
                        characterList.addAll(characters);
                        adapter.notifyDataSetChanged();
                        dataIsLoad = true;
                        addCharactersInDB();
                    }
                    //startPostponedEnterTransition();

                }
            });
        }
        if (source == "DB") {
            model.getCharactersDB(faculty).observe(this, new Observer<List<Character>>() {
                @Override
                public void onChanged(List<Character> characters) {
                    characterList.clear();
                    characterList.addAll(characters);
                    adapter.notifyDataSetChanged();
                    dataIsLoad = true;
                    //addCharactersInDB();
                    //startPostponedEnterTransition();

                }
            });
        }


    }


    public void addCharactersInDB() {

        model.getCharactersDB(faculty).observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                if ((characters.isEmpty()) && (dataIsLoadDB == false)) {
                    if (characterList.isEmpty() == false) {
                        dataIsLoadDB = true;
                        task = model.addCharacters(characterList);
                    }
                }
                if (task != null) {
                    if (task.getStatus() == AsyncTask.Status.FINISHED) {
                        task = null;
                        for (int i = 0; i < characters.size(); i++) {
                            for (int j = 0; j < characterList.size(); j++) {
                                if (characters.get(i).getName().equals(characterList.get(j).getName())) {
                                    characterList.get(j).setId(characters.get(i).getId());
                                    model.addWand(characterList.get(j).getWand(), characters.get(i).getId());
                                }
                            }
                        }
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
                if (dataIsLoad) {
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

        model.getWandDB(characterList.get(i).getId()).observe(getViewLifecycleOwner(), new Observer<Wand>() {
            @Override
            public void onChanged(Wand wand) {
                if (wand != null) {
                    characterList.get(i).setWand(wand);
                    model.setSelectedCharacterLiveData(characterList.get(i));
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Fragment fragment = WandFragment.newInstance();
                    fragment.setEnterTransition(new Slide(Gravity.RIGHT));
                    fragment.setExitTransition(new Slide(Gravity.LEFT));
                    //fm.beginTransaction().setReorderingAllowed(true).add(R.id.fragmentContainerView, fragment).hide(getParentFragment()).addToBackStack(null).commit();
                    fm.beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();

                } else {
                    Toast.makeText(getContext(), "Please, check your internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

//        if(hasConnection(getContext()) == true) {
//
//            model.setSelectedCharacterLiveData(characterList.get(i));
//            FragmentManager fm = getActivity().getSupportFragmentManager();
//            Fragment fragment = WandFragment.newInstance();
//            fragment.setEnterTransition(new Slide(Gravity.RIGHT));
//            fragment.setExitTransition(new Slide(Gravity.LEFT));
//            //fm.beginTransaction().setReorderingAllowed(true).add(R.id.fragmentContainerView, fragment).hide(getParentFragment()).addToBackStack(null).commit();
//            fm.beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
//
//        }
//        else{
//
//        }


    }


}