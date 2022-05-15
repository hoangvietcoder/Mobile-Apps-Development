package vn.edu.tdtu.lab08_exercise02_receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class ReceiverActivity extends AppCompatActivity {

  private static final String TAG = ReceiverActivity.class.getName();
  private TextView welcomeText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_receiver);

    welcomeText = findViewById(R.id.welcomeText);
  }

  @Override
  protected void onResume() {
    super.onResume();
    // Register broadcast receiver in onResume method of the activity.
    registerBroadcastReceiver();
  }

  private void registerBroadcastReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("vn.edu.tdtu.lab08exercise02sender.MY_NOTIFICATION");
    registerReceiver(broadcastReceiver, intentFilter);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    // UnRegister the receiver when ever you pause the activity to avoid leak of receiver.
    unregisterReceiver(broadcastReceiver);
  }

  //  https://www.tutorialspoint.com/android/android_notifications.htm
  // Create a receiver that has to run on receiving WiFi state change
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    public static final String CHANNEL_ID = "123";

    @Override
    public void onReceive(Context context, Intent intent) {
      String intentData = intent.getStringExtra("msg");

      Log.d(TAG, "Receiver: Anonymous class broadcast receiver");

      Toast.makeText(context, "Receiver: Received the Intent's message: " + intentData,
          Toast.LENGTH_LONG).show();

      welcomeText.setText("Receiver: Received the Intent's message: " + intentData);

      NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ReceiverActivity.this,
          CHANNEL_ID)
          .setSmallIcon(R.drawable.user)
          .setContentTitle("1 new message")
          .setContentText(intentData)
          .setPriority(NotificationCompat.PRIORITY_DEFAULT);

      Intent notificationIntent = new Intent(getBaseContext(), ReceiverActivity.class);
      PendingIntent contentIntent = PendingIntent
          .getActivity(getBaseContext(), 0, notificationIntent,
              PendingIntent.FLAG_UPDATE_CURRENT);
      mBuilder.setContentIntent(contentIntent);

      // Add as notification
      NotificationManager manager = (NotificationManager) getSystemService(
          Context.NOTIFICATION_SERVICE);
      // notificationID allows you to update the notification later on.
      manager.notify(0, mBuilder.build());
    }
  };
}
