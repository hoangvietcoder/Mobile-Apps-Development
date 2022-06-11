package com.example.notesappfinal;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomVH extends RecyclerView.ViewHolder {

    TextView nametv, createdtv;
    LinearLayout opentv;


    public RoomVH(@NonNull View itemView) {
        super(itemView);
    }

    public void setroom(FragmentActivity activity, String rooname,String adminid,String address,String time,String members,String created,String search) {
        nametv = itemView.findViewById(R.id.nametv_item);
        createdtv = itemView.findViewById(R.id.createdtv_item);
        opentv = itemView.findViewById(R.id.item_room);

        nametv.setText(rooname);
        createdtv.setText("Created by: "+ created);

    }
}
