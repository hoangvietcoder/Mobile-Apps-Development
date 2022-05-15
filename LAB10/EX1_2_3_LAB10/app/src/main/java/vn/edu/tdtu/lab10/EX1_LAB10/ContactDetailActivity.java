package vn.edu.tdtu.lab10.EX1_LAB10;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import vn.edu.tdtu.lab10.R;

public class ContactDetailActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.exercise01_contact_detail_activity);

    Intent intent = getIntent();
    final String contactName = intent.getStringExtra("vName");
    final String contactPhoneNumber = intent.getStringExtra("vPhoneNumber");


    final ImageView ivAvatar = findViewById(R.id.iv_avatar);

    final TextView tvName = findViewById(R.id.tv_name);
    tvName.setText(contactName);

    final TextView tvPhoneNumber = findViewById(R.id.tv_phone_number);
    tvPhoneNumber.setText(contactPhoneNumber);
  }

}
