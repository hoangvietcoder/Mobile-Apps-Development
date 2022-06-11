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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Register extends Activity {

    private EditText emailInput, passInput, re_passInput;
    private Button registerBtn, gotoLogin;
    private final static int RC_SIG_IN = 1;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        loading = new ProgressDialog(this);

        emailInput = findViewById(R.id.email_register);
        passInput = findViewById(R.id.password_register);
        re_passInput = findViewById(R.id.re_password_register);
        registerBtn = findViewById(R.id.btn_register);

        registerBtn.setOnClickListener(view -> {
            register();
        });


        gotoLogin = findViewById(R.id.btn_goLogin);
        gotoLogin.setOnClickListener(view -> {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
            finish();
        });

    }

    private void register() {
        String email = emailInput.getText().toString().trim();
        String pass = passInput.getText().toString().trim();
        String re_pass = re_passInput.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(re_pass)) {
            if(pass.equals(re_pass)) {
                loading.show();
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    loading.dismiss();
                    if(task.isSuccessful()) {
                        gotomain();
                    } else {
                        Toast.makeText(this, "ERROR"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Pass don't match.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter all field", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            gotomain();
        }
    }

    private void gotomain() {
        Intent i = new Intent(Register.this, Splash.class);
        startActivity(i);
        finish();
    }
}