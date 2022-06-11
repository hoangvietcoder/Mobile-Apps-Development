package com.example.notesappfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

        private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String uid = user.getUid();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                new Fragment01()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigation = item -> {
        Fragment selected = null;
        switch (item.getItemId()) {
            case R.id.Home:
                selected = new Fragment01();
                break;
            case R.id.Chats:
                selected = new Fragment02();
                break;
            case R.id.Tags:
                selected = new Fragment04();
                break;
            case R.id.Settings:
                selected = new Fragment03();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selected).commit();
        return true;
    };



}