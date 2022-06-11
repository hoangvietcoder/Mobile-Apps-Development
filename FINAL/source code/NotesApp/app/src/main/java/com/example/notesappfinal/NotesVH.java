package com.example.notesappfinal;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class NotesVH extends RecyclerView.ViewHolder {

    TextView titletv, notetv, tagtv;
    ImageView imageView;
    ImageButton moreoptionsbtn;


    public NotesVH(@NonNull View itemView) {
        super(itemView);
    }

    public void setnote(FragmentActivity activity, String title, String notes, String search, String url, String delete, String type) {

        titletv = itemView.findViewById(R.id.tv_title_item);
        tagtv = itemView.findViewById(R.id.tv_tag_item);
        notetv = itemView.findViewById(R.id.tv_note_item);
        imageView = itemView.findViewById(R.id.iv_notes_item);
        moreoptionsbtn = itemView.findViewById(R.id.moreoptions_btn);

        if(type.equals("TXT")) {
            tagtv.setText("Tag: "+type);
            notetv.setText(notes);
            titletv.setText(title);
            imageView.setVisibility(View.GONE);
        } else if(type.equals("IMG")){
            tagtv.setText("Tag: "+type);
            notetv.setText(notes);
            titletv.setText(title);
            imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(url).into(imageView);
        }
    }
}
