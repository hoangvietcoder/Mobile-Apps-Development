package com.example.ex2_lab5;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ActivityEvent extends AppCompatActivity {

    private TextInputLayout textInputLayout_Name;
    private TextInputLayout textInputLayout_Place;
    private TextInputLayout textInputLayout_Date;
    private TextInputLayout textInputLayout_Time;

    private static final String[] ROOMS = {"C201", "C202", "C203", "C204"};
    private int selectedRoom = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initViews();
    }

    private void initViews() {
        textInputLayout_Name = findViewById(R.id.textInputLayout_Name);
        textInputLayout_Place = findViewById(R.id.textInputLayout_Place);
        textInputLayout_Date = findViewById(R.id.textInputLayout_Date);
        textInputLayout_Time = findViewById(R.id.textInputLayout_Time);

        Objects.requireNonNull(textInputLayout_Place.getEditText()).setOnClickListener(view -> new AlertDialog.Builder(this)
                .setTitle("Select Place")
                .setSingleChoiceItems(ROOMS, selectedRoom, (dialogInterface, i) -> {
                    selectedRoom = i;
                    textInputLayout_Place.getEditText().setText(ROOMS[i]);
                    dialogInterface.dismiss();
                })
                .create().show());

        Objects.requireNonNull(textInputLayout_Date.getEditText()).setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, y, m, d) -> textInputLayout_Date.getEditText().setText(String.format(Locale.ENGLISH, "%02d/%02d/%02d", d, m + 1, y)), year, month, day);
            dialog.show();
        });
        Objects.requireNonNull(textInputLayout_Time.getEditText()).setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            int hour = cal.get(Calendar.HOUR);
            int minutes = cal.get(Calendar.MINUTE);
            TimePickerDialog dialog = new TimePickerDialog(this, (timePicker, h, m) -> textInputLayout_Time.getEditText().setText(String.format(Locale.ENGLISH, "%02d:%02d", h, m)), hour, minutes, true);
            dialog.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saveItem) {
            saveData();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValidInput() {
        String name = Objects.requireNonNull(textInputLayout_Name.getEditText()).getText().toString();
        String place = Objects.requireNonNull(textInputLayout_Place.getEditText()).getText().toString();
        String date = Objects.requireNonNull(textInputLayout_Date.getEditText()).getText().toString();
        String time = Objects.requireNonNull(textInputLayout_Time.getEditText()).getText().toString();

        if (name.trim().isEmpty()) {
            textInputLayout_Name.setError("Please enter your name");
            return false;
        } else if (place.trim().isEmpty()) {
            textInputLayout_Place.setError("Please enter your place");
            return false;
        } else if (date.trim().isEmpty()) {
            textInputLayout_Date.setError("Please enter your date");
            return false;
        } else if (time.trim().isEmpty()) {
            textInputLayout_Time.setError("Please enter your time");
            return false;
        } else {
            return true;
        }
    }

    private void saveData() {
        if (!isValidInput()) {
            return;
        }

        String name = Objects.requireNonNull(textInputLayout_Name.getEditText()).getText().toString();
        String place = Objects.requireNonNull(textInputLayout_Place.getEditText()).getText().toString();
        String dateResult = Objects.requireNonNull(textInputLayout_Date.getEditText()).getText().toString() + " " + Objects.requireNonNull(textInputLayout_Time.getEditText()).getText().toString();


        Events event = new Events(name, place, dateResult);

        Intent intent = new Intent();
        intent.putExtra("data", event);
        setResult(1, intent);

        finish();
    }
}