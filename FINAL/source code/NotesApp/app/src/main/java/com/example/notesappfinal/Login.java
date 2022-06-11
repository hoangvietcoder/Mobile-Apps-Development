package com.example.notesappfinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends Activity {
    private EditText emailInput, passInput;
    private TextView forgotPass;
    private Button gotoRegister, Login_btn;
    private final static int RC_SIG_IN = 1;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        loading = new ProgressDialog(this);

        forgotPass = findViewById(R.id.forgotPass);
        emailInput = findViewById(R.id.email);
        passInput = findViewById(R.id.password);
        Login_btn = findViewById(R.id.btn_login);
        gotoRegister = findViewById(R.id.btn_goto_register);

        forgotPass.setOnClickListener(view -> {
            Intent i = new Intent(this, Resetpass.class);
            startActivity(i);
        });

        gotoRegister.setOnClickListener(view -> {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        });

        Login_btn.setOnClickListener(view -> {
            login();
        });
    }

    private void login() {
        String email = emailInput.getText().toString().trim();
        String pass = passInput.getText().toString().trim();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            loading.show();
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                loading.dismiss();
                if (task.isSuccessful()) {
                    gotomain();
                } else {
                    Toast.makeText(this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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
        Intent i = new Intent(Login.this, Splash.class);
        startActivity(i);
        finish();
    }

}