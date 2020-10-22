package com.example.flutternativewebview;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class FlutterWebViewInterface {

    Context context;

    public FlutterWebViewInterface(Context ctx) {
        context = ctx;
    }

    @JavascriptInterface
    public String getString() {
        return "String fetched from Native";
    }
}
