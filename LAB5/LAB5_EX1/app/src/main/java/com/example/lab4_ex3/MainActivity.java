package com.example.lab4_ex3;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Phone> phones;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        phones = new ArrayList<>();
        Button buttonSelected = findViewById(R.id.buttonSelected);
        Button buttonAll = findViewById(R.id.buttonAll);

        buttonSelected.setOnClickListener(view -> removeSelected());
        buttonAll.setOnClickListener(view -> removeAll());
    }

    private void initData() {
        phones.add(new Phone(R.drawable.phone_icon, "Apple", false));
        phones.add(new Phone(R.drawable.phone_icon, "Samsung", false));
        phones.add(new Phone(R.drawable.phone_icon, "Nokia", false));
        phones.add(new Phone(R.drawable.phone_icon, "Oppo", false));
        phones.add(new Phone(R.drawable.phone_icon, "Xiaomi", false));
        phones.add(new Phone(R.drawable.phone_icon, "Asus", false));
        phones.add(new Phone(R.drawable.phone_icon, "Lenovo", false));
        phones.add(new Phone(R.drawable.phone_icon, "LG", false));
        phones.add(new Phone(R.drawable.phone_icon, "Vivo", false));
        phones.add(new Phone(R.drawable.phone_icon, "Huawei", false));
        phones.add(new Phone(R.drawable.phone_icon, "OnePlus", false));
        phones.add(new Phone(R.drawable.phone_icon, "Realme", false));
        phones.add(new Phone(R.drawable.phone_icon, "Google", false));
        phones.add(new Phone(R.drawable.phone_icon, "Sony", false));
        phones.add(new Phone(R.drawable.phone_icon, "ZTE", false));

        adapter = new MyAdapter(this, phones);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void removeSelected() {
        for(int i = phones.size() - 1; i >= 0 ; i --) {
            if (phones.get(i).isChecked()) {
                phones.remove(i);
                adapter.notifyItemRemoved(i);
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void removeAll() {
        phones.clear();
        adapter.notifyDataSetChanged();

        if (phones.size() == 0) {
            Toast.makeText(this, "List of phone is empty", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.deleteSelected) {
            for(int i = phones.size() - 1; i >= 0 ; i--) {
                if (phones.get(i).isChecked()) {
                    phones.remove(i);
                    adapter.notifyItemRemoved(i);
                }
            }
        }
        else if (id == R.id.deleteAll) {
            deleteAllItems();
        }
        else if (id == R.id.checkAll) {
            for(int i = phones.size() - 1; i >= 0; i--) {
                phones.get(i).setChecked(true);
            }
            adapter.notifyDataSetChanged();
        }
        else if (id == R.id.unCheckAll) {
            for(int i = phones.size() - 1; i >= 0; i--) {
                phones.get(i).setChecked(false);
            }
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllItems() {

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Delete all items");
        builder.setMessage("Are you sure you want to delete all items?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            phones.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "All items have been deleted", Toast.LENGTH_LONG).show();
        });

        builder.setNegativeButton("No", null);
        builder.setCancelable(false);

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}