package com.example.notesappfinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Resetpass extends Activity {

    private EditText emailReset;
    private Button resetBtn, gotoLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_resetpass);

        loading = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        emailReset = findViewById(R.id.emailReset);
        resetBtn = findViewById(R.id.btn_reset);
        gotoLogin = findViewById(R.id.btn_gotoLogin);

        gotoLogin.setOnClickListener(view -> {
            gotoLogin();
        });

        resetBtn.setOnClickListener(view -> {
            resetPass();
        });

    }

    private void resetPass() {
        String email = emailReset.getText().toString().trim();
        if(!TextUtils.isEmpty(email)) {
            loading.show();
//            mAuth.sendPasswordResetEmail(email)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
////
//                                Toast.makeText(Resetpass.this, "Send request successfully " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(Resetpass.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }

            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        loading.dismiss();
                        emailReset.setText("");
                        Toast.makeText(getApplicationContext(),"Mail sent, you can recover your password using mail",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Email is Wrong or Account not Exit",Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Please enter all field", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoLogin() {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        finish();
    }
}