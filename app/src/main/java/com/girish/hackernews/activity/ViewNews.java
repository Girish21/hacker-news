package com.girish.hackernews.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.girish.hackernews.R;

public class ViewNews extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    WebView webView;
    SwipeRefreshLayout refreshLayout;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);


        final Activity activity = this;

        Toolbar toolbar = findViewById(R.id.news_view_toolbar);
        setSupportActionBar(toolbar);

        refreshLayout = findViewById(R.id.news_view_refresh);
        refreshLayout.setOnRefreshListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        url = intent.getStringExtra("URL");

        webView = findViewById(R.id.news_web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAppCacheEnabled(false);


        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(activity, "Some error while fetching data, please try again later", Toast.LENGTH_SHORT).show();
            }
        });

        loadUrl();

    }

    void loadUrl() {
        webView.loadUrl(url);

        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        loadUrl();
    }
}
