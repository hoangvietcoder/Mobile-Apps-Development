package com.example.notesappfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Images extends AppCompatActivity {

    private TextView back;
    private EditText title, des;
    private Button btn_createNote;
    private ImageView iv_notes_iv;
    Notemember notemember;
    DatabaseReference notesref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Uri imageuri, imageurl;
    String uri;
    String note;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        back = findViewById(R.id.backBtn_iv);
        title = findViewById(R.id.title_et_iv);
        des = findViewById(R.id.notes_et_iv);
        btn_createNote = findViewById(R.id.btn_createNote_iv);
        iv_notes_iv = findViewById(R.id.iv_notes_iv);
        progressDialog = new ProgressDialog(Images.this);

        notemember = new Notemember();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        notesref = database.getReference("Notes").child(currentuid);

        back.setOnClickListener(view -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            uri = bundle.getString("u");
        } else  {
            Toast.makeText(this, "unable to fetch url", Toast.LENGTH_SHORT).show();
        }

        imageurl = Uri.parse(uri);
        Picasso.get().load(imageurl).into(iv_notes_iv);

        btn_createNote.setOnClickListener(view -> {
            uploadImage();
        });



    }

    private void uploadImage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference;
        storageReference = storage.getReference("images");

        String descript = des.getText().toString().trim();
        String tilteNote = title.getText().toString().trim();

        final StorageReference reference1 = storageReference.child(System.currentTimeMillis()+"."+"jpg");
        UploadTask uploadTask = reference1.putFile(imageurl);
        progressDialog.show();
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()) {
                    throw task.getException();
                }
                return reference1.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    Uri downloadUri = task.getResult();

                    notemember.setDelete(String.valueOf(System.currentTimeMillis()));
                    notemember.setNotes(descript);
                    notemember.setSearch(tilteNote.toLowerCase());
                    notemember.setTitle(tilteNote);
                    notemember.setUrl(downloadUri.toString());
                    notemember.setType("IMG");

                    String key = notesref.push().getKey();
                    notesref.child(key).setValue(notemember);
                    Toast.makeText(Images.this, "File uploaded", Toast.LENGTH_SHORT).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Images.this, MainActivity.class);
                            startActivity(i);

                            finish();
                        }
                    }, 1000);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Images.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}