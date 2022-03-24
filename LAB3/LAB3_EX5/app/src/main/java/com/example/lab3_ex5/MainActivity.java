package com.example.lab3_ex5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> users;
    private MyUserAdapter adapter;

    private TextView textViewTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        users = new ArrayList<>();
        adapter = new MyUserAdapter(this, R.layout.item_view, users);
        listView.setAdapter(adapter);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonRemove = findViewById(R.id.buttonRemove);
        textViewTotal = findViewById(R.id.textViewTotal);

        buttonAdd.setOnClickListener(view -> addFiveMore());
        buttonRemove.setOnClickListener(view -> removeLastFive());

        textViewTotal.setText(String.format("Total users: %s", users.size()));
    }

    private void addFiveMore() {
        int count = users.size();
        for (int i = 1; i <= 5; i++) {
            int id = count + i;
            users.add(new User("User " + id, "user" + id + "@tdtu.edu.vn"));
        }

        textViewTotal.setText(String.format("Total users: %s", users.size()));

        adapter.notifyDataSetChanged();
    }

    private void removeLastFive() {
        int count = 0;

        while (count < 5 && users.size() > 0) {
            users.remove(users.size() - 1);
            count++;
        }

        if (users.size() == 0) {
            Toast.makeText(this, "List of users is empty", Toast.LENGTH_LONG).show();
        }

        textViewTotal.setText(String.format("Total users: %s", users.size()));

        adapter.notifyDataSetChanged();
    }
}