package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ambitious.parampara.R;

public class DarshanBooking_Activity extends AppCompatActivity {

    TextView txt_headername;
    WebView darshan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_darshan_booking_);
        findId();
    }

    private void findId() {
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Temple Locater");
        darshan= findViewById(R.id.darshan_webview);
        darshan.setWebViewClient(new WebViewClient());
        darshan.loadUrl("http://onlinebooking.srisailamonline.com/");
        WebSettings webSettings=darshan.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
