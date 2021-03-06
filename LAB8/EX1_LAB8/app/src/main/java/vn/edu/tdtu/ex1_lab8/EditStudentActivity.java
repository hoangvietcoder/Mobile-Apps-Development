package vn.edu.tdtu.ex1_lab8;

import static vn.edu.tdtu.ex1_lab8.StudentsActivity.INTENT_STUDENT_EMAIL;
import static vn.edu.tdtu.ex1_lab8.StudentsActivity.INTENT_STUDENT_ID;
import static vn.edu.tdtu.ex1_lab8.StudentsActivity.INTENT_STUDENT_NAME;
import static vn.edu.tdtu.ex1_lab8.StudentsActivity.INTENT_STUDENT_PHONE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditStudentActivity extends AppCompatActivity {

  private static final String DEFAULT_EMPTY_VALUE = "";
  private EditText etId;
  private EditText etName;
  private EditText etEmail;
  private EditText etPhone;

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.edit_student_activity);

    // bind ui controls
    etId = findViewById(R.id.et_id);
    etName = findViewById(R.id.et_name);
    etEmail = findViewById(R.id.et_phone);
    etPhone = findViewById(R.id.et_email);

    etId.setEnabled(false);

    Button btnSave = findViewById(R.id.btn_save);

    // get intent that started this activity
    Intent callerIntent = getIntent();

    // retrieve map of extended data from the intent
    final Bundle callerBundle = callerIntent.getExtras();

    // get given key-value map
    populateUI(callerBundle);

    btnSave.setOnClickListener(view -> {
      final String studentName = etName.getText().toString();
      final String studentEmail = etEmail.getText().toString();
      final String studentPhone = etPhone.getText().toString();

      if (studentName.trim().isEmpty()) {
        // do something
      } else {
        final String studentId = etId.getText().toString();
        Student updatedStudent = new Student(studentId, studentName, studentEmail,
            studentPhone);
        updateStudent(updatedStudent);
      }
    });
  }

  private void updateStudent(final Student updatedStudent) {
    StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE,
            response -> {
              try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("status")) {
                  Toast.makeText(getBaseContext(), jsonObject.getString("message"),
                      Toast.LENGTH_SHORT).show();
                  Intent studentsIntent = new Intent();
                  setResult(Activity.RESULT_OK, studentsIntent);
                  finish();
                } else {
                  Toast.makeText(getBaseContext(), "Failed: " + jsonObject.getString("message"),
                      Toast.LENGTH_SHORT).show();
                }
              } catch (JSONException e) {
                e.printStackTrace();
              }
            },
            error -> Toast.makeText(getBaseContext(), "Failed: " + error.toString(),
                Toast.LENGTH_SHORT).show()
    ) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("id", updatedStudent.getId());
        params.put("name", updatedStudent.getName());
        params.put("email", updatedStudent.getEmail());
        params.put("phone", updatedStudent.getPhone());

        return params;
      }
    };
    RequestHandler.getInstance(getBaseContext()).addToRequestQueue(postRequest);
  }

  private void populateUI(Bundle callerBundle) {
    etId.setText(callerBundle.getString(INTENT_STUDENT_ID, DEFAULT_EMPTY_VALUE));
    etName.setText(callerBundle.getString(INTENT_STUDENT_NAME, DEFAULT_EMPTY_VALUE));
    etEmail.setText(callerBundle.getString(INTENT_STUDENT_EMAIL, DEFAULT_EMPTY_VALUE));
    etPhone.setText(callerBundle.getString(INTENT_STUDENT_PHONE, DEFAULT_EMPTY_VALUE));
  }

}
