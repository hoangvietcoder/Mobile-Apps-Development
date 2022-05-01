package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText textMessage;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textMessage = findViewById(R.id.textMessage);
        Button buttonMessage = findViewById(R.id.buttonMessage);
        textResult = findViewById(R.id.textResult);

        buttonMessage.setOnClickListener(view -> {
            String message = textMessage.getText().toString().trim();
            if (message.isEmpty()){
                Toast toast = Toast.makeText(MainActivity.this, "Vui lòng nhập thông tin", Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                textResult.setText(message);
                textMessage.setText("");
            }
        });

        textMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String message = textMessage.getText().toString().toLowerCase();
                if(message.equals("on")) {
                    buttonMessage.setEnabled(true);
                }
                else if (message.equals("off")) {
                    buttonMessage.setEnabled(false);
                }
            }
        });

    }
}