package com.soyinka.soyombo.currencyratesandbureaudechange;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class TrendsActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);

        webView = findViewById(R.id.web_display);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://fxtop.com/en/historical-exchange-rates.php");
        webView.setWebViewClient(new WebViewController());

    }

    public class WebViewController extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
