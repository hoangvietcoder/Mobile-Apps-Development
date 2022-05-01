package com.example.lab1_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonResult;
    private CheckBox checkBoxAndroid, checkBoxIOS, checkBoxWindows, checkBoxRIM;
    private TextView textViewShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonResult = findViewById(R.id.buttonResult);
        checkBoxAndroid = findViewById(R.id.checkBoxAndroid);
        checkBoxIOS = findViewById(R.id.checkBoxIOS);
        checkBoxWindows = findViewById(R.id.checkBoxWindows);
        checkBoxRIM = findViewById(R.id.checkBoxRIM);
        textViewShow = findViewById(R.id.textViewShow);

        buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "";
                if (checkBoxAndroid.isChecked()) {
                    text += checkBoxAndroid.getText().toString() + ": " + true + "\n";
                } else {
                    text += checkBoxAndroid.getText().toString() + ": " + false + "\n";
                }

                if (checkBoxIOS.isChecked()) {
                    text += checkBoxIOS.getText().toString() + ": " + true + "\n";
                } else {
                    text += checkBoxIOS.getText().toString() + ": " + false + "\n";
                }

                if (checkBoxWindows.isChecked()) {
                    text += checkBoxWindows.getText().toString() + ": " + true + "\n";
                } else {
                    text += checkBoxWindows.getText().toString() + ": " + false + "\n";
                }

                if (checkBoxRIM.isChecked()) {
                    text += checkBoxRIM.getText().toString() + ": " + true + "\n";
                } else {
                    text += checkBoxRIM.getText().toString() + ": " + false + "\n";
                }

                textViewShow.setText(text);
            }
        });
    }
}