package com.example.lab2_ex1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView main_TextView;
    private EditText main_EditText;
    private Button main_Button;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null && result.getData().getStringExtra("dataFromSecondActivity") != null) {
                                main_TextView.setText("Hẹn gặp lại");
                                main_EditText.setText(result.getData().getStringExtra("dataFromSecondActivity"));
                                main_Button.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_TextView = findViewById(R.id.main_TextView);
        main_EditText = findViewById(R.id.main_EditText);
        main_Button = findViewById(R.id.main_Button);

        main_Button.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("dataFromMainActivity", main_EditText.getText().toString());
            startForResult.launch(intent);
        });
    }
}