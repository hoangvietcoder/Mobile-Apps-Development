package vn.edu.tdtu.lab09.EX1_LAB9;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import vn.edu.tdtu.lab09.R;

public class DownloadFileListActivity extends AppCompatActivity {

  public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100;

  private static final String TAG = DownloadFileListActivity.class.getName();

  private RecyclerView mRecyclerView;
  private DownloadFileAdaptor mAdapter;
  private List<DownloadFile> mFileList = new ArrayList<>();

  public static final int DIALOG_DOWNLOAD_THUMBNAIL_PROGRESS = 0;
  private ProgressDialog mProgressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.exercise01_activity);

    requestRuntimePermission();

    checkNetworkConnection();

    new LoadContentFromServer().execute();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
      String[] permissions, int[] grantResults) {
    switch (requestCode) {
      case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          // permission was granted, yay! Do the
          // contacts-related task you need to do.
        } else {
          // permission denied, boo! Disable the
          // functionality that depends on this permission.
        }
        return;
      }

      // other 'case' lines to check for other
      // permissions this app might request.
    }
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    switch (id) {
      case DIALOG_DOWNLOAD_THUMBNAIL_PROGRESS:
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Downloading Thumbnail.....");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        return mProgressDialog;
      default:
        return null;
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    // Register broadcast receiver in onResume method of the activity.
    registerBroadcastReceiver();
  }

  @Override
  protected void onPause() {
    super.onPause();
    // UnRegister the receiver when ever you pause the activity to avoid leak of receiver.
    unregisterReceiver(broadcastReceiver);
  }

  private void requestRuntimePermission() {
    // Here, thisActivity is the current activity
    if (ContextCompat.checkSelfPermission(getBaseContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {

      // Permission is not granted
      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(this,
          Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.
      } else {
        // No explanation needed; request the permission
        ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
      }
    } else {
      // Permission has already been granted
    }
  }

  private void registerBroadcastReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
    registerReceiver(broadcastReceiver, intentFilter);
  }

  private void checkNetworkConnection() {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    if (!isConnected) {
      AlertDialog.Builder builder = new AlertDialog.Builder(DownloadFileListActivity.this);
      builder.setTitle("Warning")
          .setMessage("The application need wifi connection to use the application!")
          .setPositiveButton(android.R.string.yes, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
              gotoWifiSetting();
            }
          })
          .setNegativeButton(android.R.string.no, null)
          .setIcon(android.R.drawable.ic_dialog_alert);

      AlertDialog dialog = builder.create();
      dialog.show();
    }
  }

  private void gotoWifiSetting() {
    startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
  }

  // Create a receiver that has to run on receiving WiFi state change
  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      checkNetworkConnection();
    }
  };


  class LoadContentFromServer extends AsyncTask<Object, Integer, Object> {

    protected void onPreExecute() {
      super.onPreExecute();
      showDialog(DIALOG_DOWNLOAD_THUMBNAIL_PROGRESS);
    }

    @Override
    protected Object doInBackground(Object... params) {
      Bitmap bm;

      for (int i = 0; i < 5; i++) {
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.setName("Image " + i);
        downloadFile.setSize("Size " + i);
        downloadFile.setDownloadPath("https://png.pngtree.com/png-clipart/20190516/original/pngtree-users-vector-icon-png-image_3725294.jpg");
        downloadFile.setStatus("");
        bm = FileHelper.loadBitmap("https://png.pngtree.com/png-clipart/20190516/original/pngtree-users-vector-icon-png-image_3725294.jpg");
        downloadFile.setImageThumBitmap(bm);

        mFileList.add(downloadFile);
      }

      return null;
    }


    @Override
    protected void onPostExecute(Object result) {
      showThumbnailData();
      dismissDialog(DIALOG_DOWNLOAD_THUMBNAIL_PROGRESS);
      removeDialog(DIALOG_DOWNLOAD_THUMBNAIL_PROGRESS);
    }
  }


  public void showThumbnailData() {
    mRecyclerView = findViewById(R.id.recyclerView);

    mAdapter = new DownloadFileAdaptor(this);
    mRecyclerView.setAdapter(mAdapter);
    mAdapter.setData(mFileList);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
  }

}
