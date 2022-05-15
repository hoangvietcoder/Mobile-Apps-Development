package vn.edu.tdtu.lab08exercise02sender;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SenderActivity extends AppCompatActivity implements OnClickListener {

  private static final String TAG = SenderActivity.class.getSimpleName();
  IntentFilter intentFilter;
  EditText etReceivedBroadcast;
  Button btnSendBroadcast;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    etReceivedBroadcast = findViewById(R.id.etReceivedBroadcast);
    btnSendBroadcast = findViewById(R.id.btnSendBroadcast);

    btnSendBroadcast.setOnClickListener(this);

    intentFilter = new IntentFilter("vn.edu.tdtu.lab08exercise02sender.MY_NOTIFICATION");
  }

  @Override
  protected void onResume() {
    super.onResume();
    registerReceiver(new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        Bundle results = getResultExtras(true);
        String hierarchy = results.getString("hierarchy");

        results.putString("hierarchy", hierarchy + "->" + TAG);

        Log.d(TAG, "Sender: Anonymous class broadcast receiver");
      }
    }, intentFilter);
  }


  @Override
  public void onClick(View view) {
    String msg = etReceivedBroadcast.getText().toString().trim();

    Intent intent = new Intent();
    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    intent.setAction("vn.edu.tdtu.lab08exercise02sender.MY_NOTIFICATION");
    intent.putExtra("msg", msg);
    sendBroadcast(intent);
  }
}
