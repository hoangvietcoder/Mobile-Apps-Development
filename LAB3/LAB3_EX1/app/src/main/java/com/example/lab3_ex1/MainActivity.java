package com.example.lab3_ex1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = data.get(position);
            Toast.makeText(this, item, Toast.LENGTH_SHORT).show();
        });
    }

    private void initData() {
        Random random = new Random();
        data = new ArrayList<>();

        for(int i = 1; i <= random.nextInt(100); i++) {
            data.add("Item " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_custom_row, data);
        listView.setAdapter(adapter);
    }
}