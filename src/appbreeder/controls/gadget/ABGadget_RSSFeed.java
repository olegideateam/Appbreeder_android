package appbreeder.controls.gadget;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import appbreeder.activity.BaseActivity;

public class ABGadget_RSSFeed extends ABTabRecord {
	private String rssFeedCanel;

	public static String c_RSSURL = "RSSURL";
	private LayoutInflater mInflater;
	private View currentView;

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

	WebView webView;

	@Override
	public View getTabView(Context context) {
		// if (mInflater == null)
		// mInflater = (LayoutInflater) context
		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// View v = mInflater.inflate(R.layout.webview_layout, null);
		// if (currentView == null) {

		webView = new WebView(context);// (WebView)
		webView.getSettings().setJavaScriptEnabled(true);
		// webView.setWebViewClient(new MyWebViewClient());
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				Log.i("$$$$$$$$$$$$$$$$$$$$$$$$$$$", "progress:" + progress);
				// activity.setProgress(progress * 1000);
				if (progress == 100) {
					STATE_GADGET=STATE_LOADED;
					((BaseActivity) view.getContext())
							.setProgressBarCustomTitleVisibility(false);
				} else
				{
					STATE_GADGET=STATE_LOADING;
					((BaseActivity) view.getContext())
							.setProgressBarCustomTitleVisibility(true);
				}
					// ((BaseActivity)view.getContext()).setProgressBarCustomTitleVisibility(false);

			}
		});
		webView.setWebViewClient(new HelloWebViewClient());
		STATE_GADGET=STATE_LOADING;
		webView.loadUrl(rssFeedCanel);
		// webView.set
		currentView = webView;
		// }
		return currentView;
	}

	@Override
	public void closeGadget() {
		super.closeGadget();
		if (STATE_GADGET == STATE_LOADING) {
			
			webView.cancelLongPress();
			webView.stopLoading();
			webView.destroy();
			((BaseActivity) webView.getContext())
					.setProgressBarCustomTitleVisibility(false);
		}
		STATE_GADGET=STATE_STOPED;
	}

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

	}

}
