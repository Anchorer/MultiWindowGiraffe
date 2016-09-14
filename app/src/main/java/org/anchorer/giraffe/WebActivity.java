package org.anchorer.giraffe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * An Activity with a WebView to load web page.
 * Created by Anchorer on 16/9/14.
 */
public class WebActivity extends AppCompatActivity {

    public static final String FIELD_URL = "url";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = (WebView) findViewById(R.id.wv);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.setOnDragListener(new GiraffeDragEventListener(this));

        Intent mIntent = getIntent();
        String url = mIntent.getStringExtra(FIELD_URL);
        mWebView.loadUrl(url);
    }

}
