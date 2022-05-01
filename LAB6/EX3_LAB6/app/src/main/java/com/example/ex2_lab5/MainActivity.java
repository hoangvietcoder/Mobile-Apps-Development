package com.example.ex2_lab5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mainContent;
    private List<Events> event;
    private CustomAdapter mycustomAdapter;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchEvent;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();

        mycustomAdapter = new CustomAdapter(this, event);
        mainContent.hasFixedSize();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mainContent.setLayoutManager(manager);
        mainContent.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        mainContent.setAdapter(mycustomAdapter);
    }

    private void initData() {
        event = new ArrayList<>();
        database = new Database(this, "ex3lab6.sqlite", null, 1);
        try {
            database.QueryData("CREATE TABLE IF NOT EXISTS ghichu(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name VARCHAR(255)," +
                    "place VARCHAR(255)," +
                    "createAt VARCHAR(255)," +
                    "checked INTEGER DEFAULT 0" +
                    ")");
            getDataNote("SELECT * FROM ghichu WHERE checked = 1");
            Log.e("test", "size " + event.size());
        } catch (Exception e) {
            Log.e("test", "" + e);
        }


    }

    private void getDataNote(String sql) {
        Cursor data = database.getData(sql);
        while (data.moveToNext()) {
            int id = data.getInt(0);
            String ten = data.getString(1);
            String place = data.getString(2);
            String createAt = data.getString(3);
            int check = data.getInt(4);
            if (check == 1) {
                event.add(new Events(id, ten, place, createAt, true));
            } else {
                event.add(new Events(id, ten, place, createAt, false));
            }

        }
    }

    private void initViews() {
        mainContent = findViewById(R.id.recyclerView);
        switchEvent = findViewById(R.id.switchEvent);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_option, menu);

        final MenuItem toggleService = menu.findItem(R.id.switchEvent);
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch actionView = (Switch) toggleService.getActionView();
        actionView.setOnCheckedChangeListener((compoundButton, b) -> switchEventfc(b));
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void switchEventfc(boolean b) {
        event.clear();

        if (b) {
            getDataNote("SELECT * FROM ghichu ");

        } else {
            getDataNote("SELECT * FROM ghichu WHERE checked = 1");
        }

        mycustomAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addEvent) {
            addEvent();
        }

        if (id == R.id.switchEvent) {
            return false;
        }

        if (id == R.id.removeAll) {
            removeAll();
        }

        if (id == R.id.about) {
            Toast.makeText(this, "About Event", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void addEvent() {
        Intent intent = new Intent(this, ActivityEvent.class);
        startActivityForResult(intent, 1);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        assert data != null;
        Events result = data.getParcelableExtra("data");
        super.onActivityResult(requestCode, resultCode, data);
        if (result == null) {
            Toast.makeText(this, "Can not receive anything", Toast.LENGTH_SHORT).show();
        }
        try {
            assert result != null;
            database.QueryData("INSERT INTO ghichu VALUES(null,'" + result.getTitle() + "','" + result.getRoom() + "','" + result.getTime() + "',0)");

        } catch (Exception e) {
            Log.e("test", "" + e);
        }

        event.add(0, result);
        mycustomAdapter.notifyDataSetChanged();
        mycustomAdapter.notifyItemInserted(0);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeAll() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Remove All ")
                .setMessage("Are you sure you want to remove all this item?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    event.clear();
                    mycustomAdapter.notifyDataSetChanged();
                    try {
                        database.QueryData("DELETE FROM ghichu");

                    } catch (Exception e) {
                        Log.e("error", "remove All" + e);
                    }
                    Toast.makeText(this, "All items have been deleted", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}