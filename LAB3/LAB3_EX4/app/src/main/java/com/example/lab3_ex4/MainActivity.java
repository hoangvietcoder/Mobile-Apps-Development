package com.example.lab3_ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Boolean> checkboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.gridView);
        ArrayList<String> data = new ArrayList<>();
        checkboxes = new ArrayList<>();

        Random random = new Random();

        for(int i = 1; i <= (10 + random.nextInt(91)); i++) {
            data.add("PC " + i);
            checkboxes.add(false);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.grid_item_view, R.id.textView, data);
        gridView.setAdapter(adapter);
    }
}