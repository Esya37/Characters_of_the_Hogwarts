package com.example.charactersofthehogwarts.View;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charactersofthehogwarts.Model.Character;
import com.example.charactersofthehogwarts.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Character> characters;
    private ItemClickListener clickListener;
    public boolean isClickable;

    private final Context context;

    private Character character;

    private boolean[] isExpanded;

    public RecyclerViewAdapter(Context context, List<Character> list) {
        this.characters = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        isClickable = false;
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
        private final TextView nameView;
        private final TextView dateOfBirthView;
        private final TextView genderTextView;
        private final TextView speciesTextView;
        private final TextView yearOfBirthTextView;
        private final TextView ancestryTextView;
        private final TextView eyeColourTextView;
        private final TextView hairColourTextView;
        private final TextView patronusTextView;
        private final TextView hogwartsStudentOrStaffTextView;
        private final TextView actorTextView;
        private final TextView aliveTextView;
        private final Button collapseButton;
        private final ImageView imageView;

        private final Group additionalInformationGroup;

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
            if(isClickable) {
                if (clickListener != null) {
                    clickListener.onItemClick(view, getAbsoluteAdapterPosition());
                }
            }
        }
    }


}
