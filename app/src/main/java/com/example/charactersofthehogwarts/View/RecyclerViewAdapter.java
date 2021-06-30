package com.example.charactersofthehogwarts.View;

import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.R;
import com.example.charactersofthehogwarts.ViewModel.MainActivityViewModel;
import com.example.charactersofthehogwarts.services.CharacterService;
import com.example.charactersofthehogwarts.services.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Character> characters;
    private ItemClickListener clickListener;

    Context context;

    Character character;

    private boolean[] isExpanded;

    public RecyclerViewAdapter(Context context, List<Character> list) {
        this.characters = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        character = characters.get(position);
        holder.nameView.setText(character.getName());
        holder.genderTextView.setText(character.getGender());
        holder.dateOfBirthView.setText(character.getDateOfBirth());
        holder.speciesTextView.setText(character.getSpecies());
        holder.yearOfBirthTextView.setText(String.valueOf(character.getYearOfBirth()));
        holder.ancestryTextView.setText(character.getAncestry());
        holder.eyeColourTextView.setText(character.getEyeColour());
        holder.hairColourTextView.setText(character.getHairColour());
        holder.patronusTextView.setText(character.getPatronus());
        if (character.isHogwartsStaff()) {
            holder.hogwartsStudentOrStaffTextView.setText("staff");
        } else if (character.isHogwartsStudent()) {
            holder.hogwartsStudentOrStaffTextView.setText("student");
        }
        holder.actorTextView.setText(character.getActor());
        if (character.isAlive()) {
            holder.aliveTextView.setText("alive");
        } else {
            holder.aliveTextView.setText("dead");
        }

        holder.additionalInformationGroup.setVisibility(View.GONE);

        Picasso.with(holder.imageView.getContext()).load(character.getImage()).resize((int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.31), 0).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameView;
        TextView dateOfBirthView;
        TextView genderTextView;
        TextView speciesTextView;
        TextView yearOfBirthTextView;
        TextView ancestryTextView;
        TextView eyeColourTextView;
        TextView hairColourTextView;
        TextView patronusTextView;
        TextView hogwartsStudentOrStaffTextView;
        TextView actorTextView;
        TextView aliveTextView;
        TextView speciesTextView2;
        TextView yearOfBirthTextView2;
        TextView ancestryTextView2;
        TextView eyeColourTextView2;
        TextView hairColourTextView2;
        TextView patronusTextView2;
        TextView hogwartsStudentOrStaffTextView2;
        TextView actorTextView2;
        TextView aliveTextView2;
        Button collapseButton;
        ImageView imageView;

        Group additionalInformationGroup;

        ViewHolder(View view) {
            super(view);

            isExpanded = new boolean[characters.size()];
            for (boolean bool : isExpanded) {
                bool = false;
            }

            nameView = view.findViewById(R.id.nameTextView);
            imageView = view.findViewById(R.id.imageView);
            dateOfBirthView = view.findViewById(R.id.dateOfBirthTextView);
            genderTextView = view.findViewById(R.id.genderTextView);
            speciesTextView = view.findViewById(R.id.speciesTextView);
            yearOfBirthTextView = view.findViewById(R.id.yearOfBirthTextView);
            ancestryTextView = view.findViewById(R.id.ancestryTextView);
            eyeColourTextView = view.findViewById(R.id.eyeColourTextView);
            hairColourTextView = view.findViewById(R.id.hairColourTextView);
            patronusTextView = view.findViewById(R.id.patronusTextView);
            hogwartsStudentOrStaffTextView = view.findViewById(R.id.hogwartsStudentOrStaffTextView);
            actorTextView = view.findViewById(R.id.actorTextView);
            aliveTextView = view.findViewById(R.id.aliveTextView);

            speciesTextView2 = view.findViewById(R.id.speciesTextView2);
            yearOfBirthTextView2 = view.findViewById(R.id.yearOfBirthTextView2);
            ancestryTextView2 = view.findViewById(R.id.ancestryTextView2);
            eyeColourTextView2 = view.findViewById(R.id.eyeColourTextView2);
            hairColourTextView2 = view.findViewById(R.id.hairColourTextView2);
            patronusTextView2 = view.findViewById(R.id.patronusTextView2);
            hogwartsStudentOrStaffTextView2 = view.findViewById(R.id.hogwartsStudentOrStaffTextView2);
            actorTextView2 = view.findViewById(R.id.actorTextView2);
            aliveTextView2 = view.findViewById(R.id.aliveTextView2);

            additionalInformationGroup = view.findViewById(R.id.additionalInformationGroup);

            collapseButton = view.findViewById(R.id.collapseButton);
            collapseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isExpanded[getAbsoluteAdapterPosition()]) {
                        additionalInformationGroup.setVisibility(View.VISIBLE);
                        isExpanded[getAbsoluteAdapterPosition()] = true;
                        collapseButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_up_float, 0, 0, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            collapseButton.setCompoundDrawableTintList(ContextCompat.getColorStateList(context, R.color.black));
                        }
                    } else {
                        additionalInformationGroup.setVisibility(View.GONE);
                        isExpanded[getAbsoluteAdapterPosition()] = false;
                        collapseButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_down_float, 0, 0, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            collapseButton.setCompoundDrawableTintList(ContextCompat.getColorStateList(context, R.color.black));
                        }
                    }


                }
            });
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAbsoluteAdapterPosition());
            }
        }
    }


}
