package com.ushfree.webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    WebView webView;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView)findViewById(R.id.webView);
        final ProgressBar pBar = (ProgressBar)findViewById(R.id.progressBar);
        final RelativeLayout errorLayout = (RelativeLayout)findViewById(R.id.layoutError);
        final Button btnReload = (Button)findViewById(R.id.btnReload);
        webView.loadUrl("https://google.com/");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            pBar.setProgress(newProgress);
            if (newProgress == 100){
                pBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }else{
                pBar.setVisibility(View.VISIBLE);
            }
            }
        });
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.setVisibility(View.GONE);
                errorLayout.setVisibility(View.GONE);
                pBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                errorLayout.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
                pBar.setVisibility(View.GONE);
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
            }
        });

    }
}
