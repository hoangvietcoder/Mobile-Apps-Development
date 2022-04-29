package com.example.lab5_ex2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private MyAdapter adapter;
    private List<Event> data;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null && result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null && result.getData().getParcelableExtra("data") != null) {
                            Event event = result.getData().getParcelableExtra("data");

                            data.add(0, event);
                            adapter.notifyItemInserted(0);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        recyclerView.setLayoutManager(layoutManager);

        registerForContextMenu(recyclerView);
    }

    private void initData() {
        data = new ArrayList<>();

        data.add(new Event("Báo cáo bài tập lớn", "E001", "02/07/2022", "17:10"));
        data.add(new Event("Báo cáo đồ án", "C004", "02/08/2022", "15:00"));
        data.add(new Event("Báo cáo cuối kỳ", "A005", "02/09/2022", "8:00"));

        adapter = new MyAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_add) {
            startForResult.launch(new Intent(this, EditorActivity.class));
        }
        else if (id == R.id.menu_remove_all) {
            removeAllItems();
        }
        else if (id == R.id.menu_about) {
            aboutThisApp();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void removeAllItems() {
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Remove all items");
        builder.setMessage("Are you sure you want to remove all items?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            data.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "All items have been deleted", Toast.LENGTH_LONG).show();
        });

        builder.setNegativeButton("No", null);
        builder.setCancelable(false);

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void aboutThisApp() {
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("About");
        builder.setMessage("This app is LAB5_EX2 app");
        builder.setPositiveButton("OK", null);

        builder.setCancelable(false);

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}