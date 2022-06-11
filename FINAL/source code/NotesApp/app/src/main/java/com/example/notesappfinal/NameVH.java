package com.example.notesappfinal;

import android.app.Application;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NameVH  extends RecyclerView.ViewHolder {

    TextView nametv;
    CheckBox checkBox;

    public NameVH(@NonNull View itemView) {
        super(itemView);
    }

    public void setname(Application application, String name, String category, String search) {
        nametv = itemView.findViewById(R.id.add_usernametv);
        checkBox = itemView.findViewById(R.id.cb_adduser);

        nametv.setText(name);
    }
}
