package com.example.charactersofthehogwarts.View.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Fade;
import androidx.transition.Scene;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

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

        postponeEnterTransition();
        inflatedView = inflater.inflate(R.layout.fragment_main, container, false);

        Button griffindorButton = (Button) inflatedView.findViewById(R.id.griffindorButton);
        Button hufflepuffButton = (Button) inflatedView.findViewById(R.id.hufflepuffButton);
        Button ravenclawButton = (Button) inflatedView.findViewById(R.id.ravenclawButton);
        Button slytherinButton = (Button) inflatedView.findViewById(R.id.slytherinButton);

        buttonSetOnClickListener(griffindorButton, "Griffindor");
        buttonSetOnClickListener(hufflepuffButton, "Hufflepuff");
        buttonSetOnClickListener(ravenclawButton, "Ravenclaw");
        buttonSetOnClickListener(slytherinButton, "Slytherin");

        startPostponedEnterTransition();
        // Inflate the layout for this fragment
        return inflatedView;
    }

    public void buttonSetOnClickListener(Button button, String faculty){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment = CharactersFragment.newInstance(faculty);
//                Scene scene1 = Scene.getSceneForLayout((ViewGroup) requireActivity().findViewById(R.id.constraintLayout), R.layout.fragment_main, inflatedView.getContext());
//                Scene scene2 = Scene.getSceneForLayout((ViewGroup) requireActivity().findViewById(R.id.constraintLayout2), R.layout.fragment_characters, inflatedView.getContext());
//                TransitionSet set = new TransitionSet();
//                set.setDuration(1000);
//                set.addTransition(new Slide());
//                TransitionManager.go(scene1, set);

                fragment.setEnterTransition(new Slide(Gravity.BOTTOM));
                fragment.setExitTransition(new Slide(Gravity.LEFT));
                fragment.setReenterTransition(new Slide(Gravity.LEFT));


                //requireParentFragment().setEnterTransition(new Fade());
                requireParentFragment().setReenterTransition(new Slide(Gravity.TOP));
                requireParentFragment().setExitTransition(new Slide(Gravity.TOP));
                //requireParentFragment().setReturnTransition(new Fade());

                fm.beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();

                //fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();

            }
        });

    }

}