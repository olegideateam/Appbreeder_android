package appbreeder.controls.gadget;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ABGadget_RSSFeed extends ABTabRecord {
	private String rssFeedCanel;

	public static String c_RSSURL = "RSSURL";

	public ABGadget_RSSFeed(SQLiteDatabase sqldb, Cursor cur) {
		super(sqldb, cur);

		Cursor cursor = super.getTabelCursor(sqldb, "ABGadget_RSSFeed");
		cursor.moveToFirst();
		rssFeedCanel = super.getValueString(cursor, c_RSSURL);
	}

	public String getRssFeedCanel() {
		return rssFeedCanel;
	}

	public void setRssFeedCanel(String rssFeedCanel) {
		this.rssFeedCanel = rssFeedCanel;
	}

	@Override
	public View getTabView(Context context) {
		WebView webView = new WebView(context);
		webView.getSettings().setJavaScriptEnabled(true);
		// webView.setWebViewClient(new MyWebViewClient());
		webView.setWebViewClient(new HelloWebViewClient());
		webView.loadUrl(rssFeedCanel);
		return webView;
	}
	private class HelloWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}


}
