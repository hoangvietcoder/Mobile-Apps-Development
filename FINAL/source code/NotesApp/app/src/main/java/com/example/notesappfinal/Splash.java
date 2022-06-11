package com.example.notesappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {

    private ImageView imgSplash;
    private TextView textSplash;
    long animation = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        imgSplash = findViewById(R.id.splash);
        textSplash = findViewById(R.id.TextSplash);

//        ObjectAnimator animatory = ObjectAnimator.ofFloat(imgSplash, "y", 500f);
//        ObjectAnimator animatorx = ObjectAnimator.ofFloat(imgSplash, "x", 500f);
//
//        animatory.setDuration(animation);
//        animatorx.setDuration(animation);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(animatory, animatorx);
//        animatorSet.start();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent i = new Intent(Splash.this, MainActivity.class);
            startActivity(i);
            finish();
        }, 1300);
    }
}