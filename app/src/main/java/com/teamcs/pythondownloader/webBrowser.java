package com.teamcs.pythondownloader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webBrowser extends AppCompatActivity {
    String current_url = "https://google.com";
    WebView webView;
    String loading_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();// hide action bar

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent download_view_intent = new Intent(getBaseContext(),DownloadView.class);
                download_view_intent.putExtra("url",current_url);
                startActivity(download_view_intent);
            }
        });

        // get intent url
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            current_url = extras.getString("url");
        }
        webView = (WebView) findViewById(R.id.webview_one);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        loading_message =  "loading..";// getString(R.string.loading_message);
        final ProgressDialog pd = ProgressDialog.show(this, "", loading_message,true);
        pd.dismiss();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!pd.isShowing()) {
                    pd.show();
                }
                current_url = view.getUrl();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                if(pd!=null && pd.isShowing())
                {
                    pd.dismiss();
                }
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                webView.loadUrl("file:///android_asset/error.html");
                getSupportActionBar().show();

            }

        });




        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                pd.setMessage(loading_message+ " " +progress+" %");
            }
        });


        // load url
        webView.loadUrl(current_url);




    }

}
