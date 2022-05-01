package com.example.lab7_ex1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText et_search;

    ArrayAdapter<String> adapter;
    ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        et_search = findViewById(R.id.et_search);
        data = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);

        listView.setAdapter(adapter);

        loadData();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loadData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED )
        {
            loadData();
        }
        else
        {
            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS},1234);
            return;
        }
        data.clear();

        String selection = null;
        String[] selectionArgs = null;
        String keyword = et_search.getText().toString();

        if(!keyword.equals(""))
        {
            selection= ContactsContract.Contacts.DISPLAY_NAME + "LIKE ?";
            selectionArgs = new String[] {"%" + keyword + "%"};
        }

        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                ContactsContract.Contacts.DISPLAY_NAME
        );

        while(cursor.moveToNext())
        {
            int colIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String displayName = cursor.getString(colIndex);
            data.add(displayName);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_item_add)
        {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivityForResult(intent, 5678);
        }
        return true;
    }
}