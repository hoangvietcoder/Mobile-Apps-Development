package com.example.lab4_ex4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MyPCClickListener {

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();
        List<PC> pcList = PC.generate(10 + random.nextInt(91));
        adapter = new MyAdapter(pcList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        adapter.setPCListener(this);
    }

    public void onMyPCClicked(PC pc, int position) {
        pc.changMode();
        adapter.notifyItemChanged(position);
    }
}