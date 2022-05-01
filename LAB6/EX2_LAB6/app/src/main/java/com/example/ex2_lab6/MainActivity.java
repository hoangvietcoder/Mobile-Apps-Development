package com.example.ex2_lab6;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private File fileInternal, fileExternal;

    private static final int REQUEST_STORAGE_CODE = 4860;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        editText = findViewById(R.id.editText);
        Button buttonInternalRead = findViewById(R.id.buttonInternalRead);
        Button buttonInternalWrite = findViewById(R.id.buttonInternalWrite);
        Button buttonExternalRead = findViewById(R.id.buttonExternalRead);
        Button buttonExternalWrite = findViewById(R.id.buttonExternalWrite);

        fileInternal = new File(this.getFilesDir(), "data.txt");

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        fileExternal = new File(root, "data.txt");

        buttonInternalRead.setOnClickListener(this);
        buttonInternalWrite.setOnClickListener(this);
        buttonExternalRead.setOnClickListener(this);
        buttonExternalWrite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.buttonInternalRead) {
            readInternal();
        }

        if (id == R.id.buttonInternalWrite) {
           writeInternal();
        }

        if (id == R.id.buttonExternalRead) {
            readExternal();
        }

        if (id == R.id.buttonExternalWrite) {
            if (hasPermission()) {
                writeExternal();
            } else {
                requestPermission();
            }
        }
    }

    private boolean hasPermission() {
        int code = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return code == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                }, REQUEST_STORAGE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_STORAGE_CODE) {
            return;
        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            writeExternal();
        }
        else {
            Toast.makeText(this, "No permission granted", Toast.LENGTH_LONG).show();
        }
    }

    private void readInternal() {
        try {
            Scanner scanner = new Scanner(fileInternal);

            StringBuilder result = new StringBuilder();

            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }

            if (result.toString().isEmpty()) {
                Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
            }

            editText.setText(result.toString());

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeInternal() {
        String value = editText.getText().toString();
        try {
            PrintWriter writer = new PrintWriter(fileInternal);
            writer.write(value);

            writer.close();
            Toast.makeText(this, "Read Internal Done", Toast.LENGTH_LONG).show();
            editText.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readExternal() {
        try {
            Scanner scanner = new Scanner(fileExternal);

            StringBuilder result = new StringBuilder();

            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }

            if (result.toString().isEmpty()) {
                Toast.makeText(this, "No data to show", Toast.LENGTH_LONG).show();
            }

            editText.setText(result.toString());

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeExternal() {
        String value = editText.getText().toString();
        try {
            PrintWriter writer = new PrintWriter(fileExternal);
            writer.write(value);

            writer.close();
            Toast.makeText(this, "Read External Done", Toast.LENGTH_LONG).show();
            editText.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}