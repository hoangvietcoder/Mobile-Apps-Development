package com.example.notesappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Test extends AppCompatActivity {

    private TextView back;
    private EditText title, des;
    private Button btn_createNote;
    Notemember notemember;
    DatabaseReference notesref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        back = findViewById(R.id.backBtn);
        title = findViewById(R.id.title_et);
        des = findViewById(R.id.notes_et);
        btn_createNote = findViewById(R.id.btn_createNote);

        notemember = new Notemember();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        notesref = database.getReference("Notes").child(currentuid);



        back.setOnClickListener(view -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });

        btn_createNote.setOnClickListener(view -> {
            String descript = des.getText().toString().trim();
            String tilteNote = title.getText().toString().trim();

            if(tilteNote != null || descript != null) {
                notemember.setType("TXT");
                notemember.setTitle(tilteNote);
                notemember.setNotes(descript);
                notemember.setDelete(String.valueOf(System.currentTimeMillis()));
                notemember.setSearch(tilteNote.toLowerCase());

                String key = notesref.push().getKey();
                notesref.child(key).setValue(notemember);

                Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(Test.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 1000);

            } else  {
                Toast.makeText(this, "Please enter all field", Toast.LENGTH_SHORT).show();

            }
        });
    }

    
}