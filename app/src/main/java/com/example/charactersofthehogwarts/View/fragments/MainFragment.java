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
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.transition.Slide;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.R;
import com.example.charactersofthehogwarts.View.OnDeleteCompleted;
import com.example.charactersofthehogwarts.ViewModel.MainActivityViewModel;

import java.util.List;

public class MainFragment extends Fragment {

    private List<Character> characterList;

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
    Button gryffindorButton;
    Button hufflepuffButton;
    Button ravenclawButton;
    Button slytherinButton;
    Button clearDBButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        postponeEnterTransition();
        inflatedView = inflater.inflate(R.layout.fragment_main, container, false);
        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        gryffindorButton = (Button) inflatedView.findViewById(R.id.gryffindorButton);
        hufflepuffButton = (Button) inflatedView.findViewById(R.id.hufflepuffButton);
        ravenclawButton = (Button) inflatedView.findViewById(R.id.ravenclawButton);
        slytherinButton = (Button) inflatedView.findViewById(R.id.slytherinButton);
        clearDBButton = (Button) inflatedView.findViewById(R.id.clearDBButton);

        buttonSetOnClickListener(gryffindorButton, "Gryffindor");
        buttonSetOnClickListener(hufflepuffButton, "Hufflepuff");
        buttonSetOnClickListener(ravenclawButton, "Ravenclaw");
        buttonSetOnClickListener(slytherinButton, "Slytherin");


        clearDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getAllCharactersDB().observe(getViewLifecycleOwner(), new Observer<List<Character>>() {
                    @Override
                    public void onChanged(List<Character> characters) {
                        if (!characters.isEmpty()) {
                            gryffindorButton.setClickable(false);
                            hufflepuffButton.setClickable(false);
                            ravenclawButton.setClickable(false);
                            slytherinButton.setClickable(false);
                            clearDBButton.setClickable(false);
                            model.deleteAllCharacters(new OnDeleteCompleted() {
                                @Override
                                public void onDeleteCompleted() {
                                    gryffindorButton.setClickable(true);
                                    hufflepuffButton.setClickable(true);
                                    ravenclawButton.setClickable(true);
                                    slytherinButton.setClickable(true);
                                    clearDBButton.setClickable(true);
                                }
                            });

                        }
                    }
                });
            }
        });

        startPostponedEnterTransition();
        // Inflate the layout for this fragment
        return inflatedView;
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

    NavController navController;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

    }

    public void buttonSetOnClickListener(Button button, String faculty) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getCharactersDB(faculty).observe(getViewLifecycleOwner(), new Observer<List<Character>>() {
                    @Override
                    public void onChanged(List<Character> characters) {
                        if (!characters.isEmpty()) {

                            Bundle bundle = new Bundle();
                            bundle.putString("faculty", faculty);
                            bundle.putString("source", "DB");
                            navController.navigate(R.id.action_mainFragment_to_charactersFragment, bundle);

                        } else {
                            if (hasConnection(getContext())) {

                                Bundle bundle = new Bundle();
                                bundle.putString("faculty", faculty);
                                bundle.putString("source", "internet");
                                navController.navigate(R.id.action_mainFragment_to_charactersFragment, bundle);

                            } else {
                                Toast.makeText(getContext(), "Please, check your internet connection", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });

    }

}