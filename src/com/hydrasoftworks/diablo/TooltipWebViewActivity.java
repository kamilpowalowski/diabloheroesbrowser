package com.hydrasoftworks.diablo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;

public class TooltipWebViewActivity extends SherlockActivity {
	private static final String TAG = TooltipWebViewActivity.class
			.getSimpleName();
	private static final String URL_CSS = "http://eu.battle.net/d3/static/css/tooltips.css";
	public static final String URL = "url_key";
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tooltip_dialog);
		String url = getIntent().getStringExtra(URL);

		webView = (WebView) findViewById(R.id.web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new Callback());
		//webView.setPadding(0, 0, 0, 0);
		webView.setInitialScale(getScale());
		webView.setBackgroundColor(getResources().getColor(android.R.color.black));
		
		try {
			new PageLoad().execute(new URL(url));
		} catch (MalformedURLException e) {
			// TODO: error catching
		}
		super.onCreate(savedInstanceState);
	}

	private class Callback extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return (false);
		}

	}
	
	private int getScale(){
	    Display display = ((WindowManager)
	    		getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
	    int width = display.getWidth(); 
	    Double val = Double.valueOf(width)/Double.valueOf(400);
	    val = val * 100d;
	    return val.intValue();
	}

	private class PageLoad extends AsyncTask<java.net.URL, Void, String> {
		@Override
		protected String doInBackground(java.net.URL... params) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						params[0].openStream()));
				StringBuilder sb = new StringBuilder();
				String str;
				while ((str = in.readLine()) != null) {
					sb.append(str);
				}
				in.close();

				return sb.toString().replace("\t", "");
			} catch (Exception ex1) {
				Log.d(TAG, ex1.getMessage());
				return getString(R.string.tooltip_download_error);
			}
		}

		@Override
		protected void onPostExecute(String result) {
			String html = "<html>"
					+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + URL_CSS + "\" />"
					+ "<style type=\"text/css\">"
					+ ".ui-tooltip { background: black; padding: 1px; border: 1px solid #322a20; opacity: 0.95; max-width: 355px;"
					+ "-moz-border-radius: 2px; -webkit-border-radius: 2px; border-radius: 2px;"
					+ "-moz-box-shadow: 0 0 10px #000; -webkit-box-shadow: 0 0 10px #000; box-shadow: 0 0 10px #000;"
					+ "width: 355px;}"
					+ ".ui-tooltip .tooltip-content { background: black; padding: 10px; color: #CFB991; font-size: 12px; }"
					+ ".ui-tooltip-d3 .tooltip-content { padding: 0; }"
					+ ".ui-tooltip .subheader { font-size: 18px; color: #F3E6D0; font-weight: normal; margin-bottom: 4px; } "
					+ "</style>" + "<body><div class=\"ui-tooltip\">" + result
					+ "</div></body></html>";
			webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
			//webView.loadData(html, "text/html", "utf-8");
		}

	}
}
