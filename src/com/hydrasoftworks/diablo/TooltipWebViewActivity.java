package com.hydrasoftworks.diablo;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;

public class TooltipWebViewActivity extends SherlockActivity {
	public static final String URL = "url_key";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tooltip_dialog);
		String url = getIntent().getStringExtra(URL);
		WebView webView = (WebView) findViewById(R.id.web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new Callback());
		webView.loadUrl(url);
		super.onCreate(savedInstanceState);
	}
	
    private class Callback extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }

    }
}
