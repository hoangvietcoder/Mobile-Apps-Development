package com.example.lab3_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Phone> data;
    private PhoneAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        listView = findViewById(R.id.listView);
        Button buttonSelected = findViewById(R.id.buttonSelected);
        Button buttonAll = findViewById(R.id.buttonAll);

        buttonSelected.setOnClickListener(view -> removeSelected());
        buttonAll.setOnClickListener(view -> removeAll());
    }

    private void initData() {
        data = new ArrayList<>();

        data.add(new Phone(R.drawable.phone_icon, "Apple", false));
        data.add(new Phone(R.drawable.phone_icon, "Samsung", false));
        data.add(new Phone(R.drawable.phone_icon, "Nokia", false));
        data.add(new Phone(R.drawable.phone_icon, "Oppo", false));
        data.add(new Phone(R.drawable.phone_icon, "Xiaomi", false));
        data.add(new Phone(R.drawable.phone_icon, "Asus", false));
        data.add(new Phone(R.drawable.phone_icon, "Lenovo", false));
        data.add(new Phone(R.drawable.phone_icon, "LG", false));
        data.add(new Phone(R.drawable.phone_icon, "Vivo", false));
        data.add(new Phone(R.drawable.phone_icon, "Huawei", false));
        data.add(new Phone(R.drawable.phone_icon, "OnePlus", false));
        data.add(new Phone(R.drawable.phone_icon, "Realme", false));
        data.add(new Phone(R.drawable.phone_icon, "Google", false));
        data.add(new Phone(R.drawable.phone_icon, "Sony", false));
        data.add(new Phone(R.drawable.phone_icon, "ZTE", false));

        adapter = new PhoneAdapter(this, R.layout.item_view, data);
        listView.setAdapter(adapter);
    }

    private void removeSelected() {
        for(int i = data.size() - 1; i >= 0; i--) {
            if (data.get(i).isChecked()) {
                data.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void removeAll() {
        data.clear();
        adapter.notifyDataSetChanged();

        if(data.size() == 0) {
            Toast.makeText(this, "List of phone is empty", Toast.LENGTH_SHORT).show();
        }
    }
}