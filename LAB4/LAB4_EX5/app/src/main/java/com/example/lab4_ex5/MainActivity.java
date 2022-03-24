package com.example.lab4_ex5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<User> users;
    private MyAdapter adapter;

    private TextView textViewTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonRemove = findViewById(R.id.buttonRemove);
        textViewTotal = findViewById(R.id.textViewTotal);

        users = new ArrayList<>();
        adapter = new MyAdapter(this, users);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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

        adapter.notifyItemRangeInserted(count, 5);
    }

    private void removeLastFive() {
        int prevSize = users.size();
        int count = 0;

        while (count < 5 && users.size() > 0) {
            count++;
            users.remove(users.size() - 1);
        }

        if (users.size() == 0) {
            Toast.makeText(this, "List of users is empty", Toast.LENGTH_LONG).show();
        }

        textViewTotal.setText(String.format("Total users: %s", users.size()));

        adapter.notifyItemRangeRemoved(prevSize - 5, 5);
    }
}