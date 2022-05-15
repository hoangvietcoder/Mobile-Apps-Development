package vn.edu.tdtu.lab09.EX2_LAB9;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import vn.edu.tdtu.lab09.R;

public class PlayActivity extends Activity {

  private MediaPlayer mMedia;
  private final Handler handler = new Handler();
  private SeekBar seekBar;

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.exercise02_activity_play);

    if (mMedia != null) {
      mMedia.release();
    }

    Intent intent = getIntent();
    final String musicName = intent.getStringExtra("vMusicName");
    final String musicPath = intent.getStringExtra("vMusicPath");

    final TextView txtView = findViewById(R.id.textView1);
    txtView.setText(musicName);

    /* Resource in R.
     * mMedia = MediaPlayer.create(this, R.raw.music);
     * mMedia.start();
     */

    /*
     * from DataSource
     * mMedia = new MediaPlayer();
     * mMedia.setDataSource("https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3");
     * mMedia.start();
     *
     */
    mMedia = new MediaPlayer();
    try {
      assert musicPath != null;
      Log.d("Path", musicPath);
      mMedia.setDataSource(musicPath);
      mMedia.prepare();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    seekBar = findViewById(R.id.seekBar1);
    seekBar.setMax(mMedia.getDuration());
    seekBar.setOnTouchListener((v, event) -> {
      updateSeekChange(v);
      return false;
    });

    final Button btn1 = findViewById(R.id.button1); // Start
    final Button btn2 = findViewById(R.id.button2); // Pause
    final Button btn3 = findViewById(R.id.button3); // Stop
    final Button btn4 = findViewById(R.id.button4); // Back

    // Start
    btn1.setOnClickListener(v -> {
      txtView.setText("Playing music...");
      mMedia.start();
      startPlayProgressUpdater();
      btn1.setEnabled(false);
      btn2.setEnabled(true);
      btn3.setEnabled(true);
    });

    // Pause
    btn2.setOnClickListener(v -> {
      txtView.setText("Pause!");
      mMedia.pause();
      btn1.setEnabled(true);
      btn2.setEnabled(false);
      btn3.setEnabled(false);
    });

    // Stop
    btn3.setOnClickListener(v -> {
      txtView.setText("Stop!");
      mMedia.stop();
      btn1.setEnabled(true);
      btn2.setEnabled(false);
      btn3.setEnabled(false);
      try {
        mMedia.prepare();
        mMedia.seekTo(0);
      } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    });

    // Back
    btn4.setOnClickListener(v -> {
      if (mMedia.isPlaying()) {
        mMedia.stop();
      }
      Intent newActivity = new Intent(PlayActivity.this, MediaFileActivity.class);
      startActivity(newActivity);
    });

  }

  private void updateSeekChange(View v) {
    if (mMedia.isPlaying()) {
      SeekBar sb = (SeekBar) v;
      mMedia.seekTo(sb.getProgress());
    }
  }

  public void startPlayProgressUpdater() {
    seekBar.setProgress(mMedia.getCurrentPosition());

    if (mMedia.isPlaying()) {
      Runnable notification = this::startPlayProgressUpdater;
      handler.postDelayed(notification, 1000);
    }
  }


  @Override
  protected void onDestroy() {
    // TODO Auto-generated method stub
    super.onDestroy();
    if (mMedia != null) {
      mMedia.release();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
  }

}
