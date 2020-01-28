package com.teamcs.pythondownloader;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;

import com.chaquo.python.Kwarg;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class DownloadView extends AppCompatActivity {
    String current_url = "https://google.com";
    String TAG = "teamcs_log";
    WebView webview_two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));

        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();
        webview_two = (WebView) findViewById(R.id.webview_two);
        String html = "<html><head></head><body><p>Preparing download</p></body></html>";
        webview_two.loadDataWithBaseURL("", html, "text/html", "utf-8", "");
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            current_url = extras.getString("url");
        }

                        String webview_url = current_url; // current_url is always update on page start and finished
                        Python py = Python.getInstance();
                        PyObject youtube_dl = py.getModule("youtube_dl");
                        PyObject ydl = youtube_dl.callAttr("YoutubeDL");
                        boolean tf = false;
                        PyObject result;
                        try{
                            result = ydl.callAttr("extract_info",webview_url, new Kwarg("download",tf));
                        } catch (Exception e){
                            // return could not download
                            Log.i(TAG,"extract_info exception :"+e.toString());

                            html = "<html><head></head><body><p>Cannot Download Now, try again later.</p></body></html>";
                            webview_two.loadDataWithBaseURL("", html, "text/html", "utf-8", "");
                            return;
                        }
                        String video_url = "";
                        try{
                            video_url = result.get("entries").asList().get(0).asMap().get("url").toString();

                        }catch(Exception e){
                            video_url = result.asMap().get("url").toString();
                        }
                        //webView.loadUrl(video_url);
                        DownloadManager.Request request = new DownloadManager.Request(
                                Uri.parse(video_url));
                        final String filename= "video_downloader.mp4";
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                        dm.enqueue(request);
                        Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                                Toast.LENGTH_LONG).show();
                        webview_two.loadUrl(video_url);

//        html = "<html><head></head><body><p>Downloading.... check in notification bar</p></body></html>";
//        webview_two.loadDataWithBaseURL("", html, "text/html", "utf-8", "");

    }

}
