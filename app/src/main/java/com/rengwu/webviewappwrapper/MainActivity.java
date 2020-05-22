package com.rengwu.webviewappwrapper;

import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private LinearLayout loadingScreen;
    private TextView internetErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        loadingScreen = (LinearLayout) findViewById(R.id.loadingScreen);
        internetErrorMessage = (TextView) findViewById(R.id.internetErrorMessage);

        // hide loading splash screen upon finish loading webView page
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingScreen.setVisibility(LinearLayout.GONE);
            }
        });

        // web settings: enable javascript, auto load images, enable DOM local storage
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);


        // if there's no internet connection, show a warning toast
        if (!DetectConnection.checkInternetConnection(this)) {
            Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
            internetErrorMessage.setVisibility(LinearLayout.VISIBLE);
        } else {
            // else, load the url LMAO
            webView.loadUrl("https://google.com");
            final Handler handler = new Handler();
        }
    }

    // simulate browser's back button on back pressed
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
