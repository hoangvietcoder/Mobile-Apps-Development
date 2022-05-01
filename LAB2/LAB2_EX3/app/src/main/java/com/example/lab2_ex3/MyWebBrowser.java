package com.example.lab2_ex3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebBrowser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_browser);

        WebView webView = findViewById(R.id.webView);

        String url = getIntent().getData().toString();
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}