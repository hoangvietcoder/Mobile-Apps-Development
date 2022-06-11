package com.example.notesappfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OpenRoom extends AppCompatActivity {

    String roomname, address, adminid, name, category, currentuid, status;
    Button adduserbtn;
    TextView roomnametv;
    ImageButton sendbtn, addbtn;
    EditText noteEt;
    DatabaseReference nameref,roomlist,roomref,memberRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Uri selecteduri;
    RoomnoteMember roomnoteMember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_room);

        storageReference = storage.getReference("files");
        roomnametv = findViewById(R.id.nameRoom_v2);
        adduserbtn = findViewById(R.id.add_people);
        addbtn = findViewById(R.id.ib_add_or);
        sendbtn = findViewById(R.id.ib_send);
        noteEt = findViewById(R.id.edit_message);
        recyclerView = findViewById(R.id.recyclerView2);

        linearLayoutManager = new LinearLayoutManager(OpenRoom.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentuid= user.getUid();

        nameref = database.getReference("users");

        Bundle extras = getIntent().getExtras();

        if (extras!= null){
            roomname = extras.getString("rn");
            address =  extras.getString("a");
            adminid = extras.getString("ai");
        }else {
            Toast.makeText(this, "value missing", Toast.LENGTH_SHORT).show();
        }

//        FirebaseMessaging.getInstance().subscribeToTopic(address);


        roomnametv.setText(roomname);
        if (adminid.equals(currentuid)){
            adduserbtn.setText("Add");
            status = "add";

        }else {
            adduserbtn.setText("Leave");
            status = "leave";
        }

        roomlist = database.getReference("notelist").child(address);

        nameref.child(currentuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    name = (String) snapshot.child("name").getValue();
                    category = (String) snapshot.child("category").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adduserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("add")){

                    Intent intent = new Intent(OpenRoom.this, RoomActivity.class);
                    intent.putExtra("rn",roomname);
                    intent.putExtra("ai",adminid);
                    intent.putExtra("a",address);
                    startActivity(intent);

                }else if (status.equals("leave")){

                    roomref = database.getReference("rooms").child(currentuid);
                    memberRef = database.getReference("members").child(address);

                    roomref.child(address).removeValue();
                    memberRef.child(currentuid).removeValue();

                    Intent intent = new Intent(OpenRoom.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        roomnametv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OpenRoom.this, Showmember.class);
                i.putExtra("a", address);
                startActivity(i);
            }
        });

        addbtn.setOnClickListener(view -> openBs());

    }

    private void openBs() {

    }
}