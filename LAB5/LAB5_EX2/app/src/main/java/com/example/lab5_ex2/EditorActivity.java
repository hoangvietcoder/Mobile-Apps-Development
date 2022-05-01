package com.example.lab5_ex2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class EditorActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private TextInputLayout textInputLayout_Name;
    private TextInputLayout textInputLayout_Place;
    private TextInputLayout textInputLayout_Date;
    private TextInputLayout textInputLayout_Time;

    private static final String[] ROOMS = {"C201", "C202", "C203", "C204"};
    private int selectedRoom = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        initView();
    }

    private void initView() {
        textInputLayout_Name = findViewById(R.id.textInputLayout_Name);
        textInputLayout_Place = findViewById(R.id.textInputLayout_Place);
        textInputLayout_Date = findViewById(R.id.textInputLayout_Date);
        textInputLayout_Time = findViewById(R.id.textInputLayout_Time);

        Objects.requireNonNull(textInputLayout_Place.getEditText()).setOnClickListener(this);
        Objects.requireNonNull(textInputLayout_Date.getEditText()).setOnClickListener(this);
        Objects.requireNonNull(textInputLayout_Time.getEditText()).setOnClickListener(this);

        textInputLayout_Place.getEditText().setOnFocusChangeListener(this);
        textInputLayout_Date.getEditText().setOnFocusChangeListener(this);
        textInputLayout_Time.getEditText().setOnFocusChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_editor, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_save) {
            saveData();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        if (!isInputValid()) {
            return;
        }

        String name = Objects.requireNonNull(textInputLayout_Name.getEditText()).getText().toString();
        String place = Objects.requireNonNull(textInputLayout_Place.getEditText()).getText().toString();
        String date = Objects.requireNonNull(textInputLayout_Date.getEditText()).getText().toString();
        String time = Objects.requireNonNull(textInputLayout_Time.getEditText()).getText().toString();

        Event event = new Event(name, place, date, time);

        Intent intent = new Intent();
        intent.putExtra("data", event);

        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean isInputValid() {
        String name = Objects.requireNonNull(textInputLayout_Name.getEditText()).getText().toString();
        String place = Objects.requireNonNull(textInputLayout_Place.getEditText()).getText().toString();
        String date = Objects.requireNonNull(textInputLayout_Date.getEditText()).getText().toString();
        String time = Objects.requireNonNull(textInputLayout_Time.getEditText()).getText().toString();

        if (name.trim().isEmpty()) {
            textInputLayout_Name.setError("Please enter event name");
            return false;
        }

        if (place.trim().isEmpty()) {
            textInputLayout_Place.setError("Please select a room");
            return false;
        }

        if (date.trim().isEmpty()) {
            textInputLayout_Date.setError("Please select event date");
            return false;
        }

        if (time.trim().isEmpty()) {
            textInputLayout_Time.setError("Please select event time");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.textInputEditText_Place) {
            showPlacesDialog();
        } else if (id == R.id.textInputEditText_Date) {
            showDatePicker();
        } else if (id == R.id.textInputEditText_Time) {
            showTimePicker();
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            return;
        }

        int id = view.getId();

        if (id == R.id.textInputEditText_Place) {
            showPlacesDialog();
        } else if (id == R.id.textInputEditText_Date) {
            showDatePicker();
        } else if (id == R.id.textInputEditText_Time) {
            showTimePicker();
        }
    }

    private void showPlacesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select place");
        builder.setCancelable(true);
        builder.setSingleChoiceItems(ROOMS, selectedRoom, (dialogInterface, position) -> {
            selectedRoom = position;
            Objects.requireNonNull(textInputLayout_Place.getEditText()).setText(ROOMS[position]);
            dialogInterface.dismiss();
            textInputLayout_Place.clearFocus();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year1, month1, dayOfMonth) -> {
            String date = String.format(Locale.US, "%02d/%02d/%02d", dayOfMonth, month1 + 1, year1);
            Objects.requireNonNull(textInputLayout_Date.getEditText()).setText(date);
            textInputLayout_Date.clearFocus();
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, hour1, minute1) -> {
            String time = String.format(Locale.US, "%02d:%02d", hour1, minute1);
            Objects.requireNonNull(textInputLayout_Time.getEditText()).setText(time);
            textInputLayout_Time.clearFocus();
        }, hour, minute, true);

        timePickerDialog.show();
    }
}