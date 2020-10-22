package com.example.flutternativewebview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);

        //Set the WebViewClient that will receive various notifications and requests
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //asynchronously evaluates JavaScript in the context of the currently displayed page
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("javascript:functionFromNative()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            if (!TextUtils.isEmpty(value) && !value.equalsIgnoreCase("null")) {
                                Toast.makeText(MainActivity.this, value, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        //set Chrome handler
        webView.setWebChromeClient(new WebChromeClient() {

        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.addJavascriptInterface(new FlutterWebViewInterface(this), "NativeFlutterInterface");
        webView.loadUrl("https://peaceful-bardeen-f03541.netlify.app/#/");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}