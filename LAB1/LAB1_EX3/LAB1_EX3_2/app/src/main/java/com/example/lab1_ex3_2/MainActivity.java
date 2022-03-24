package com.example.lab1_ex3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioButton radioButtonAndroid, radioButtonIOS, radioButtonWindows, radioButtonRIM;
    private Button buttonResult;
    private TextView textViewShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioButtonAndroid = findViewById(R.id.radioButtonAndroid);
        radioButtonIOS = findViewById(R.id.radioButtonIOS);
        radioButtonWindows = findViewById(R.id.radioButtonWindows);
        radioButtonRIM = findViewById(R.id.radioButtonRIM);
        buttonResult = findViewById(R.id.buttonResult);
        textViewShow = findViewById(R.id.textViewShow);

        buttonResult.setOnClickListener(new View.OnClickListener() {

            String text_1 = "";
            String text_2 = "";
            String text_3 = "";
            String text_4 = "";

            @Override
            public void onClick(View view) {
                if (radioButtonAndroid.isChecked()) {
                    text_1 = "";
                    text_1 += radioButtonAndroid.getText().toString() + ": " + true + "\n";
                    text_1 += radioButtonIOS.getText().toString() + ": " + false + "\n";
                    text_1 += radioButtonWindows.getText().toString() + ": " + false + "\n";
                    text_1 += radioButtonRIM.getText().toString() + ": " + false + "\n";

                    textViewShow.setText(text_1);
                }

                if (radioButtonIOS.isChecked()) {
                    text_2 = "";
                    text_2 += radioButtonAndroid.getText().toString() + ": " + false + "\n";
                    text_2 += radioButtonIOS.getText().toString() + ": " + true + "\n";
                    text_2 += radioButtonWindows.getText().toString() + ": " + false + "\n";
                    text_2 += radioButtonRIM.getText().toString() + ": " + false + "\n";

                    textViewShow.setText(text_2);
                }

                if (radioButtonWindows.isChecked()) {
                    text_3 = "";
                    text_3 += radioButtonAndroid.getText().toString() + ": " + false + "\n";
                    text_3 += radioButtonIOS.getText().toString() + ": " + false + "\n";
                    text_3 += radioButtonWindows.getText().toString() + ": " + true + "\n";
                    text_3 += radioButtonRIM.getText().toString() + ": " + false + "\n";

                    textViewShow.setText(text_3);
                }

                if (radioButtonRIM.isChecked()) {
                    text_4 = "";
                    text_4 += radioButtonAndroid.getText().toString() + ": " + false + "\n";
                    text_4 += radioButtonIOS.getText().toString() + ": " + false + "\n";
                    text_4 += radioButtonWindows.getText().toString() + ": " + false + "\n";
                    text_4 += radioButtonRIM.getText().toString() + ": " + true + "\n";

                    textViewShow.setText(text_4);
                }
            }
        });
    }
}