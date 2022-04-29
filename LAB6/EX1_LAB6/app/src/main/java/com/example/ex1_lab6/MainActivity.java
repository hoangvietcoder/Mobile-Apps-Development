package com.example.ex1_lab6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewCount = findViewById(R.id.textViewCount);

        pref = getPreferences(MODE_PRIVATE);

        count = pref.getInt("counter", 0);

        count ++;

        textViewCount.setText(String.valueOf(count));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("counter", count);

        editor.apply();
    }
}