package com.example.charactersofthehogwarts.View.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.Fade;
import androidx.transition.Scene;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.R;
import com.example.charactersofthehogwarts.ViewModel.MainActivityViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        postponeEnterTransition();
        inflatedView = inflater.inflate(R.layout.fragment_main, container, false);
        model = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        Button griffindorButton = (Button) inflatedView.findViewById(R.id.griffindorButton);
        Button hufflepuffButton = (Button) inflatedView.findViewById(R.id.hufflepuffButton);
        Button ravenclawButton = (Button) inflatedView.findViewById(R.id.ravenclawButton);
        Button slytherinButton = (Button) inflatedView.findViewById(R.id.slytherinButton);
        Button clearDBButton = (Button) inflatedView.findViewById(R.id.clearDBButton);

        buttonSetOnClickListener(griffindorButton, "Gryffindor");
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
                            model.deleteCharacters(characters);
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

    public void buttonSetOnClickListener(Button button, String faculty) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getCharactersDB(faculty).observe(getViewLifecycleOwner(), new Observer<List<Character>>() {
                    @Override
                    public void onChanged(List<Character> characters) {
                        if (characters.isEmpty() == false) {
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            Fragment fragment = CharactersFragment.newInstance(faculty, "DB");

                            fragment.setEnterTransition(new Slide(Gravity.BOTTOM));
                            fragment.setExitTransition(new Slide(Gravity.LEFT));
                            fragment.setReenterTransition(new Slide(Gravity.LEFT));

                            requireParentFragment().setReenterTransition(new Slide(Gravity.TOP));
                            requireParentFragment().setExitTransition(new Slide(Gravity.TOP));

                            fm.beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();

                        } else {
                            if (hasConnection(getContext()) == true) {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                Fragment fragment = CharactersFragment.newInstance(faculty, "internet");

                                fragment.setEnterTransition(new Slide(Gravity.BOTTOM));
                                fragment.setExitTransition(new Slide(Gravity.LEFT));
                                fragment.setReenterTransition(new Slide(Gravity.LEFT));

                                requireParentFragment().setReenterTransition(new Slide(Gravity.TOP));
                                requireParentFragment().setExitTransition(new Slide(Gravity.TOP));

                                fm.beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
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