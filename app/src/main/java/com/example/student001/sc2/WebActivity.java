package com.example.student001.sc2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Nyamura on 2016/02/15.
 */
public class WebActivity extends Activity{

    private static final String TAG = WebActivity.class.getSimpleName();
    private final WebActivity self = this;

    //WebView
    private WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = (WebView)findViewById(R.id.webView);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings();

        mWebView.loadUrl("http://www3.jeed.or.jp/miyagi/college/admission/opencampus.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.return_botton, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_return){
            Intent intent = new Intent(this, Top.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        mWebView.stopLoading();
        ViewGroup webParent = (ViewGroup)mWebView.getParent();
        if(webParent != null){
            webParent.removeView(mWebView);
        }
        mWebView.destroy();
    }

}
