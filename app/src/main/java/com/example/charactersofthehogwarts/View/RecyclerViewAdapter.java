package com.example.charactersofthehogwarts.View;

import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

    private LayoutInflater inflater;
    private List<Character> characters;
    private ItemClickListener clickListener;

    Context context;

    Character character;
    WindowManager wm;
    Display display;
    Point size;
    int width;

    private boolean[] isExpanded;

    public RecyclerViewAdapter(Context context, List<Character> list) {
        this.characters = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        width = (int) ((size.x - 20) * 0.327);

    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        character = characters.get(position);
        if (character.getName().isEmpty()) {
            holder.nameView.setText("unknown");
        } else {
            holder.nameView.setText(character.getName());
        }

        if (character.getGender().isEmpty()) {
            holder.genderTextView.setText("unknown");
        } else {
            holder.genderTextView.setText(character.getGender());
        }

        if (character.getDateOfBirth().isEmpty()) {
            holder.dateOfBirthView.setText("unknown");
        } else {
            holder.dateOfBirthView.setText(character.getDateOfBirth());
        }

        if (character.getSpecies().isEmpty()) {
            holder.speciesTextView.setText("unknown");
        } else {
            holder.speciesTextView.setText(character.getSpecies());
        }

        if (character.getYearOfBirth().isEmpty()) {
            holder.yearOfBirthTextView.setText("unknown");
        } else {
            holder.yearOfBirthTextView.setText(String.valueOf(character.getYearOfBirth()));
        }

        if (character.getAncestry().isEmpty()) {
            holder.ancestryTextView.setText("unknown");
        } else {
            holder.ancestryTextView.setText(character.getAncestry());
        }

        if (character.getEyeColour().isEmpty()) {
            holder.eyeColourTextView.setText("unknown");
        } else {
            holder.eyeColourTextView.setText(character.getEyeColour());
        }

        if (character.getHairColour().isEmpty()) {
            holder.hairColourTextView.setText("unknown");
        } else {
            holder.hairColourTextView.setText(character.getHairColour());
        }

        if (character.getPatronus().isEmpty()) {
            holder.patronusTextView.setText("unknown");
        } else {
            holder.patronusTextView.setText(character.getPatronus());
        }

        if (character.getHogwartsStaff() == true) {
            holder.hogwartsStudentOrStaffTextView.setText("staff");
        } else if (character.getHogwartsStudent() == true) {
            holder.hogwartsStudentOrStaffTextView.setText("student");
        } else {
            holder.hogwartsStudentOrStaffTextView.setText("unknown");
        }

        if (character.getActor().isEmpty()) {
            holder.actorTextView.setText("unknown");
        } else {
            holder.actorTextView.setText(character.getActor());
        }

        if (character.getAlive()) {
            holder.aliveTextView.setText("alive");
        } else {
            holder.aliveTextView.setText("dead");
        }

        holder.speciesTextView.setVisibility(View.GONE);
        holder.yearOfBirthTextView.setVisibility(View.GONE);
        holder.ancestryTextView.setVisibility(View.GONE);
        holder.eyeColourTextView.setVisibility(View.GONE);
        holder.hairColourTextView.setVisibility(View.GONE);
        holder.patronusTextView.setVisibility(View.GONE);
        holder.hogwartsStudentOrStaffTextView.setVisibility(View.GONE);
        holder.actorTextView.setVisibility(View.GONE);
        holder.aliveTextView.setVisibility(View.GONE);
        holder.speciesTextView2.setVisibility(View.GONE);
        holder.yearOfBirthTextView2.setVisibility(View.GONE);
        holder.ancestryTextView2.setVisibility(View.GONE);
        holder.eyeColourTextView2.setVisibility(View.GONE);
        holder.hairColourTextView2.setVisibility(View.GONE);
        holder.patronusTextView2.setVisibility(View.GONE);
        holder.hogwartsStudentOrStaffTextView2.setVisibility(View.GONE);
        holder.actorTextView2.setVisibility(View.GONE);
        holder.aliveTextView2.setVisibility(View.GONE);

        Picasso.with(holder.imageView.getContext()).load(character.getImage()).resize(width, 0).into(holder.imageView);

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

            collapseButton = view.findViewById(R.id.collapseButton);
            collapseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isExpanded[getAbsoluteAdapterPosition()] == false) {
                        speciesTextView.setVisibility(View.VISIBLE);
                        yearOfBirthTextView.setVisibility(View.VISIBLE);
                        ancestryTextView.setVisibility(View.VISIBLE);
                        eyeColourTextView.setVisibility(View.VISIBLE);
                        hairColourTextView.setVisibility(View.VISIBLE);
                        patronusTextView.setVisibility(View.VISIBLE);
                        hogwartsStudentOrStaffTextView.setVisibility(View.VISIBLE);
                        actorTextView.setVisibility(View.VISIBLE);
                        aliveTextView.setVisibility(View.VISIBLE);
                        speciesTextView2.setVisibility(View.VISIBLE);
                        yearOfBirthTextView2.setVisibility(View.VISIBLE);
                        ancestryTextView2.setVisibility(View.VISIBLE);
                        eyeColourTextView2.setVisibility(View.VISIBLE);
                        hairColourTextView2.setVisibility(View.VISIBLE);
                        patronusTextView2.setVisibility(View.VISIBLE);
                        hogwartsStudentOrStaffTextView2.setVisibility(View.VISIBLE);
                        actorTextView2.setVisibility(View.VISIBLE);
                        aliveTextView2.setVisibility(View.VISIBLE);
                        isExpanded[getAbsoluteAdapterPosition()] = true;
                        collapseButton.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.arrow_up_float, 0, 0, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            collapseButton.setCompoundDrawableTintList(ContextCompat.getColorStateList(context, R.color.black));
                        }
                    } else {
                        speciesTextView.setVisibility(View.GONE);
                        yearOfBirthTextView.setVisibility(View.GONE);
                        ancestryTextView.setVisibility(View.GONE);
                        eyeColourTextView.setVisibility(View.GONE);
                        hairColourTextView.setVisibility(View.GONE);
                        patronusTextView.setVisibility(View.GONE);
                        hogwartsStudentOrStaffTextView.setVisibility(View.GONE);
                        actorTextView.setVisibility(View.GONE);
                        aliveTextView.setVisibility(View.GONE);
                        speciesTextView2.setVisibility(View.GONE);
                        yearOfBirthTextView2.setVisibility(View.GONE);
                        ancestryTextView2.setVisibility(View.GONE);
                        eyeColourTextView2.setVisibility(View.GONE);
                        hairColourTextView2.setVisibility(View.GONE);
                        patronusTextView2.setVisibility(View.GONE);
                        hogwartsStudentOrStaffTextView2.setVisibility(View.GONE);
                        actorTextView2.setVisibility(View.GONE);
                        aliveTextView2.setVisibility(View.GONE);
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
