package com.example.lab2_ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private EditText second_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView second_TextView = findViewById(R.id.second_TextView);
        second_EditText = findViewById(R.id.second_EditText);
        Button second_Button = findViewById(R.id.second_Button);

        String email = getIntent().getStringExtra("dataFromMainActivity");
        second_TextView.setText(String.format("Xin chào, %1$s. Vui lòng nhập tên", email));

        second_Button.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("dataFromSecondActivity", second_EditText.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });

    }
}