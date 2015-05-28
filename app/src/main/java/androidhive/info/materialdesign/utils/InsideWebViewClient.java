package androidhive.info.materialdesign.utils;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Alex on 26.05.2015.
 */
public class InsideWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}