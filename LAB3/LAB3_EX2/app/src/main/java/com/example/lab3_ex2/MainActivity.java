package com.example.lab3_ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        listView = findViewById(R.id.listView);
    }

    private void initData() {
        Random random = new Random();
        List<Item> data = new ArrayList<>();

        for(int i = 1; i <= random.nextInt(100); i++) {
            data.add(new Item("Item " + i, "REMOVE"));
        }

        MyCustomAdapter adapter = new MyCustomAdapter(this, R.layout.activity_my_custom_adapter, data);
        listView.setAdapter(adapter);
    }
}